package org.corella.tfg.paginamatriculas.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

/**
 * Esta clase representa los objetos de la tabla matricula de la base de datos de SisMat.
 * @author Petteri Ketola
 * @version 27-05-2025
 */

@Getter
@Setter
@Entity
@Table(name = "matricula")
public class Matricula {
    @Id
    @Column(name = "Numero_Mat", nullable = false, length = 8)
    private String numeroMat;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "DNI_Usuario_Resp", nullable = false)
    private Usuario usuarioResp;

    @Column(name = "Desc_Vehiculo", nullable = false, length = 200)
    private String descVehiculo;

}