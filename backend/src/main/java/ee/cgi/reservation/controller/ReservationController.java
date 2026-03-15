package ee.cgi.reservation.controller;

import ee.cgi.reservation.dto.ReservationDTO;
import ee.cgi.reservation.dto.ReservationRequestDTO;
import ee.cgi.reservation.service.ReservationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reservations")
public class ReservationController {

    private final ReservationService reservationService;

    public ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @GetMapping
    public List<ReservationDTO> getAllReservations() {
        return reservationService.getAllReservations();
    }

    @PostMapping
    public ResponseEntity<?> createReservation(@RequestBody ReservationRequestDTO request) {
        System.out.println("Received reservation request for: " + request.getCustomerName() + " at " + request.getDate() + " " + request.getTime());
        try {
            ReservationDTO createdReservation = reservationService.createReservation(request);
            System.out.println("Reservation successfully created with ID: " + createdReservation.getId());
            return new ResponseEntity<>(createdReservation, HttpStatus.CREATED);
        } catch (IllegalArgumentException | IllegalStateException e) {
            System.err.println("Reservation failed: " + e.getMessage());
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (RuntimeException e) {
            System.err.println("Reservation error: " + e.getMessage());
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
