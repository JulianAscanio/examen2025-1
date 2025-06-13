package co.edu.ufps.examen20251.models;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "tipo_producto")
public class TipoProducto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "nombre", length = 100, nullable = false)
    private String nombre;

}
