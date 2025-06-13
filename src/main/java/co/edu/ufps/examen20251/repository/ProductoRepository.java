package co.edu.ufps.examen20251.repository;

import co.edu.ufps.examen20251.models.Producto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProductoRepository extends JpaRepository<Producto, Integer> {
    Optional<Producto> findByReferencia(String referencia);
}
