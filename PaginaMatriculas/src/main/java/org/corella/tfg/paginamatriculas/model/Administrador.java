package org.corella.tfg.paginamatriculas.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "administrador")
public class Administrador {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false)
    private Integer id;

    @Column(name = "Nombre_Completo", nullable = false, length = 200)
    private String nombreCompleto;

    @Column(name = "Cargo", length = 50)
    private String cargo;

    @Column(name = "Permiso", nullable = false)
    private Boolean permiso = false;

}