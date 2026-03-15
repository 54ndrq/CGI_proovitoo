package ee.cgi.reservation.service;

import ee.cgi.reservation.dto.RestaurantTableDTO;
import ee.cgi.reservation.model.RestaurantTable;
import ee.cgi.reservation.repository.ReservationRepository;
import ee.cgi.reservation.repository.TableRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TableService {

    private final TableRepository tableRepository;
    private final ReservationRepository reservationRepository;

    public TableService(TableRepository tableRepository, ReservationRepository reservationRepository) {
        this.tableRepository = tableRepository;
        this.reservationRepository = reservationRepository;
    }

    public List<RestaurantTableDTO> getTables(String date, String time, Integer partySize, Boolean isByTheWindow, Boolean isPrivate, Boolean smokingAllowed) {
        List<RestaurantTable> tables = tableRepository.findAll();

        LocalDateTime requestedDateTime;
        if (date != null && !date.isEmpty() && time != null && !time.isEmpty()) {
            requestedDateTime = LocalDateTime.of(LocalDate.parse(date), LocalTime.parse(time));
        } else {
            requestedDateTime = null;
        }

        // Used AI assistant here to help structure the Java Stream filtering and
        // refine the LocalDateTime boundary calculations for checking overlapping reservations.
        return tables.stream()
                .filter(table -> partySize == null || table.getCapacity() >= partySize)
                .filter(table -> isByTheWindow == null || !isByTheWindow || table.isByTheWindow())
                .filter(table -> isPrivate == null || !isPrivate || table.isPrivate())
                .filter(table -> smokingAllowed == null || !smokingAllowed || table.isSmokingAllowed())
                .map(table -> {
                    boolean isAvailable = "AVAILABLE".equals(table.getAvailabilityStatus());

                    if (isAvailable && requestedDateTime != null) {
                        LocalTime requestTime = requestedDateTime.toLocalTime();

                        // Check available hours 12:00 - 23:00
                        if (requestTime.isBefore(LocalTime.of(12, 0)) || requestTime.isAfter(LocalTime.of(23, 0))) {
                            isAvailable = false;
                        } else {
                            // Check if there is an existing reservation for this table at the requested time
                            boolean hasReservation = !reservationRepository.findByRestaurantTableAndDateTimeBetween(
                                    table,
                                    requestedDateTime.minusHours(2).plusSeconds(1),
                                    requestedDateTime.plusHours(2).minusSeconds(1)
                            ).isEmpty();
                            if (hasReservation) {
                                isAvailable = false;
                            }
                        }
                    }

                    return new RestaurantTableDTO(
                            table.getId(),
                            table.getCapacity(),
                            table.getAvailabilityStatus(),
                            table.getArea() != null ? table.getArea().getId() : null,
                            table.getArea() != null ? table.getArea().getName() : "Unknown",
                            isAvailable,
                            table.isByTheWindow(),
                            table.isPrivate(),
                            table.isSmokingAllowed()
                    );
                })
                .collect(Collectors.toList());
    }
}
