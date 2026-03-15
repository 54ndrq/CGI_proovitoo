package ee.cgi.reservation.dto;

import java.time.LocalDateTime;

public class ReservationDTO {
    private Long id;
    private LocalDateTime dateTime;
    private int numberOfGuests;
    private Long customerId;
    private Long tableId;

    public ReservationDTO() {}

    public ReservationDTO(Long id, LocalDateTime dateTime, int numberOfGuests, Long customerId, Long tableId) {
        this.id = id;
        this.dateTime = dateTime;
        this.numberOfGuests = numberOfGuests;
        this.customerId = customerId;
        this.tableId = tableId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public int getNumberOfGuests() {
        return numberOfGuests;
    }

    public void setNumberOfGuests(int numberOfGuests) {
        this.numberOfGuests = numberOfGuests;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public Long getTableId() {
        return tableId;
    }

    public void setTableId(Long tableId) {
        this.tableId = tableId;
    }
}
