package pl.schodowski.weatherRealityCheck.api;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import pl.schodowski.weatherRealityCheck.model.meteoBlue.MeteoBluePrediction;

@Service
public class MeteoBlueFetcher {

    private final RestTemplate restTemplate;

    private final String baseUrl;

    private final String apiKey;

    private static final Logger log = LoggerFactory.getLogger(AccuWeatherFetcher.class);

    public MeteoBlueFetcher(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
        this.baseUrl = "https://my.meteoblue.com/packages/basic-1h?apikey=";
        this.apiKey = "0frBJoeunVMqk1hY";
    }

    public MeteoBluePrediction getMeteoBluePredictionFromApi(String lon, String lat) {
        String url = buildUrlForLocation(lon, lat);
        ResponseEntity<MeteoBluePrediction> response = restTemplate.exchange(url, HttpMethod.GET, null, MeteoBluePrediction.class);
        return response.getBody();
    }


    private String buildUrlForLocation(String lon, String lat) {
        return baseUrl + apiKey + "&lat=" + lat + "&lon=" + lon + "&asl=266&timestep=1h&format=json&numdays=1&location_list=name:Current%20Location";
    }
}
