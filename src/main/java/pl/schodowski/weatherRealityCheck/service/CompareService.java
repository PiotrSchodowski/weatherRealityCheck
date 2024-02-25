package pl.schodowski.weatherRealityCheck.service;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import pl.schodowski.weatherRealityCheck.entity.ImgwWeatherDataEntity;
import pl.schodowski.weatherRealityCheck.entity.WeatherForecastEntity;
import pl.schodowski.weatherRealityCheck.repository.ImgwWeatherDataRepository;
import pl.schodowski.weatherRealityCheck.repository.WeatherForecastRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CompareService {

    private final WeatherForecastRepository weatherForecastRepository;
    private final ImgwWeatherDataRepository imgwWeatherDataRepository;

    @Scheduled(fixedRate = 3600000)
    public void comparePredictionsWithMeasurements() {
        List<WeatherForecastEntity> predictions = weatherForecastRepository.findAll();
        List<ImgwWeatherDataEntity> measurements = imgwWeatherDataRepository.findAll();

        for (WeatherForecastEntity prediction : predictions) {
            for (ImgwWeatherDataEntity measurement : measurements) {
                if (prediction.getDate().equals(measurement.getDate()) &&
                        prediction.getForecastTime() == Float.parseFloat(measurement.getTimeOfMeasurement())) {

                    if (prediction.getTemperature() != measurement.getTemperature()) {
                        float temperatureDifference = prediction.getTemperature() - measurement.getTemperature();
                        System.out.println("Roznica w temperaturze dla prognozy " + prediction.getName() + "(" +
                                 prediction.getSource() + ")" +
                                " o godzinie " + prediction.getForecastTime() + " wynosi: " +
                                temperatureDifference);
                    }
                }
            }
        }
    }
}

