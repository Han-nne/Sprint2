/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package utility;

import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;

/**
 * Clase utilitaria encargada de cargar el tile-set de las cartas y el mazo central de GroggyRing
 * y recortarla en subpartes para signarla a cada tipo de carta o al mazo.
 * 
 * @author emmanuelarce
 */
public class GroggyRingCartas {
    
    /**Imagen completa del tileset de cartas.*/
    private static BufferedImage TILESETCARTAS;
    
    /**Ancho en píxeles del sprite de cada carta.*/
    public static final int ANCHOCARTA = 62;
    
    /**Alto en píxeles del sprite de cada carta.*/
    public static final int ALTOCARTA = 62;
    
    /**
     * Carga todo el tileset de las cartas y el mazo central.
     * @return El tiliset completo de cartas y mazo central.
     * @throws IOException Si ocurre un error al cargar la imagen del tileset.
     */
    public static BufferedImage cargarImagenCartas() throws IOException{
        if(TILESETCARTAS == null){
            TILESETCARTAS = ImageIO.read(GroggyRingCartas.class.getResourceAsStream("/assets/tileSetCartasGroggyRing.png"));
        }
        return TILESETCARTAS;
    }
    
    /**
     * Carga el sprite de {@link CartaAtaque} desde el tileset.
     * @return La imagen de la carta, recortada del tileset.
     * @throws IOException Si ocurre algún error al cargar la imagen.
     */
    public static BufferedImage cargarSpriteAtaque() throws IOException{
        BufferedImage sheet = cargarImagenCartas();
        return sheet.getSubimage(0, 0, ANCHOCARTA, ALTOCARTA);
    }
    
    /**
     * Carga el sprite de {@link CartaDefensa} desde el tileset.
     * @return La imagen de la carta, recortada del tileset.
     * @throws IOException Si ocurre algún error al cargar la imagen.
     */
    public static BufferedImage cargarSpriteDefensa() throws IOException{
        BufferedImage sheet = cargarImagenCartas();
        return sheet.getSubimage(ANCHOCARTA + 2, 0, ANCHOCARTA, ALTOCARTA);
    }
    
    /**
     * Carga el sprite de {@link CartaDesplazamiento} desde el tileset.
     * @return La imagen de la carta, recortada del tileset.
     * @throws IOException Si ocurre algún error al cargar la imagen.
     */
    public static BufferedImage cargarSpriteDesplazamiento() throws IOException{
        BufferedImage sheet = cargarImagenCartas();
        return sheet.getSubimage(ANCHOCARTA* 2 + 3, 0, ANCHOCARTA, ALTOCARTA);
    }
    
    /**
     * Carga el sprite del mazo central (representado como el revés de una carta) desde el tileset.
     * @return La imagen del mazo, recortada del tileset.
     * @throws IOException Si ocurre algún error al cargar la imagen.
     */
    public static BufferedImage cargarSpriteMazoCentral() throws IOException{
        BufferedImage sheet = cargarImagenCartas();
        return sheet.getSubimage(ANCHOCARTA * 3 + 6, 0, ANCHOCARTA, ALTOCARTA);
    }
} // fin clase
