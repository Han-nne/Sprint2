/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package utility;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import javax.imageio.ImageIO;

/**
 * Clase de utilidad que lee la imagen de la bandera de una
 * {@link Dominio.Tripulacion} a partir de su ruta.
 * <p>
 * Busca la imagen primero como un archivo del disco, que es la ruta que se
 * guarda al registrar una tripulación y en el archivo de texto, y si ahí no
 * existe la busca como un recurso dentro del proyecto (por ejemplo, una bandera
 * por defecto empaquetada en el {@code .jar}).
 * </p>
 *
 * @author johan
 * @see Dominio.Tripulacion#getBandera()
 */
public class TripulacionImagen {
 
    /**
     * Lee la imagen indicada por la ruta y la devuelve como
     * {@link BufferedImage}.
     * <p>
     * El orden de búsqueda es:
     * </p>
     * <ol>
     *   <li>Un archivo del disco, por ejemplo
     *       {@code C:\Users\johan\banderas\pirata.png}.</li>
     *   <li>Un recurso dentro del proyecto, por ejemplo
     *       {@code /banderas/default.png}, usando
     *       {@link Class#getResourceAsStream(java.lang.String)}.</li>
     * </ol>
     *
     * @param ruta ruta del archivo de imagen en el disco, o ruta de un recurso
     *             dentro del proyecto
     * @return la imagen leída; nunca es {@code null}
     * @throws FileNotFoundException si la ruta no corresponde a un archivo del
     *                               disco ni a un recurso del proyecto
     * @throws IOException           si la ruta es {@code null} o está vacía, si
     *                               el archivo no es una imagen en un formato
     *                               soportado o si ocurre un error de lectura
     */
    public static BufferedImage imagentripulacion(String ruta) throws IOException {
        if (ruta == null || ruta.isBlank()) {
            throw new IOException("La ruta de la imagen está vacía");
        }
 
        BufferedImage img = null;
 
        File archivo = new File(ruta);
        if (archivo.isFile()) {
            img = ImageIO.read(archivo);
        } else {
            
            try (InputStream imagenDentro = TripulacionImagen.class.getResourceAsStream(ruta)) {
                if (imagenDentro == null) {
                    throw new FileNotFoundException("No se encontró la imagen: " + ruta);
                }
                img = ImageIO.read(imagenDentro);
            }
        }
 
        if (img == null) {
            throw new IOException("El archivo no es una imagen válida: " + ruta);
        }
        return img;
    }
}