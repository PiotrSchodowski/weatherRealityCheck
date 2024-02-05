package pl.schodowski.weatherRealityCheck.api;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import pl.schodowski.weatherRealityCheck.model.accuWeather.AccuWeatherPrediction;

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

    public ResponseEntity<AccuWeatherPrediction> getAccuWeatherPredictionFromApi(String locationKey) {
        try {
            if (locationKey == null) {
                throw new IllegalArgumentException("Location key cannot be null");
            }

            String url = buildUrlForLocation(locationKey);
            return restTemplate.exchange(url, HttpMethod.GET, null, AccuWeatherPrediction.class);

        } catch (HttpClientErrorException e) {
            // Obsługa błędów związanego z nieprawidłowym zapytaniem (np. 404 Not Found)
            log.error("HTTP error while fetching AccuWeather data for locationKey {}: {}", locationKey, e.getMessage());
            return ResponseEntity.status(e.getRawStatusCode()).body(null);
        } catch (Exception e) {
            // Obsługa ogólnych błędów
            log.error("An unexpected error occurred while fetching AccuWeather data for locationKey {}: {}", locationKey, e.getMessage());
            return ResponseEntity.status(500).body(null); // 500 Internal Server Error
        }
    }

    private String buildUrlForLocation(String locationKey) {
        return baseUrl + locationKey + apiKey;
    }

}
