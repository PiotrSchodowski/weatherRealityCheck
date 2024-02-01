package pl.schodowski.weatherRealityCheck.api;

import jakarta.annotation.PostConstruct;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
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
    private final ImgwWeatherDataRepository repo;

    private final List<String> stations = Arrays.asList("sniezka", "zakopane", "bielskobiala", "kasprowywierch");

    public ImgwDataFetcher(RestTemplate restTemplate, ImgwWeatherDataRepository repo) {
        this.restTemplate = restTemplate;
        this.baseUrl = "https://danepubliczne.imgw.pl/api/data/synop/station/";
        this.repo = repo;
    }

    @PostConstruct
    public void init() {
        stations.forEach(this::getImgwWeatherDataEntityFromApi);
    }

    private void getImgwWeatherDataEntityFromApi(String station) {
        try {
            String url = buildUrlForStation(station);
            ResponseEntity<ImgwWeatherDataEntity> response = restTemplate.exchange(url, HttpMethod.GET, null, ImgwWeatherDataEntity.class);
            repo.save(Objects.requireNonNull(response.getBody()));
        } catch (Exception e) {
            e.printStackTrace();   //todo zrobic lepsza obsługe błędów
        }
    }

    private String buildUrlForStation(String station) {
        return baseUrl + station;
    }
}
