package org.corella.tfg.paginamatriculas.repository;

import org.corella.tfg.paginamatriculas.model.Administrador;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface administradorrepo extends JpaRepository<Administrador, Integer> {
    List<Administrador> findByPermisoTrue();
}
