/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Dominio;

import java.awt.image.BufferedImage;
import java.io.IOException;
import utility.TripulacionImagen;

/**
 * La clase {@code Tripulacion} representa al equipo que maneja un {@link Barco}
 * dentro del juego.
 * <p>
 * Almacena el nombre de la tripulación, su puntaje acumulado y la bandera que
 * la identifica. El puntaje inicia en 0.0 al crear la tripulación.
 * </p>
 * <p>
 * De la bandera se guarda la <b>ruta</b> del archivo de imagen
 * ({@link #getRutaImagen()}), que es lo que se escribe en el archivo de texto
 * de tripulaciones. La imagen en sí ({@link BufferedImage}) no se carga al
 * crear la tripulación, sino la primera vez que se pide con
 * {@link #getBandera()}. Así, una tripulación siempre se puede crear aunque la
 * imagen se haya movido o borrado.
 * </p>
 *
 * @author johan
 * @see Barco
 * @see TripulacionImagen#imagentripulacion(java.lang.String)
 */
public class Tripulacion  {
 
    /** Nombre que identifica a la tripulación. */
    private String nombreTripulacion;
 
    /** Puntaje acumulado de la tripulación. */
    private double puntaje = 0;
 
    /** Ruta del archivo de imagen de la bandera. */
    private String rutaImagen;
 
    /**
     * Imagen de la bandera. Se carga bajo demanda en {@link #getBandera()};
     * es {@code null} mientras no se haya cargado o si la carga falló.
     */
    private BufferedImage bandera;
 
    /**
     * Construye una tripulación con el nombre y la ruta de bandera indicados.
     * El puntaje inicial es 0.0.
     * <p>
     * Este constructor solo guarda los datos; no lee la imagen ni valida que
     * la ruta exista, por lo que nunca lanza excepciones por ese motivo.
     * </p>
     *
     * @param nombreTripulacion nombre de la tripulación
     * @param rutaImagen        ruta del archivo de imagen de la bandera
     */
    public Tripulacion(String nombreTripulacion, String rutaImagen) {
        this.nombreTripulacion = nombreTripulacion;
        this.rutaImagen = rutaImagen;
    }
 
    /**
     * Devuelve la imagen de la bandera, cargándola desde {@link #getRutaImagen()}
     * la primera vez que se pide.
     * <p>
     * Si la ruta es {@code null}, el archivo ya no existe o no es una imagen
     * válida, devuelve {@code null}; quien use la bandera debe revisar ese
     * caso antes de dibujarla.
     * </p>
     *
     * @return la imagen de la bandera, o {@code null} si no se pudo obtener
     */
    public BufferedImage getBandera() {
        if (bandera == null && rutaImagen != null) {
            try {
                bandera = TripulacionImagen.imagentripulacion(rutaImagen);
            } catch (IOException ex) {
                bandera = null;
            }
        }
        return bandera;
    }
 
    /**
     * Establece la ruta de la imagen de la bandera. Descarta la imagen ya
     * cargada para que {@link #getBandera()} la lea de nuevo desde la ruta
     * nueva.
     *
     * @param rutaImagen nueva ruta del archivo de imagen
     */
    public void setRutaImagen(String rutaImagen) {
        this.rutaImagen = rutaImagen;
        this.bandera = null; // fuerza a recargar con la nueva ruta
    }
 
    /**
     * Obtiene la ruta del archivo de imagen de la bandera.
     *
     * @return la ruta de la imagen
     */
    public String getRutaImagen() {
        return rutaImagen;
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
     * @param n nuevo nombre de la tripulación
     */
    public void setNombreTripulacion(String n) {
        this.nombreTripulacion = n;
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
     * @param p nuevo puntaje de la tripulación
     */
    public void setPuntaje(double p) {
        this.puntaje = p;
    }
 
}