package uni.source.carmanagement.DTO.response.maintenance;

public class MaintenanceReportResponse {
    private String yearMonth;
    private long requests;

    public MaintenanceReportResponse() {
    }

    public MaintenanceReportResponse(String yearMonth, long requests) {
        this.yearMonth = yearMonth;
        this.requests = requests;
    }

    public String getYearMonth() {
        return yearMonth;
    }

    public void setYearMonth(String yearMonth) {
        this.yearMonth = yearMonth;
    }

    public long getRequests() {
        return requests;
    }

    public void setRequests(long requests) {
        this.requests = requests;
    }
}
