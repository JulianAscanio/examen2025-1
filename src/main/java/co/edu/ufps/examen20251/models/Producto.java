package co.edu.ufps.examen20251.models;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Entity
@Table(name = "producto")
public class Producto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "nombre", length = 200, nullable = false)
    private String nombre;

    @Column(name = "descripcion", length = 1000)
    private String descripcion;

    @Column(name = "precio", precision = 10, scale = 2, nullable = false)
    private BigDecimal precio;

    @ManyToOne
    @JoinColumn(name = "tipo_producto_id", nullable = false)
    private TipoProducto tipoProducto;

    @Column(name = "cantidad")
    private Integer cantidad = 0;

    @Column(name = "referencia", length = 20)
    private String referencia;
}
