package pl.schodowski.weatherRealityCheck.entity;

import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DifferenceForMeasurementsEntity {

    private float tempByPercentage;
    private String date;
    private String source;
}
