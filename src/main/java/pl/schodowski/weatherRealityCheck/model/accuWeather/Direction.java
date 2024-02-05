package pl.schodowski.weatherRealityCheck.model.accuWeather;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Direction{
    @JsonProperty("Degrees")
    public int degrees;
    @JsonProperty("Localized")
    public String localized;
    @JsonProperty("English")
    public String english;
}
