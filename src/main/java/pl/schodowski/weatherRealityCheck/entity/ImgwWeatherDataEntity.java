package pl.schodowski.weatherRealityCheck.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
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
public class ImgwWeatherDataEntity {

    @JsonProperty("id")
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;


    @JsonProperty("stacja")
    private String name;
    @JsonProperty("data_pomiaru")
    private String date;
    @JsonProperty("godzina_pomiaru")
    private String timeOfMeasurement;
    @JsonProperty("temperatura")
    private float temperature;
    @JsonProperty("predkosc_wiatru")
    private float wind;
    @JsonProperty("suma_opadu")
    private float rainfallTotal;

}
