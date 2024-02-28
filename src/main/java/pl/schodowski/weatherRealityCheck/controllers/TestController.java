package pl.schodowski.weatherRealityCheck.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pl.schodowski.weatherRealityCheck.api.AccuWeatherFetcher;
import pl.schodowski.weatherRealityCheck.dto.accuWeather.AccuWeatherPrediction;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class TestController {

    private final AccuWeatherFetcher accuWeatherFetcher;

    @GetMapping("/test")
    public List<AccuWeatherPrediction> getPredictionFor12Hour(@RequestParam String locationKey) {
        return accuWeatherFetcher.getAccuWeatherPredictionFromApi(locationKey);
    }
}
