package com.Grupo02HDP.GestionNotas.models;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "grupos")
public class Grupo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    private String codigo ;
    private int  docente;
    private int  materia;


}
