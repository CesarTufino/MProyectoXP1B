package epn.gr6.modelo.logica;

import javax.persistence.*;
import java.util.Calendar;

@Entity
@Table(name= "descuento_temporada")
public class DescuentoTemporada {
    @Id
    @Column(name = "codigo_descuento", nullable = false, length = 5)
    private String codigoDescuento;
    @Column(name = "nombre", nullable = true, length = 50)
    private String nombre;
    @Column(name = "fechaInicioTemporada", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Calendar fechaInicioTemporada;
    @Column(name = "fechaFinTemporada", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Calendar fechaFInTemporada;
    @Column(name = "porcentaje_descuento", nullable = false)
    private double porcentajeDescuento;

    public DescuentoTemporada() {
    }

    public DescuentoTemporada(String codigoDescuento, String nombre, Calendar fechaInicioTemporada, Calendar fechaFInTemporada, double porcentajeDescuento) {
        this.codigoDescuento = codigoDescuento;
        this.nombre = nombre;
        this.fechaInicioTemporada = fechaInicioTemporada;
        this.fechaFInTemporada = fechaFInTemporada;
        this.porcentajeDescuento = porcentajeDescuento;
    }

    public String getCodigoDescuento() {
        return codigoDescuento;
    }

    public void setCodigoDescuento(String codigoDescuento) {
        this.codigoDescuento = codigoDescuento;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Calendar getFechaInicioTemporada() {
        return fechaInicioTemporada;
    }

    public void setFechaInicioTemporada(Calendar fechaInicioTemporada) {
        this.fechaInicioTemporada = fechaInicioTemporada;
    }

    public Calendar getFechaFInTemporada() {
        return fechaFInTemporada;
    }

    public void setFechaFInTemporada(Calendar fechaFInTemporada) {
        this.fechaFInTemporada = fechaFInTemporada;
    }

    public double getPorcentajeDescuento() {
        return porcentajeDescuento;
    }

    public void setPorcentajeDescuento(double porcentajeDescuento) {
        this.porcentajeDescuento = porcentajeDescuento;
    }
}
