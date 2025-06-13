package co.edu.ufps.examen20251.models;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "cajero")
public class Cajero {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "nombre", length = 200, nullable = false)
    private String nombre;

    @Column(name = "documento", length = 20)
    private String documento;

    @ManyToOne
    @JoinColumn(name = "tienda_id", nullable = false)
    private Tienda tienda;

    @Column(name = "email", length = 50)
    private String email;

    @Column(name = "token", length = 100)
    private String token;

    @ManyToOne
    @JoinColumn(name = "vendedor_id")
    private Vendedor vendedor;

}
