package tools.wesley.wpscanner.repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import tools.wesley.wpscanner.domain.Theme;

import java.util.Optional;

public interface ThemeRepository extends CrudRepository<Theme, Integer> {
    @Query("SELECT t FROM Theme t WHERE t.directoryName = ?1")
    Optional<Theme> findByDirectoryName(String directoryName);
}
