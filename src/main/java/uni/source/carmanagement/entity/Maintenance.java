package uni.source.carmanagement.entity;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
public class Maintenance {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne()
    private Garage garage;

    @ManyToOne()
    private Car car;

    private String serviceType;

    private LocalDate scheduledDate;

    public Maintenance() {
    }

    public Maintenance(long id, Garage garage, Car car, String serviceType, LocalDate scheduledDate) {
        this.id = id;
        this.garage = garage;
        this.car = car;
        this.serviceType = serviceType;
        this.scheduledDate = scheduledDate;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Garage getGarage() {
        return garage;
    }

    public void setGarage(Garage garage) {
        this.garage = garage;
    }

    public Car getCar() {
        return car;
    }

    public void setCar(Car car) {
        this.car = car;
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
}
