package ee.cgi.reservation.service;

import ee.cgi.reservation.dto.RestaurantTableDTO;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;

@Service
public class RecommendationService {

    private final TableService tableService;

    public RecommendationService(TableService tableService) {
        this.tableService = tableService;
    }

    public RestaurantTableDTO getRecommendations(String date, String time, Integer partySize, Boolean isByTheWindow, Boolean isPrivate, Boolean smokingAllowed) {
        // Only Date and Time are strictly required for availability check
        if (date == null || time == null) {
            return null;
        }

        // Get all tables fitting the party size (ignore attributes filters to allow fallbacks)
        List<RestaurantTableDTO> allTables = tableService.getTables(date, time, partySize, null, null, null);

        // Filter for available tables only and find the one with the highest score
        return allTables.stream()
                .filter(RestaurantTableDTO::isAvailable)
                .max(Comparator.comparingInt(t ->
                        calculateScore(t, partySize, isByTheWindow, isPrivate, smokingAllowed)))
                .orElse(null);
    }

    private int calculateScore(RestaurantTableDTO t, Integer partySize, Boolean isByTheWindow, Boolean isPrivate, Boolean smokingAllowed) {
        int score = 0;
        int capacity = t.getCapacity();
        if (capacity == partySize) {
            score += 60;
        } else if (capacity > partySize) {
            if (capacity == partySize + 1) {
                score += 40;
            } else if (capacity == partySize + 2) {
                score += 20;
            } else if (capacity == partySize + 3) {
                score += 10;
            }
        }

        // AI suggested the use of Boolean.TRUE.equals() here when I let it code review
        if (Boolean.TRUE.equals(smokingAllowed) && t.isSmokingAllowed()) {
            score += 20;
        }

        if (Boolean.TRUE.equals(isByTheWindow) && t.isByTheWindow()) {
            score += 20;
        }

        if (Boolean.TRUE.equals(isPrivate) && t.isPrivate()) {
            score += 20;
        }

        return score;
    }
}
