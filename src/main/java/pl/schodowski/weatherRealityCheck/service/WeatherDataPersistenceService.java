package pl.schodowski.weatherRealityCheck.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import pl.schodowski.weatherRealityCheck.api.AccuWeatherFetcher;
import pl.schodowski.weatherRealityCheck.entity.AccuWeatherDataEntity;
import pl.schodowski.weatherRealityCheck.model.accuWeather.AccuWeatherPrediction;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
@RequiredArgsConstructor
public class WeatherDataPersistenceService {


    private final AccuWeatherFetcher accuWeatherFetcher;

    @Value("${zakopaneLocationForAccuWeather}")
    private final String zakopaneLocationForAccuWeather;

    public void saveForecastToDatabase(){
        List<AccuWeatherPrediction> accuWeatherPredictionList = accuWeatherFetcher.getAccuWeatherPredictionFromApi(zakopaneLocationForAccuWeather);
        AccuWeatherDataEntity  accuWeatherDataEntity = makeAccuWeatherDataEntityFromAccuWeatherPrediction(accuWeatherPredictionList.get(11));
    }

    private AccuWeatherDataEntity makeAccuWeatherDataEntityFromAccuWeatherPrediction(AccuWeatherPrediction accuWeatherPrediction){
        AccuWeatherDataEntity  accuWeatherDataEntity = new AccuWeatherDataEntity();

        LocalDateTime dateTime = LocalDateTime.parse(accuWeatherPrediction.dateTime, DateTimeFormatter.ISO_OFFSET_DATE_TIME);
        String date = dateTime.toLocalDate().toString();
        String time = String.valueOf(dateTime.getHour());

        accuWeatherDataEntity.setDate(date);
        accuWeatherDataEntity.setTime(time);
        accuWeatherDataEntity.setName("Zakopane");
        accuWeatherDataEntity.setTemperature(accuWeatherPrediction.temperature.value);
        accuWeatherDataEntity.setWind(accuWeatherPrediction.wind.speed.value);
        accuWeatherDataEntity.setRainfallTotal(accuWeatherPrediction.rain.value);  //todo najpierw trzeba określić jaki jest typ opadu i przyporządkować odpowiedni
    }
}
