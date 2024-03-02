package pl.schodowski.weatherRealityCheck.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DifferenceForMeasurementsEntity {


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;

    private float temperatureDifference;
    private float temperatureForecast;
    private float temperatureMeasurement;
    private String date;
    private float time;
    private String source;
    private String name;
}
