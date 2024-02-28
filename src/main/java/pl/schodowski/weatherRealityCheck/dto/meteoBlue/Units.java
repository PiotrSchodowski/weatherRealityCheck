package pl.schodowski.weatherRealityCheck.dto.meteoBlue;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Units{
    public String time;
    public String precipitationProbability;
    public String pressure;
    public String relativeHumidity;
    public String co;
    public String precipitation;
    public String temperature;
    public String windSpeed;
    public String windDirection;
}
