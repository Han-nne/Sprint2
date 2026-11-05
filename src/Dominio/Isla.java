/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Dominio;
 
import java.awt.Graphics;
import java.io.IOException;
import static utility.TileSetManager.cargarImagenDonutRace;
 
/**
 * La clase {@code Isla} representa la isla central del escenario, un obstáculo
 * fijo e inmóvil con el que chocan los barcos.
 * <p>
 * Extiende de {@link ObjetoMaritimo}. Su imagen (300x300 píxeles) se obtiene del
 * tileset del juego y su radio de colisión es de 140. A diferencia de otros
 * objetos, sobrescribe {@link #getX()} y {@link #getY()} para que la posición
 * usada en las colisiones corresponda al centro de la isla y no a la esquina
 * superior izquierda de la imagen.
 * </p>
 * <p>
 * {@link DonutRace} se encarga de crearla y registrarla en el motor de colisiones.
 * </p>
 *
 * @author johan
 * @see ObjetoMaritimo
 * @see DonutRace
 */
public class Isla extends ObjetoMaritimo{
 
    /**
     * Construye la isla en la posición indicada. Establece el radio de colisión
     * en 140 y carga su imagen (300x300 píxeles) desde el tileset.
     *
     * @param posX posición de dibujo en el eje X (esquina superior izquierda)
     * @param posY posición de dibujo en el eje Y (esquina superior izquierda)
     * @throws IOException si ocurre un error al cargar la imagen del tileset
     */
    public Isla(int posX, int posY) throws IOException {
        super(posX, posY);
        this.radio = 140;
        this.imagen= cargarImagenDonutRace();
        this.imagen=this.imagen.getSubimage( 10 , 10 , 300, 300);
    }
 
    /**
     * Dibuja la isla en su posición.
     *
     * @param g contexto gráfico sobre el que se dibuja
     */
    @Override
    public void dibujar(Graphics g) {
        g.drawImage(imagen, (int)this.posX, (int)this.posY, null);
    }
 
    /**
     * Define la reacción ante un choque. La isla no hace nada al ser tocada.
     *
     * @param otro objeto {@link Colisionable} con el que se produjo el choque
     */
    @Override
    public void accionChoque(Colisionable otro) {
 
    }
 
    /**
     * Obtiene la coordenada X del centro de colisión de la isla: la posición de
     * dibujo más la mitad de la imagen, menos 25 píxeles de ajuste.
     *
     * @return la coordenada X usada para detectar colisiones
     */
    @Override
    public double getX() {
        return this.posX+ (this.imagen.getHeight() / 2.0)-25;
    }
 
    /**
     * Obtiene la coordenada Y del centro de colisión de la isla: la posición de
     * dibujo más la mitad de la imagen, menos 25 píxeles de ajuste.
     *
     * @return la coordenada Y usada para detectar colisiones
     */
    @Override
    public double getY() {
        return this.posY + (this.imagen.getWidth() / 2.0)-25;
    }
 
    /**
     * Obtiene el radio de colisión de la isla en el eje X.
     *
     * @return el radio de la isla
     */
    @Override
    public double getRadioX() {
      return radio ;
    }
 
    /**
     * Obtiene el radio de colisión de la isla en el eje Y.
     *
     * @return el radio de la isla
     */
    @Override
    public double getRadioY() {
      return radio ;
    }
 
} //fin clase
