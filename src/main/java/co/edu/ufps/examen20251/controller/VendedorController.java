package co.edu.ufps.examen20251.controller;

import co.edu.ufps.examen20251.models.Vendedor;
import co.edu.ufps.examen20251.services.VendedorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/vendedores")
public class VendedorController {

    @Autowired
    private VendedorService vendedorService;

    @GetMapping
    public ResponseEntity<List<Vendedor>> listarVendedores() {
        return ResponseEntity.ok(vendedorService.listarTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Vendedor> obtenerVendedor(@PathVariable Integer id) {
        return vendedorService.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/documento/{documento}")
    public ResponseEntity<Vendedor> obtenerPorDocumento(@PathVariable String documento) {
        return vendedorService.buscarPorDocumento(documento)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Vendedor> crearVendedor(@RequestBody Vendedor vendedor) {
        return ResponseEntity.ok(vendedorService.guardar(vendedor));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Vendedor> actualizarVendedor(
            @PathVariable Integer id,
            @RequestBody Vendedor vendedor) {

        vendedor.setId(id);
        return ResponseEntity.ok(vendedorService.guardar(vendedor));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarVendedor(@PathVariable Integer id) {
        vendedorService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
