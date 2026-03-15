package ee.cgi.reservation.repository;

import ee.cgi.reservation.model.Reservation;
import ee.cgi.reservation.model.RestaurantTable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {
    List<Reservation> findByRestaurantTableAndDateTimeBetween(RestaurantTable restaurantTable, LocalDateTime start, LocalDateTime end);
}
