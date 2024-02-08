package pl.schodowski.weatherRealityCheck.model.meteoBlue;

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
    public ArrayList<Float> snowfraction;
    public ArrayList<String> rainspot;
    public ArrayList<Float> temperature;
    public ArrayList<Float> felttemperature;
    public ArrayList<Integer> pictocode;
    public ArrayList<Float> windspeed;
    public ArrayList<Integer> winddirection;
    public ArrayList<Integer> relativehumidity;
    public ArrayList<Float> sealevelpressure;
    public ArrayList<Integer> precipitation_probability;
    public ArrayList<Float> convective_precipitation;
    public ArrayList<Integer> isdaylight;
    public ArrayList<Integer> uvindex;
}
