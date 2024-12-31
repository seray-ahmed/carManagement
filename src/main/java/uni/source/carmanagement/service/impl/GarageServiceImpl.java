package uni.source.carmanagement.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uni.source.carmanagement.DTO.request.garage.GarageRequest;
import uni.source.carmanagement.DTO.response.garage.GarageResponse;
import uni.source.carmanagement.DTO.request.garage.GarageReportResponse;
import uni.source.carmanagement.entity.Garage;
import uni.source.carmanagement.repository.GarageRepository;
import uni.source.carmanagement.repository.MaintenanceRepository;
import uni.source.carmanagement.util.Mapper;
import uni.source.carmanagement.service.GarageService;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class GarageServiceImpl implements GarageService {

    private final GarageRepository garageRepository;
    private final MaintenanceRepository maintenanceRepository;
    private final Mapper mapper;

    public GarageServiceImpl(GarageRepository garageRepository, MaintenanceRepository maintenanceRepository, Mapper mapper) {
        this.garageRepository = garageRepository;
        this.maintenanceRepository = maintenanceRepository;
        this.mapper = mapper;
    }

    @Override
    public GarageResponse saveGarage(GarageRequest garageRequest) {
        Garage garage = mapper.toGarageEntity(garageRequest);
        Garage savedGarage = garageRepository.save(garage);
        return mapper.toGarageRequest(savedGarage);
    }

    @Override
    public GarageResponse fetchGarage(long id) {
        Garage garage = garageRepository.findById(id)
                .orElseThrow(() -> new RuntimeException(String.format("Garage with id %d not found!", id)));
        return mapper.toGarageRequest(garage);
    }

    @Override
    public GarageResponse updateGarage(GarageRequest garageRequest, long id) {
        Garage garage = garageRepository.findById(id)
                .orElseThrow(() -> new RuntimeException(String.format("Garage with id %d not found!", id)));

        garage.setName(garageRequest.getName());
        garage.setCity(garageRequest.getCity());
        garage.setLocation(garageRequest.getLocation());
        garage.setCapacity(garageRequest.getCapacity());

        Garage updatedGarage = garageRepository.save(garage);
        return mapper.toGarageRequest(updatedGarage);
    }

    @Override
    @Transactional
    public void deleteGarage(long id) {
        Garage garage = garageRepository.findById(id)
                .orElseThrow(() -> new RuntimeException(String.format("Garage with id %d not found!", id)));
        garage.getCars().forEach(car -> car.getGarages().remove(garage));
        garage.getMaintenances().forEach(maintenance -> maintenance.setGarage(null));

        garageRepository.delete(garage);
    }

    @Override
    public List<GarageResponse> fetchAllGarages(String city) {
        List<Garage> garages = (city == null)
                ? garageRepository.findAll()
                : garageRepository.findAllByNameStartingWith(city);

        return garages.stream()
                .map(mapper::toGarageRequest)
                .collect(Collectors.toList());
    }

    @Override
    public List<GarageReportResponse> getCapacityReport(Long garageId, LocalDate startDate, LocalDate endDate) {
        Garage garage = garageRepository.findById(garageId)
                .orElseThrow(() -> new RuntimeException("Garage not found"));

        List<Object[]> maintenanceData = maintenanceRepository.findMaintenanceRequestsByDate(garageId, startDate, endDate);

        Map<String, Long> maintenanceMap = maintenanceData.stream()
                .collect(Collectors.toMap(
                        row -> row[0].toString(),
                        row -> (Long) row[1]
                ));

        List<GarageReportResponse> report = new ArrayList<>();
        for (LocalDate date = startDate; !date.isAfter(endDate); date = date.plusDays(1)) {
            long requests = maintenanceMap.getOrDefault(date.toString(), 0L);
            long availableCapacity = garage.getCapacity() - requests;

            report.add(new GarageReportResponse(date, requests, availableCapacity));
        }

        return report;
    }
}
