/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Dominio;

import static Dominio.ColorBarco.AZUL;
import static Dominio.ColorBarco.ROJO;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import static java.lang.Math.max;
import static java.lang.Math.signum;
import java.util.ArrayList;
import java.util.List;
import static utility.TileSetManager.TILESIZEBARCO;
import static utility.TileSetManager.cargarImagenDonutRace;

/**
 * La clase {@code Barco} representa una embarcación controlable dentro del juego.
 * <p>
 * Extiende de {@link ObjetoMovil}, por lo que hereda su posición, radio e imagen,
 * y define el comportamiento propio de un barco: movimiento en cuatro direcciones,
 * cambio de sprite según la dirección, límites del área de juego y manejo de
 * colisiones.
 * </p>
 * <p>
 * Al colisionar con un {@link Obstaculo}, el barco sufre una ralentización del 25%
 * de su velocidad que se mantiene hasta recorrer 75 píxeles, momento en que recupera
 * su velocidad original. Al colisionar con cualquier otro objeto (excepto una
 * {@link Meta}), el barco retrocede y se detiene.
 * </p>
 *
 * @author johan
 * @see ObjetoMovil
 * @see Colisionable
 * @see Tripulacion
 * @see ColorBarco
 */
public class Barco extends ObjetoMovil {
    
   /** Tripulación que maneja este barco. */
   private final Tripulacion tripulacion;
 
   /**Cantidad de obstáculos lanzados que puede lanzar un barco.*/
   private int municionObstaculosLanzados = 0;
   
   /** Dirección horizontal actual: -1 (izquierda), 0 (quieto) o 1 (derecha). */
   private double dirX = 0;
 
   /** Dirección vertical actual: -1 (arriba), 0 (quieto) o 1 (abajo). */
   private double dirY = 0;
 
   /** Dirección horizontal del obstáculo, por defecto: 1 (derecha). */
   private int ultimaDirX = 1;
   
   /** Dirección vertical del obstáculo, por defecto: 0. */
   private int ultimaDirY = 0;
   
   /** Indica si el barco ha cumplido la condición de victoria. */
   private boolean condicionVictoria = false;
 
   /** Velocidad actual del barco (puede estar reducida por una ralentización). */
   private double velocidadEmbarcacion;
 
   /** Respaldo de {@link #velocidadEmbarcacion} para restaurarla tras una ralentización. */
   private double velocidadOriginal;
 
   /** Distancia total recorrida por el barco, en píxeles. */
   private double distanciaRecorrida = 0.0;
 
   //Varibles de control de debuff y recuperación por colisión
   /** Indica si el barco se encuentra actualmente ralentizado por un choque. */
   private boolean enRalentización = false;
 
   /** Distancia recorrida, en píxeles, desde que inició la ralentización actual. */
   private double distanciaRalentizadaRecorrida = 0;
 
   /**
    * Sprites del barco según su dirección. Índices: 0 = arriba, 1 = derecha,
    * 2 = abajo, 3 = izquierda.
    */
   private List <BufferedImage> imagenBarcos;
 
   /**
    * Construye un barco con la tripulación, velocidad, posición y color indicados.
    * Establece el radio de colisión en 15 y carga los sprites correspondientes al
    * color; el sprite inicial es el que mira hacia la derecha.
    *
    * @param tripulacion tripulación que controla el barco
    * @param velocidadEmbarcacion velocidad inicial del barco, en píxeles por actualización
    * @param posX posición inicial en el eje X
    * @param posY posición inicial en el eje Y
    * @param colorBarco color del barco, que determina los sprites que se cargan
    * @throws IOException si ocurre un error al cargar la imagen de los sprites
    */
    public Barco(Tripulacion tripulacion, double velocidadEmbarcacion, double posX, double posY , int municionObstaculosLanzados, ColorBarco colorBarco) throws IOException {
        super(posX, posY );
        this.tripulacion = tripulacion;
        this.velocidadEmbarcacion = velocidadEmbarcacion;
        this.velocidadOriginal = velocidadEmbarcacion;
        this.municionObstaculosLanzados = municionObstaculosLanzados;
        this.radio = 15;
 
        this.imagenBarcos = new ArrayList<>();
        imagenBarco( colorBarco);
    }
 
    /**
     * Carga desde el tileset los cuatro sprites del barco (arriba, derecha, abajo e
     * izquierda) según su color y los almacena en la lista de imágenes. Luego
     * establece como imagen actual la que mira hacia la derecha.
     *
     * @param colorBarco color del barco ({@link ColorBarco#AZUL} o {@link ColorBarco#ROJO})
     * @throws IOException si ocurre un error al cargar la imagen del tileset
     */
    public void imagenBarco( ColorBarco colorBarco) throws IOException{
         BufferedImage tile = cargarImagenDonutRace();
         int tileSize = TILESIZEBARCO;
 
         if (colorBarco == AZUL) {
                this.imagenBarcos.add( tile.getSubimage(( tileSize * 0 ) + 10, ( tileSize * 7 ) - 30, tileSize, tileSize));
                this.imagenBarcos.add( tile.getSubimage( ( tileSize * 1 ) + 20, ( tileSize * 7 ) - 30, tileSize, tileSize));
                this.imagenBarcos.add( tile.getSubimage( ( tileSize * 2 ) + 30, ( tileSize * 7 ) - 30, tileSize, tileSize));
                this.imagenBarcos.add( tile.getSubimage( ( tileSize * 3 ) + 40, ( tileSize * 7 ) - 30, tileSize, tileSize));
                this.imagen = this.imagenBarcos.get(1);
         }
 
         if (colorBarco == ROJO) {
                this.imagenBarcos.add( tile.getSubimage( ( tileSize * 0 ) + 10, tileSize * 9 ,tileSize,tileSize));
                this.imagenBarcos.add( tile.getSubimage( ( tileSize * 1 ) + 20 , tileSize * 9, tileSize, tileSize));
                this.imagenBarcos.add( tile.getSubimage( (tileSize * 2 ) + 30, tileSize * 9,tileSize, tileSize));
                this.imagenBarcos.add( tile.getSubimage( (tileSize * 3) + 40, tileSize * 9 , tileSize, tileSize));
                this.imagen = this.imagenBarcos.get(1);
        }
 
    }

    /**
     * Obtiene la cantidad de obstáculos lanzados puede lanzar un barco.
     * @return Retorna la {@link municionObstaculosLanzados} asociada al barco.
     */
    public int getMunicionObstaculosLanzados() {
        return municionObstaculosLanzados;
    }

    /**
     * Indica si el barco ha cumplido la condición de victoria.
     *
     * @return {@code true} si el barco ganó; {@code false} en caso contrario
     */
    public boolean isCondicionVictoria() {
        return condicionVictoria;
    }
 
    /**
     * Obtiene la tripulación que maneja el barco.
     *
     * @return la {@link Tripulacion} asociada al barco
     */
    public Tripulacion getTripulacion() {
        return tripulacion;
    }
 
    /**
     * Permite que el barco lance un obstáculo lanzado si y sólo si {@link municionObstaculosLanzados} es distinto a cero.
     * @return Retorna {@code true} si permitió el disparo.
     * <p>
     * Retorna {@code false} si el barco se quedó sin munición.
     */
    public boolean lanzar(){
        if(municionObstaculosLanzados > 0){
            municionObstaculosLanzados --;
            return true;
        }
        return false;
    }
    
    /**
     * Dibuja el sprite actual del barco en su posición.
     *
     * @param g contexto gráfico sobre el que se dibuja
     */
    @Override
    public void dibujar(Graphics g) {
        g.drawImage(imagen, (int)this.posX, (int)this.posY, null);
    }
 
    /**
     * Método que retorna la velocidad original de los barcos para utilizarse en la clase DonutRace.
     * @return Retorna {@link velocidadOriginal} donde sea necesitado.
     */
    public double getVelocidadOriginal(){
        return velocidadOriginal;
    }
    
    /**
     * Define la reacción del barco al colisionar con otro objeto:
     * <ul>
     *   <li>Si es un {@link Obstaculo}: aplica la reducción de velocidad.</li>
     *   <li>Si es una {@link Meta}: no realiza ninguna acción por el momento.</li>
     *   <li>Cualquier otro objeto: revierte el último desplazamiento y detiene
     *       el barco en ambos ejes.</li>
     * </ul>
     *
     * @param otro objeto {@link Colisionable} con el que se produjo el choque
     */
    @Override
    public void accionChoque(Colisionable otro) {
 
        if(otro instanceof Obstaculo){
            aplicarReduccionVelocidad();
        }else if ( otro instanceof Meta) {
 
        }else {
             this.posX -= (dirX * velocidadEmbarcacion);
            dirX = 0;
 
            this.posY -= (dirY * velocidadEmbarcacion);
            dirY = 0;
        }
 
    } //accionChoque
 
    /**
     * El método {@link #aplicarReduccionVelocidad()} aplica una reducción de un cuarto (25%) de la velocidad
     * y reinicia el contador para medir los 75 píxeles de recuperación.
     * <p>
     * Si el barco no estaba ya ralentizado, respalda la velocidad actual en
     * {@link #velocidadOriginal}; así, choques consecutivos no acumulan la reducción.
     * </p>
     */
    private void aplicarReduccionVelocidad(){
        if(!enRalentización){
            this.velocidadOriginal = this.velocidadEmbarcacion;
        }
        this.velocidadEmbarcacion = this.velocidadOriginal *0.75;
        this.enRalentización = true;
        this.distanciaRalentizadaRecorrida = 0;
 
    }// aplicarReduccionVelocidad
 
    /**
     * Actualiza el estado del barco en cada ciclo del juego.
     * <p>
     * Si el barco se mueve, desplaza su posición según la dirección y la velocidad,
     * acumula la distancia recorrida y, si está ralentizado, controla la
     * recuperación: al alcanzar 75 píxeles recorridos restaura la velocidad original.
     * Finalmente, restringe la posición a los límites del área de juego
     * (X entre 0 y 1230, Y entre 0 y 670).
     * </p>
     */
    public void actualizar(){
        if (dirX != 0 || dirY != 0){
            posX += (dirX * velocidadEmbarcacion);
            posY += (dirY * velocidadEmbarcacion);
 
            int desplazamientoPixeles = (int) max(1, velocidadEmbarcacion);
            distanciaRecorrida += desplazamientoPixeles; 
            
            if(distanciaRecorrida >= 120.0){
                distanciaRecorrida = 0.0;
            }
 
            if(enRalentización){
                distanciaRalentizadaRecorrida += desplazamientoPixeles;
 
                if(distanciaRalentizadaRecorrida >=75.0){
                    this.velocidadEmbarcacion = this.velocidadOriginal;
                    this.enRalentización = false;
                    this.distanciaRalentizadaRecorrida = 0.0;
                }
            }
        } //if barco en movimiento
 
        if ((int)posX < 0) posX = 0;
        if ((int)posX > 1230) posX = 1230;
        if ((int)posY < 0) posY = 0;
        if ((int)posY > 670) posY = 670;
    }
 
    /**
     * Método que retorna el último valor horizontal del barco para determinar la dirección horizontal del obstáculo por lanzar.
     * @return Retorna {@link dirX} si el barco se mueve horizontalmente.
     * <p>
     * Retorna {@link ultimaDirX} si el barco se mueve horizontalmente y
     * usa la última dirección horizontal guardada en {@link #moverDerecha() } o {@link #moverIzquierda() }.
     */
    public int getDireccionDisparoX(){
        if(dirX != 0){
            return (int) Math.signum(dirX);
        }
        return ultimaDirX;
    } // getDireccionDisparoX
    
    /**
     * Método que retorna el último valor vertical del barco para determinar la dirección vertical del obstáculo por lanzar.
     * @return Retorna {@link dirY} si el barco se mueve verticalmente.
     * <p>
     * Retorna {@link ultimaDirY} si el barco se mueve verticalmente y
     * usa la última dirección vertical guardada en {@link #moverAdelante()} o {@link #moverAtras()}.
     */
    public int getDireccionDisparoY(){
        if(dirY != 0){
            return (int) signum(dirY);
        }
        return ultimaDirY;
    } // getDireccionDisparoY
    
    /**
     * Mueve el barco hacia arriba (dirección vertical -1), 
     * guarda la última dirección vertical para determinar la dirección del obstáculo lanzado y
     * cambia el sprite al que mira hacia arriba.
     */
   @Override
public void moverAdelante() {
    dirY = -1;  dirX = 0; // Mueve solamente hacia arriba
    ultimaDirX = 0;
    ultimaDirY = -1;
    this.imagen = this.imagenBarcos.get(0);
}
 
/**
 * Mueve el barco hacia abajo (dirección vertical 1), 
 * guarda la última dirección vertical para determinar la dirección del obstáculo lanzado y
 * cambia el sprite al que mira hacia abajo.
 */
@Override
public void moverAtras() {
    dirY = 1; 
    dirX = 0; // Mueve solamente hacia abajo
    ultimaDirX = 0;
    ultimaDirY = 1;
    this.imagen = this.imagenBarcos.get(2);
}
 
/**
 * Mueve el barco hacia la izquierda (dirección horizontal -1) y
 * cambia el sprite al que mira hacia la izquierda.
 */
@Override
public void moverIzquierda() {
    dirX = -1; 
    dirY = 0; // Mueve solamente a la izquierda
    ultimaDirX = -1;
    ultimaDirY = 0;
    this.imagen = this.imagenBarcos.get(3);
}
 
/**
 * Mueve el barco hacia la derecha (dirección horizontal 1) y
 * cambia el sprite al que mira hacia la derecha.
 */
@Override
public void moverDerecha() {
    dirX = 1; 
    dirY = 0; // Mueve solamente a la derecha
    ultimaDirX = 1;
    ultimaDirY = 0;
    this.imagen = this.imagenBarcos.get(1);
}
 
/**
 * Detiene el movimiento vertical del barco (dirección vertical en 0).
 */
public void detenerVertical(){
    dirY = 0; //detiene el barco
}
 
/**
 * Detiene el movimiento horizontal del barco (dirección horizontal en 0).
 */
public void detenerHorizontal(){
    dirX = 0; //detiene el barco
}
 
    /**
     * Obtiene el radio de colisión del barco en el eje X.
     *
     * @return el radio del barco
     */
    @Override
    public double getRadioX() {
       return radio ;
    }
 
    /**
     * Obtiene el radio de colisión del barco en el eje Y.
     *
     * @return el radio del barco
     */
    @Override
    public double getRadioY() {
        return radio ;
    }
}// fin clase
