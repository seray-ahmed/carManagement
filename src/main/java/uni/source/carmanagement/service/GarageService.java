package uni.source.carmanagement.service;

import uni.source.carmanagement.DTO.request.garage.GarageReportResponse;
import uni.source.carmanagement.DTO.request.garage.GarageRequest;
import uni.source.carmanagement.DTO.response.garage.GarageResponse;
import uni.source.carmanagement.entity.Garage;

import java.time.LocalDate;
import java.util.List;

public interface GarageService {
    GarageResponse saveGarage(GarageRequest garageRequest);
    GarageResponse fetchGarage(long id);
    GarageResponse updateGarage(GarageRequest garageRequest, long id);
    void deleteGarage(long id);
    List<GarageResponse> fetchAllGarages(String city);
    List<Garage> findSpecificGarages(List<Long> garageIds);
    List<GarageReportResponse> getCapacityReport(Long garageId, LocalDate startDate, LocalDate endDate);
}
