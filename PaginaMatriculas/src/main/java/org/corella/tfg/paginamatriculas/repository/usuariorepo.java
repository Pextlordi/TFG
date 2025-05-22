package org.corella.tfg.paginamatriculas.repository;

import org.corella.tfg.paginamatriculas.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface usuariorepo extends JpaRepository<Usuario, String> {
    @Query("SELECT usu FROM Usuario as usu WHERE usu.fechaComienzo <= :hoy AND (usu.fechaFinal IS NULL OR usu.fechaFinal >= :hoy)")
    List<Usuario> findActivas(@Param("hoy") LocalDate hoy);
}
