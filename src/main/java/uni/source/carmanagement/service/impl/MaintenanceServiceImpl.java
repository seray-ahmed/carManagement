package uni.source.carmanagement.service.impl;

import uni.source.carmanagement.DTO.request.maintenance.MaintenanceRequest;
import uni.source.carmanagement.DTO.response.maintenance.MaintenanceReportResponse;
import uni.source.carmanagement.DTO.response.maintenance.MaintenanceResponse;
import uni.source.carmanagement.entity.Car;
import uni.source.carmanagement.entity.Garage;
import uni.source.carmanagement.entity.Maintenance;
import uni.source.carmanagement.repository.CarRepository;
import uni.source.carmanagement.repository.GarageRepository;
import uni.source.carmanagement.repository.MaintenanceRepository;
import uni.source.carmanagement.service.MaintenanceService;
import uni.source.carmanagement.util.MaintenanceSpec;
import uni.source.carmanagement.util.Mapper;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class MaintenanceServiceImpl implements MaintenanceService {

    private final MaintenanceRepository maintenanceRepository;
    private final GarageRepository garageRepository;
    private final CarRepository carRepository;
    private final Mapper mapper;

    public MaintenanceServiceImpl(MaintenanceRepository maintenanceRepository,
                                  GarageRepository garageRepository,
                                  CarRepository carRepository,
                                  Mapper mapper) {
        this.maintenanceRepository = maintenanceRepository;
        this.garageRepository = garageRepository;
        this.carRepository = carRepository;
        this.mapper = mapper;
    }

    @Override
    public MaintenanceResponse getMaintenanceById(Long id) {
        Maintenance maintenance = maintenanceRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Maintenance with ID " + id + " not found!"));
        return mapper.toMaintenanceResponse(maintenance);
    }

    @Override
    public List<MaintenanceResponse> getMaintenancesByCriteria(Long carId, Long garageId, LocalDate startDate, LocalDate endDate) {
        Specification<Maintenance> spec = MaintenanceSpec.filterMaintenance(carId, garageId, startDate, endDate);
        List<Maintenance> maintenances = maintenanceRepository.findAll(spec);
        return maintenances.stream()
                .map(mapper::toMaintenanceResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<MaintenanceReportResponse> generateMonthlyRequestsReport(Long garageId, String startMonth, String endMonth) {
        LocalDate startDate = LocalDate.parse(startMonth + "-01");
        LocalDate endDate = LocalDate.parse(endMonth + "-01");

        List<Object[]> rawData = maintenanceRepository.getMonthlyRequestsReport(garageId, startDate, endDate);

        Map<String, Long> dataMap = rawData.stream()
                .collect(Collectors.toMap(
                        row -> (String) row[0],
                        row -> ((Number) row[1]).longValue()
                ));

        List<MaintenanceReportResponse> report = new ArrayList<>();
        LocalDate current = startDate;
        while (!current.isAfter(endDate)) {
            String month = current.format(DateTimeFormatter.ofPattern("yyyy-MM"));
            long requests = dataMap.getOrDefault(month, 0L);
            report.add(new MaintenanceReportResponse(month, requests));
            current = current.plusMonths(1);
        }

        return report;
    }

    @Override
    public MaintenanceResponse addMaintenance(MaintenanceRequest maintenanceRequest) {
        Car car = carRepository.findById(maintenanceRequest.getCarId())
                .orElseThrow(() -> new RuntimeException("Car with ID " + maintenanceRequest.getCarId() + " not found!"));
        Garage garage = garageRepository.findById(maintenanceRequest.getGarageId())
                .orElseThrow(() -> new RuntimeException("Garage with ID " + maintenanceRequest.getGarageId() + " not found!"));

        int garageBusyness = maintenanceRepository.countMaintenanceEntitiesByGarageAndScheduledDate(garage, maintenanceRequest.getScheduledDate());
        if (garageBusyness >= garage.getCapacity()) {
            throw new RuntimeException("Garage capacity exceeded!");
        }

        Maintenance maintenance = mapper.toMaintenanceEntity(maintenanceRequest, garage, car);
        return mapper.toMaintenanceResponse(maintenanceRepository.save(maintenance));
    }

    @Override
    public MaintenanceResponse modifyMaintenance(long id, MaintenanceRequest maintenanceRequest) {
        Maintenance maintenance = maintenanceRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Maintenance with ID " + id + " not found!"));
        Car car = carRepository.findById(maintenanceRequest.getCarId())
                .orElseThrow(() -> new RuntimeException("Car with ID " + maintenanceRequest.getCarId() + " not found!"));
        Garage garage = garageRepository.findById(maintenanceRequest.getGarageId())
                .orElseThrow(() -> new RuntimeException("Garage with ID " + maintenanceRequest.getGarageId() + " not found!"));

        mapper.toMaintenanceEntityUpdate(car, maintenance, garage, maintenanceRequest);
        return mapper.toMaintenanceResponse(maintenanceRepository.save(maintenance));
    }

    @Override
    public void removeMaintenance(long id) {
        if (!maintenanceRepository.existsById(id)) {
            throw new RuntimeException("Maintenance with ID " + id + " not found!");
        }
        maintenanceRepository.deleteById(id);
    }
}
