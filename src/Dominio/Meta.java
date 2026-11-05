/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Dominio;

import java.awt.Graphics;
import java.io.IOException;
import static utility.TileSetManager.cargarImagenDonutRace;

/**
 * La clase {@code Meta} representa la línea de meta de la carrera, a la que
 * deben llegar los barcos.
 * <p>
 * Extiende de {@link ObjetoMaritimo}. Es un objeto estático que se dibuja con una
 * imagen (40x190 píxeles) tomada del tileset del juego y participa en la
 * detección de colisiones. Su reacción ante un choque aún está pendiente de
 * implementar; por ahora {@link Barco#accionChoque(Colisionable)} simplemente la
 * ignora.
 * </p>
 * <p>
 * {@link DonutRace} se encarga de crearla y registrarla en el motor de colisiones.
 * </p>
 *
 * @author johan
 * @see ObjetoMaritimo
 * @see Barco
 * @see DonutRace
 */
public class Meta extends ObjetoMaritimo {

     /**
     * Construye la meta en la posición indicada y carga su imagen (40x190
     * píxeles) desde el tileset.
     *
     * @param posX posición en el eje X
     * @param posY posición en el eje Y
     * @throws IOException si ocurre un error al cargar la imagen del tileset
     */
    public Meta(int posX, int posY) throws IOException {
        super(posX, posY);
        this.imagen= cargarImagenDonutRace();
        this.imagen=this.imagen.getSubimage(370,10,40, 190);
    }

    /**
     * Dibuja la meta en su posición.
     *
     * @param g contexto gráfico sobre el que se dibuja
     */
    @Override
    public void dibujar(Graphics g) {
     g.drawImage(imagen, (int)this.posX, (int)this.posY, null);
    }

     /**
     * Define la reacción de la meta ante un choque. Pendiente de implementar: por
     * ahora no realiza ninguna acción.
     *
     * @param otro objeto {@link Colisionable} con el que se produjo el choque
     */
    @Override
    public void accionChoque(Colisionable otro) {
    //pendiente
    } 

       /**
     * Obtiene el radio de colisión en el eje X, calculado como el ancho de la
     * imagen menos 25 píxeles de margen.
     *
     * @return el radio horizontal de la meta
     */
    @Override
    public double getRadioX() {
      return  this.imagen.getWidth()-25;
    }

    /**
     * Obtiene el radio de colisión en el eje Y, calculado como el alto de la
     * imagen menos 25 píxeles de margen.
     *
     * @return el radio vertical de la meta
     */
    @Override
    public double getRadioY() {
        return this.imagen.getHeight()-25;
    }
}
