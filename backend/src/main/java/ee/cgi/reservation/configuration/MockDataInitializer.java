package ee.cgi.reservation.configuration;

import ee.cgi.reservation.model.Customer;
import ee.cgi.reservation.model.Reservation;
import ee.cgi.reservation.model.RestaurantTable;
import ee.cgi.reservation.repository.CustomerRepository;
import ee.cgi.reservation.repository.ReservationRepository;
import ee.cgi.reservation.repository.TableRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Random;

@Component
@Profile("!test")
public class MockDataInitializer implements CommandLineRunner {

    private final ReservationRepository reservationRepository;
    private final TableRepository tableRepository;
    private final CustomerRepository customerRepository;

    public MockDataInitializer(ReservationRepository reservationRepository,
                               TableRepository tableRepository,
                               CustomerRepository customerRepository) {
        this.reservationRepository = reservationRepository;
        this.tableRepository = tableRepository;
        this.customerRepository = customerRepository;
    }

    @Override
    public void run(String... args) throws Exception {

        List<RestaurantTable> tables = tableRepository.findAll();
        List<Customer> customers = customerRepository.findAll();

        if (tables.isEmpty() || customers.isEmpty()) {
            return;
        }

        Random random = new Random();
        LocalDate today = LocalDate.now();

        // Generate reservations for the next 14 days
        // I used my IDEs AI agent to generate mock data here
        for (int i = 0; i < 14; i++) {
            LocalDate date = today.plusDays(i);

            // Available hours for booking 12:00 - 23:00
            for (int hour = 12; hour <= 23; hour++) {

                for (RestaurantTable table : tables) {
                    if (random.nextDouble() < 0.2) {
                        LocalDateTime dateTime = LocalDateTime.of(date, LocalTime.of(hour, 0));

                        if (reservationRepository.findByRestaurantTableAndDateTimeBetween(
                                table,
                                dateTime.minusHours(2).plusSeconds(1),
                                dateTime.plusHours(2).minusSeconds(1)
                        ).isEmpty()) {
                            Reservation reservation = new Reservation();
                            reservation.setDateTime(dateTime);

                            reservation.setNumberOfGuests(random.nextInt(table.getCapacity()) + 1);
                            reservation.setRestaurantTable(table);

                            reservation.setCustomer(customers.get(random.nextInt(customers.size())));

                            reservationRepository.save(reservation);
                        }
                    }
                }
            }
        }
        System.out.println("Mock data initialized: Added random reservations for the next 14 days.");
    }
}
