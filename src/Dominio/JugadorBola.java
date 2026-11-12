/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Dominio;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author johan
 */
public class JugadorBola {
    
    /**Valor de vida inicial del jugador.*/
    private int vida = 100;
    
    /**Lista de la mano de cartas del jugador.*/
    private List<Carta> mano = new ArrayList<>();
    
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
    
    /**
     * Se obtiene la vida actual del jugador.
     * @return La vida correspondiente al jugador.
     */
    public int getVida(){
        return vida;
    }
    
    /**
     * Actualiza la vida actual del jugador.
     */
    public void setVida(){
        this.vida = Math.max(0, vida);
    }
    
    /**
     * Disminuye la vida del jugador según la cantidad recibida.
     * @param cantidad valor que se le restará a la vida del jugador.
     */
    public void restarVida(int cantidad){
       this.vida = Math.max(0, vida - cantidad);
    }
    
    /**
     * Se obtiene la mano de cartas del jugador.
     * @return La mano correspondiente al jugador.
     */
    public List<Carta> getMano(){
        return mano;
    }
    
    /**
     * Agrega una carta al mano si se encuetra dentro del límite de cinco cartas.
     * @param carta carta correspondiente a la mano.
     */
    public void agregarCarta(Carta carta){
        if(mano.size() < 5) mano.add(carta);
    }
    
    /**
     * Elimina una carta de la mano.
     * @param carta carta correspondiente a la mano.
     */
    public void removerCarta(Carta carta){
        mano.remove(carta);
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