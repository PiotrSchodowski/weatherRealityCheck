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
    List<WeatherForecastEntity> weatherForecastEntityList = new ArrayList<>();

    public ManageService(@Value("${locationName1}") String locationName1,
                         @Value("${locationName2}") String locationName2,
                         @Value("${predictionTime1}") String predictionTime1,
                         @Value("${predictionTime2}") String predictionTime2,
                         WeatherForecastRepository weatherForecastRepository,
                         AccuWeatherService accuWeatherService,MeteoBlueService meteoBlueService){
        this.locationTimePairs = Arrays.asList(
                new LocationTimePair(locationName1, predictionTime1),
                new LocationTimePair(locationName2, predictionTime2),
                new LocationTimePair(locationName1, predictionTime2),
                new LocationTimePair(locationName2, predictionTime1)
        );
        this.weatherForecastRepository = weatherForecastRepository;
        this.accuWeatherService = accuWeatherService;
        this.meteoBlueService = meteoBlueService;
    }

    @PostConstruct
    public void init(){
        for (LocationTimePair pair : locationTimePairs) {
            weatherForecastEntityList.addAll(distributor(pair));
            saveEntities(weatherForecastEntityList);
        }
    }


    private List<WeatherForecastEntity> distributor(LocationTimePair pair) {
        weatherForecastEntityList.add(accuWeatherService.getEntityFromPrediction(pair.getLocationName(), pair.getPredictionTime()));
//        weatherForecastEntityList.add(meteoBlueService.getEntityFromPrediction(pair.getLocationName(), pair.getPredictionTime())); // limit calls
        return weatherForecastEntityList;
    }


    private void saveEntities(List<WeatherForecastEntity> weatherForecastEntities) {
        for (WeatherForecastEntity weatherForecastEntity : weatherForecastEntities) {
            weatherForecastRepository.save(weatherForecastEntity);
        }
    }
}
