package co.edu.ufps.examen20251.controller;

import co.edu.ufps.examen20251.models.Tienda;
import co.edu.ufps.examen20251.services.TiendaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/tiendas")
public class TiendaController {

    @Autowired
    private TiendaService tiendaService;

    @GetMapping
    public ResponseEntity<List<Tienda>> listarTiendas() {
        return ResponseEntity.ok(tiendaService.listarTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Tienda> obtenerTienda(@PathVariable Integer id) {
        return tiendaService.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/uuid/{uuid}")
    public ResponseEntity<Tienda> obtenerPorUuid(@PathVariable UUID uuid) {
        return tiendaService.buscarPorUuid(uuid.toString())
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Tienda> crearTienda(@RequestBody Tienda tienda) {
        return ResponseEntity.ok(tiendaService.guardar(tienda));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Tienda> actualizarTienda(
            @PathVariable Integer id,
            @RequestBody Tienda tienda) {

        tienda.setId(id);
        return ResponseEntity.ok(tiendaService.guardar(tienda));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarTienda(@PathVariable Integer id) {
        tiendaService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
