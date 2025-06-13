package co.edu.ufps.examen20251.models.DTO;

import lombok.Data;

import java.util.List;

@Data
public class FacturaRequestDTO {
    private Double impuesto;
    private ClienteDTO cliente;
    private List<ProductoDTO> productos;
    private List<MedioPagoDTO> medios_pago;
    private VendedorDTO vendedor;
    private CajeroDTO cajero;
}
