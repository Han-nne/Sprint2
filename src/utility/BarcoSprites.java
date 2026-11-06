/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package utility;

import Dominio.ColorBarco;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Utilidad para cargar los sprites de un barco a partir del tileset del juego.
 * <p>
 * Los sprites se recortan de la imagen del tileset según el color del barco.
 * Cada color tiene una fila propia en el tileset con cuatro sprites, uno por
 * dirección, en el orden definido por {@link Direccion#getIndiceSprite()}:
 * 0 = arriba, 1 = derecha, 2 = abajo y 3 = izquierda.
 * </p>
 *
 * @author johan
 * @see TileSetManager
 * @see ColorBarco
 * @see Direccion
 */
public class BarcoSprites {

    /**
     * Carga los cuatro sprites de un barco del color indicado.
     * <p>
     * Recorta del tileset de {@link TileSetManager#cargarImagenDonutRace()} una
     * subimagen de {@link TileSetManager#TILESIZEBARCO} por
     * {@link TileSetManager#TILESIZEBARCO} píxeles por cada dirección. La fila
     * del tileset depende del color: los barcos {@link ColorBarco#AZUL} usan una
     * fila y los demás colores usan otra.
     * </p>
     *
     * @param color color del barco cuyos sprites se desean cargar
     * @return lista de cuatro imágenes, con los índices 0 = arriba,
     *         1 = derecha, 2 = abajo y 3 = izquierda
     * @throws IOException si ocurre un error al cargar la imagen del tileset
     */
    public static List<BufferedImage> cargar(ColorBarco color) throws IOException {
        BufferedImage tile = TileSetManager.cargarImagenDonutRace();
        int y = (color == ColorBarco.AZUL) ? (TileSetManager.TILESIZEBARCO * 7) - 30 : TileSetManager.TILESIZEBARCO * 9;

        List<BufferedImage> sprites = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            int x = (TileSetManager.TILESIZEBARCO * i) + (10 * (i + 1));
            sprites.add(tile.getSubimage(x, y, TileSetManager.TILESIZEBARCO, TileSetManager.TILESIZEBARCO));
        }
        return sprites;
    }
}
