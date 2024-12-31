package uni.source.carmanagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import uni.source.carmanagement.entity.Maintenance;

import java.time.LocalDate;
import java.util.List;

public interface MaintenanceRepository extends JpaRepository<Maintenance, Long> {
    @Query(value = """
    SELECT CAST(m.scheduledDate AS date) as maintenanceDate, COUNT(m.id) as requestCount
    FROM Maintenance m
    WHERE m.garage.id = :garageId AND m.scheduledDate BETWEEN :startDate AND :endDate
    GROUP BY CAST(m.scheduledDate AS date)
    """)
    List<Object[]> findMaintenanceRequestsByDate(
            @Param("garageId") Long garageId,
            @Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate
    );
}
