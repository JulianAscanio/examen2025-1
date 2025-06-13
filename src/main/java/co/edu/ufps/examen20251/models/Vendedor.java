package co.edu.ufps.examen20251.models;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "vendedor")
public class Vendedor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "nombre", length = 200, nullable = false)
    private String nombre;

    @Column(name = "documento", length = 20)
    private String documento;

    @Column(name = "email", length = 50)
    private String email;

}
