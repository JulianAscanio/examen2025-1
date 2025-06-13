package co.edu.ufps.examen20251.repository;

import co.edu.ufps.examen20251.models.Tienda;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TiendaRepository extends JpaRepository<Tienda, Integer> {
    Optional<Tienda> findByUuid(String uuid);
}
