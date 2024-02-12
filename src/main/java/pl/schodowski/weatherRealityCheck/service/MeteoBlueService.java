package pl.schodowski.weatherRealityCheck.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import pl.schodowski.weatherRealityCheck.api.MeteoBlueFetcher;
import pl.schodowski.weatherRealityCheck.entity.WeatherForecastEntity;
import pl.schodowski.weatherRealityCheck.model.meteoBlue.MeteoBluePrediction;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
@RequiredArgsConstructor
public class MeteoBlueService {

    @Value("${zakopaneLat}")
    private String zakopaneLat;

    @Value("${zakopaneLon}")
    private String zakopaneLon;

    @Value("${bielskoLat}")
    private String bielskoLat;

    @Value("${bielskoLat}")
    private String bielskoLon;

    private final MeteoBlueFetcher meteoBlueFetcher;

    public WeatherForecastEntity getEntityFromPrediction(String locationName, String predictionTime) {


        if ("Zakopane".equals(locationName)) {
            return buildEntityBasedPrediction(meteoBlueFetcher.getMeteoBluePredictionFromApi(zakopaneLon, zakopaneLat), locationName, predictionTime);
        } else {
            return buildEntityBasedPrediction(meteoBlueFetcher.getMeteoBluePredictionFromApi(bielskoLon, zakopaneLat), locationName, predictionTime);
        }

    }


    WeatherForecastEntity buildEntityBasedPrediction(MeteoBluePrediction meteoBluePrediction, String locationName, String predictionTime) {
        int predictionTimeInt = Integer.parseInt(predictionTime);

        if (predictionTimeInt < 0 || predictionTimeInt >= meteoBluePrediction.data1h.time.size()) {
            throw new IllegalArgumentException("Nieprawidłowy czas prognozy. Upewnij się, że mieści się on w zakresie prognozy.");
        }

        LocalDateTime currentDateTime = LocalDateTime.now();
        LocalDateTime predictionDateTime = currentDateTime.plusHours(predictionTimeInt - 1);
        String forecastDate = predictionDateTime.toLocalDate().toString();
        int forecastTime = predictionDateTime.getHour();

        WeatherForecastEntity weatherForecastEntity = new WeatherForecastEntity();
        weatherForecastEntity.setDate(forecastDate);
        weatherForecastEntity.setForecastTime(forecastTime);
        weatherForecastEntity.setName(locationName);
        weatherForecastEntity.setTemperature(meteoBluePrediction.data1h.temperature.get(predictionTimeInt));
        weatherForecastEntity.setWind(meteoBluePrediction.data1h.windSpeed.get(predictionTimeInt).floatValue());
        weatherForecastEntity.setIntervalTime(predictionTimeInt);
        weatherForecastEntity.setSource("meteoBlue");
        weatherForecastEntity.setRainfallTotal(meteoBluePrediction.data1h.getConvective_precipitation().get(predictionTimeInt));

        return weatherForecastEntity;
    }



}
