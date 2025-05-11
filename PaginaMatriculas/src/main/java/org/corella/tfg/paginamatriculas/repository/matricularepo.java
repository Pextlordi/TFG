package org.corella.tfg.paginamatriculas.repository;

import org.corella.tfg.paginamatriculas.model.Matricula;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface matricularepo extends JpaRepository<Matricula, Long> {
}
