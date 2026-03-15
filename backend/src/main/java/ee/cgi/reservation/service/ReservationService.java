package ee.cgi.reservation.service;

import ee.cgi.reservation.dto.ReservationDTO;
import ee.cgi.reservation.dto.ReservationRequestDTO;
import ee.cgi.reservation.model.Customer;
import ee.cgi.reservation.model.Reservation;
import ee.cgi.reservation.model.RestaurantTable;
import ee.cgi.reservation.repository.CustomerRepository;
import ee.cgi.reservation.repository.ReservationRepository;
import ee.cgi.reservation.repository.TableRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final TableRepository tableRepository;
    private final CustomerRepository customerRepository;

    public ReservationService(ReservationRepository reservationRepository,
                              TableRepository tableRepository,
                              CustomerRepository customerRepository) {
        this.reservationRepository = reservationRepository;
        this.tableRepository = tableRepository;
        this.customerRepository = customerRepository;
    }

    public List<ReservationDTO> getAllReservations() {
        return reservationRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }


    @Transactional
    public ReservationDTO createReservation(ReservationRequestDTO request) {
        RestaurantTable table = tableRepository.findById(request.getTableId())
                .orElseThrow(() -> new RuntimeException("Table " + request.getTableId() + " not found"));

        if (table.getCapacity() < request.getPartySize()) {
            throw new IllegalArgumentException("Table capacity is insufficient for party size.");
        }

        LocalDateTime dateTime = LocalDateTime.of(
                LocalDate.parse(request.getDate()),
                LocalTime.parse(request.getTime())
        );

        if (dateTime.isBefore(LocalDateTime.now())) {
            throw new IllegalArgumentException("Cannot create a reservation in the past.");
        }

        boolean isReserved = !reservationRepository.findByRestaurantTableAndDateTimeBetween(
                table,
                dateTime.minusHours(2).plusSeconds(1),
                dateTime.plusHours(2).minusSeconds(1)
        ).isEmpty();
        if (isReserved) {
            throw new IllegalStateException("Table is already reserved for this time.");
        }

        Customer customer = customerRepository.findByPhoneNumber(request.getPhone())
                .orElseGet(() -> {
                    Customer newCustomer = new Customer();
                    String[] nameParts = (request.getCustomerName() != null ? request.getCustomerName() : "Guest").split(" ", 2);
                    newCustomer.setFirstName(nameParts[0]);
                    newCustomer.setLastName(nameParts.length > 1 ? nameParts[1] : "");
                    newCustomer.setPhoneNumber(request.getPhone());
                    return customerRepository.save(newCustomer);
                });

        Reservation reservation = new Reservation();
        reservation.setDateTime(dateTime);
        reservation.setNumberOfGuests(request.getPartySize());
        reservation.setCustomer(customer);
        reservation.setRestaurantTable(table);

        Reservation saved = reservationRepository.save(reservation);
        return convertToDTO(saved);
    }

    private ReservationDTO convertToDTO(Reservation reservation) {
        return new ReservationDTO(
                reservation.getId(),
                reservation.getDateTime(),
                reservation.getNumberOfGuests(),
                reservation.getCustomer().getId(),
                reservation.getRestaurantTable().getId()
        );
    }
}
