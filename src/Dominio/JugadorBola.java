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
    
    /**Lista de la mano de cartas del jugador.*/
    private List<Carta> mazoPersonal = new ArrayList<>();
    
    /**bandera para protegerse de un ataque.*/
    private boolean escudoActivo = false;
    
    /**Bandera del efecto de un ataque recibido.*/
    private boolean turnoOmitido = false;
    
    /**Indica la cantidad de casillas extra que se puede mover el jugador 
     * por la carta de desplazamiento.
     */
    private int casillasExtra = 0;
    
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
     * Se obtiene la mano de cartas del jugador.
     * @return La mano correspondiente al jugador.
     */
    public List<Carta> getMazoPersonal(){
        return mazoPersonal;
    }
    
    /**
     * Agrega una carta al mazo personal si se encuetra dentro del límite de cinco cartas.
     * @param carta carta correspondiente al mazo personal.
     */
    public void guardarCartaEnMazoPersonal(Carta carta){
        if(mazoPersonal.size() < 5) mazoPersonal.add(carta);
    }
    
    /**
     * Elimina una carta del mazo personal.
     * @param carta carta correspondiente al mazo personal.
     */
    public void usarCartaEnMazoPersonal(Carta carta){
        mazoPersonal.remove(carta);
    }

    /** Se obtiene si el jugador está protegido por una carta.
     * 
     * @return {@code true} Si la protección está activada.
     * <p>
     * {@code false} Si la protección está desactivada.
     */
    public boolean isEscudoActivo() { return escudoActivo;}

    /**
     * Establece una protección al jugador.
     * @param escudoActivo bandera a modificar para proteger o desproteger el jugador correspondiente.
     */
    public void setEscudoActivo(boolean escudoActivo) { this.escudoActivo = escudoActivo;}

    /**Se obtiene si el jugador ha sido afectado por la carta de ataque.
     * 
     * @return {@code true} Si fue atacado.
     * <p>
     * {@code false} Si no fue atacado.
     */
    public boolean isTurnoOmitido() { return turnoOmitido;}

    /**
     * Establece una afectación por ataque al jugador.
     * @param turnoOmitido bandera a modificar para omitir o permitir el turno del jugador correspondiente.
     */
    public void setTurnoOmitido(boolean turnoOmitido) { this.turnoOmitido = turnoOmitido;}

    /**
     * Se obtiene cuántas casillas extra puede moverse el jugador (efecto de cartaDesplazamiento).
     * @return número de casillas extra a moverse.
     */
    public int getCasillasExtra() {
        return casillasExtra;
    }

    /**
     * Establece cuántas casillas extra se puede mover el jugador (efecto de cartaDesplazamiento).
     * @param casillasExtra número de casillas extra a moverse.
     */
    public void setCasillasExtra(int casillasExtra) {
        this.casillasExtra = casillasExtra;
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