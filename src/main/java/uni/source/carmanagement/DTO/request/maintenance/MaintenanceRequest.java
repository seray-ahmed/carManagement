package uni.source.carmanagement.DTO.request.maintenance;

import java.time.LocalDate;

public class MaintenanceRequest {
    private Long carId;
    private String serviceType;
    private LocalDate scheduledDate;
    private Long garageId;

    public MaintenanceRequest() {
    }

    public MaintenanceRequest(Long carId, String serviceType, LocalDate scheduledDate, Long garageId) {
        this.carId = carId;
        this.serviceType = serviceType;
        this.scheduledDate = scheduledDate;
        this.garageId = garageId;
    }

    public Long getCarId() {
        return carId;
    }

    public void setCarId(Long carId) {
        this.carId = carId;
    }

    public String getServiceType() {
        return serviceType;
    }

    public void setServiceType(String serviceType) {
        this.serviceType = serviceType;
    }

    public LocalDate getScheduledDate() {
        return scheduledDate;
    }

    public void setScheduledDate(LocalDate scheduledDate) {
        this.scheduledDate = scheduledDate;
    }

    public Long getGarageId() {
        return garageId;
    }

    public void setGarageId(Long garageId) {
        this.garageId = garageId;
    }
}
