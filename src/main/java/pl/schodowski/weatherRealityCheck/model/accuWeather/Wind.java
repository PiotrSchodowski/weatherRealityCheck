package pl.schodowski.weatherRealityCheck.model.accuWeather;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Wind{
    @JsonProperty("Speed")
    public Speed speed;
    @JsonProperty("Direction")
    public Direction direction;
}
