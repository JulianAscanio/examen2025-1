package co.edu.ufps.examen20251.repository;

import co.edu.ufps.examen20251.models.Cliente;
import co.edu.ufps.examen20251.models.TipoDocumento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Integer> {
    Optional<Cliente> findByDocumentoAndTipoDocumento(String documento, TipoDocumento tipoDocumento);
    Optional<Cliente> findByDocumento(String documento);
}
