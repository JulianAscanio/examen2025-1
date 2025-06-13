package co.edu.ufps.examen20251.models;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "tipo_pago")
public class TipoPago {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "nombre", length = 200, nullable = false)
    private String nombre;

}
