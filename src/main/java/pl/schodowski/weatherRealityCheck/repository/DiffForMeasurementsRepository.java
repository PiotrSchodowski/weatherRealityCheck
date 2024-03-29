package pl.schodowski.weatherRealityCheck.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.schodowski.weatherRealityCheck.entity.DifferenceForMeasurementsEntity;

@Repository
public interface DiffForMeasurementsRepository extends JpaRepository<DifferenceForMeasurementsEntity, Long> {
}
