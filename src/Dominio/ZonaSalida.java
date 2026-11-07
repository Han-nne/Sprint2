/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Dominio;
 
import java.awt.Graphics;
import java.io.IOException;
import utility.TileSetManagerDonutRace;
 
/**
 * La clase {@code ZonaSalida} representa la zona de salida de la carrera, el área
 * desde la cual parten los barcos.
 * <p>
 * Extiende de {@link ObjetoMaritimo} e implementa {@link Colisionable}, por lo que
 * participa en la detección de colisiones. Es un objeto estático: no reacciona
 * al choque y solo se dibuja con una imagen (40x190 píxeles) tomada del tileset
 * del juego.
 * </p>
 * <p>
 * {@link DonutRace} se encarga de crearla y registrarla en el motor de colisiones.
 * </p>
 *
 * @author johan
 * @see ObjetoMaritimo
 * @see Colisionable
 * @see DonutRace
 */
public class ZonaSalida extends ObjetoMaritimo implements Colisionable{
 
    /**
     * Construye la zona de salida en la posición indicada y carga su imagen
     * (40x190 píxeles) desde el tileset.
     *
     * @param posX posición en el eje X
     * @param posY posición en el eje Y
     * @throws IOException si ocurre un error al cargar la imagen del tileset
     */
    public ZonaSalida(int posX, int posY) throws IOException {
        super(posX, posY);
        this.imagen= TileSetManagerDonutRace.cargarImagenDonutRace();
        this.imagen=this.imagen.getSubimage( 320,10,40,190);
    }
 
    /**
     * Obtiene el radio de colisión en el eje X, calculado como el ancho de la
     * imagen menos 25 píxeles de margen.
     *
     * @return el radio horizontal de la zona de salida
     */
    @Override
    public double getRadioX() {
        return  this.imagen.getWidth()-25;
    }
 
    /**
     * Obtiene el radio de colisión en el eje Y, calculado como el alto de la
     * imagen menos 25 píxeles de margen.
     *
     * @return el radio vertical de la zona de salida
     */
    @Override
    public double getRadioY() {
        return this.imagen.getHeight()-25;
    }
 
    /**
     * Define la reacción ante un choque. La zona de salida no hace nada al ser
     * tocada.
     *
     * @param otro objeto {@link Colisionable} con el que se produjo el choque
     */
    @Override
    public void accionChoque(Colisionable otro) {
    }
 
    /**
     * Dibuja la zona de salida en su posición.
     *
     * @param g contexto gráfico sobre el que se dibuja
     */
    @Override
    public void dibujar(Graphics g) {
         g.drawImage(imagen, (int)this.posX, (int)this.posY, null);
    }
 
}