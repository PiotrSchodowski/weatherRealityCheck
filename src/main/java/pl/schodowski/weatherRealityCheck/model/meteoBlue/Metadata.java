package pl.schodowski.weatherRealityCheck.model.meteoBlue;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Metadata{
    public String name;
    public double latitude;
    public double longitude;
    public int height;
    public String timezoneAbbreviation;
    public double utcTimeOffset;
    public String modelRunUtc;
    public String modelRunUpdateTimeUtc;
}
