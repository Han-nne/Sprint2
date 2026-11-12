/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Dominio;

import java.awt.image.BufferedImage;

/**
 *
 * @author emmanuelarce
 */
public class CartaDesplazamiento extends Carta{

    public CartaDesplazamiento(String nombre, String descripcion, BufferedImage imagen) {
        super(nombre, descripcion, imagen);
    }

    @Override
    public void aplicarEfecto(JugadorBola usuario, JugadorBola rival, GroggyRing juego) {
        usuario.setCasillasExtra(2);
    }
    
}
