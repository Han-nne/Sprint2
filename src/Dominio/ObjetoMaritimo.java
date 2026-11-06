/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Dominio;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import static java.lang.Math.max;

/**
 * La clase abstracta {@code ObjetoMaritimo} es la base de todos los elementos
 * que existen en el escenario marítimo del torneo (barcos, obstáculos, meta, etc.).
 * <p>
 * Define los atributos comunes de cualquier objeto del juego: su posición en
 * pantalla, su radio de colisión y su imagen. Además implementa la interfaz
 * {@link Colisionable}, proporcionando la posición necesaria para detectar
 * colisiones; las subclases deben completar el resto del contrato (por ejemplo,
 * los radios y la acción de choque) y definir cómo se dibujan.
 * </p>
 *
 * @author johan
 * @see Colisionable
 */
public abstract  class ObjetoMaritimo implements Colisionable {
     /** Posición del objeto en el eje X, en píxeles. */
    protected double posX;
 
    /** Posición del objeto en el eje Y, en píxeles. */
    protected double posY;
 
    /** Radio de colisión del objeto, en píxeles. */
    protected double radio;
 
    /** Imagen (sprite) con la que se representa el objeto. */
    protected BufferedImage imagen;
 
    /**
     * Construye un objeto marítimo en la posición indicada.
     *
     * @param posX posición inicial en el eje X
     * @param posY posición inicial en el eje Y
     */
    public ObjetoMaritimo( double posX,  double posY ) {
        this.posX = posX;
        this.posY = posY;
    }
    
 /**
 * Mantiene el objeto dentro de la pista. posX/posY es la esquina superior
 * izquierda del sprite, así que se trabaja con su centro.
 *
 * @param pista área en la que el objeto puede estar
 */
public void limitarA(Pista pista) {
    double mitadAncho = (imagen != null) ? imagen.getWidth() / 2.0 : radio;
    double mitadAlto  = (imagen != null) ? imagen.getHeight() / 2.0 : radio;
    double margen = max(mitadAncho, mitadAlto);

    double[] centro = pista.limitar(posX + mitadAncho, posY + mitadAlto, margen);
    posX = centro[0] - mitadAncho;
    posY = centro[1] - mitadAlto;
}
    
    /**
     * Dibuja el objeto en pantalla. Cada subclase define cómo se representa.
     *
     * @param g contexto gráfico sobre el que se dibuja
     */
    public abstract void dibujar(Graphics g);
 
    /**
     * Mueve el objeto. La implementación por defecto no hace nada, de modo que
     * los objetos estáticos no necesitan sobrescribirlo; las subclases con
     * movimiento propio lo redefinen.
     */
    public void mover(){}
 
    /**
     * Obtiene la posición del objeto en el eje X.
     *
     * @return la posición en X, en píxeles
     */
    @Override
    public double getX() {
        return this.posX;
    }
 
    /**
     * Obtiene la posición del objeto en el eje Y.
     *
     * @return la posición en Y, en píxeles
     */
    @Override
    public double getY() {
        return this.posY;
    }

    public double getPosX() {
        return posX;
    }

    public void setPosX(double posX) {
        this.posX = posX;
    }

    public double getPosY() {
        return posY;
    }

    public void setPosY(double posY) {
        this.posY = posY;
    }

    public double getRadio() {
        return radio;
    }

    public void setRadio(double radio) {
        this.radio = radio;
    }

    public BufferedImage getImagen() {
        return imagen;
    }

    public void setImagen(BufferedImage imagen) {
        this.imagen = imagen;
    }
}
