package pl.schodowski.weatherRealityCheck.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.schodowski.weatherRealityCheck.entity.ImgwWeatherDataEntity;

@Repository
public interface ImgwWeatherDataRepository extends JpaRepository<ImgwWeatherDataEntity, Long> {
}
