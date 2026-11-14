/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package utility;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import javax.imageio.ImageIO;

/**
 *
 * Clase utilitaria encargada de cargar y mantener en memoria el fondo
 * usado por el minijuego Combat.
 * 
 * @author Keithy Carrillo
 */
public class TileSetManagerCombat {
    
     /**
     * Imagen del tileset de Combat ({@code tilesheet_Combat.png}).
     * <p>
     * Se inicializa la primera vez que se invoca
     * {@link #cargarImagenCombat() ()}. Vale {@code null} mientras no se haya
     * cargado.
     */
      private static BufferedImage TILESETCOMBAT;
      
      
      /**
     * Imagen del tileset del fondo ({@code fondoCombat.jfif}).
     * <p>
     * Se inicializa la primera vez que se invoca {@link #cargarImagenFondoCombat() }.
     * Vale {@code null} mientras no se haya cargado.
     */
       private static BufferedImage TILESETFONDO;
       
       
   /**
     * Carga el tileset de Combat desde el directorio de recursos
     * ({@code /assets/fondoCombat.jfif}).
     * <p>
     * La imagen se lee solo la primera vez; en las siguientes invocaciones se
     * devuelve la imagen ya almacenada en {@link #TILESETFONDO}.
     *
     * @return imagen del tileset de Combat
     * @throws IOException si ocurre un error al leer el archivo de imagen
     * @throws IllegalArgumentException si el recurso no se encuentra en la
     *         ruta indicada (lo lanza {@code ImageIO.read} al recibir un
     *         flujo {@code null})
     */
  public static BufferedImage cargarImagenFondoCombat() throws IOException{
        if(TILESETFONDO == null){
            TILESETFONDO =  ImageIO.read(TileSetManagerCombat.class.getResourceAsStream("/assets/fondoCombat.png"));
        }
        return TILESETFONDO;
    } //cargarImagenFondo
  
  /**
     * Carga el tileset desde el directorio de recursos
     * ({@code /assets/tilesheet_Combat.png}).
     * <p>
     * La imagen se lee solo la primera vez; en las siguientes invocaciones se
     * devuelve la imagen ya almacenada en {@link #TILESETCOMBAT}.
     *
     * @return imagen del tilesheet..
     * @throws IOException si ocurre un error al leer el archivo de imagen
     * @throws IllegalArgumentException si el recurso no se encuentra en la
     *         ruta indicada (lo lanza {@code ImageIO.read} al recibir un
     *         flujo {@code null})
     */
   public static BufferedImage cargarImagenCombat() throws IOException{
        if(TILESETCOMBAT == null){
            TILESETCOMBAT = ImageIO.read(TileSetManagerCombat.class.getResourceAsStream("/assets/tilesheet_Combat.png"));
        }
        return TILESETCOMBAT;
    } //cargarImagen
    
}
