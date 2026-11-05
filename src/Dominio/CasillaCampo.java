/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Dominio;

import java.awt.Graphics;

/**
 *
 * @author johan
 */
public class CasillaCampo extends Casilla{

    public CasillaCampo(float posX, float posY) {
        super(posX, posY);
    }

    @Override
    public void dibujar(Graphics g) {
        g.fillRect( (int)this.posX, (int)this.posY, 128, 128);
    }
    
}
