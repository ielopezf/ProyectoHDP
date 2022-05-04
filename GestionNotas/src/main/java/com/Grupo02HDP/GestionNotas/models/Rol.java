package com.Grupo02HDP.GestionNotas.models;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Table(name = "rol")
@Data
public class Rol implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    private String nombre;
    private LocalDateTime fecha_creacion;
}
