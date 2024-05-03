package pl.schodowski.weatherRealityCheck.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.schodowski.weatherRealityCheck.entity.PredictionPointsEntity;

import java.util.Optional;

@Repository
public interface PredictionPointsRepository extends JpaRepository<PredictionPointsEntity, Long> {

    public Optional<PredictionPointsEntity> findByName(String name);
}
