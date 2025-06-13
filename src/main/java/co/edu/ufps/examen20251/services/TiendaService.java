package co.edu.ufps.examen20251.services;

import co.edu.ufps.examen20251.models.Tienda;
import co.edu.ufps.examen20251.repository.TiendaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TiendaService {
    @Autowired
    private TiendaRepository tiendaRepository;

    public List<Tienda> listarTodos() {
        return tiendaRepository.findAll();
    }

    public Optional<Tienda> buscarPorId(Integer id) {
        return tiendaRepository.findById(id);
    }

    public Optional<Tienda> buscarPorUuid(String uuid) {
        return tiendaRepository.findByUuid(uuid);
    }

    public Tienda guardar(Tienda tienda) {
        return tiendaRepository.save(tienda);
    }

    public void eliminar(Integer id) {
        tiendaRepository.deleteById(id);
    }
}
