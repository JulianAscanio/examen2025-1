package co.edu.ufps.examen20251.models;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Entity
@Table(name = "detalles_compra")
public class DetalleCompra {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "compra_id", nullable = false)
    private Compra compra;

    @ManyToOne
    @JoinColumn(name = "producto_id", nullable = false)
    private Producto producto;

    @Column(name = "cantidad", nullable = false)
    private Integer cantidad;

    @Column(name = "precio", precision = 10, scale = 2, nullable = false)
    private BigDecimal precio;

    @Column(name = "descuento", precision = 5, scale = 2)
    private BigDecimal descuento = BigDecimal.ZERO;

}
