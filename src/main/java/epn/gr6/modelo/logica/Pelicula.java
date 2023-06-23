package epn.gr6.modelo.logica;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "pelicula")
public class Pelicula {
    @Id
    @Column(name = "codigo", nullable = false, length = 5)
    private String codigo;
    @Column(name = "titulo", nullable = false, length = 100)
    private String titulo;

    @OneToMany(mappedBy = "pelicula", cascade = CascadeType.ALL, fetch= FetchType.EAGER)
    private List<Ejemplar> ejemplares = new ArrayList<Ejemplar>();

    public Pelicula() {
    }

    public Pelicula(String codigo, String titulo) {
        this.codigo = codigo;
        this.titulo = titulo;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public List<Ejemplar> getEjemplares() {
        return ejemplares;
    }

    public void setEjemplares(List<Ejemplar> ejemplares) {
        this.ejemplares = ejemplares;
    }
}
