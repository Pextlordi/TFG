package org.corella.tfg.paginamatriculas.model;

import jakarta.persistence.*;

@Entity
public class Matricula {
    @Id
    @GeneratedValue
    private Long id;
    private String plateNumber;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private Usuario user;
}