package pl.schodowski.weatherRealityCheck.model.meteoBlue;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class Root{
    public Metadata metadata;
    public Units units;
    public Data1h data_1h;
}
