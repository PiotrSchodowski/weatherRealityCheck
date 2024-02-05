package pl.schodowski.weatherRealityCheck.model.accuWeather;

import com.fasterxml.jackson.annotation.JsonProperty;

public class RealFeelTemperatureShade{
    @JsonProperty("Value")
    public double value;
    @JsonProperty("Unit")
    public String unit;
    @JsonProperty("UnitType")
    public int unitType;
    @JsonProperty("Phrase")
    public String phrase;
}
