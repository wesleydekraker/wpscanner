package tools.wesley.wpscanner.repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import tools.wesley.wpscanner.domain.Administrator;

import java.util.Optional;

public interface AdministratorRepository extends CrudRepository<Administrator, Integer> {
    @Query("SELECT a FROM Administrator a WHERE a.username = ?1 AND a.password = ?2")
    Optional<Administrator> find(String username, String password);

    @Query("SELECT a FROM Administrator a WHERE a.username = ?1")
    Optional<Administrator> findByUsername(String username);
}
