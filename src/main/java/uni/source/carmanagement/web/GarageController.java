package uni.source.carmanagement.web;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uni.source.carmanagement.DTO.request.GarageReportResponse;
import uni.source.carmanagement.DTO.request.GarageRequest;
import uni.source.carmanagement.DTO.response.GarageResponse;
import uni.source.carmanagement.service.GarageService;

import java.time.LocalDate;
import java.util.List;

public class GarageController {

    private final GarageService garageService;

    public GarageController(GarageService garageService) {
        this.garageService = garageService;
    }

    @PostMapping()
    public ResponseEntity<GarageResponse> addGarage(@RequestBody GarageRequest garageRequest) {
        GarageResponse garageResponse = garageService.saveGarage(garageRequest);
        return ResponseEntity.status(200).body(garageResponse);
    }

    @GetMapping("/{id}")
    public ResponseEntity<GarageResponse> getGarageById(@PathVariable long id) {
        GarageResponse garage = garageService.fetchGarage(id);
        return ResponseEntity.status(200).body(garage);
    }

    @PutMapping("/{id}")
    public ResponseEntity<GarageResponse> modifyGarage(@PathVariable long id, @RequestBody GarageRequest garageRequest) {
        GarageResponse updatedGarage = garageService.updateGarage(garageRequest, id);
        return ResponseEntity.status(200).body(updatedGarage);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> removeGarage(@PathVariable long id) {
        this.garageService.deleteGarage(id);
        return ResponseEntity.status(200).build();
    }

    @GetMapping()
    public ResponseEntity<List<GarageResponse>> getAllGarages(@RequestParam(required = false) String city) {
        List<GarageResponse> garages = garageService.fetchAllGarages(city);
        return ResponseEntity.status(200).body(garages);
    }

    @GetMapping("/dailyAvailabilityReport")
    public List<GarageReportResponse> generateCapacityReport(
            @RequestParam Long garageId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        return garageService.getCapacityReport(garageId, startDate, endDate);
    }
}
