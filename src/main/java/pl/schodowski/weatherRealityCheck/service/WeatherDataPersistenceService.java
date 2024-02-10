package pl.schodowski.weatherRealityCheck.service;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import pl.schodowski.weatherRealityCheck.api.AccuWeatherFetcher;
import pl.schodowski.weatherRealityCheck.entity.WeatherForecastEntity;
import pl.schodowski.weatherRealityCheck.model.accuWeather.AccuWeatherPrediction;
import pl.schodowski.weatherRealityCheck.repository.WeatherForecastRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class WeatherDataPersistenceService {

    private final AccuWeatherFetcher accuWeatherFetcher;
    private final WeatherForecastRepository weatherForecastRepository;

    private final AccuWeatherConversionService accuWeatherConversionService;
    private final String zakopaneLocationKey;
    private final String bielskoLocationKey;
    private static final String CRON_SCHEDULE = "0 */5 * * * *";

    public WeatherDataPersistenceService(AccuWeatherFetcher accuWeatherFetcher, WeatherForecastRepository weatherForecastRepository,
                                         @Value("${zakopaneLocationForAccuWeather}") String zakopaneLocationKey,
                                         @Value("${bielskoLocationForAccuWeather}") String bielskoLocationKey,
                                         AccuWeatherConversionService accuWeatherConversionService) {
        this.accuWeatherFetcher = accuWeatherFetcher;
        this.weatherForecastRepository = weatherForecastRepository;
        this.zakopaneLocationKey = zakopaneLocationKey;
        this.bielskoLocationKey = bielskoLocationKey;
        this.accuWeatherConversionService = accuWeatherConversionService;
    }


    @PostConstruct
    public void enterLocationToSaving() {
        getPredictionsForRecording(zakopaneLocationKey, "Zakopane");
        getPredictionsForRecording(bielskoLocationKey, "Bielsko-Biała");
    }


    private void getPredictionsForRecording(String locationKey, String locationName) {
        List<AccuWeatherPrediction> accuWeatherPredictions = accuWeatherFetcher.getAccuWeatherPredictionFromApi(locationKey);
        List<WeatherForecastEntity> weatherForecastEntityList = new ArrayList<>();

        weatherForecastEntityList.add(accuWeatherConversionService.buildEntityBasedPrediction(accuWeatherPredictions.get(11), locationName));
        weatherForecastEntityList.add(accuWeatherConversionService.buildEntityBasedPrediction(accuWeatherPredictions.get(1), locationName));

        saveEntities(weatherForecastEntityList);

    }

    private void saveEntities(List<WeatherForecastEntity> weatherForecastEntities) {
        for (WeatherForecastEntity weatherForecastEntity : weatherForecastEntities) {
            weatherForecastRepository.save(weatherForecastEntity);
        }
    }


}

