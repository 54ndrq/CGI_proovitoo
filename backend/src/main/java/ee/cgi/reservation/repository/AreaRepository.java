package ee.cgi.reservation.repository;

import ee.cgi.reservation.model.Area;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AreaRepository extends JpaRepository<Area, Long> {
}
