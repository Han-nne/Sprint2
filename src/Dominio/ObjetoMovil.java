/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Dominio;


/**
 * La clase abstracta {@code ObjetoMovil} representa un objeto marítimo que el
 * jugador puede desplazar en las cuatro direcciones básicas.
 * <p>
 * Extiende de {@link ObjetoMaritimo}, heredando su posición, radio e imagen, y
 * agrega el contrato de movimiento que deben implementar las subclases concretas
 * (por ejemplo, {@link Barco}).
 * </p>
 *
 * @author johan
 * @see ObjetoMaritimo
 * @see Barco
 */
public abstract class ObjetoMovil extends ObjetoMaritimo{

 /**
     * Construye un objeto móvil en la posición indicada.
     *
     * @param posX posición inicial en el eje X
     * @param posY posición inicial en el eje Y
     */
    public ObjetoMovil(double posX, double posY ) {
        super(posX, posY);
    }
 
    /**
     * Mueve el objeto hacia adelante (hacia arriba en pantalla).
     */
      public abstract void moverAdelante();
 
    /**
     * Mueve el objeto hacia atrás (hacia abajo en pantalla).
     */
      public abstract void moverAtras();
 
    /**
     * Mueve el objeto hacia la izquierda.
     */
      public abstract void moverIzquierda();
 
    /**
     * Mueve el objeto hacia la derecha.
     */
      public abstract void moverDerecha();
 
}
