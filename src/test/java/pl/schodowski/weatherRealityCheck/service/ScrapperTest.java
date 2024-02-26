package pl.schodowski.weatherRealityCheck.service;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;



import java.io.IOException;

public class ScrapperTest {

    @InjectMocks
    private ScrapperGoogleForecast scrapperGoogleForecast;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }


    String url = "https://www.google.com/search?q=zakopane+pogoda";

    @Test
    void shouldReturnTempForNext12Hours(){

        String text;
        for( int i = 0; i<12 ; i++){

            String predictionTimeString = String.valueOf(i);

            text = scrapperGoogleForecast.downloadForecastForLocation("zakopane", predictionTimeString);
            System.out.println(text);
        }
    }

    @Test
    void shouldReturnTempForSetHour(){

        String text;

        text = scrapperGoogleForecast.downloadForecastForLocation("zakopane", "3");
        System.out.println(text);

    }

    @Test
    void test() throws IOException {
        System.setProperty("webdriver.chrome.driver", "C:\\Users\\48798\\Documents\\chrome-driver\\chromedriver-win64\\chromedriver.exe");

        // Inicjalizacja WebDrivera
        WebDriver driver = new ChromeDriver();
        String pageSource = null;
        try {
            // Otwarcie strony
            driver.get(url);

            // Znalezienie przycisku za pomocą ID i kliknięcie go
            WebElement acceptButton = driver.findElement(By.id("L2AGLb"));
            acceptButton.click();

            // Odczekanie na ewentualne dynamiczne załadowanie strony
            Thread.sleep(2000); // Użyj WebDriverWait dla lepszego rozwiązania

            // Pobranie i wyświetlenie kodu źródłowego strony

            pageSource = driver.getPageSource();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            // Zamknięcie przeglądarki
            driver.quit();
        }

        // Pobranie źródła strony
        Document doc = Jsoup.parse(pageSource);

        Elements elements = doc.getElementsByClass("wob_t wob_gs_l4");

        for (Element element : elements) {
            String ariaLabel = element.attr("aria-label");
            System.out.println(ariaLabel);
        }
    }


}
