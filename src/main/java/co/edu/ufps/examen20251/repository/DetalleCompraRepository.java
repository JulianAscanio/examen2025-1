package co.edu.ufps.examen20251.repository;

import co.edu.ufps.examen20251.models.DetalleCompra;
import co.edu.ufps.examen20251.models.Vendedor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DetalleCompraRepository extends JpaRepository<Vendedor, Integer> {
    List<DetalleCompra> findByCompraId(Integer compraId);
}
