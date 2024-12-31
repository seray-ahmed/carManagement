package uni.source.carmanagement.util;

import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;
import uni.source.carmanagement.entity.Maintenance;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class MaintenanceSpec {
    public static Specification<Maintenance> filterMaintenance(Long carId, Long garageId, LocalDate fromDate, LocalDate toDate) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (carId != null && carId > 0) {
                predicates.add(criteriaBuilder.equal(root.join("car").get("id"), carId));
            }
            if (garageId != null && garageId > 0) {
                predicates.add(criteriaBuilder.equal(root.join("garage").get("id"), garageId));
            }
            if (fromDate != null) {
                predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("scheduledDate"), fromDate));
            }
            if (toDate != null) {
                predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("scheduledDate"), toDate));
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }
}
