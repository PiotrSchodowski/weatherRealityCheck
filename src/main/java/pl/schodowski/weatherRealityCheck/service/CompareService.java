package pl.schodowski.weatherRealityCheck.service;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import pl.schodowski.weatherRealityCheck.entity.DifferenceForMeasurementsEntity;
import pl.schodowski.weatherRealityCheck.entity.ImgwWeatherDataEntity;
import pl.schodowski.weatherRealityCheck.entity.WeatherForecastEntity;
import pl.schodowski.weatherRealityCheck.repository.DiffForMeasurementsRepository;
import pl.schodowski.weatherRealityCheck.repository.ImgwWeatherDataRepository;
import pl.schodowski.weatherRealityCheck.repository.WeatherForecastRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CompareService {

    private final WeatherForecastRepository weatherForecastRepository;
    private final ImgwWeatherDataRepository imgwWeatherDataRepository;
    private final DiffForMeasurementsRepository diffForMeasurementsRepository;

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

                        DifferenceForMeasurementsEntity diffEntity = getDifferenceForMeasurementsEntity(prediction, measurement, temperatureDifference);
                        diffForMeasurementsRepository.save(diffEntity);

                        System.out.println("Roznica w temperaturze dla prognozy " + prediction.getName() + "(" +
                                prediction.getSource() + ")" +
                                " o godzinie " + prediction.getForecastTime() + " wynosi: " +
                                temperatureDifference);
                    }
                }
            }
        }
    }

    private static DifferenceForMeasurementsEntity getDifferenceForMeasurementsEntity(WeatherForecastEntity prediction, ImgwWeatherDataEntity measurement, float temperatureDifference) {
        DifferenceForMeasurementsEntity diffEntity = new DifferenceForMeasurementsEntity();
        diffEntity.setDate(prediction.getDate());
        diffEntity.setSource(prediction.getSource());
        diffEntity.setTemperatureDifference(temperatureDifference);
        diffEntity.setTemperatureForecast(prediction.getTemperature());
        diffEntity.setTemperatureMeasurement(measurement.getTemperature());
        diffEntity.setTime(prediction.getForecastTime());
        return diffEntity;
    }
}
