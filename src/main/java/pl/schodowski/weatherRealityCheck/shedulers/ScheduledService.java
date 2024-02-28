package pl.schodowski.weatherRealityCheck.shedulers;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import pl.schodowski.weatherRealityCheck.api.ImgwDataFetcher;
import pl.schodowski.weatherRealityCheck.service.ManageService;

@Service
@RequiredArgsConstructor
public class ScheduledService {

    private ImgwDataFetcher imgwDataFetcher;
    private ManageService manageService;

    @Scheduled(fixedRate = 3600000)
    public void doRepetitions(){
        imgwDataFetcher.updateRealWeather();
        manageService.processLocationTimePairs();
    }
}
