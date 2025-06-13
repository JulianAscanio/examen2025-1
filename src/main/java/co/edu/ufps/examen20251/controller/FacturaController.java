package co.edu.ufps.examen20251.controller;

import co.edu.ufps.examen20251.models.DTO.ConsultaFacturaRequestDTO;
import co.edu.ufps.examen20251.models.DTO.FacturaRequestDTO;
import co.edu.ufps.examen20251.models.DTO.FacturaResponseDTO;
import co.edu.ufps.examen20251.services.FacturaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/facturas")
public class FacturaController {

    @Autowired
    private FacturaService facturaService;

    @PostMapping("/crear/{tiendaId}")
    public ResponseEntity<FacturaResponseDTO> crearFactura(
            @PathVariable UUID tiendaId,
            @RequestBody FacturaRequestDTO facturaRequest) {

        try {
            FacturaResponseDTO response = facturaService.procesarFactura(tiendaId, facturaRequest);
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            FacturaResponseDTO errorResponse = new FacturaResponseDTO();
            errorResponse.setStatus("error");
            errorResponse.setMessage(e.getMessage());
            errorResponse.setData(null);

            if (e.getMessage().contains("no existe") ||
                    e.getMessage().contains("No hay")) {
                return ResponseEntity.status(404).body(errorResponse);
            } else {
                return ResponseEntity.status(403).body(errorResponse);
            }
        }
    }

    @PostMapping("/consultar/{tiendaId}")
    public ResponseEntity<?> consultarFactura(
            @PathVariable UUID tiendaId,
            @RequestBody ConsultaFacturaRequestDTO consultaRequest) {

        try {
            ConsultaFacturaResponseDTO response = facturaService.consultarFactura(tiendaId, consultaRequest);
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            ConsultaFacturaResponseDTO errorResponse = new ConsultaFacturaResponseDTO();
            errorResponse.setStatus("error");
            errorResponse.setMessage(e.getMessage());
            errorResponse.setData(null);

            if (e.getMessage().contains("no existe") ||
                    e.getMessage().contains("No hay")) {
                return ResponseEntity.status(404).body(errorResponse);
            } else {
                return ResponseEntity.status(403).body(errorResponse);
            }
        }
    }
}
