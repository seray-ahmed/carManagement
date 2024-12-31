package uni.source.carmanagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import uni.source.carmanagement.entity.Garage;
import uni.source.carmanagement.entity.Maintenance;

import java.time.LocalDate;
import java.util.List;

public interface MaintenanceRepository extends JpaRepository<Maintenance, Long>, JpaSpecificationExecutor<Maintenance> {
    int countMaintenanceEntitiesByGarageAndScheduledDate(Garage garage, LocalDate scheduledDate);
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

    @Query("SELECT FUNCTION('TO_CHAR', m.scheduledDate, 'yyyy-MM') AS mnth, COUNT(m) AS requests " +
            "FROM Maintenance m " +
            "WHERE m.garage.id = :garageId " +
            "AND m.scheduledDate BETWEEN :startDate AND :endDate " +
            "GROUP BY FUNCTION('TO_CHAR', m.scheduledDate, 'yyyy-MM') " +
            "ORDER BY mnth")
    List<Object[]> getMonthlyRequestsReport(@Param("garageId") long garageId,
                                            @Param("startDate") LocalDate startDate,
                                            @Param("endDate") LocalDate endDate);
}
