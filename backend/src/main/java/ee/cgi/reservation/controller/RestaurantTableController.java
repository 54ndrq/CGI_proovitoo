package ee.cgi.reservation.controller;

import ee.cgi.reservation.dto.RestaurantTableDTO;
import ee.cgi.reservation.service.TableService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tables")
public class RestaurantTableController {

    private final TableService tableService;

    public RestaurantTableController(TableService tableService) {
        this.tableService = tableService;
    }

    @GetMapping
    public List<RestaurantTableDTO> getTables(
            @RequestParam(required = false) String date,
            @RequestParam(required = false) String time,
            @RequestParam(required = false) Integer partySize,
            @RequestParam(required = false) Boolean isByTheWindow,
            @RequestParam(required = false) Boolean isPrivate,
            @RequestParam(required = false) Boolean smokingAllowed
    ) {
        return tableService.getTables(date, time, partySize, isByTheWindow, isPrivate, smokingAllowed);
    }
}
