package uni.source.carmanagement.util;

import org.springframework.stereotype.Component;
import uni.source.carmanagement.DTO.request.garage.GarageRequest;
import uni.source.carmanagement.DTO.response.garage.GarageResponse;
import uni.source.carmanagement.entity.Garage;

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
}
