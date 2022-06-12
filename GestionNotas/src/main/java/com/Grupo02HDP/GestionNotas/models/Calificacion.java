package com.Grupo02HDP.GestionNotas.models;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "calificaciones")
public class Calificacion implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    private int materia;
    private int alumno;
    private int nota;
    private int tipo_nota;
    private LocalDateTime fecha_creacion;



}
