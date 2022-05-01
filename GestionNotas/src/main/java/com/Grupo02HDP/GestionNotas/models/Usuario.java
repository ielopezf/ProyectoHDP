package com.Grupo02HDP.GestionNotas.models;

import lombok.Data;
import lombok.NonNull;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;


@Entity
@Data
@Table(name = "usuarios")
public class Usuario implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    private String carnet;
    private String nombre;
    private String telefono;
    private String direccion;
    private String email;

    @OneToOne
    @JoinColumn(name = "rol_id")
    private Rol rol;
    private String contrasena;
    private LocalDateTime fecha_creacion;

}
