package com.Grupo02HDP.GestionNotas.models;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Data
@Table( name= "clases")
public class Clase implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    private int grupo;

    private String horario;

    private Long materia;

    private int ciclo;

    private String anio;

}
