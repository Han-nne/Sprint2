/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Dominio;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

/**
 * Clase base abstracta para las casillas del tablero de {@link GroggyRing}.
 * <p>
 * Una casilla tiene una posición en coordenadas locales del tablero, una
 * imagen y, opcionalmente, una bola ({@link JugadorBola}) que la ocupa.
 * Las subclases definen cómo se dibuja cada tipo de casilla.
 * </p>
 *
 * @author johan
 * @see CasillaCampo
 * @see CasillaNeumatico
 * @see JugadorBola
 */
public abstract class Casilla {

    /** Tamaño en píxeles del lado de cada casilla (las casillas son cuadradas). */
    public static final int TAM = 64;

    /** Coordenada X de la casilla (en coordenadas locales del tablero). */
    protected double posX;

    /** Coordenada Y de la casilla (en coordenadas locales del tablero). */
    protected double posY;

    /** Bola que ocupa actualmente la casilla, o {@code null} si está libre. */
    protected JugadorBola jugadorBola;

    /** Imagen utilizada para dibujar la casilla. */
    protected BufferedImage imagenCasilla;

    /**
     * Crea una casilla en la posición indicada.
     *
     * @param posX coordenada X de la casilla
     * @param posY coordenada Y de la casilla
     */
    public Casilla(float posX, float posY) {
        this.posX = posX;
        this.posY = posY;
    }

    /**
     * Dibuja la casilla en el contexto gráfico dado. Cada subclase define
     * su propia representación.
     *
     * @param g contexto gráfico sobre el cual dibujar
     */
    public abstract void dibujar(Graphics g);

    /**
     * Obtiene la bola que ocupa la casilla.
     *
     * @return la bola que ocupa la casilla, o {@code null} si está libre
     */
    public JugadorBola getJugadorBola() {
        return jugadorBola;
    }

    /**
     * Establece la bola que ocupa la casilla.
     *
     * @param jugadorBola bola que ocupa la casilla, o {@code null} para liberarla
     */
    public void setJugadorBola(JugadorBola jugadorBola) {
        this.jugadorBola = jugadorBola;
    }

    /**
     * Obtiene la imagen de la casilla.
     *
     * @return la imagen con la que se dibuja la casilla
     */
    public BufferedImage getImagenCasilla() {
        return imagenCasilla;
    }

    /**
     * Establece la imagen de la casilla.
     *
     * @param imagenCasilla nueva imagen con la que se dibujará la casilla
     */
    public void setImagenCasilla(BufferedImage imagenCasilla) {
        this.imagenCasilla = imagenCasilla;
    }

    /**
     * Obtiene la coordenada X de la casilla.
     *
     * @return la coordenada X
     */
    public double getPosX() {
        return posX;
    }

    /**
     * Establece la coordenada X de la casilla.
     *
     * @param posX nueva coordenada X
     */
    public void setPosX(float posX) {
        this.posX = posX;
    }

    /**
     * Obtiene la coordenada Y de la casilla.
     *
     * @return la coordenada Y
     */
    public double getPosY() {
        return posY;
    }

    /**
     * Establece la coordenada Y de la casilla.
     *
     * @param posY nueva coordenada Y
     */
    public void setPosY(float posY) {
        this.posY = posY;
    }
}