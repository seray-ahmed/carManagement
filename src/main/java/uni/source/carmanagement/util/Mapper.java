package uni.source.carmanagement.util;

import org.springframework.stereotype.Component;
import uni.source.carmanagement.DTO.request.car.CarRequest;
import uni.source.carmanagement.DTO.request.car.CarUpdateRequest;
import uni.source.carmanagement.DTO.request.garage.GarageRequest;
import uni.source.carmanagement.DTO.request.maintenance.MaintenanceRequest;
import uni.source.carmanagement.DTO.response.car.CarResponse;
import uni.source.carmanagement.DTO.response.garage.GarageResponse;
import uni.source.carmanagement.DTO.response.maintenance.MaintenanceResponse;
import uni.source.carmanagement.entity.Car;
import uni.source.carmanagement.entity.Garage;
import uni.source.carmanagement.entity.Maintenance;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class Mapper {

    public Garage toGarageEntity(GarageRequest garageRequest){
        Garage garage = new Garage();
        garage.setCity(garageRequest.getCity());
        garage.setName(garageRequest.getName());
        garage.setCapacity(garageRequest.getCapacity());
        garage.setLocation(garageRequest.getLocation());
        return garage;
    }

    public GarageResponse toGarageRequest(Garage garage){
        return new GarageResponse(garage.getId(), garage.getName(), garage.getLocation(), garage.getCity(), garage.getCapacity());
    }

    public CarResponse toCarResponse(Car car){
        List<GarageResponse> garageResponses = car.getGarages().stream().map(this::toGarageRequest).toList();
        return new CarResponse(car.getId(), car.getMake(), car.getModel(), car.getProductionYear(), car.getLicensePlate(), garageResponses);
    }

    public Car toCarEntity(CarRequest carRequest){
        Car car = new Car();
        car.setMake(carRequest.getMake());
        car.setModel(carRequest.getModel());
        car.setProductionYear(carRequest.getProductionYear());
        car.setLicensePlate(carRequest.getLicensePlate());
        return car;
    }

    public Car toCarEntity(Car car, CarUpdateRequest carUpdateRequest){
        car.setMake(carUpdateRequest.getMake());
        car.setModel(carUpdateRequest.getModel());
        car.setProductionYear(carUpdateRequest.getProductionYear());
        car.setLicensePlate(carUpdateRequest.getLicensePlate());
        return car;
    }

    public MaintenanceResponse toMaintenanceResponse(Maintenance maintenance){
        return new MaintenanceResponse(
                maintenance.getId(),
                maintenance.getCar().getId(),
                maintenance.getCar().getModel(),
                maintenance.getServiceType(),
                maintenance.getScheduledDate(),
                maintenance.getGarage().getId(),
                maintenance.getGarage().getName());
    }

    public Maintenance toMaintenanceEntity(MaintenanceRequest maintenanceRequest, Garage garage, Car car){
        Maintenance maintenance = new Maintenance();
        maintenance.setCar(car);
        maintenance.setGarage(garage);
        maintenance.setServiceType(maintenanceRequest.getServiceType());
        maintenance.setScheduledDate(maintenanceRequest.getScheduledDate());
        return maintenance;
    }

    public Maintenance toMaintenanceEntityUpdate(Car car, Maintenance maintenance, Garage garage, MaintenanceRequest request){
        maintenance.setGarage(garage);
        maintenance.setCar(car);
        maintenance.setServiceType(request.getServiceType());
        maintenance.setScheduledDate(request.getScheduledDate());
        return maintenance;
    }
}
