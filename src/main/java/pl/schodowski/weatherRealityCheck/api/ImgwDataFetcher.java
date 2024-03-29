package pl.schodowski.weatherRealityCheck.api;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import pl.schodowski.weatherRealityCheck.entity.ImgwWeatherDataEntity;
import pl.schodowski.weatherRealityCheck.repository.ImgwWeatherDataRepository;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@Service
public class ImgwDataFetcher {

    private final RestTemplate restTemplate;
    private final String baseUrl;
    private final ImgwWeatherDataRepository repository;
    private static final Logger log = LoggerFactory.getLogger(ImgwDataFetcher.class);
    private final List<String> stations = Arrays.asList("zakopane", "bielskobiala");


    public ImgwDataFetcher(RestTemplate restTemplate, ImgwWeatherDataRepository repository) {
        this.restTemplate = restTemplate;
        this.baseUrl = "https://danepubliczne.imgw.pl/api/data/synop/station/";
        this.repository = repository;
    }


    public void updateRealWeather() {
        stations.forEach(this::saveImgwWeatherDataFromApi);
    }


    private void saveImgwWeatherDataFromApi(String station) {
        try {
            String url = buildUrlForStation(station);
            ResponseEntity<ImgwWeatherDataEntity> response = restTemplate.exchange(url, HttpMethod.GET, null, ImgwWeatherDataEntity.class);
            repository.save(Objects.requireNonNull(response.getBody()));
        } catch (Exception e) {
            log.error("An error occurred while updating weather for station {}: {}", station, e.getMessage());
        }
    }


    private String buildUrlForStation(String station) {
        return baseUrl + station;
    }
}
