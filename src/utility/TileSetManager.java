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
 * La carga no está sincronizada, así que no es segura para uso concurrente
 * desde varios hilos.
 *
 * @author johan
 */
public class TileSetManager {

    /**
     * Imagen del tileset de la carrera de donas ({@code tilesheet_DonutRace.png}).
     * <p>
     * Se inicializa la primera vez que se invoca
     * {@link #cargarImagenDonutRace()}. Vale {@code null} mientras no se haya
     * cargado.
     */
    private static BufferedImage TILESETDONUTRACE;

    /**
     * Imagen del tileset del mar ({@code Mar.png}).
     * <p>
     * Se inicializa la primera vez que se invoca {@link #cargarImagenMar()}.
     * Vale {@code null} mientras no se haya cargado.
     */
    private static BufferedImage TILESETMAR;

    /**
     * Tamaño en píxeles del lado de cada tile de barco (50 x 50).
     */
    public final static int TILESIZEBARCO= 50 ;

    /**
     * Carga el tileset de la carrera de donas desde el directorio de recursos
     * ({@code /assets/tilesheet_DonutRace.png}).
     * <p>
     * La imagen se lee solo la primera vez; en las siguientes invocaciones se
     * devuelve la imagen ya almacenada en {@link #TILESETDONUTRACE}.
     *
     * @return imagen del tileset de la carrera de donas
     * @throws IOException si ocurre un error al leer el archivo de imagen
     * @throws IllegalArgumentException si el recurso no se encuentra en la
     *         ruta indicada (lo lanza {@code ImageIO.read} al recibir un
     *         flujo {@code null})
     */
     public static BufferedImage cargarImagenDonutRace() throws IOException{
        if(TILESETDONUTRACE == null){
            TILESETDONUTRACE = ImageIO.read(TileSetManager.class.getResourceAsStream("/assets/tilesheet_DonutRace.png"));
        }
        return TILESETDONUTRACE;
    } //cargarImagenIsla

    /**
     * Carga el tileset del mar desde el directorio de recursos
     * ({@code /assets/Mar.png}).
     * <p>
     * La imagen se lee solo la primera vez; en las siguientes invocaciones se
     * devuelve la imagen ya almacenada en {@link #TILESETMAR}.
     *
     * @return imagen del tileset del mar
     * @throws IOException si ocurre un error al leer el archivo de imagen
     * @throws IllegalArgumentException si el recurso no se encuentra en la
     *         ruta indicada (lo lanza {@code ImageIO.read} al recibir un
     *         flujo {@code null})
     */
    public static BufferedImage cargarImagenMar() throws IOException{
         if(TILESETMAR == null){
            TILESETMAR = ImageIO.read(TileSetManager.class.getResourceAsStream("/assets/Mar.png"));
        }
        return TILESETMAR;
    }
}// fin clase
