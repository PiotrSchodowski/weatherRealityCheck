package pl.schodowski.weatherRealityCheck.service;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;


@Service
public class ScrapperGoogleForecast {


    public String downloadForecastForLocation(String location, String predictionTime) {

        String url = "https://www.google.com/search?q=" + location + "+pogoda";
        String text = null;

        System.setProperty("webdriver.chrome.driver", "C:\\Users\\48798\\Documents\\chrome-driver\\chromedriver-win64\\chromedriver.exe");

        WebDriver driver = new ChromeDriver();
        String pageSource = null;
        
        try {
            driver.get(url);
            WebElement acceptButton = driver.findElement(By.id("L2AGLb"));
            acceptButton.click();
            Thread.sleep(2000); // Użyj WebDriverWait dla lepszego rozwiązania
            pageSource = driver.getPageSource();
            
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            driver.quit();
        }

        Document doc = Jsoup.parse(pageSource);
        Elements elements = doc.getElementsByClass("wob_t wob_gs_l" + predictionTime);

        for (Element element : elements) {
            String ariaLabel = element.attr("aria-label");
            System.out.println(ariaLabel);
            text = ariaLabel;
        }
        return text;
    }

}
