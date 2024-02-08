package pl.schodowski.weatherRealityCheck.service;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import pl.schodowski.weatherRealityCheck.api.AccuWeatherFetcher;
import pl.schodowski.weatherRealityCheck.entity.WeatherForecastEntity;
import pl.schodowski.weatherRealityCheck.model.accuWeather.AccuWeatherPrediction;
import pl.schodowski.weatherRealityCheck.repository.AccuWeatherDataRepository;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class WeatherDataPersistenceService {

    private final AccuWeatherFetcher accuWeatherFetcher;
    private final AccuWeatherDataRepository accuWeatherDataRepository;
    private final String zakopaneLocationKey;
    private final String bielskoLocationKey;
    private final String korbielowLocationKey;
    private static final String CRON_SCHEDULE = "0 */5 * * * *";

    public WeatherDataPersistenceService(AccuWeatherFetcher accuWeatherFetcher, AccuWeatherDataRepository accuWeatherDataRepository,
                                         @Value("${zakopaneLocationForAccuWeather}") String zakopaneLocationKey,
                                         @Value("${bielskoLocationForAccuWeather}") String bielskoLocationKey,
                                         @Value("${korbielowLocationForAccuWeather}") String korbielowLocationKey) {
        this.accuWeatherFetcher = accuWeatherFetcher;
        this.accuWeatherDataRepository = accuWeatherDataRepository;
        this.zakopaneLocationKey = zakopaneLocationKey;
        this.bielskoLocationKey = bielskoLocationKey;
        this.korbielowLocationKey = korbielowLocationKey;
    }

    @PostConstruct
    public void enterLocationToSaving() {
        getPredictionsForRecording(zakopaneLocationKey, "Zakopane");
        getPredictionsForRecording(bielskoLocationKey, "Bielsko-Biała");
    }


    private void getPredictionsForRecording(String locationKey, String locationName) {
        List<AccuWeatherPrediction> accuWeatherPredictions = accuWeatherFetcher.getAccuWeatherPredictionFromApi(locationKey);
            WeatherForecastEntity weatherForecastEntity12H = buildEntityBasedPrediction(accuWeatherPredictions.get(11), locationName);
            WeatherForecastEntity weatherForecastEntity2H = buildEntityBasedPrediction(accuWeatherPredictions.get(1), locationName);
            accuWeatherDataRepository.save(weatherForecastEntity12H);
            accuWeatherDataRepository.save(weatherForecastEntity2H);
    }


    private WeatherForecastEntity buildEntityBasedPrediction(AccuWeatherPrediction accuWeatherPrediction, String locationName) {
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

