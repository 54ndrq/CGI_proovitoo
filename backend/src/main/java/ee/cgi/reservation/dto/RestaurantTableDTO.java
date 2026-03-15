package ee.cgi.reservation.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class RestaurantTableDTO {

    private Long id;
    private int capacity;
    private String availabilityStatus;
    private Long areaId;
    private String location;
    private boolean available;
    private boolean isByTheWindow;
    private boolean isPrivate;
    private boolean smokingAllowed;

    public RestaurantTableDTO() {}

    public RestaurantTableDTO(Long id, int capacity, String availabilityStatus, Long areaId, String location, boolean available, boolean isByTheWindow, boolean isPrivate, boolean smokingAllowed) {
        this.id = id;
        this.capacity = capacity;
        this.availabilityStatus = availabilityStatus;
        this.areaId = areaId;
        this.location = location;
        this.available = available;
        this.isByTheWindow = isByTheWindow;
        this.isPrivate = isPrivate;
        this.smokingAllowed = smokingAllowed;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public String getAvailabilityStatus() {
        return availabilityStatus;
    }

    public void setAvailabilityStatus(String availabilityStatus) {
        this.availabilityStatus = availabilityStatus;
    }

    public Long getAreaId() {
        return areaId;
    }

    public void setAreaId(Long areaId) {
        this.areaId = areaId;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    @JsonProperty("isByTheWindow")
    public boolean isByTheWindow() {
        return isByTheWindow;
    }

    public void setByTheWindow(boolean byTheWindow) {
        isByTheWindow = byTheWindow;
    }

    @JsonProperty("isPrivate")
    public boolean isPrivate() {
        return isPrivate;
    }

    public void setPrivate(boolean aPrivate) {
        isPrivate = aPrivate;
    }

    public boolean isSmokingAllowed() {
        return smokingAllowed;
    }

    public void setSmokingAllowed(boolean smokingAllowed) {
        this.smokingAllowed = smokingAllowed;
    }
}
