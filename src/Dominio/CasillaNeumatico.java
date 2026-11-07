/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Dominio;

import java.awt.Graphics;  
import java.io.IOException;
import utility.TileSetManagerGroggyRing;

/**
 * Casilla especial que representa un neumático en el tablero de
 * {@link GroggyRing}.
 * <p>
 * Cada neumático pertenece a una bola ({@link JugadorBola}) en particular.
 * Cuando la bola del rival llega a esta casilla, el rival anota un punto.
 * </p>
 *
 * @see Casilla
 * @see JugadorBola
 * @see GroggyRing
 */
public class CasillaNeumatico extends Casilla {

    /** Bola a la que pertenece esta casilla (su dueña). */
    private JugadorBola dueno;

    /**
     * Crea una casilla de neumático en la posición indicada y carga su
     * imagen a partir del conjunto de tiles de Groggy Ring.
     *
     * @param posX coordenada X de la casilla (en coordenadas locales del tablero)
     * @param posY coordenada Y de la casilla (en coordenadas locales del tablero)
     * @throws IOException si ocurre un error al cargar la imagen del tile
     */
    public CasillaNeumatico(float posX, float posY) throws IOException {
        super(posX, posY);
        this.imagenCasilla = TileSetManagerGroggyRing.cargarImagenGroggyRing();
        this.imagenCasilla = this.imagenCasilla.getSubimage(72, 0, TAM, TAM);
    }

    /**
     * Obtiene la bola dueña de este neumático.
     *
     * @return la bola dueña, o {@code null} si aún no se ha asignado
     */
    public JugadorBola getDueno() { return dueno; }

    /**
     * Asigna la bola dueña de este neumático.
     *
     * @param dueno bola a la que pertenece esta casilla
     */
    public void setDueno(JugadorBola dueno) { this.dueno = dueno; }

    /**
     * Dibuja la imagen del neumático en su posición dentro del tablero.
     *
     * @param g contexto gráfico sobre el cual dibujar
     */
    @Override
    public void dibujar(Graphics g) {
        g.drawImage(imagenCasilla, (int) posX, (int) posY, TAM, TAM, null);
    }
}