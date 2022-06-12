package com.Grupo02HDP.GestionNotas.models;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Data
public class AsistenciaDTO implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private String codigo;

    private String nombre;

    private  String carnet;

    private  String nombre_alumn;

    private int horario;

    private int grupo;

}
