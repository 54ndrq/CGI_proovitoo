package ee.cgi.reservation.controller;

import ee.cgi.reservation.dto.RestaurantTableDTO;
import ee.cgi.reservation.service.RecommendationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/recommendations")
public class RecommendationController {

    private final RecommendationService recommendationService;

    public RecommendationController(RecommendationService recommendationService) {
        this.recommendationService = recommendationService;
    }

    @GetMapping
    public ResponseEntity<RestaurantTableDTO> getRecommendation(
            @RequestParam String date,
            @RequestParam String time,
            @RequestParam Integer partySize,
            @RequestParam(required = false) Boolean isByTheWindow,
            @RequestParam(required = false) Boolean isPrivate,
            @RequestParam(required = false) Boolean smokingAllowed
    ) {
        RestaurantTableDTO recommendation = recommendationService.getRecommendations(date, time, partySize, isByTheWindow, isPrivate, smokingAllowed);
        if (recommendation == null) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(recommendation);
    }
}
