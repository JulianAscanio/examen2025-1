package co.edu.ufps.examen20251.models;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "tienda")
public class Tienda {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "nombre", length = 200, nullable = false)
    private String nombre;

    @Column(name = "direccion", length = 500, nullable = false)
    private String direccion;

    @Column(name = "uuid", length = 50)
    private String uuid;
}
