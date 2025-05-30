package org.corella.tfg.paginamatriculas.repository;

import org.corella.tfg.paginamatriculas.model.Administrador;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Esta interfaz representa la obtención de elementos de la tabla adminsitrado de la base de datos de SisMat.
 * @author Petteri Ketola
 * @version 27-05-2025
 */

@Repository
public interface administradorrepo extends JpaRepository<Administrador, Integer> {
    List<Administrador> findByPermisoTrue();
}
