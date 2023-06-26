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

    public boolean verificarFidelidadCliente(Cliente cliente) {
        List<Cliente> clientes = GestorCliente.obtenerClienteMayorPuntaje();
        System.out.println(cliente.getCedula());
        for (Cliente cliente1: clientes){
            System.out.println(cliente1.getCedula());
            if(cliente1.getCedula().equals(cliente.getCedula())){
                return true;
            }
        }
        System.out.println("No puede aplicar descuento por temporada");
        return false;
    }

    public void alquilarTemporada(int diasAlquiler, List<Ejemplar> ejemplares, Cliente cliente, String codigoDescuento) {

        double precioTotal = calcularPrecioTotal(ejemplares, diasAlquiler);
        if(verificarFidelidadCliente(cliente)){
            precioTotal = GestorAlquiler.aplicarDescuentoTemporada(precioTotal, codigoDescuento);
        }
        cambiarDisponibilidadEjemplares(ejemplares);
        if(cliente.getPuntosPorFidelidad() < 100){
            GestorAlquiler.sumarPuntajeAlquiler(cliente);
        }else {
            //CUARTA HISTORIA
            System.out.println("\n Cantidad de puntos maximos");
        }

        System.out.println(precioTotal);

        Alquiler alquiler = new Alquiler(diasAlquiler, precioTotal, cliente, ejemplares);
        PersistenciaAlquiler.registrarAlquiler(alquiler);
    }

    public void alquilarFidelidad(int diasAlquiler, List<Ejemplar> ejemplares, Cliente cliente) {
        double precioTotal = calcularPrecioTotal(ejemplares, diasAlquiler);
        if(verificarPuntosFidelidad(cliente.getPuntosPorFidelidad())){
            precioTotal = GestorAlquiler.aplicarDescuento(precioTotal, cliente);
        }
        cambiarDisponibilidadEjemplares(ejemplares);
        if(cliente.getPuntosPorFidelidad() < 100){
            GestorAlquiler.sumarPuntajeAlquiler(cliente);
        }

        System.out.println(precioTotal);

        Alquiler alquiler = new Alquiler(diasAlquiler, precioTotal, cliente, ejemplares);
        PersistenciaAlquiler.registrarAlquiler(alquiler);
    }


    public void alquilar(int diasAlquiler, List<Ejemplar> ejemplares, Cliente cliente) {
        double precioTotal = calcularPrecioTotal(ejemplares, diasAlquiler);
        cambiarDisponibilidadEjemplares(ejemplares);
        if(cliente.getPuntosPorFidelidad() < 100){
            GestorAlquiler.sumarPuntajeAlquiler(cliente);
        }else {
            //CUARTA HISTORIA
            System.out.println("Cantidad de puntos maximos");
        }

        System.out.println(precioTotal);

        Alquiler alquiler = new Alquiler(diasAlquiler, precioTotal, cliente, ejemplares);
        PersistenciaAlquiler.registrarAlquiler(alquiler);
    }

    public void devolver(List<Ejemplar> ejemplares, Cliente cliente,boolean estado) {

        for(Ejemplar ejemplar: ejemplares){
            if(ejemplar.verificarPercance(estado) == true){
                cliente.setPuntosPorFidelidad(0);
                PersistenciaCliente.actualizarCliente(cliente);
                System.out.println("El ejemplar con código " + ejemplar.getCodigoEjemplar() + " tiene un percance.");
            }
            ejemplar.setEstadoDisponibilidad(true);
            PersistenciaEjemplar.actualizarEjemplar(ejemplar);
        }
    }

    private void cambiarDisponibilidadEjemplares(List<Ejemplar> ejemplares) {
        for(Ejemplar ejemplar: ejemplares){
            ejemplar.setEstadoDisponibilidad(false);
            PersistenciaEjemplar.actualizarEjemplar(ejemplar);
        }
    }

    private double calcularPrecioTotal(List<Ejemplar> ejemplares, int diasAlquiler) {
        double precioPorDia = 0;
        double precioFinal = 0;
        for(Ejemplar ejemplar: ejemplares){
                precioPorDia = precioFinal + ejemplar.getCostoPorDia();
        }
        return precioPorDia*diasAlquiler;
    }


    private boolean verificarPuntosFidelidad(int puntosPorFidelidad) {
        if(puntosPorFidelidad<50){
            return false;
        }
        return true;
    }

    /*public int getPuntosPorFidelidad() {
        return 0;
    }*/

    public Alquiler getUltimoAlquiler() {
        return new Alquiler();
    }


}
