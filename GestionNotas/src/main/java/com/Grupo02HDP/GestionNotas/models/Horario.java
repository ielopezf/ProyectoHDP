package com.Grupo02HDP.GestionNotas.models;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Data
@Table(name = "horario")
public class Horario implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

   private String codigo;
    private int id_materia;
    private  String hora;
    private String descripcion;
}
