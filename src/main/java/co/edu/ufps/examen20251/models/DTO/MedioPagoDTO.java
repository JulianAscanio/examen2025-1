package co.edu.ufps.examen20251.models.DTO;

import lombok.Data;

@Data
public class MedioPagoDTO {
    private String tipo_pago;
    private String tipo_tarjeta;
    private Integer cuotas;
    private Double valor;
}
