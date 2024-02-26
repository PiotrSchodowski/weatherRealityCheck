package pl.schodowski.weatherRealityCheck.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.schodowski.weatherRealityCheck.entity.WeatherForecastEntity;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class GoogleService {

    private final ScrapperGoogleForecast scrapperGoogleForecast;

    public WeatherForecastEntity getEntityFromPrediction(String locationName, String predictionTime) {  //nie uzywane bo nie mam sposobu na pobranie NIE aktualnej godziny prognozy

        WeatherForecastEntity weatherForecastEntity = new WeatherForecastEntity();

        LocalDateTime currentDateTime = LocalDateTime.now();

        weatherForecastEntity.setSource("google");
        weatherForecastEntity.setForecastTime(currentDateTime.getHour() + 1);
        weatherForecastEntity.setDate(currentDateTime.toLocalDate().toString());
        weatherForecastEntity.setIntervalTime(1);
        weatherForecastEntity.setName(locationName);
        weatherForecastEntity.setTemperature(Float.parseFloat(scrapperGoogleForecast.downloadForecastForLocation(locationName, predictionTime)));


        return weatherForecastEntity;

    }
}
