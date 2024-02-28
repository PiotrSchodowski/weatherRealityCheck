package pl.schodowski.weatherRealityCheck.dto.accuWeather;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Wind{
    @JsonProperty("Speed")
    public Speed speed;
    @JsonProperty("Direction")
    public Direction direction;
}
