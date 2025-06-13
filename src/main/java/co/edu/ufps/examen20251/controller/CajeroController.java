package co.edu.ufps.examen20251.controller;

import co.edu.ufps.examen20251.models.Cajero;
import co.edu.ufps.examen20251.services.CajeroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cajeros")
public class CajeroController {

    @Autowired
    private CajeroService cajeroService;

    @GetMapping
    public ResponseEntity<List<Cajero>> listarCajeros() {
        return ResponseEntity.ok(cajeroService.listarTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Cajero> obtenerCajero(@PathVariable Integer id) {
        return cajeroService.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/token/{token}")
    public ResponseEntity<Cajero> obtenerPorToken(@PathVariable String token) {
        return cajeroService.buscarPorToken(token)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Cajero> crearCajero(@RequestBody Cajero cajero) {
        return ResponseEntity.ok(cajeroService.guardar(cajero));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Cajero> actualizarCajero(
            @PathVariable Integer id,
            @RequestBody Cajero cajero) {

        cajero.setId(id);
        return ResponseEntity.ok(cajeroService.guardar(cajero));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarCajero(@PathVariable Integer id) {
        cajeroService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
