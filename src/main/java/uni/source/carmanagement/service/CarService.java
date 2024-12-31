package uni.source.carmanagement.service;

import uni.source.carmanagement.DTO.request.CarRequest;
import uni.source.carmanagement.DTO.request.CarUpdateRequest;
import uni.source.carmanagement.DTO.response.CarResponse;

import java.util.List;

public interface CarService {
    CarResponse getCarById(long carId);
    CarResponse modifyCar(long carId, CarUpdateRequest request);
    CarResponse addCar(CarRequest carRequest);
    void removeCar(long carId);
    List<CarResponse> findCarsByCriteria(String carMake, long garageId, int fromYear, int toYear);

}
