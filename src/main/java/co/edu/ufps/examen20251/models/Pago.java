package co.edu.ufps.examen20251.models;

import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
@Table(name = "pago")
public class Pago {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "compra_id", nullable = false)
    private Compra compra;

    @ManyToOne
    @JoinColumn(name = "tipo_pago_id", nullable = false)
    private TipoPago tipoPago;

    @Column(name = "tarjeta_tipo", length = 20)
    private String tarjetaTipo;

    @Column(name = "valor", precision = 10, scale = 2)
    private BigDecimal valor;

    @Column(name = "cuotas")
    private Integer cuotas;

}
