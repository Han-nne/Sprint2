/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Dominio;


import java.awt.Graphics;
import java.io.IOException;
import static utility.TileSetManager.cargarImagenDonutRace;

/**
 * La clase {@code Obstaculo} representa un obstáculo estático del escenario
 * contra el cual puede chocar un {@link Barco}.
 * <p>
 * Extiende de {@link ObjetoMaritimo}. Mientras está activo se dibuja en pantalla;
 * al recibir un choque se desactiva y deja de dibujarse. Su imagen se obtiene
 * del tileset del juego.
 * </p>
 * <p>
 * Los obstáculos son creados y administrados por {@link DonutRace}, que en cada
 * actualización elimina de su lista y del motor de colisiones los que ya están
 * inactivos.
 * </p>
 *
 * @author johan
 * @see ObjetoMaritimo
 * @see Barco
 */
public class Obstaculo extends ObjetoMaritimo{
 
    /** Indica si el obstáculo sigue activo (visible y disponible para chocar). */
    private boolean activo;
 
    /**
     * Construye un obstáculo fijo activo en la posición indicada. Establece el radio de
     * colisión en 14 y carga su imagen (25x25 píxeles) desde el tileset.
     *
     * @param posX posición inicial en el eje X
     * @param posY posición inicial en el eje Y
     * @throws IOException si ocurre un error al cargar la imagen del tileset
     */
    public Obstaculo(double posX, double posY) throws IOException {
        super(posX, posY);
        this.activo = true;
        this.radio = 14;
 
        this.imagen= cargarImagenDonutRace();
        this.imagen=this.imagen.getSubimage( 10 , 380, 25, 25);
    } //Constructor
    
    /**
     * Indica si el obstáculo sigue activo.
     *
     * @return {@code true} si el obstáculo está activo; {@code false} si ya fue
     *         golpeado
     */
    public boolean isActivo() {
        return activo;
    }
 
    /**
     * Define la reacción del obstáculo al ser golpeado: se desactiva, por lo que
     * deja de dibujarse y {@link DonutRace} lo elimina en la siguiente
     * actualización. Se desactiva con cualquier objeto que choque con él, no solo
     * con un {@link Barco}.
     *
     * @param otro objeto {@link Colisionable} con el que se produjo el choque
     */
    @Override
    public void accionChoque( Colisionable otro ){
        if (otro instanceof  Barco) {
              this.activo = false;
        } else if (otro instanceof Obstaculo){
            this.activo = false;
        } else if (otro instanceof ObstaculoLanzado){
            this.activo = false;
        }
    } //accionChoque
 
    /**
     * Dibuja el obstáculo en su posición solo si está activo.
     *
     * @param g contexto gráfico sobre el que se dibuja
     */
    @Override
    public void dibujar(Graphics g) {
        if (this.activo == true ) {
             g.drawImage(imagen, (int)this.posX, (int)this.posY, null);
         }
    }
 
    /**
     * Obtiene el radio de colisión del obstáculo en el eje X.
     *
     * @return el radio del obstáculo
     */
    @Override
    public double getRadioX() {
     return radio;
    }
 
    /**
     * Obtiene el radio de colisión del obstáculo en el eje Y.
     *
     * @return el radio del obstáculo
     */
    @Override
    public double getRadioY() {
      return radio;
    }
    
    
} //fin clase
