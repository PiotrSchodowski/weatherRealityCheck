//package pl.schodowski.weatherRealityCheck.service;
//
//import jakarta.annotation.PostConstruct;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.stereotype.Service;
//import pl.schodowski.weatherRealityCheck.api.AccuWeatherFetcher;
//import pl.schodowski.weatherRealityCheck.entity.WeatherForecastEntity;
//import pl.schodowski.weatherRealityCheck.model.accuWeather.AccuWeatherPrediction;
//import pl.schodowski.weatherRealityCheck.repository.WeatherForecastRepository;
//
//import java.util.ArrayList;
//import java.util.List;
//
//@Service
//public class WeatherDataPersistenceService {
//
//    private final AccuWeatherFetcher accuWeatherFetcher;
//    private final WeatherForecastRepository weatherForecastRepository;
//    private final AccuWeatherConversionService accuWeatherConversionService;
//    private final String zakopaneLocationKey;
//    private final String bielskoLocationKey;
//    private final String zakopaneLat;
//    private final String zakopaneLon;
//    private final String bielskoLat;
//    private final String bielskoLon;
//    private static final String CRON_SCHEDULE = "0 */5 * * * *";
//
//    public WeatherDataPersistenceService(AccuWeatherFetcher accuWeatherFetcher, WeatherForecastRepository weatherForecastRepository,
//                                         @Value("${zakopaneLocationForAccuWeather}") String zakopaneLocationKey,
//                                         @Value("${bielskoLocationForAccuWeather}") String bielskoLocationKey,
//                                         @Value("${zakopaneLat}") String zakopaneLat,
//                                         @Value("${zakopaneLon}") String zakopaneLon,
//                                         @Value("${bielskoLat}") String bielskoLat,
//                                         @Value("${bielskoLon}") String bielskoLon,
//                                         AccuWeatherConversionService accuWeatherConversionService) {
//        this.accuWeatherFetcher = accuWeatherFetcher;
//        this.weatherForecastRepository = weatherForecastRepository;
//        this.accuWeatherConversionService = accuWeatherConversionService;
//        this.zakopaneLocationKey = zakopaneLocationKey;
//        this.bielskoLocationKey = bielskoLocationKey;
//        this.zakopaneLat = zakopaneLat;
//        this.zakopaneLon = zakopaneLon;
//        this.bielskoLat = bielskoLat;
//        this.bielskoLon = bielskoLon;
//    }
//
//    @PostConstruct
//    public void enterLocationToSaving() {
//        getPredictionsFromAccuWeatherForRecording(zakopaneLocationKey, "Zakopane");
//        getPredictionsFromAccuWeatherForRecording(bielskoLocationKey, "Bielsko-Biała");
//    }
//
//
//    private void getPredictionsFromAccuWeatherForRecording(String locationKey, String locationName) {
//        List<AccuWeatherPrediction> accuWeatherPredictions = accuWeatherFetcher.getAccuWeatherPredictionFromApi(locationKey);
//        List<WeatherForecastEntity> weatherForecastEntityList = new ArrayList<>();
//
    //        weatherForecastEntityList.add(accuWeatherConversionService.buildEntityBasedPrediction(accuWeatherPredictions.get(11), locationName));
//        weatherForecastEntityList.add(accuWeatherConversionService.buildEntityBasedPrediction(accuWeatherPredictions.get(1), locationName));
//
//    }
//
//
//
//
//
//}
//
//public class WeatherForecast {
//
//    public static void main(String[] args) {
//        ObjectMapper mapper = new ObjectMapper();
//        try {
//            // Załóżmy, że "path_to_json" to ścieżka do pliku JSON
//            Map<String, Object> map = mapper.readValue(new File("path_to_json"), Map.class);
//            Map<String, Object> data1h = (Map<String, Object>) map.get("data_1h");
//            String[] times = (String[]) data1h.get("time");
//            double[] temperatures = (double[]) data1h.get("temperature");
//
//            // Zakładając, że "hoursFromNow" to liczba godzin od teraz
//            int hoursFromNow = 5; // przykładowa wartość
//            if (hoursFromNow < times.length) {
//                String forecastTime = times[hoursFromNow];
//                double forecastTemperature = temperatures[hoursFromNow];
//
//                System.out.println("Prognoza na " + forecastTime + ":");
//                System.out.println("Temperatura: " + forecastTemperature + "°C");
//                // Tutaj można dodać więcej szczegółów prognozy, jeśli są potrzebne
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//}