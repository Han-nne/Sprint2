/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Dominio;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

/**
 *
 * @author johan
 */
public class JugadorBola {
    
    public static final int TAM = Casilla.TAM;
     
    private int anotaciones;
     
    private int fila;
    private int columna;

    private Tripulacion tripulacion;
    private BufferedImage imagen;

    public JugadorBola(Tripulacion tripulacion , BufferedImage imagen ) {
        this.imagen = imagen;
        this.tripulacion = tripulacion;
    }
    
    public int getAnotaciones() { 
        return anotaciones; 
    }

    public void sumarAnotacion() { 
        anotaciones++; 
    }

    public void setPosicion(int fila, int columna) {
        this.fila = fila;
        this.columna = columna;
    }

    public int getFila()    { return fila; }
    public int getColumna() { return columna; }

    // derivadas de fila/columna
    public double getPosX() { return columna * TAM; }
    public double getPosY() { return fila * TAM; }

    public Tripulacion getTripulacion() { return tripulacion; }
    public void setTripulacion(Tripulacion tripulacion) { this.tripulacion = tripulacion; }

    public BufferedImage getImagen() { return imagen; }
    public void setImagen(BufferedImage imagen) { this.imagen = imagen; }

    public void dibujar(Graphics g) {
        int x = (int) getPosX();
        int y = (int) getPosY();
        if (imagen != null) {
            g.drawImage(imagen, x, y, TAM, TAM, null);
        } else {
            g.setColor(Color.RED);        // temporal , faltan los tiles
            g.fillOval(x + 16, y + 16, TAM - 32, TAM - 32);
        }
    }

    public void setAnotaciones(int anotaciones) {
        this.anotaciones = anotaciones;
    }

    public void setFila(int fila) {
        this.fila = fila;
    }

    public void setColumna(int columna) {
        this.columna = columna;
    }
    
}