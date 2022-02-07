package tools.wesley.wpscanner.repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import tools.wesley.wpscanner.domain.Plugin;

import java.util.Optional;

public interface PluginRepository extends CrudRepository<Plugin, Integer> {
    @Query("SELECT p FROM Plugin p WHERE p.directoryName = ?1")
    Optional<Plugin> findByDirectoryName(String directoryName);
}
