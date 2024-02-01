package pl.schodowski.weatherRealityCheck.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Time;
import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ImgwWeatherDataEntity {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @JsonProperty("stacja")
    private String name;
    @JsonProperty("data_pomiaru")
    private String date;
    @JsonProperty("godzina_pomiaru")
    private String time;
    @JsonProperty("temperatura")
    private float temperature;
    @JsonProperty("predkosc_wiatru")
    private float wind;
    @JsonProperty("suma_opadu")
    private float rainfallTotal;

    private LocalDateTime fullDate;
}
