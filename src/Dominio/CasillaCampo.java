/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Dominio;

import static Dominio.Casilla.TAM;
import java.awt.Graphics;
import java.io.IOException;
import utility.TileSetManagerGroggyRing;

/**
 * Casilla de campo del tablero de {@link GroggyRing}.
 * <p>
 * Representa una casilla normal por la que las bolas pueden desplazarse.
 * No otorga puntos ni tiene dueño, a diferencia de {@link CasillaNeumatico}.
 * </p>
 *
 * @author johan
 * @see Casilla
 * @see CasillaNeumatico
 * @see GroggyRing
 */
public class CasillaCampo extends Casilla {

    /**
     * Crea una casilla de campo en la posición indicada y carga su imagen
     * a partir del conjunto de tiles de Groggy Ring.
     *
     * @param posX coordenada X de la casilla (en coordenadas locales del tablero)
     * @param posY coordenada Y de la casilla (en coordenadas locales del tablero)
     * @throws IOException si ocurre un error al cargar la imagen del tile
     */
    public CasillaCampo(float posX, float posY) throws IOException {
        super(posX, posY);
        this.imagenCasilla = TileSetManagerGroggyRing.cargarImagenGroggyRing();
        this.imagenCasilla = this.imagenCasilla.getSubimage(0, 0, TAM, TAM);
    }

    /**
     * Dibuja la imagen de la casilla de campo en su posición dentro del tablero.
     *
     * @param g contexto gráfico sobre el cual dibujar
     */
    @Override
    public void dibujar(Graphics g) {
        g.drawImage(imagenCasilla, (int) posX, (int) posY, TAM, TAM, null);
    }

}