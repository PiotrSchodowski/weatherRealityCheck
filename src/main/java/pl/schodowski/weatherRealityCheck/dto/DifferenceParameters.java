package pl.schodowski.weatherRealityCheck.dto;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class DifferenceParameters {

    private float temperature;
    private float wind;
    private float rainfallTotal;

}
