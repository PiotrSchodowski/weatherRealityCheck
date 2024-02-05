package pl.schodowski.weatherRealityCheck.model.accuWeather;

import com.fasterxml.jackson.annotation.JsonProperty;

public class WindGust{
    @JsonProperty("Speed")
    public Speed speed;
}
