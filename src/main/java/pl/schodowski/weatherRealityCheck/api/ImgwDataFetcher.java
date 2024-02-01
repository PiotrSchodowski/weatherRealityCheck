package pl.schodowski.weatherRealityCheck.api;

import jakarta.annotation.PostConstruct;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import pl.schodowski.weatherRealityCheck.entity.ImgwWeatherDataEntity;
import pl.schodowski.weatherRealityCheck.repository.ImgwWeatherDataRepository;

@Service
public class ImgwDataFetcher {

    private final RestTemplate restTemplate;
    private final String baseUrl;

    private final String sniezkaStation = "sniezka";
    private final String zakopaneStation = "zakopane";
    private final String bielskoStation = "bielskobiala";
    private final String kasprowyStation = "kasprowywierch";


    private final ImgwWeatherDataRepository repo;


    public ImgwDataFetcher(RestTemplate restTemplate, ImgwWeatherDataRepository repo) {
        this.restTemplate = restTemplate;
        this.baseUrl = "https://danepubliczne.imgw.pl/api/data/synop/station/";
        this.repo = repo;
    }

    @PostConstruct
    public void init() {
        getImgwWeatherDataEntityFromApi();
    }

    public ImgwWeatherDataEntity getImgwWeatherDataEntityFromApi() {
        String url = baseUrl + sniezkaStation;
        ResponseEntity<ImgwWeatherDataEntity> response = restTemplate.exchange(url, HttpMethod.GET, null, ImgwWeatherDataEntity.class);
        return repo.save(response.getBody());
    }
}
