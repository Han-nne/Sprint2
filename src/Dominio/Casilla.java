/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Dominio;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

/**
 *
 * @author johan
 */
public abstract class Casilla {
    protected double posX;
    protected double posY;
    protected JugadorBola jugadorBola;
    protected BufferedImage imagenCasilla;

    public Casilla(float posX, float posY) {
        this.posX = posX;
        this.posY = posY;
    }

    public abstract void dibujar(Graphics g);

    public JugadorBola getJugadorBola() {
        return jugadorBola;
    }

    public void setJugadorBola(JugadorBola jugadorBola) {
        this.jugadorBola = jugadorBola;
    }

    public BufferedImage getImagenCasilla() {
        return imagenCasilla;
    }

    public void setImagenCasilla(BufferedImage imagenCasilla) {
        this.imagenCasilla = imagenCasilla;
    }
    
    public double getPosX() {
        return posX;
    }

    public void setPosX(float posX) {
        this.posX = posX;
    }

    public double getPosY() {
        return posY;
    }

    public void setPosY(float posY) {
        this.posY = posY;
    }
}
