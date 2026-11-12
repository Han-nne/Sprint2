/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Dominio;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

/**
 *  Representa una carta individual en Groggy Ring.
 * <p>
 *  Cada carta contiene un identificador de tipo, un nombre descriptivo, 
 *  una descripción textual y un valor numérico que determina la magnitud
 *  de su efecto (daño, protección o casillas de movimiento).
 * <p>
 * 
 * @author emmanuelarce
 * @see CartaTipo
 * @see GroggyRing
 */
public abstract class Carta {
    
    /**Nombre identificativo de la carta. */
    private final String nombre;
    
    /**Descripción breve del efecto de la carta.*/
    private final String descripcion;
    
    /**base abstracta de la imagen de la carta.*/
    protected BufferedImage imagen;
    
    /**
     *  Construye una carta con sus atributos principales.
     * 
     * @param nombre nombre identificativo de la carta.
     * @param descripcion explicación del efecto.
     */
    public Carta(String nombre, String descripcion, BufferedImage imagen) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.imagen = imagen;
    }

    /**
     * Se obtiene el nombre de la carta.
     * @return nombre de la carta correspondiente.
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Se obtiene la descripción textual de la carta.
     * @return información de la carta correspondiente.
     */
    public String getDescripcion() {return descripcion;}

    /**
     * Se obtiene la imagen de la carta según su tipo.
     * @return La imagen de la carta correspondiente.
     */
    public BufferedImage getImagen(){ return imagen; }
    
    /**
     * Establece una imagen al tipo de carta correspondiente.
     * @param imagen imagen sacada del tile-set.
     */
    public void setImagen(BufferedImage imagen){ this.imagen = imagen; }
    
    /**
     * Método abstracto para aplicar el efecto específico de cada carta sobre los jugadores.
     * @param usuario Jugador que utiliza la carta.
     * @param rival Jugador opuesto.
     * @param juego Instancia actual de GroggyRing para consultas del tablero.
     */
    public abstract void aplicarEfecto(JugadorBola usuario, JugadorBola rival, GroggyRing juego);
    
    public void dibujar(Graphics g, int x, int y, int ancho, int alto){
        if(imagen == null){
            g.drawImage(imagen, x, y, ancho, alto, null);
        }
    }
} // fin clase
