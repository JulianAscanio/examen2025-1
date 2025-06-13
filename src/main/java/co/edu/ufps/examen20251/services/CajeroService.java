package co.edu.ufps.examen20251.services;

import co.edu.ufps.examen20251.models.Cajero;
import co.edu.ufps.examen20251.repository.CajeroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CajeroService {
    @Autowired
    private CajeroRepository cajeroRepository;

    public List<Cajero> listarTodos() {
        return cajeroRepository.findAll();
    }

    public Optional<Cajero> buscarPorId(Integer id) {
        return cajeroRepository.findById(id);
    }

    public Optional<Cajero> buscarPorToken(String token) {
        return cajeroRepository.findByToken(token);
    }

    public Cajero guardar(Cajero cajero) {
        return cajeroRepository.save(cajero);
    }

    public void eliminar(Integer id) {
        cajeroRepository.deleteById(id);
    }
}
