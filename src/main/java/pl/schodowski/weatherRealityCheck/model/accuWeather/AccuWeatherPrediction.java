package pl.schodowski.weatherRealityCheck.model.accuWeather;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AccuWeatherPrediction {

    @JsonProperty("DateTime")
    public String dateTime;
    @JsonProperty("EpochDateTime")
    public int epochDateTime;
    @JsonProperty("WeatherIcon")
    public int weatherIcon;
    @JsonProperty("IconPhrase")
    public String iconPhrase;
    @JsonProperty("HasPrecipitation")
    public boolean hasPrecipitation;
    @JsonProperty("PrecipitationType")
    public String precipitationType;
    @JsonProperty("PrecipitationIntensity")
    public String precipitationIntensity;
    @JsonProperty("IsDaylight")
    public boolean isDaylight;
    @JsonProperty("Temperature")
    public Temperature temperature;
    @JsonProperty("RealFeelTemperature")
    public RealFeelTemperature realFeelTemperature;
    @JsonProperty("RealFeelTemperatureShade")
    public RealFeelTemperatureShade realFeelTemperatureShade;
    @JsonProperty("WetBulbTemperature")
    public WetBulbTemperature wetBulbTemperature;
    @JsonProperty("WetBulbGlobeTemperature")
    public WetBulbGlobeTemperature wetBulbGlobeTemperature;
    @JsonProperty("DewPoint")
    public DewPoint dewPoint;
    @JsonProperty("Wind")
    public Wind wind;
    @JsonProperty("WindGust")
    public WindGust windGust;
    @JsonProperty("RelativeHumidity")
    public int relativeHumidity;
    @JsonProperty("IndoorRelativeHumidity")
    public int indoorRelativeHumidity;
    @JsonProperty("Visibility")
    public Visibility visibility;
    @JsonProperty("Ceiling")
    public Ceiling ceiling;
    @JsonProperty("UVIndex")
    public int uVIndex;
    @JsonProperty("UVIndexText")
    public String uVIndexText;
    @JsonProperty("PrecipitationProbability")
    public int precipitationProbability;
    @JsonProperty("ThunderstormProbability")
    public int thunderstormProbability;
    @JsonProperty("RainProbability")
    public int rainProbability;
    @JsonProperty("SnowProbability")
    public int snowProbability;
    @JsonProperty("IceProbability")
    public int iceProbability;
    @JsonProperty("TotalLiquid")
    public TotalLiquid totalLiquid;
    @JsonProperty("Rain")
    public Rain rain;
    @JsonProperty("Snow")
    public Snow snow;
    @JsonProperty("Ice")
    public Ice ice;
    @JsonProperty("CloudCover")
    public int cloudCover;
    @JsonProperty("Evapotranspiration")
    public Evapotranspiration evapotranspiration;
    @JsonProperty("SolarIrradiance")
    public SolarIrradiance solarIrradiance;
    @JsonProperty("MobileLink")
    public String mobileLink;
    @JsonProperty("Link")
    public String link;

}
