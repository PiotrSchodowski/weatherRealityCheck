package pl.schodowski.weatherRealityCheck.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.schodowski.weatherRealityCheck.dto.DifferenceParameters;
import pl.schodowski.weatherRealityCheck.entity.DifferenceForMeasurementsEntity;
import pl.schodowski.weatherRealityCheck.entity.ImgwWeatherDataEntity;
import pl.schodowski.weatherRealityCheck.entity.WeatherForecastEntity;
import pl.schodowski.weatherRealityCheck.repository.DiffForMeasurementsRepository;
import pl.schodowski.weatherRealityCheck.repository.ImgwWeatherDataRepository;
import pl.schodowski.weatherRealityCheck.repository.WeatherForecastRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class WeatherComparisonService {

    private final WeatherForecastRepository weatherForecastRepository;
    private final ImgwWeatherDataRepository imgwWeatherDataRepository;
    private final DiffForMeasurementsRepository diffForMeasurementsRepository;

    public void comparePredictionsWithMeasurements() {
        List<WeatherForecastEntity> predictions = weatherForecastRepository.findAll();
        List<ImgwWeatherDataEntity> measurements = imgwWeatherDataRepository.findAll();

        for (WeatherForecastEntity prediction : predictions) {
            for (ImgwWeatherDataEntity measurement : measurements) {
                comparePredictionWithMeasurement(prediction, measurement);
            }
        }
    }

    private void comparePredictionWithMeasurement(WeatherForecastEntity prediction, ImgwWeatherDataEntity measurement) {
        if (prediction.getDate().equals(measurement.getDate()) &&
                prediction.getForecastTime() == Float.parseFloat(measurement.getTimeOfMeasurement())) {
            comparePredictionNameLength(prediction, measurement);
        }
    }

    private void comparePredictionNameLength(WeatherForecastEntity prediction, ImgwWeatherDataEntity measurement) {
        if (prediction.getName().length() == measurement.getName().length() ||
                prediction.getName().length() == measurement.getName().length() - 1) {
            calculateAndSaveDifference(prediction, measurement);
        }
    }

    private void calculateAndSaveDifference(WeatherForecastEntity prediction, ImgwWeatherDataEntity measurement) {
        DifferenceParameters differenceParameters = new DifferenceParameters();
        differenceParameters.setTemperature(prediction.getTemperature() - measurement.getTemperature());
        differenceParameters.setWind(prediction.getWind() - measurement.getWind());
        differenceParameters.setRainfallTotal(prediction.getRainfallTotal() - measurement.getRainfallTotal());

        DifferenceForMeasurementsEntity diffEntity = createDifferenceEntity(prediction, measurement, differenceParameters);
        diffForMeasurementsRepository.save(diffEntity);
    }

    private DifferenceForMeasurementsEntity createDifferenceEntity(WeatherForecastEntity prediction, ImgwWeatherDataEntity measurement, DifferenceParameters differenceParameters) {
        DifferenceForMeasurementsEntity diffEntity = new DifferenceForMeasurementsEntity();
        diffEntity.setName(prediction.getName());
        diffEntity.setDate(prediction.getDate());
        diffEntity.setSource(prediction.getSource());
        diffEntity.setTemperatureDifference(differenceParameters.getTemperature());
        diffEntity.setTime(prediction.getForecastTime());
        return diffEntity;
    }

}
