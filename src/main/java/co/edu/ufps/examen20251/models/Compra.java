package co.edu.ufps.examen20251.models;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Entity
@Table(name = "compra")
public class Compra {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "cliente_id", nullable = false)
    private Cliente cliente;

    @ManyToOne
    @JoinColumn(name = "tienda_id", nullable = false)
    private Tienda tienda;

    @ManyToOne
    @JoinColumn(name = "vendedor_id", nullable = false)
    private Vendedor vendedor;

    @ManyToOne
    @JoinColumn(name = "cajero_id", nullable = false)
    private Cajero cajero;

    @Column(name = "total", precision = 10, scale = 2)
    private BigDecimal total = BigDecimal.ZERO;

    @Column(name = "impuestos", precision = 5, scale = 2)
    private BigDecimal impuestos = BigDecimal.ZERO;

    @Column(name = "fecha")
    private LocalDateTime fecha = LocalDateTime.now();

    @Column(name = "observaciones", length = 1000)
    private String observaciones;

    @OneToMany(mappedBy = "compra", cascade = CascadeType.ALL)
    private List<DetalleCompra> detalles;

    @OneToMany(mappedBy = "compra", cascade = CascadeType.ALL)
    private List<Pago> pagos;
}
