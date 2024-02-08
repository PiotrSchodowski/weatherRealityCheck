package pl.schodowski.weatherRealityCheck.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.schodowski.weatherRealityCheck.entity.WeatherForecastEntity;

@Repository
public interface AccuWeatherDataRepository extends JpaRepository<WeatherForecastEntity, Long> {
}
