/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Dominio;

import java.awt.Color;
import java.awt.Graphics;

public class CasillaNeumatico extends Casilla {

    private JugadorBola dueno;   // Esta casilla pertenece a una bola en especifico 

    public CasillaNeumatico(float posX, float posY) {
        super(posX, posY);
    }

    public JugadorBola getDueno() { return dueno; }
    public void setDueno(JugadorBola dueno) { this.dueno = dueno; }

    @Override
    public void dibujar(Graphics g) {
        g.setColor(Color.BLACK);
        g.fillOval((int) this.posX, (int) this.posY, 128, 128);
    }
}