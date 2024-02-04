package pl.schodowski.weatherRealityCheck.api;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import pl.schodowski.weatherRealityCheck.entity.ImgwWeatherDataEntity;

@Service
public class AccuWeatherFetcher {

    private final RestTemplate restTemplate;

    private final String baseUrl;

    private final String apiKey;

    private static final Logger log = LoggerFactory.getLogger(ImgwDataFetcher.class);


    public AccuWeatherFetcher(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
        this.baseUrl = "http://dataservice.accuweather.com/forecasts/v1/hourly/12hour/";
        this.apiKey = "?apikey=ADwmIpoDktVRR5z9AnXszHCcGflutNL4&details=true&metric=true";
    }

    public ResponseEntity<ImgwWeatherDataEntity> getAccuWeatherPredictionFromApi(String locationKey) {
        try {
            String url = buildUrlForLocation(locationKey);
            return restTemplate.exchange(url, HttpMethod.GET, null, ImgwWeatherDataEntity.class);

        } catch (Exception e) {
            log.error("An error occurred while updating weather from AccuWeather for locationKey {}: {}", locationKey, e.getMessage());
        }
        return null;
    }

    private String buildUrlForLocation(String locationKey) {
        return baseUrl + locationKey + apiKey;
    }

}
