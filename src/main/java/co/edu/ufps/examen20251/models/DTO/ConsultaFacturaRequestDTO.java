package co.edu.ufps.examen20251.models.DTO;

import lombok.Data;

@Data
public class ConsultaFacturaRequestDTO {
    private String token;
    private String cliente;
    private Integer factura;
}
