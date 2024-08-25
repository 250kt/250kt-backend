package fr.gofly.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import fr.gofly.dto.MetarDto;
import fr.gofly.dto.TafDto;
import fr.gofly.dto.WeatherDto;
import fr.gofly.helper.WeatherHelper;
import fr.gofly.mapper.MetarDtoMapper;
import fr.gofly.mapper.WeatherDtoMapper;
import fr.gofly.model.Metar;
import fr.gofly.model.Taf;
import fr.gofly.repository.MetarRepository;
import fr.gofly.repository.TafRepository;
import fr.gofly.response.MetarResponse;
import fr.gofly.response.TafResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class WeatherService {

    private final RestTemplate restTemplate;
    private final MetarRepository metarRepository;
    private final TafRepository tafRepository;
    private final WeatherHelper weatherHelper;
    private final WeatherDtoMapper weatherDtoMapper;


    @Value("${weather-api.url}")
    private String apiUrl;

    @Value("${weather-api.key}")
    private String apiKey;

    public WeatherDto getAirfieldWeather(String airfieldCode) {
        Metar metar = getMetar(airfieldCode);
        Taf taf = getTaf(airfieldCode);

        return weatherDtoMapper.map(airfieldCode, taf, metar);
    }

    public Taf getTaf(String airfieldCode) {
        Optional<Taf> tafOptional = tafRepository.findByAirfieldCode(airfieldCode);

        if (tafOptional.isPresent()) {
            return handleExistingTaf(tafOptional.get(), airfieldCode);
        } else {
            return handleNewTaf(airfieldCode);
        }
    }

    public Metar getMetar(String airfieldCode) {
        Optional<Metar> metarOptional = metarRepository.findByAirfieldCode(airfieldCode);

        if (metarOptional.isPresent()) {
            return handleExistingMetar(metarOptional.get(), airfieldCode);
        } else {
            return handleNewMetar(airfieldCode);
        }
    }

    private Metar handleExistingMetar(Metar metar, String airfieldCode) {
        if (weatherHelper.isMetarExpired(metar.getPublishedAt())) {
            String metarData = getMetarFromApi(airfieldCode);
            if (metarData.isEmpty()) {
                return null;
            }
            metar.setData(metarData);
            metar.setPublishedAt(weatherHelper.parsePublishedAt(metarData.split(" ")[1]));
            return metarRepository.save(metar);
        } else {
            return metar;
        }
    }

    private Taf handleExistingTaf(Taf taf, String airfieldCode) {
        if (weatherHelper.isTafExpired(taf.getPublishedAt())) {
            String tafData = getTafFromApi(airfieldCode);
            if (tafData.isEmpty()) {
                return null;
            }
            tafData = weatherHelper.removeTafIfIsPresent(tafData);
            taf.setData(tafData);
            taf.setPublishedAt(weatherHelper.parsePublishedAt(tafData.split(" ")[1]));
            return tafRepository.save(taf);
        } else {
            return taf;
        }
    }

    private Metar handleNewMetar(String airfieldCode) {
        String metarData = getMetarFromApi(airfieldCode);
        if (metarData.isEmpty()) {
            return null;
        }
        Metar newMetar = Metar.builder()
                .airfieldCode(airfieldCode)
                .publishedAt(weatherHelper.parsePublishedAt(metarData.split(" ")[1]))
                .data(metarData)
                .build();
        return metarRepository.save(newMetar);
    }

    private Taf handleNewTaf(String airfieldCode) {
        String tafData = getTafFromApi(airfieldCode);
        if (tafData.isEmpty()) {
            return null;
        }
        tafData = weatherHelper.removeTafIfIsPresent(tafData);
        Taf newTaf = Taf.builder()
                .airfieldCode(airfieldCode)
                .publishedAt(weatherHelper.parsePublishedAt(tafData.split(" ")[1]))
                .data(tafData)
                .build();
        return tafRepository.save(newTaf);
    }

    private String getMetarFromApi(String airfieldCode) {
        String url = buildMetarUrl(airfieldCode);
        HttpHeaders headers = createHeaders();
        HttpEntity<String> entity = new HttpEntity<>(headers);
        ResponseEntity<String> responseEntity = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);
        return handleMetarResponse(responseEntity, airfieldCode);
    }

    private String getTafFromApi(String airfieldCode) {
        String url = buildTafUrl(airfieldCode);
        HttpHeaders headers = createHeaders();
        HttpEntity<String> entity = new HttpEntity<>(headers);
        ResponseEntity<String> responseEntity = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);
        return handleTafResponse(responseEntity, airfieldCode);
    }

    private String buildMetarUrl(String airfieldCode) {
        return apiUrl + "/metar/" + airfieldCode;
    }

    private String buildTafUrl(String airfieldCode) {
        return apiUrl + "/taf/" + airfieldCode;
    }

    private HttpHeaders createHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.set("x-api-key", apiKey);
        return headers;
    }

    private String handleMetarResponse(ResponseEntity<String> responseEntity, String airfieldCode) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            MetarResponse metarResponse = objectMapper.readValue(responseEntity.getBody(), MetarResponse.class);
            if (metarResponse.getResults() == 0) {
                log.error("No Metar found for airfieldCode: {}", airfieldCode);
                return "";
            }
            return metarResponse.getData().get(0);
        } catch (Exception e) {
            log.error("Error while parsing MetarResponse", e);
            return "";
        }
    }

    private String handleTafResponse(ResponseEntity<String> responseEntity, String airfieldCode) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            TafResponse tafResponse = objectMapper.readValue(responseEntity.getBody(), TafResponse.class);
            if (tafResponse.getResults() == 0) {
                log.error("No TAF found for airfieldCode: {}", airfieldCode);
                return "";
            }
            return tafResponse.getData().get(0);
        } catch (Exception e) {
            log.error("Error while parsing MetarResponse", e);
            return "";
        }
    }
}
