package uni.source.carmanagement.service.impl;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uni.source.carmanagement.DTO.request.car.CarRequest;
import uni.source.carmanagement.DTO.request.car.CarUpdateRequest;
import uni.source.carmanagement.DTO.response.car.CarResponse;
import uni.source.carmanagement.service.CarService;

import java.util.List;

@RestController
@RequestMapping("/cars")
public class CarController {

    private final CarService carService;

    public CarController(CarService carService) {
        this.carService = carService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<CarResponse> getCarById(@PathVariable long id) {
        CarResponse car = carService.getCarById(id);
        return ResponseEntity.status(200).body(car);
    }

    @GetMapping()
    public ResponseEntity<List<CarResponse>> getCarsByCriteria(@RequestParam(required = false) String carMake,
                                                               @RequestParam(required = false) Long garageId,
                                                               @RequestParam(required = false) Integer fromYear,
                                                               @RequestParam(required = false) Integer toYear) {
        if (garageId == null) garageId = 0L;
        if (fromYear == null) fromYear = 0;
        if (toYear == null) toYear = 2100;

        List<CarResponse> carsByCriteria = carService.findCarsByCriteria(carMake, garageId, fromYear, toYear);
        return ResponseEntity.status(200).body(carsByCriteria);
    }

    @PostMapping()
    public ResponseEntity<CarResponse> addNewCar(@RequestBody CarRequest carRequest) {
        CarResponse car = carService.addCar(carRequest);
        return ResponseEntity.status(200).body(car);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CarResponse> modifyCarDetails(@PathVariable long id, @RequestBody CarUpdateRequest carUpdateRequest) {
        CarResponse car = carService.modifyCar(id, carUpdateRequest);
        return ResponseEntity.status(200).body(car);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> removeCar(@PathVariable long id) {
        carService.removeCar(id);
        return ResponseEntity.status(200).build();
    }
}