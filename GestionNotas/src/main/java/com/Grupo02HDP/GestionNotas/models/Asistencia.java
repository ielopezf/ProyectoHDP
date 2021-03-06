package com.Grupo02HDP.GestionNotas.models;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "asistencia")
public class Asistencia {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    private int materia;
    private int grupo;
    private String alumno;
    private int horario;
}
