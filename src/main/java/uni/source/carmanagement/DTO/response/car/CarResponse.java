package uni.source.carmanagement.DTO.response.car;

import uni.source.carmanagement.DTO.response.garage.GarageResponse;

import java.util.List;

public class CarResponse {
    private long id;
    private String make;
    private String model;
    private int productionYear;
    private String licensePlate;
    private List<GarageResponse> garages;

    public CarResponse(long id, String make, String model, int productionYear, String licensePlate, List<GarageResponse> garages) {
        this.id = id;
        this.make = make;
        this.model = model;
        this.productionYear = productionYear;
        this.licensePlate = licensePlate;
        this.garages = garages;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getMake() {
        return make;
    }

    public void setMake(String make) {
        this.make = make;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public int getProductionYear() {
        return productionYear;
    }

    public void setProductionYear(int productionYear) {
        this.productionYear = productionYear;
    }

    public String getLicensePlate() {
        return licensePlate;
    }

    public void setLicensePlate(String licensePlate) {
        this.licensePlate = licensePlate;
    }

    public List<GarageResponse> getGarages() {
        return garages;
    }

    public void setGarages(List<GarageResponse> garages) {
        this.garages = garages;
    }
}
