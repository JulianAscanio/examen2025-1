package co.edu.ufps.examen20251.repository;

import co.edu.ufps.examen20251.models.Compra;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CompraRepository extends JpaRepository<Compra, Integer> {
    @Query("SELECT c FROM Compra c WHERE c.id = :id AND c.cliente.id = :clienteId AND c.tienda.id = :tiendaId")
    Optional<Compra> findByIdAndClienteIdAndTiendaId(
            @Param("id") Integer id,
            @Param("clienteId") Integer clienteId,
            @Param("tiendaId") Integer tiendaId);
}
