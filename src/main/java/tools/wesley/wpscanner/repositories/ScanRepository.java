package tools.wesley.wpscanner.repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import tools.wesley.wpscanner.domain.Scan;

import java.util.Optional;

public interface ScanRepository extends CrudRepository<Scan, Integer> {
    @Query("SELECT s FROM Scan s WHERE s.guid = ?1")
    Optional<Scan> findByGuid(String guid);
}
