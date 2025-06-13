package co.edu.ufps.examen20251.repository;

import co.edu.ufps.examen20251.models.Cajero;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CajeroRepository extends JpaRepository<Cajero, Integer> {
    Optional<Cajero> findByToken(String token);
}
