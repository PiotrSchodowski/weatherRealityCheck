package pl.schodowski.weatherRealityCheck.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LocationTimePair {
    private String locationName;
    private int predictionTime;
}

