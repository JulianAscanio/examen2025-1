package co.edu.ufps.examen20251.controller;

import co.edu.ufps.examen20251.models.TipoDocumento;
import co.edu.ufps.examen20251.models.TipoPago;
import co.edu.ufps.examen20251.models.TipoProducto;
import co.edu.ufps.examen20251.services.TipoDocumentoService;
import co.edu.ufps.examen20251.services.TipoPagoService;
import co.edu.ufps.examen20251.services.TipoProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tipos")
public class TipoController {

    @Autowired
    private TipoDocumentoService tipoDocumentoService;

    @Autowired
    private TipoProductoService tipoProductoService;

    @Autowired
    private TipoPagoService tipoPagoService;

    // Endpoints para TipoDocumento
    @GetMapping("/documentos")
    public ResponseEntity<List<TipoDocumento>> listarTiposDocumento() {
        return ResponseEntity.ok(tipoDocumentoService.listarTodos());
    }

    @PostMapping("/documentos")
    public ResponseEntity<TipoDocumento> crearTipoDocumento(@RequestBody TipoDocumento tipoDocumento) {
        return ResponseEntity.ok(tipoDocumentoService.guardar(tipoDocumento));
    }

    // Endpoints para TipoProducto
    @GetMapping("/productos")
    public ResponseEntity<List<TipoProducto>> listarTiposProducto() {
        return ResponseEntity.ok(tipoProductoService.listarTodos());
    }

    @PostMapping("/productos")
    public ResponseEntity<TipoProducto> crearTipoProducto(@RequestBody TipoProducto tipoProducto) {
        return ResponseEntity.ok(tipoProductoService.guardar(tipoProducto));
    }

    // Endpoints para TipoPago
    @GetMapping("/pagos")
    public ResponseEntity<List<TipoPago>> listarTiposPago() {
        return ResponseEntity.ok(tipoPagoService.listarTodos());
    }

    @PostMapping("/pagos")
    public ResponseEntity<TipoPago> crearTipoPago(@RequestBody TipoPago tipoPago) {
        return ResponseEntity.ok(tipoPagoService.guardar(tipoPago));
    }
}
