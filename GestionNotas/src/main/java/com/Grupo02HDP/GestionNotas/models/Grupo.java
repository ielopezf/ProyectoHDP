package com.Grupo02HDP.GestionNotas.models;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name= "grupo")
public class Grupo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    private String codigo;

    @ManyToOne
    @JoinColumn(name = "materia_id")
    private Materia materia;

    @ManyToOne
    @JoinColumn(name = "docente_id")
    private Usuario docente;

}
