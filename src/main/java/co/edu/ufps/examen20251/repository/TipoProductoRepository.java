package co.edu.ufps.examen20251.repository;

import co.edu.ufps.examen20251.models.Tienda;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TipoProductoRepository extends JpaRepository<Tienda, Integer> {
}
