package pl.schodowski.weatherRealityCheck.service;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import pl.schodowski.weatherRealityCheck.api.AccuWeatherFetcher;
import pl.schodowski.weatherRealityCheck.entity.AccuWeatherDataEntity;
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
    public void saveForecastToDatabase() {
        saveForecastForLocation(zakopaneLocationKey, "Zakopane");
        saveForecastForLocation(bielskoLocationKey, "Bielsko-Biała");
        saveForecastForLocation(korbielowLocationKey, "Korbielów");
    }


    private void saveForecastForLocation(String locationKey, String locationName) {
        List<AccuWeatherPrediction> accuWeatherPredictions = accuWeatherFetcher.getAccuWeatherPredictionFromApi(locationKey);
            AccuWeatherDataEntity accuWeatherDataEntity12h = makeAccuWeatherDataEntityFromAccuWeatherPrediction(accuWeatherPredictions.get(11), locationName);
            AccuWeatherDataEntity accuWeatherDataEntity2h = makeAccuWeatherDataEntityFromAccuWeatherPrediction(accuWeatherPredictions.get(1), locationName);
            accuWeatherDataRepository.save(accuWeatherDataEntity12h);
            accuWeatherDataRepository.save(accuWeatherDataEntity2h);
    }


    private AccuWeatherDataEntity makeAccuWeatherDataEntityFromAccuWeatherPrediction(AccuWeatherPrediction accuWeatherPrediction, String locationName) {
        AccuWeatherDataEntity accuWeatherDataEntity = new AccuWeatherDataEntity();
        LocalDateTime dateTime = LocalDateTime.parse(accuWeatherPrediction.dateTime, DateTimeFormatter.ISO_OFFSET_DATE_TIME);
        String date = dateTime.toLocalDate().toString();
        int time = dateTime.getHour();

        accuWeatherDataEntity.setDate(date);
        accuWeatherDataEntity.setTimeOfForecast(time);
        accuWeatherDataEntity.setName(locationName);
        accuWeatherDataEntity.setTemperature(accuWeatherPrediction.temperature.value);
        accuWeatherDataEntity.setWind(convertKmhToMs(accuWeatherPrediction.wind.speed.value));
        accuWeatherDataEntity.setIntervalTime(calculateIntervalTime(dateTime));
        setRainfallTotal(accuWeatherDataEntity, accuWeatherPrediction);

        return accuWeatherDataEntity;
    }


    private float calculateIntervalTime(LocalDateTime forecastDateTime) {
        LocalDateTime nowDateTime = LocalDateTime.now();
        float intervalTime = forecastDateTime.getHour() - nowDateTime.getHour();
        return Math.abs(intervalTime);
    }


    private void setRainfallTotal(AccuWeatherDataEntity accuWeatherDataEntity, AccuWeatherPrediction accuWeatherPrediction) {
        if (accuWeatherPrediction.hasPrecipitation) {
            if (accuWeatherPrediction.precipitationType.equals("Rain")) {
                accuWeatherDataEntity.setRainfallTotal(accuWeatherPrediction.rain.value);
            } else if (accuWeatherPrediction.precipitationType.equals("Snow")) {
                accuWeatherDataEntity.setRainfallTotal(accuWeatherPrediction.snow.value);
            }
        }
    }

    private long convertKmhToMs(float kmh) {
        return Math.round(kmh * (1000.0f / 3600.0f));
    }


}

