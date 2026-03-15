package ee.cgi.reservation.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/time-slots")
public class SlotController {

    @GetMapping
    public List<String> getAllSlots() {
        // I used AIs help with this loop logic to make sure the time formatting was correct.
        List<String> slots = new ArrayList<>();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
        for (int hour = 12; hour <= 23; hour++) {
            slots.add(LocalTime.of(hour, 0).format(formatter));
        }
        return slots;
    }
}
