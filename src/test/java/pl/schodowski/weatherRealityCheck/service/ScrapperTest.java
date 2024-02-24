package pl.schodowski.weatherRealityCheck.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

public class ScrapperTest {

    @InjectMocks
    private ScrapperGoogleForecast scrapperGoogleForecast;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void shouldReturnText(){

        String text, text2, text3;
        text = scrapperGoogleForecast.getActualTemp("zakopane");
//        text2 = scrapperGoogleForecast.getTemperatureForSecondMeasurement("zakopane");
//        text3 = scrapperGoogleForecast.getTemperatureForThirdMeasurement("zakopane");

        System.out.println(text);
//        System.out.println(text2);
//        System.out.println(text3);
    }


}
