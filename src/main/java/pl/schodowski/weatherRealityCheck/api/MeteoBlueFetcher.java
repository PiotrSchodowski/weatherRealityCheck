package pl.schodowski.weatherRealityCheck.api;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class MeteoBlueFetcher {

    private final RestTemplate restTemplate;

    private final String baseUrl;

    private final String apiKey;

    private static final Logger log = LoggerFactory.getLogger(AccuWeatherFetcher.class);

    public MeteoBlueFetcher(RestTemplate restTemplate){
        this.restTemplate = restTemplate;
        this.baseUrl = "https://my.meteoblue.com/packages/basic-1h?apikey=";
        this.apiKey = "0frBJoeunVMqk1hY&lat=49.2990&lon=19.9488&asl=266&timestep=1h&format=json&numdays=1&location_list=name:Current%20Location";
    }

    private String buildUrlForLocation(String locationKey) {
        return baseUrl + locationKey + apiKey;
    }
}
