package uni.source.carmanagement.DTO.request.garage;

import java.time.LocalDate;

public class GarageReportResponse {
    private LocalDate date;
    private long requests;
    private long availableCapacity;

    public GarageReportResponse(LocalDate date, long requests, long availableCapacity) {
        this.date = date;
        this.requests = requests;
        this.availableCapacity = availableCapacity;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public long getRequests() {
        return requests;
    }

    public void setRequests(long requests) {
        this.requests = requests;
    }

    public long getAvailableCapacity() {
        return availableCapacity;
    }

    public void setAvailableCapacity(long availableCapacity) {
        this.availableCapacity = availableCapacity;
    }
}