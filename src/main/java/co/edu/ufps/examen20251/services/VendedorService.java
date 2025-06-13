package co.edu.ufps.examen20251.services;

import co.edu.ufps.examen20251.models.Vendedor;
import co.edu.ufps.examen20251.repository.VendedorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class VendedorService {
    @Autowired
    private VendedorRepository vendedorRepository;

    public List<Vendedor> listarTodos() {
        return vendedorRepository.findAll();
    }

    public Optional<Vendedor> buscarPorId(Integer id) {
        return vendedorRepository.findById(id);
    }

    public Optional<Vendedor> buscarPorDocumento(String documento) {
        return vendedorRepository.findByDocumento(documento);
    }

    public Vendedor guardar(Vendedor vendedor) {
        return vendedorRepository.save(vendedor);
    }

    public void eliminar(Integer id) {
        vendedorRepository.deleteById(id);
    }
}
