package uni.source.carmanagement.service.impl;

import uni.source.carmanagement.DTO.request.car.CarRequest;
import uni.source.carmanagement.DTO.request.car.CarUpdateRequest;
import uni.source.carmanagement.DTO.response.car.CarResponse;
import uni.source.carmanagement.service.CarService;

import java.util.List;

public class CarServiceImpl implements CarService {
    @Override
    public CarResponse getCarById(long carId) {
        return null;
    }

    @Override
    public CarResponse modifyCar(long carId, CarUpdateRequest request) {
        return null;
    }

    @Override
    public CarResponse addCar(CarRequest carRequest) {
        return null;
    }

    @Override
    public void removeCar(long carId) {

    }

    @Override
    public List<CarResponse> findCarsByCriteria(String carMake, long garageId, int fromYear, int toYear) {
        return List.of();
    }
}
