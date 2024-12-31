package uni.source.carmanagement.service;

import uni.source.carmanagement.DTO.request.maintenance.MaintenanceRequest;
import uni.source.carmanagement.DTO.response.maintenance.MaintenanceReportResponse;
import uni.source.carmanagement.DTO.response.maintenance.MaintenanceResponse;

import java.time.LocalDate;
import java.util.List;

public interface MaintenanceService {
    MaintenanceResponse getMaintenanceById(Long id);
    List<MaintenanceResponse> getMaintenancesByCriteria(Long carId, Long garageId, LocalDate startDate, LocalDate endDate);
    List<MaintenanceReportResponse> generateMonthlyRequestsReport(Long garageId, String startMonth, String endMonth);
    MaintenanceResponse addMaintenance(MaintenanceRequest maintenanceRequest);
    MaintenanceResponse modifyMaintenance(long id, MaintenanceRequest maintenanceRequest);
    void removeMaintenance(long id);
}
