package co.edu.ufps.examen20251.models.DTO;

import lombok.Data;

@Data
public class FacturaResponseDTO {
    private String status;
    private String message;
    private FacturaDataDTO data;
}
