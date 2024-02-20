package pl.schodowski.weatherRealityCheck.service;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import pl.schodowski.weatherRealityCheck.entity.WeatherForecastEntity;
import pl.schodowski.weatherRealityCheck.model.LocationTimePair;
import pl.schodowski.weatherRealityCheck.repository.WeatherForecastRepository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class ManageService {

    private final WeatherForecastRepository weatherForecastRepository;
    private final AccuWeatherService accuWeatherService;
    private final MeteoBlueService meteoBlueService;
    private final List<LocationTimePair> locationTimePairs;

    public ManageService(WeatherForecastRepository weatherForecastRepository,
                         AccuWeatherService accuWeatherService,
                         MeteoBlueService meteoBlueService,
                         @Value("${locationNames}") String locationNames,
                         @Value("${predictionTime}") String predictionTime) {
        this.weatherForecastRepository = weatherForecastRepository;
        this.accuWeatherService = accuWeatherService;
        this.meteoBlueService = meteoBlueService;

        this.locationTimePairs = initializeLocationTimePairs(locationNames, predictionTime);
    }

    @PostConstruct
    public void init() {
        processLocationTimePairs();
    }

    private List<LocationTimePair> initializeLocationTimePairs(String locationNames, String predictionTime) {
        String[] locations = locationNames.split(",");
        List<LocationTimePair> pairs = new ArrayList<>();
        for (String location : locations) {
            pairs.add(new LocationTimePair(location, predictionTime));
        }
        return pairs;
    }

    private void processLocationTimePairs() {
        List<WeatherForecastEntity> weatherForecastEntityList = new ArrayList<>();
        for (LocationTimePair pair : locationTimePairs) {
            weatherForecastEntityList.addAll(distributor(pair));
        }
        saveEntities(weatherForecastEntityList);
    }

    private List<WeatherForecastEntity> distributor(LocationTimePair pair) {
        List<WeatherForecastEntity> entities = new ArrayList<>();
        entities.add(accuWeatherService.getEntityFromPrediction(pair.getLocationName(), pair.getPredictionTime()));
        entities.add(meteoBlueService.getEntityFromPrediction(pair.getLocationName(), pair.getPredictionTime()));
        return entities;
    }

    private void saveEntities(List<WeatherForecastEntity> weatherForecastEntities) {
        for (WeatherForecastEntity weatherForecastEntity : weatherForecastEntities) {
            weatherForecastRepository.save(weatherForecastEntity);
        }
    }
}








