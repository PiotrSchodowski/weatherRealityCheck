package pl.schodowski.weatherRealityCheck.model.accuWeather;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Direction{
    @JsonProperty("Degrees")
    public int degrees;
    @JsonProperty("Localized")
    public String localized;
    @JsonProperty("English")
    public String english;
}
