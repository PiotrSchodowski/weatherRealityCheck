package pl.schodowski.weatherRealityCheck.service;

import org.springframework.stereotype.Service;
import pl.schodowski.weatherRealityCheck.entity.WeatherForecastEntity;
import pl.schodowski.weatherRealityCheck.model.accuWeather.AccuWeatherPrediction;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
public class AccuWeatherConversionService {

    WeatherForecastEntity buildEntityBasedPrediction(AccuWeatherPrediction accuWeatherPrediction, String locationName) {
        WeatherForecastEntity weatherForecastEntity = new WeatherForecastEntity();
        LocalDateTime dateTime = LocalDateTime.parse(accuWeatherPrediction.dateTime, DateTimeFormatter.ISO_OFFSET_DATE_TIME);
        String date = dateTime.toLocalDate().toString();
        int time = dateTime.getHour();

        weatherForecastEntity.setDate(date);
        weatherForecastEntity.setForecastTime(time);
        weatherForecastEntity.setName(locationName);
        weatherForecastEntity.setTemperature(accuWeatherPrediction.temperature.value);
        weatherForecastEntity.setWind(convertKmhToMs(accuWeatherPrediction.wind.speed.value));
        weatherForecastEntity.setIntervalTime(calculateIntervalTime(dateTime));
        setRainfallTotal(weatherForecastEntity, accuWeatherPrediction);

        return weatherForecastEntity;
    }


    private float calculateIntervalTime(LocalDateTime forecastDateTime) {
        LocalDateTime nowDateTime = LocalDateTime.now();
        float intervalTime = forecastDateTime.getHour() - nowDateTime.getHour();
        return Math.abs(intervalTime);
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
