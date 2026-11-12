/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package utility;

import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;

/**
 * Clase utilitaria encargada de cargar el tile-set de las cartas de GroggyRing
 * y recortarla en subpartes para signarla a cada tipo de carta.
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
    
    
    public static BufferedImage cargarImagenCartas() throws IOException{
        if(TILESETCARTAS == null){
            TILESETCARTAS = ImageIO.read(GroggyRingCartas.class.getResourceAsStream("/assets/tileSetCartasGroggyRing.png"));
        }
        return TILESETCARTAS;
    }
    
    public static BufferedImage cargarSpriteAtaque() throws IOException{
        BufferedImage sheet = cargarImagenCartas();
        return sheet.getSubimage(0, 0, ANCHOCARTA, ALTOCARTA);
    }
    
    public static BufferedImage cargarSpriteDefensa() throws IOException{
        BufferedImage sheet = cargarImagenCartas();
        return sheet.getSubimage(ANCHOCARTA, 0, ANCHOCARTA, ALTOCARTA);
    }
    
    public static BufferedImage cargarSpriteDesplazamiento() throws IOException{
        BufferedImage sheet = cargarImagenCartas();
        return sheet.getSubimage(ANCHOCARTA* 2, 0, ANCHOCARTA, ALTOCARTA);
    }
} // fin clase
