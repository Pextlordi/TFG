package org.corella.tfg.paginamatriculas.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

/**
 * Esta clase representa los objetos de la tabla usuario de la base de datos de SisMat.
 * @author Petteri Ketola
 * @version 27-05-2025
 */

@Getter
@Setter
@Entity
@Table(name = "usuario")
public class Usuario {
    @Id
    @Column(name = "DNI", nullable = false, length = 9)
    private String dni;

    @Column(name = "Nombre_Completo", nullable = false, length = 200)
    private String nombreCompleto;

    @Column(name = "Cargo", length = 50)
    private String cargo;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(name = "Fecha_Comienzo", nullable = false)
    private LocalDate fechaComienzo;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(name = "Fecha_Final")
    private LocalDate fechaFinal;

    @ManyToOne
    @JoinColumn(name = "id_admin")
    private Administrador adminPermiso;

    @OneToMany(mappedBy = "usuarioResp", fetch = FetchType.LAZY)
    private List<Matricula> matriculas;
}
