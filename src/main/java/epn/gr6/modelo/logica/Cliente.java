package epn.gr6.modelo.logica;

import epn.gr6.modelo.persistencia.PersistenciaAlquiler;
import epn.gr6.modelo.persistencia.PersistenciaCliente;
import epn.gr6.modelo.persistencia.PersistenciaEjemplar;

import javax.management.StringValueExp;
import javax.persistence.*;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

@Entity
@Table(name= "cliente")
public class Cliente {
    @Id
    @Column(name = "cedula", nullable = false, length = 10)
    private String cedula;
    @Column(name = "nombre", nullable = true, length = 50)
    private String nombre;
    @Column(name = "apellido", nullable = true, length = 50)
    private String apellido;
    @Column(name = "puntosPorFidelidad", nullable = true)
    private int puntosPorFidelidad;

    public Cliente() {
    }

    public Cliente(String cedula, String nombre, String apellido) {
        this.cedula = cedula;
        this.nombre = nombre;
        this.apellido = apellido;
        this.puntosPorFidelidad = 0;
    }

    public String getCedula() {
        return cedula;
    }

    public void setCedula(String cedula) {
        this.cedula = cedula;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public int getPuntosPorFidelidad() {
        return puntosPorFidelidad;
    }

    public void setPuntosPorFidelidad(int puntosPorFidelidad) {
        this.puntosPorFidelidad = puntosPorFidelidad;
    }


}
