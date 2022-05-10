package com.Grupo02HDP.GestionNotas.models;


import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Data
@Table(name= "materia")
public class Materia {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    private String codigo;
    private String nombre;
    private int matricula;
    private int estado;
    private LocalDateTime fecha_creacion;


}
