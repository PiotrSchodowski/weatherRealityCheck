package pl.schodowski.weatherRealityCheck.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.sql.Time;
import java.time.LocalDateTime;
import java.util.Date;

@Entity
public class ImgwWeatherData {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    private String name;
    private Date date;
    private Time time;
    private float temperature;
    private float wind;
    private float rainfallTotal;
    private LocalDateTime fullDate;
}
