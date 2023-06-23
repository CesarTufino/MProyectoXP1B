package epn.gr6.modelo.logica;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "ejemplar")
public class Ejemplar {
    @Id
    @Column(name = "codigo_ejemplar", nullable = false, length = 5)
    private String codigoEjemplar;
    @Column(name = "estado_disponibilidad", nullable = false)
    private Boolean estadoDisponibilidad;
    @Column(name = "costoPorDia", nullable = false)
    private double costoPorDia;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "codigo_pelicula")
    private Pelicula pelicula;
    @ManyToMany(mappedBy = "ejemplares")
    private List<Alquiler> alquileres = new ArrayList<Alquiler>();

    public Ejemplar() {
    }

    public Ejemplar(String codigoEjemplar, Boolean estadoDisponibilidad, double costoPorDia, Pelicula pelicula) {
        this.codigoEjemplar = codigoEjemplar;
        this.estadoDisponibilidad = estadoDisponibilidad;
        this.costoPorDia = costoPorDia;
        this.pelicula = pelicula;
    }

    public String getCodigoEjemplar() {
        return codigoEjemplar;
    }

    public void setCodigoEjemplar(String codigoEjemplar) {
        this.codigoEjemplar = codigoEjemplar;
    }

    public Boolean getEstadoDisponibilidad() {
        return estadoDisponibilidad;
    }

    public void setEstadoDisponibilidad(Boolean estadoDisponibilidad) {
        this.estadoDisponibilidad = estadoDisponibilidad;
    }

    public double getCostoPorDia() {
        return costoPorDia;
    }

    public void setCostoPorDia(double costoPorDia) {
        this.costoPorDia = costoPorDia;
    }

    public Pelicula getPelicula() {
        return pelicula;
    }

    public void setPelicula(Pelicula pelicula) {
        this.pelicula = pelicula;
    }

    public List<Alquiler> getAlquileres() {
        return alquileres;
    }

    public void setAlquileres(List<Alquiler> alquileres) {
        this.alquileres = alquileres;
    }
}
