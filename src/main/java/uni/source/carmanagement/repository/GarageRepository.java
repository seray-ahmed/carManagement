package uni.source.carmanagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uni.source.carmanagement.entity.Garage;

import java.util.Collection;
import java.util.List;

public interface GarageRepository extends JpaRepository<Garage, Long> {
    List<Garage> findAllByIdIn(Collection<Long> id);
    List<Garage> findAllByNameStartingWith(String cityNameBeginning);
}
