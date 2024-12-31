package uni.source.carmanagement.web;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uni.source.carmanagement.DTO.request.maintenance.MaintenanceRequest;
import uni.source.carmanagement.DTO.response.maintenance.MaintenanceReportResponse;
import uni.source.carmanagement.DTO.response.maintenance.MaintenanceResponse;
import uni.source.carmanagement.service.MaintenanceService;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@RestController
@RequestMapping("/maintenance")
public class MaintenanceController {

    private final MaintenanceService maintenanceService;

    public MaintenanceController(MaintenanceService maintenanceService) {
        this.maintenanceService = maintenanceService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<MaintenanceResponse> getMaintenanceById(@PathVariable Long id) {
        MaintenanceResponse maintenance = maintenanceService.getMaintenanceById(id);
        return ResponseEntity.ok(maintenance);
    }

    @GetMapping
    public ResponseEntity<List<MaintenanceResponse>> getAllMaintenancesByCriteria(
            @RequestParam(required = false) Long carId,
            @RequestParam(required = false) Long garageId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        List<MaintenanceResponse> maintenances = maintenanceService.getMaintenancesByCriteria(carId, garageId, startDate, endDate);
        return ResponseEntity.ok(maintenances);
    }

    @GetMapping("/monthlyRequestsReport")
    public ResponseEntity<List<MaintenanceReportResponse>> generateMonthlyRequestsReport(
            @RequestParam Long garageId,
            @RequestParam String startMonth,
            @RequestParam String endMonth) {
        List<MaintenanceReportResponse> response = maintenanceService.generateMonthlyRequestsReport(garageId, startMonth, endMonth);
        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<MaintenanceResponse> addMaintenance(@RequestBody MaintenanceRequest maintenanceRequest) {
        MaintenanceResponse maintenance = maintenanceService.addMaintenance(maintenanceRequest);
        return ResponseEntity.ok(maintenance);
    }

    @PutMapping("/{id}")
    public ResponseEntity<MaintenanceResponse> modifyMaintenance(@PathVariable long id, @RequestBody MaintenanceRequest maintenanceRequest) {
        MaintenanceResponse maintenanceResponse = maintenanceService.modifyMaintenance(id, maintenanceRequest);
        return ResponseEntity.ok(maintenanceResponse);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> removeMaintenance(@PathVariable long id) {
        maintenanceService.removeMaintenance(id);
        return ResponseEntity.status(200).build();
    }
}

