package pl.schodowski.weatherRealityCheck.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.schodowski.weatherRealityCheck.entity.DifferenceForMeasurementsEntity;
import pl.schodowski.weatherRealityCheck.entity.ImgwWeatherDataEntity;
import pl.schodowski.weatherRealityCheck.entity.PredictionPointsEntity;
import pl.schodowski.weatherRealityCheck.entity.WeatherForecastEntity;
import pl.schodowski.weatherRealityCheck.repository.DiffForMeasurementsRepository;
import pl.schodowski.weatherRealityCheck.repository.ImgwWeatherDataRepository;
import pl.schodowski.weatherRealityCheck.repository.PredictionPointsRepository;
import pl.schodowski.weatherRealityCheck.repository.WeatherForecastRepository;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class WeatherComparisonService {

    private final WeatherForecastRepository weatherForecastRepository;
    private final ImgwWeatherDataRepository imgwWeatherDataRepository;
    private final DiffForMeasurementsRepository diffForMeasurementsRepository;
    private final PredictionPointsRepository predictionPointsRepository;

    public void comparePredictionsWithMeasurements() {

        diffForMeasurementsRepository.deleteAll();
        predictionPointsRepository.deleteAll();

        List<WeatherForecastEntity> predictions = weatherForecastRepository.findAll();
        List<ImgwWeatherDataEntity> measurements = imgwWeatherDataRepository.findAll();


        for (WeatherForecastEntity prediction : predictions) {
            for (ImgwWeatherDataEntity measurement : measurements) {
                compareWhenDateAndTimeTheSame(prediction, measurement);
            }
        }
    }

    private void compareWhenDateAndTimeTheSame(WeatherForecastEntity prediction, ImgwWeatherDataEntity measurement) {
        if (prediction.getDate().equals(measurement.getDate()) &&
                prediction.getForecastTime() == Float.parseFloat(measurement.getTimeOfMeasurement())) {
            compareWhenNameOfStationIsTheSame(prediction, measurement);
        }
    }

    private void compareWhenNameOfStationIsTheSame(WeatherForecastEntity prediction, ImgwWeatherDataEntity measurement) {  //todo zmienic na equals nazwy
        if (prediction.getName().length() == measurement.getName().length() ||
                prediction.getName().length() == measurement.getName().length() - 1) {
            createDifferenceForMeasurementsEntity(prediction, measurement);
        }
    }

    private void createDifferenceForMeasurementsEntity(WeatherForecastEntity prediction, ImgwWeatherDataEntity measurement) {

        DifferenceForMeasurementsEntity differenceForMeasurementsEntity = new DifferenceForMeasurementsEntity();
        differenceForMeasurementsEntity.setName(prediction.getName());
        differenceForMeasurementsEntity.setDate(prediction.getDate());
        differenceForMeasurementsEntity.setSource(prediction.getSource());
        differenceForMeasurementsEntity.setTemperatureDifference(prediction.getTemperature() - measurement.getTemperature());
        differenceForMeasurementsEntity.setWindDifference(prediction.getWind() - measurement.getWind());
        differenceForMeasurementsEntity.setRainfallTotalDifference(prediction.getRainfallTotal() - measurement.getRainfallTotal());
        differenceForMeasurementsEntity.setTime(prediction.getForecastTime());

        saveEntities(differenceForMeasurementsEntity, addNewPredictionPoints(differenceForMeasurementsEntity));
    }

    private PredictionPointsEntity addNewPredictionPoints(DifferenceForMeasurementsEntity differenceForMeasurementsEntity){

        Optional<PredictionPointsEntity> optionalPredictionPoints = predictionPointsRepository.findByName(differenceForMeasurementsEntity.getSource());
        PredictionPointsEntity predictionPointsEntity;

        if(optionalPredictionPoints.isPresent()) {
            predictionPointsEntity = optionalPredictionPoints.get();
            predictionPointsEntity.setTempPoints(predictionPointsEntity.getTempPoints() + Math.abs(differenceForMeasurementsEntity.getTemperatureDifference()));
            predictionPointsEntity.setNumberOfMeasurements(predictionPointsEntity.getNumberOfMeasurements() + 1);
            predictionPointsEntity.setPointsPerMeasurement(predictionPointsEntity.getTempPoints() / (float) predictionPointsEntity.getNumberOfMeasurements());
        }
        else {
            predictionPointsEntity = new PredictionPointsEntity();
            predictionPointsEntity.setName(differenceForMeasurementsEntity.getSource());
            predictionPointsEntity.setTempPoints(Math.abs(differenceForMeasurementsEntity.getTemperatureDifference()));
            predictionPointsEntity.setNumberOfMeasurements(1);
        }

        return predictionPointsEntity;
    }

    private void saveEntities(DifferenceForMeasurementsEntity differenceForMeasurementsEntity, PredictionPointsEntity predictionPoints) {
        predictionPointsRepository.save(predictionPoints);
        diffForMeasurementsRepository.save(differenceForMeasurementsEntity);
    }



}
