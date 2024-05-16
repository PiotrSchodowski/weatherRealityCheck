package pl.schodowski.weatherRealityCheck.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import pl.schodowski.weatherRealityCheck.api.MeteoBlueFetcher;
import pl.schodowski.weatherRealityCheck.entity.WeatherForecastEntity;
import pl.schodowski.weatherRealityCheck.dto.meteoBlue.MeteoBluePrediction;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class MeteoBlueService {

    @Value("${zakopaneLat}")
    private String zakopaneLat;

    @Value("${zakopaneLon}")
    private String zakopaneLon;

    @Value("${bielskoLat}")
    private String bielskoLat;

    @Value("${bielskoLon}")
    private String bielskoLon;

    @Value("${laziskaLat}")
    private String laziskaLat;

    @Value("${laziskaLon}")
    private String laziskaLon;

    private final MeteoBlueFetcher meteoBlueFetcher;

    public WeatherForecastEntity getEntityFromPrediction(String locationName, String predictionTime) {
        String lat, lon;

        if ("Poronin".equals(locationName)) {
            lat = zakopaneLat;
            lon = zakopaneLon;
        } else {
            lat = laziskaLat;
            lon = laziskaLon;
        }

        MeteoBluePrediction prediction = meteoBlueFetcher.getMeteoBluePredictionFromApi(lon, lat);
        return buildEntityBasedPrediction(prediction, locationName, predictionTime);
    }

    private WeatherForecastEntity buildEntityBasedPrediction(MeteoBluePrediction prediction, String locationName, String predictionTime) {
        int predictionTimeInt = Integer.parseInt(predictionTime);

        if (predictionTimeInt < 0 || predictionTimeInt >= prediction.data1h.time.size()) {
            throw new IllegalArgumentException("Invalid prediction time. Make sure it is within the prediction range.");
        }

        LocalDateTime currentDateTime = LocalDateTime.now();
        LocalDateTime predictionDateTime = currentDateTime.plusHours(predictionTimeInt);
        String forecastDate = predictionDateTime.toLocalDate().toString();
        int forecastTime = predictionDateTime.getHour();

        WeatherForecastEntity weatherForecastEntity = new WeatherForecastEntity();
        weatherForecastEntity.setDate(forecastDate);
        weatherForecastEntity.setForecastTime(forecastTime);
        weatherForecastEntity.setName(locationName);
        weatherForecastEntity.setTemperature(prediction.data1h.temperature.get(forecastTime));
        weatherForecastEntity.setWind(prediction.data1h.windspeed.get(forecastTime));
        weatherForecastEntity.setIntervalTime(predictionTimeInt);
        weatherForecastEntity.setSource("meteoBlue");
        weatherForecastEntity.setRainfallTotal(prediction.data1h.getConvective_precipitation().get(forecastTime));

        return weatherForecastEntity;
    }
}
