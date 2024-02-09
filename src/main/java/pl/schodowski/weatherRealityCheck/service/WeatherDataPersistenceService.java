package pl.schodowski.weatherRealityCheck.service;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import pl.schodowski.weatherRealityCheck.api.AccuWeatherFetcher;
import pl.schodowski.weatherRealityCheck.entity.WeatherForecastEntity;
import pl.schodowski.weatherRealityCheck.model.accuWeather.AccuWeatherPrediction;
import pl.schodowski.weatherRealityCheck.repository.WeatherForecastRepository;

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
            WeatherForecastEntity weatherForecastEntity12H = accuWeatherConversionService.buildEntityBasedPrediction(accuWeatherPredictions.get(11), locationName);
            WeatherForecastEntity weatherForecastEntity2H = accuWeatherConversionService.buildEntityBasedPrediction(accuWeatherPredictions.get(1), locationName);
            weatherForecastRepository.save(weatherForecastEntity12H);
            weatherForecastRepository.save(weatherForecastEntity2H);

            // make list from entities
    }

    private void saveEntities(List<WeatherForecastEntity> weatherForecastEntities){
        for(WeatherForecastEntity weatherForecastEntity : weatherForecastEntities){
            weatherForecastRepository.save(weatherForecastEntity);
        }
    }





}

