package fr.gofly.mapper;

import fr.gofly.dto.WeatherDto;
import fr.gofly.model.Metar;
import fr.gofly.model.Taf;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class WeatherDtoMapper {

    private final MetarDtoMapper metarDtoMapper;
    private final TafDtoMapper tafDtoMapper;

    public WeatherDto map(String airfieldCode, Taf taf, Metar metar) {
        WeatherDto weatherDto = new WeatherDto();
        weatherDto.setAirfieldCode(airfieldCode);
        if(taf != null) {
            weatherDto.setTaf(tafDtoMapper.map(taf));
        }else{
            weatherDto.setTaf(null);
        }
        if(metar != null) {
            weatherDto.setMetar(metarDtoMapper.map(metar));
        }else{
            weatherDto.setMetar(null);
        }
        return weatherDto;
    }
}
