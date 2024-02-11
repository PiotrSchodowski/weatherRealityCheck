package pl.schodowski.weatherRealityCheck.service;

import org.springframework.stereotype.Service;
import pl.schodowski.weatherRealityCheck.entity.WeatherForecastEntity;

@Service
public class AccuWeatherService {

    public WeatherForecastEntity getEntityFromPrediction(String locationName, String predictionTime){
        System.out.println(locationName + " " + predictionTime);
        return new WeatherForecastEntity();
    }
}
