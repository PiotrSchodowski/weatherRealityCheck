package pl.schodowski.weatherRealityCheck.model.meteoBlue;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class MeteoBluePrediction{
    public Metadata metadata;
    public Units units;
    public Data1h data1h;
}
