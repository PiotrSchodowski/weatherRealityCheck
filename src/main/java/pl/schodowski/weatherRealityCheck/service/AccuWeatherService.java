package pl.schodowski.weatherRealityCheck.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import pl.schodowski.weatherRealityCheck.api.AccuWeatherFetcher;
import pl.schodowski.weatherRealityCheck.entity.WeatherForecastEntity;
import pl.schodowski.weatherRealityCheck.dto.accuWeather.AccuWeatherPrediction;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AccuWeatherService {

    private final AccuWeatherFetcher accuWeatherFetcher;

    @Value("${zakopaneLocationForAccuWeather}")
    private String locationKeyForZakopane;

    @Value("${bielskoLocationForAccuWeather}")
    private String locationKeyForBielsko;

    public WeatherForecastEntity getEntityFromPrediction(String locationName, String predictionTime) {
        String locationKey = getLocationKey(locationName);
        List<AccuWeatherPrediction> accuWeatherPredictions = accuWeatherFetcher.getAccuWeatherPredictionFromApi(locationKey);
        int predictionTimeInt = Integer.parseInt(predictionTime);
        return buildEntityBasedOnPrediction(accuWeatherPredictions.get(predictionTimeInt - 1), locationName);
    }


    private String getLocationKey(String locationName) {
        return "Zakopane".equals(locationName) ? locationKeyForZakopane : locationKeyForBielsko;
    }


    private WeatherForecastEntity buildEntityBasedOnPrediction(AccuWeatherPrediction accuWeatherPrediction, String locationName) {
        LocalDateTime dateTime = LocalDateTime.parse(accuWeatherPrediction.dateTime, DateTimeFormatter.ISO_OFFSET_DATE_TIME);
        WeatherForecastEntity weatherForecastEntity = new WeatherForecastEntity();
        weatherForecastEntity.setDate(dateTime.toLocalDate().toString());
        weatherForecastEntity.setForecastTime(dateTime.getHour());
        weatherForecastEntity.setName(locationName);
        weatherForecastEntity.setTemperature(accuWeatherPrediction.temperature.value);
        weatherForecastEntity.setWind(convertKmhToMs(accuWeatherPrediction.wind.speed.value));
        weatherForecastEntity.setIntervalTime(calculateIntervalTime(dateTime));
        weatherForecastEntity.setSource("AccuWeather");
        setRainfallTotal(weatherForecastEntity, accuWeatherPrediction);
        return weatherForecastEntity;
    }


    private float calculateIntervalTime(LocalDateTime forecastDateTime) {
        return Math.abs(forecastDateTime.getHour() - LocalDateTime.now().getHour());
    }


    private void setRainfallTotal(WeatherForecastEntity weatherForecastEntity, AccuWeatherPrediction accuWeatherPrediction) {
        if (accuWeatherPrediction.hasPrecipitation) {
            if (accuWeatherPrediction.precipitationType.equals("Rain")) {
                weatherForecastEntity.setRainfallTotal(accuWeatherPrediction.rain.value);
            } else if (accuWeatherPrediction.precipitationType.equals("Snow")) {
                weatherForecastEntity.setRainfallTotal(accuWeatherPrediction.snow.value);
            }
        }
    }


    private long convertKmhToMs(float kmh) {
        return Math.round(kmh * (1000.0f / 3600.0f));
    }
}
