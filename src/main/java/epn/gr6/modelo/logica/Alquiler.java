package epn.gr6.modelo.logica;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "alquiler")
public class Alquiler {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "numero_alquiler", nullable = false)
    private Long numeroAlquiler;
    @Column(name = "diasDeAlquiler", nullable = false)
    private int diasDeAlquiler;
    @Column(name = "precioTotal", nullable = false)
    private double precioTotal;
    @OneToOne()
    @JoinColumn(name = "cedula_cliente")
    private Cliente cliente;

    @JoinTable(
            name = "alquiler_ejemplar",
            joinColumns = @JoinColumn(name = "numero", nullable = false),
            inverseJoinColumns = @JoinColumn(name="codigo", nullable = false)
    )
    @ManyToMany(cascade =  CascadeType.ALL)
    private List<Ejemplar> ejemplares = new ArrayList<Ejemplar>();

    public Alquiler() {
    }

    public Alquiler(int diasDeAlquiler, double precioTotal, Cliente cliente, List<Ejemplar> ejemplares) {
        this.diasDeAlquiler = diasDeAlquiler;
        this.precioTotal = precioTotal;
        this.cliente = cliente;
        this.ejemplares = ejemplares;
    }

    public Alquiler(long numeroAlquiler, int diasDeAlquiler, double precioTotal, Cliente cliente) {
        this.numeroAlquiler = numeroAlquiler;
        this.diasDeAlquiler = diasDeAlquiler;
        this.precioTotal = precioTotal;
        this.cliente = cliente;
        this.ejemplares = ejemplares;
    }

    public Long getNumeroAlquiler() {
        return numeroAlquiler;
    }

    public void setNumeroAlquiler(Long numeroAlquiler) {
        this.numeroAlquiler = numeroAlquiler;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public List<Ejemplar> getEjemplares() {
        return ejemplares;
    }

    public void setEjemplares(List<Ejemplar> ejemplares) {
        this.ejemplares = ejemplares;
    }

    public int getDiasDeAlquiler() {
        return diasDeAlquiler;
    }

    public void setDiasDeAlquiler(int diasDeAlquiler) {
        this.diasDeAlquiler = diasDeAlquiler;
    }

    public double getPrecioTotal() {
        return precioTotal;
    }

    public void setPrecioTotal(double precioTotal) {
        this.precioTotal = precioTotal;
    }

    /*public int getPrecio() {
        return 0;
    }*/
}
