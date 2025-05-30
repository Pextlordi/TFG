package org.corella.tfg.paginamatriculas.repository;

import org.corella.tfg.paginamatriculas.model.Matricula;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

/**
 * Esta interfaz representa la obtención de elementos de la tabla matricula de la base de datos de SisMat.
 * @author Petteri Ketola
 * @version 27-05-2025
 */

@Repository
public interface matricularepo extends JpaRepository<Matricula, String> {
    @Query("SELECT mat FROM Matricula as mat WHERE mat.usuarioResp.fechaComienzo <= :hoy AND (mat.usuarioResp.fechaFinal IS NULL OR mat.usuarioResp.fechaFinal >= :hoy)")
    List<Matricula> findActivas(@Param("hoy") LocalDate hoy);
}
