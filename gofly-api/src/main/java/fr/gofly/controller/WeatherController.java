package fr.gofly.controller;

import fr.gofly.dto.MetarDto;
import fr.gofly.dto.WeatherDto;
import fr.gofly.service.WeatherService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@Slf4j
@RestController
@RequestMapping("/api/weather")
@RequiredArgsConstructor
public class WeatherController {

    private final WeatherService weatherService;

    @GetMapping("/{airfieldCode}")
    public ResponseEntity<WeatherDto> getAirfieldWeather(@PathVariable String airfieldCode) {
        WeatherDto weather = weatherService.getAirfieldWeather(airfieldCode);
        return new ResponseEntity<>(weather, HttpStatus.OK);
    }
}
