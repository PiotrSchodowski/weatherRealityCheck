package pl.schodowski.weatherRealityCheck.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.schodowski.weatherRealityCheck.entity.DifferenceForMeasurementsEntity;

public interface DiffForMeasurementsRepository extends JpaRepository<DifferenceForMeasurementsEntity, Long> {
}
