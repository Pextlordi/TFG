package org.corella.tfg.paginamatriculas.repository;

import org.corella.tfg.paginamatriculas.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface usuariorepo extends JpaRepository<Usuario, String> {
}
