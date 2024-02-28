package pl.schodowski.weatherRealityCheck.dto.meteoBlue;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class MeteoBluePrediction{

    public Metadata metadata;

    public Units units;

    @JsonProperty("data_1h")
    public Data1h data1h;
}
