package uni.source.carmanagement.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uni.source.carmanagement.DTO.request.car.CarRequest;
import uni.source.carmanagement.DTO.request.car.CarUpdateRequest;
import uni.source.carmanagement.DTO.response.car.CarResponse;
import uni.source.carmanagement.entity.Car;
import uni.source.carmanagement.entity.Garage;
import uni.source.carmanagement.repository.CarRepository;
import uni.source.carmanagement.service.CarService;
import uni.source.carmanagement.service.GarageService;
import uni.source.carmanagement.util.CarSpec;
import uni.source.carmanagement.util.Mapper;

import java.util.ArrayList;
import java.util.List;

@Service
public class CarServiceImpl implements CarService {

    private final CarRepository carRepository;
    private final Mapper mapper;
    private final GarageService garageService;

    public CarServiceImpl(CarRepository carRepository, Mapper mapper, GarageService garageService) {
        this.carRepository = carRepository;
        this.mapper = mapper;
        this.garageService = garageService;
    }

    @Override
    public CarResponse getCarById(long carId) {
        return mapper.toCarResponse(carRepository.findById(carId).orElseThrow(() ->
                new RuntimeException("Car not found with ID: " + carId)));
    }

    @Override
    public CarResponse modifyCar(long carId, CarUpdateRequest request) {
        Car carToUpdate = carRepository.findById(carId).orElseThrow(() ->
                new RuntimeException("Car not found with ID: " + carId));

        if (request.getGarageIds() != null && !request.getGarageIds().isEmpty()) {
            List<Garage> newGarages = garageService.findSpecificGarages(request.getGarageIds());

            carToUpdate.getGarages().forEach(oldGarage -> oldGarage.getCars().remove(carToUpdate));
            carToUpdate.getGarages().clear();

            newGarages.forEach(newGarage -> {
                if (!newGarage.getCars().contains(carToUpdate)) {
                    newGarage.getCars().add(carToUpdate);
                }
            });
            carToUpdate.setGarages(newGarages);
        }

        mapper.toCarEntity(carToUpdate, request);

        return mapper.toCarResponse(carRepository.save(carToUpdate));
    }

    @Override
    public CarResponse addCar(CarRequest carRequest) {
        Car carToSave = mapper.toCarEntity(carRequest);
        List<Garage> garages = carRequest.getGarageIds().isEmpty() ? new ArrayList<>() :
                garageService.findSpecificGarages(carRequest.getGarageIds());

        carToSave.setGarages(garages);

        garages.forEach(garage -> {
            if (!garage.getCars().contains(carToSave)) {
                garage.getCars().add(carToSave);
            }
        });

        return mapper.toCarResponse(carRepository.save(carToSave));
    }

    @Override
    @Transactional
    public void removeCar(long carId) {
        Car carToDelete = carRepository.findById(carId).orElseThrow(() ->
                new RuntimeException("Car not found with ID: " + carId));

        carToDelete.getGarages().forEach(garage -> garage.getCars().remove(carToDelete));
        carToDelete.getMaintenances().forEach(maintenance -> maintenance.setCar(null));

        carRepository.delete(carToDelete);
    }

    @Override
    public List<CarResponse> findCarsByCriteria(String carMake, long garageId, int fromYear, int toYear) {
        var spec = CarSpec.filterCars(carMake, garageId, fromYear, toYear);
        List<Car> cars = carRepository.findAll(spec);
        return cars.stream().map(mapper::toCarResponse).toList();
    }
}
