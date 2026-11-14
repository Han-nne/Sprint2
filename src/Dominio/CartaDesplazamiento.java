/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Dominio;

import java.awt.image.BufferedImage;

/**
 * Representa una carta individual con el efecto de desplazamiento en GroggyRing.
 * Extiende de {@link Carta}, heredando todos sus atributos.
 * <p>
 * El {@link aplicarEfecto} de la carta es moverse dos casillas extra de forma acumulable.
 * Solo afecta al jugador que lanza la carta.
 * @author emmanuelarce
 * @see Carta
 */
public class CartaDesplazamiento extends Carta{

    /**
     *  Construye una carta de desplazamiento con sus atributos principales.
     * 
     * @param nombre nombre identificativo de la carta.
     * @param descripcion explicación del efecto.
     * @param imagen imagen correspondiente a la carta.
     */
    public CartaDesplazamiento(String nombre, String descripcion, BufferedImage imagen) {
        super(nombre, descripcion, imagen);
    }

    @Override
    public void aplicarEfecto(JugadorBola usuario, JugadorBola rival, GroggyRing juego) {
        usuario.sumarCasillasExtra(2);
    }
    
}
