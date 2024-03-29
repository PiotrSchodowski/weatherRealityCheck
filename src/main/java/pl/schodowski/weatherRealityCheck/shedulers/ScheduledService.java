package pl.schodowski.weatherRealityCheck.shedulers;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import pl.schodowski.weatherRealityCheck.api.ImgwDataFetcher;
import pl.schodowski.weatherRealityCheck.service.WeatherComparisonService;
import pl.schodowski.weatherRealityCheck.service.ManageService;

@Service
@RequiredArgsConstructor
public class ScheduledService {

    private final ImgwDataFetcher imgwDataFetcher;
    private final ManageService manageService;
    private final WeatherComparisonService weatherComparisonService;

    @Scheduled(fixedRate = 3600000)
    public void doRepetitions(){
        imgwDataFetcher.updateRealWeather();
        manageService.processLocationTimePairs();
        weatherComparisonService.comparePredictionsWithMeasurements();
    }
}
