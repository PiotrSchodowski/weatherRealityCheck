package pl.schodowski.weatherRealityCheck.api;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import pl.schodowski.weatherRealityCheck.model.accuWeather.AccuWeatherPrediction;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@Service
public class AccuWeatherFetcher {

    private final RestTemplate restTemplate;

    private final String baseUrl;

    private final String apiKey;

    private static final Logger log = LoggerFactory.getLogger(AccuWeatherFetcher.class);


    public AccuWeatherFetcher(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
        this.baseUrl = "http://dataservice.accuweather.com/forecasts/v1/hourly/12hour/";
        this.apiKey = "?apikey=ADwmIpoDktVRR5z9AnXszHCcGflutNL4&details=true&metric=true";
    }


    public List<AccuWeatherPrediction> getAccuWeatherPredictionFromApi(String locationKey) {
        String url = buildUrlForLocation(locationKey);
        ResponseEntity<AccuWeatherPrediction[]> response = restTemplate.exchange(url, HttpMethod.GET, null, AccuWeatherPrediction[].class);
        return Arrays.asList(Objects.requireNonNull(response.getBody()));
    }


    private String buildUrlForLocation(String locationKey) {
        return baseUrl + locationKey + apiKey;
    }

}
