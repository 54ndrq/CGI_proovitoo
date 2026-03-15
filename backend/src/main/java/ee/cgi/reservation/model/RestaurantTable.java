package ee.cgi.reservation.model;

import jakarta.persistence.*;

@Entity
public class RestaurantTable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int capacity;
    private String availabilityStatus;

    @ManyToOne
    @JoinColumn(name = "area_id")
    private Area area;

    private boolean isByTheWindow;
    private boolean isPrivate;
    private boolean smokingAllowed;

    public RestaurantTable() {}

    public RestaurantTable(int capacity, String availabilityStatus, Area area, boolean isByTheWindow, boolean isPrivate, boolean smokingAllowed) {
        this.capacity = capacity;
        this.availabilityStatus = availabilityStatus;
        this.area = area;
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

    public Area getArea() {
        return area;
    }

    public void setArea(Area area) {
        this.area = area;
    }

    public boolean isByTheWindow() {
        return isByTheWindow;
    }

    public void setByTheWindow(boolean byTheWindow) {
        isByTheWindow = byTheWindow;
    }

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
