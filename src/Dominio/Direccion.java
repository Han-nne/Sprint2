/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Enum.java to edit this template
 */
package Dominio;

/**
 * Representa las cuatro direcciones cardinales en las que puede moverse
 * un elemento del juego (por ejemplo, un barco) sobre una cuadrícula.
 * <p>
 * Cada dirección define el desplazamiento que produce en los ejes X e Y
 * y el índice del sprite que debe usarse para dibujarla.
 * El eje Y crece hacia abajo, por lo que {@link #ARRIBA} tiene un
 * desplazamiento vertical negativo.
 * </p>
 *
 * @author johan
 */
public enum Direccion {

    /** Movimiento hacia arriba: dx = 0, dy = -1, sprite 0. */
    ARRIBA(0, -1, 0),

    /** Movimiento hacia la derecha: dx = 1, dy = 0, sprite 1. */
    DERECHA(1, 0, 1),

    /** Movimiento hacia abajo: dx = 0, dy = 1, sprite 2. */
    ABAJO(0, 1, 2),

    /** Movimiento hacia la izquierda: dx = -1, dy = 0, sprite 3. */
    IZQUIERDA(-1, 0, 3);

    /** Desplazamiento horizontal asociado a la dirección. */
    private final int dx;

    /** Desplazamiento vertical asociado a la dirección. */
    private final int dy;

    /** Posición del sprite de esta dirección en la lista de imágenes. */
    private final int indiceSprite;

    /**
     * Crea una dirección con su desplazamiento y su sprite asociado.
     *
     * @param dx           desplazamiento horizontal (-1, 0 o 1)
     * @param dy           desplazamiento vertical (-1, 0 o 1)
     * @param indiceSprite posición del sprite de esta dirección en la lista
     *                     de imágenes del barco
     */
    private Direccion(int dx, int dy, int indiceSprite) {
        this.dx = dx;
        this.dy = dy;
        this.indiceSprite = indiceSprite;
    }

    /**
     * Obtiene el desplazamiento horizontal de esta dirección.
     *
     * @return -1, 0 o 1 según el movimiento horizontal
     */
    public int getDx() {
        return dx;
    }

    /**
     * Obtiene el desplazamiento vertical de esta dirección.
     *
     * @return -1, 0 o 1 según el movimiento vertical
     */
    public int getDy() {
        return dy;
    }

    /**
     * Obtiene el índice del sprite correspondiente a esta dirección.
     *
     * @return posición del sprite de esta dirección en la lista de imágenes
     *         del barco
     */
    public int getIndiceSprite() {
        return indiceSprite;
    }
}