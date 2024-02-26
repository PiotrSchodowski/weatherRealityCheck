package pl.schodowski.weatherRealityCheck.service;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.springframework.stereotype.Service;

@Service
public class ScrapperGoogleForecast {

    public String downloadForecastForLocation(String location, String predictionTime) {
        WebDriver driver = initializeHeadlessChromeDriver();
        try {
            String url = "https://www.google.com/search?q=" + location + "+pogoda";
            driver.get(url);
            acceptCookies(driver);
            Thread.sleep(2000);
            String pageSource = driver.getPageSource();
            return extractForecastFromPageSource(pageSource, predictionTime);
        } catch (InterruptedException e) {
            System.err.println("Interrupted while waiting: " + e.getMessage());
            return null;
        } finally {
            driver.quit();
        }
    }

    private WebDriver initializeHeadlessChromeDriver() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless");
        options.addArguments("--disable-gpu");
        options.addArguments("--window-size=1920,1080");
        return new ChromeDriver(options);
    }

    private void acceptCookies(WebDriver driver) {
        WebElement acceptButton = driver.findElement(By.id("L2AGLb"));
        acceptButton.click();
    }

    private String extractTemperatureFromAriaLabel(String ariaLabel) {
        int indexOfDegree = ariaLabel.indexOf("°");
        String temperaturePart = ariaLabel.substring(0, indexOfDegree);
        return temperaturePart.replaceAll("[^0-9-]", "");
    }

    private String extractForecastFromPageSource(String pageSource, String predictionTime) {
        Document doc = Jsoup.parse(pageSource);
        Elements elements = doc.getElementsByClass("wob_t wob_gs_l" + predictionTime);
        for (Element element : elements) {
            String ariaLabel = element.attr("aria-label");
            return extractTemperatureFromAriaLabel(ariaLabel);
        }
        return null;
    }

}
