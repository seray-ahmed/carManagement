package uni.source.carmanagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uni.source.carmanagement.entity.Car;

public interface CarRepository extends JpaRepository<Car, Long> {
}
