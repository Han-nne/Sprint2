/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package utility;

import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;

/**
 * Clase utilitaria encargada de cargar y mantener en memoria los tilesets
 * (hojas de imágenes) usados por el juego.
 * <p>
 * Cada tileset se lee una sola vez desde el directorio de recursos y queda
 * almacenado en una variable estática, de modo que las llamadas posteriores
 * reutilizan la misma imagen sin volver a leer el archivo (carga perezosa).
 * <p>
 * Todos sus miembros son estáticos, por lo que no necesita ser instanciada.
 *
 * @author johan
 */
public class TileSetManagerGroggyRing {
    
     /**
     * Imagen del tileset de Groggy Ring ({@code tilesheet_GroggyRing.png}).
     * <p>
     * Se inicializa la primera vez que se invoca
     * {@link #cargarImagenGroggyRing() ()}. Vale {@code null} mientras no se haya
     * cargado.
     */
      private static BufferedImage TILESETGROGGYRING;
      
      
      /**
     * Imagen del tileset del fondo ({@code fondoGroggyRing.jfif}).
     * <p>
     * Se inicializa la primera vez que se invoca {@link #cargarImagenFondoGroggyRing() }.
     * Vale {@code null} mientras no se haya cargado.
     */
       private static BufferedImage TILESETFONDO;
       
       
   /**
     * Carga el tileset de GroggyRing desde el directorio de recursos
     * ({@code /assets/fondoGroggyRing.jfif}).
     * <p>
     * La imagen se lee solo la primera vez; en las siguientes invocaciones se
     * devuelve la imagen ya almacenada en {@link #TILESETFONDO}.
     *
     * @return imagen del tileset de GroggyRing
     * @throws IOException si ocurre un error al leer el archivo de imagen
     * @throws IllegalArgumentException si el recurso no se encuentra en la
     *         ruta indicada (lo lanza {@code ImageIO.read} al recibir un
     *         flujo {@code null})
     */
  public static BufferedImage cargarImagenFondoGroggyRing() throws IOException{
        if(TILESETFONDO == null){
            TILESETFONDO = ImageIO.read(TileSetManagerGroggyRing.class.getResourceAsStream("/assets/fondoGroggyRing.png"));
        }
        return TILESETFONDO;
    } //cargarImageFondo
  
  /**
     * Carga el tileset desde el directorio de recursos
     * ({@code /assets/tilesheet_GroggyRing.png}).
     * <p>
     * La imagen se lee solo la primera vez; en las siguientes invocaciones se
     * devuelve la imagen ya almacenada en {@link #TILESETGROGGYRING}.
     *
     * @return imagen del tilesheet..
     * @throws IOException si ocurre un error al leer el archivo de imagen
     * @throws IllegalArgumentException si el recurso no se encuentra en la
     *         ruta indicada (lo lanza {@code ImageIO.read} al recibir un
     *         flujo {@code null})
     */
   public static BufferedImage cargarImagenGroggyRing() throws IOException{
        if(TILESETGROGGYRING == null){
            TILESETGROGGYRING = ImageIO.read(TileSetManagerGroggyRing.class.getResourceAsStream("/assets/tilesheet_GroggyRing.png"));
        }
        return TILESETGROGGYRING;
    } //cargarImagen
   
}
