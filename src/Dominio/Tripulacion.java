/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Dominio;

import java.awt.image.BufferedImage;

/**
 * La clase {@code Tripulacion} representa al equipo que maneja un {@link Barco}
 * dentro del juego.
 * <p>
 * Almacena el nombre de la tripulación, su puntaje acumulado y la bandera que
 * la identifica. El puntaje inicia en 0.0 al crear la tripulación.
 * </p>
 *
 * @author johan
 * @see Barco
 */
public class Tripulacion  {
 
    /** Nombre que identifica a la tripulación. */
    private String nombreTripulacion;
 
    /** Puntaje acumulado de la tripulación. */
    private double puntaje = 0;
 
    /** Imagen de la bandera de la tripulación. */
    private BufferedImage bandera;
 
    /**
     * Construye una tripulación con el nombre y la bandera indicados.
     * El puntaje inicial es 0.0.
     *
     * @param nombreTripulacion nombre de la tripulación
     * @param bandera imagen de la bandera que representa a la tripulación
     */
    public Tripulacion(String nombreTripulacion , BufferedImage bandera) {
        this.bandera  = bandera ;
        this.nombreTripulacion = nombreTripulacion;
    }
 
    /**
     * Obtiene el nombre de la tripulación.
     *
     * @return el nombre de la tripulación
     */
    public String getNombreTripulacion() {
        return nombreTripulacion;
    }
 
    /**
     * Establece el nombre de la tripulación.
     *
     * @param nombreTripulacion nuevo nombre de la tripulación
     */
    public void setNombreTripulacion(String nombreTripulacion) {
        this.nombreTripulacion = nombreTripulacion;
    }
 
    /**
     * Obtiene el puntaje acumulado de la tripulación.
     *
     * @return el puntaje actual
     */
    public double getPuntaje() {
        return puntaje;
    }
 
    /**
     * Establece el puntaje de la tripulación.
     *
     * @param puntaje nuevo puntaje de la tripulación
     */
    public void setPuntaje(double puntaje) {
        this.puntaje = puntaje;
    }

    /**
     *
     * @return
     */
    public BufferedImage getBandera() {
        return bandera;
    }

    /**
     *
     * @param bandera
     */
    public void setBandera(BufferedImage bandera) {
        this.bandera = bandera;
    }
    
}
