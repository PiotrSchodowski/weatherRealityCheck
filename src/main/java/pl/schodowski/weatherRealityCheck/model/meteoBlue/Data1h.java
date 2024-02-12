package pl.schodowski.weatherRealityCheck.model.meteoBlue;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Data1h{
    public ArrayList<String> time;
    public ArrayList<Float> precipitation;
    public ArrayList<Float> snowFraction;
    public ArrayList<String> rainSpot;
    public ArrayList<Float> temperature;
    public ArrayList<Float> feltTemperature;
    public ArrayList<Integer> pictoCode;
//    @JsonProperty("wind_speed")
    public ArrayList<Float> windSpeed;
    public ArrayList<Integer> windDirection;
    public ArrayList<Integer> relativeHumidity;
    public ArrayList<Float> seaLevelPressure;
    public ArrayList<Integer> precipitation_probability;
    public ArrayList<Float> convective_precipitation;
    public ArrayList<Integer> isDayLight;
    public ArrayList<Integer> uvIndex;
}
