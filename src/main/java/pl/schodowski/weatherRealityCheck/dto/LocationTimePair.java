package pl.schodowski.weatherRealityCheck.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LocationTimePair {

    private String locationName;
    private String predictionTime;
}

