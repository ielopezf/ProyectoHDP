package com.Grupo02HDP.GestionNotas.models;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "horario")
public class Horario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

   private String descripcion;
}
