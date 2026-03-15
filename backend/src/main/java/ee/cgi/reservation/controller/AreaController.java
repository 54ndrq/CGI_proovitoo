package ee.cgi.reservation.controller;

import ee.cgi.reservation.dto.AreaDTO;
import ee.cgi.reservation.model.Area;
import ee.cgi.reservation.repository.AreaRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/areas")
public class AreaController {

    private final AreaRepository areaRepository;

    public AreaController(AreaRepository areaRepository) {
        this.areaRepository = areaRepository;
    }

    @GetMapping
    public List<AreaDTO> getAllAreas() {
        return areaRepository.findAll().stream()
                .map(area -> new AreaDTO(area.getId(), area.getName()))
                .collect(Collectors.toList());
    }
}
