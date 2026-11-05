/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Dominio;

import java.awt.Graphics;
import java.io.IOException;
import utility.TileSetManager;

/**
 * La clase {@code ObstaculoLanzado} representa un obstáculo lanzado (móvil 
 * en las cuatro direcciones: arriba, abajo, derecha, izquierda) por un barco del escenario
 * contra el cual puede chocar un {@link Barco}, {@link Obstaculo} o {@link ObstaculoLanzado}.
 * <p>
 * Extiende de {@link ObjetoMaritimo}. Mientras está activo se dibuja en pantalla;
 * al recibir un choque se desactiva y deja de dibujarse. Su imagen se obtiene
 * del tileset del juego.
 * </p>
 * <p>
 * Los obstáculos lanzados son creados y administrados por {@link DonutRace}, que en cada
 * actualización elimina de su lista y del motor de colisiones los que ya están
 * inactivos.
 * </p>
 *
 * @author emmanuelarce
 * @see ObjetoMaritimo
 * @see Obstaculo
 * @see Barco
 */
public class ObstaculoLanzado extends ObjetoMaritimo{

     /** Indica si el obstáculo sigue activo (visible y disponible para chocar). */
    private boolean activo;
    
    /** Atributo que diferencia si un obtáculo es fijo o lanzado.*/
    private boolean esLanzado = false;
    
    /** Atributo que indica la velocidad de desplazamiento de un obstáculo lanzado*/
    private double velocidadObstaculo = 0.0;
    
    /** Atributo que indica la dirección vertical del obstáculo lanzado*/
    private int direccionY = 0;
    
    /** Atributo que indica la dirección horizontal del obstáculo lanzado*/
    private int direccionX = 0;
    
    /**
     * Construye un obstáculo lanzado y móvil activo en la posición indicada. Establece el radio de
     * colisión en 14 y carga su imagen (25x25 píxeles) desde el tileset.
     * 
     * @param posX posición inicial en el eje X
     * @param posY posición inicial en el eje Y
     * @param direccionY dirección del movimiento en el eje Y.
     * @param velocidadObstaculo velocidad de movimiento en el eje Y.
     * @throws IOException si ocurre un error al cargar la imagen del tileset
     */
    public ObstaculoLanzado(double posX, double posY, int direccionX, int direccionY, double velocidadObstaculo) throws IOException {
        super(posX, posY);
        this.activo = true;
        this.radio = 14;
        this.esLanzado = true;
        this.direccionX = direccionX;
        this.direccionY = direccionY;
        this.velocidadObstaculo = velocidadObstaculo;
        
        this.imagen= TileSetManager.cargarImagenDonutRace();
        this.imagen=this.imagen.getSubimage( 150 , 380, 25, 25);
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
        
        if (otro instanceof  Barco || otro instanceof Obstaculo || otro instanceof ObstaculoLanzado
                || otro instanceof Isla || otro instanceof Meta || otro instanceof ZonaSalida) {
              this.activo = false;
        }
    } // accionChoque
    
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
    } // dibujar

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
    
    /**
     * Desplaza verticalmente al doble de velocidad el obstáculo lanzado (móvil).
     * <p>
     * Si el obstáculo lanzado se sale de los límites de la pantalla, 
     * se desactiva para que la clase {@code DonutRace} la elimine del sistema.
     */
    @Override
    public void mover(){
        if(esLanzado && activo){
            posX += (direccionX * velocidadObstaculo);
            posY += (direccionY * velocidadObstaculo);
            
            if(posY < 0 || posY > 720 || posX < 0 || posX > 1280){
                this.activo = false;
            }
        }
    } // mover
} // fin clase
