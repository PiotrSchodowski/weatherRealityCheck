package pl.schodowski.weatherRealityCheck.service;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import java.io.IOException;


@Service
public class ScrapperGoogleForecast {

    public String getTemperatureForFirstMeasurement(String location) {
        String url = "https://www.google.com/search?q=pogoda+zakopane&oq=pogoda+zako&gs_lcrp=EgZjaHJvbWUqDQgAEAAYgwEYsQMYgAQyDQgAEAAYgwEYsQMYgAQyBggBEEUYOTIHCAIQABiABDIHCAMQABiABDIHCAQQABiABDIHCAUQABiABDIHCAYQABiABDIHCAcQABiABDIHCAgQABiPAjIHCAkQABiPAqgCALACAA&sourceid=chrome&ie=UTF-8";

        try {
            Document document = Jsoup.connect(url).get();
            Element searchClass = document.selectFirst(".wob_t");
            if (searchClass != null) {
                String text = searchClass.text();
                return text;
            } else {
                throw new RuntimeException("Nie można znaleźć prognozy pogody dla podanej lokalizacji: " + location);
            }
        } catch (IOException e) {
            throw new RuntimeException("Nie udało się pobrać danych");
        }
    }

    public String getTemperatureForSecondMeasurement(String location) {

        String url = "https://www.google.com/search?q=pogoda+zakopane&oq=pogoda+zako&gs_lcrp=EgZjaHJvbWUqDQgAEAAYgwEYsQMYgAQyDQgAEAAYgwEYsQMYgAQyBggBEEUYOTIHCAIQABiABDIHCAMQABiABDIHCAQQABiABDIHCAUQABiABDIHCAYQABiABDIHCAcQABiABDIHCAgQABiPAjIHCAkQABiPAqgCALACAA&sourceid=chrome&ie=UTF-8";

        try {
            Document document = Jsoup.connect(url).get();
            Element searchClass = document.selectFirst(".wob_gsvg");
            if (searchClass != null) {
                Element textElement = searchClass.selectFirst(".wob_t.wob_gs_l0");
                if (textElement != null) {
                    String textContent = textElement.text();
                    return textContent;
                } else {
                    throw new RuntimeException("Nie można znaleźć elementu tekstowego z temperaturą w elemencie SVG.");
                }
            } else {
                throw new RuntimeException("Nie można znaleźć prognozy pogody dla podanej lokalizacji: " + location);
            }
        } catch (IOException e) {
            throw new RuntimeException("Nie udało się pobrać danych");
        }
    }

    public String getTemperatureForThirdMeasurement(String location) {
        String url = "https://www.google.com/search?q=pogoda+" + location + "&oq=pogoda+" + location + "&gs_lcrp=EgZjaHJvbWUqDQgAEAAYgwEYsQMYgAQyDQgAEAAYgwEYsQMYgAQyBggBEEUYOTIHCAIQABiABDIHCAMQABiABDIHCAQQABiABDIHCAUQABiABDIHCAYQABiABDIHCAcQABiABDIHCAgQABiPAjIHCAkQABiPAqgCALACAA&sourceid=chrome&ie=UTF-8";

        try {
            Document document = Jsoup.connect(url).get();
            Elements measurementElements = document.select(".wob_t");
            if (!measurementElements.isEmpty() && measurementElements.size() >= 16) {
                Element seventhMeasurement = measurementElements.get(16);
                String text = seventhMeasurement.text();
                return text;
            } else {
                throw new RuntimeException("Nie można znaleźć siódmego pomiaru pogody dla podanej lokalizacji: " + location);
            }
        } catch (IOException e) {
            throw new RuntimeException("Nie udało się pobrać danych");
        }
    }
}
