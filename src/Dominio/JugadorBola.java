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
    
    private boolean turnoExtra = false;
    
    /**Indica la cantidad de casillas extra que se puede mover el jugador 
     * por la carta de desplazamiento.
     */
    private int casillasExtra = 0;
    
    /**Indica si el jugador ya ha usado una carta de ataque en su turno.*/
    private boolean ataqueUsado = false;
    
    /**Indica si el jugador ya ha usado una carta de defensa en su turno.*/
    private boolean defensaUsada = false;
    
    /**Indica la cantidad de cartas que el jugador a robado del mazo central.*/
    private int cartasRobadas = 0;
    
    /**Bandera que indica si el jugador ha robado alguna carta del mazo central durante su turno.*/
    private boolean cartaRobada = false;
    
    /**Bandera que indica si el jugador ha usado alguna carta del mazo personal durante su turno.*/
    private boolean cartaUsada = false;
    
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

    public boolean isTurnoExtra() { return turnoExtra;}

    public void setTurnoExtra(boolean turnoExtra) {this.turnoExtra = turnoExtra;}

    /**
     * Aumenta la cantidad de turnos omitidos del jugador afectado.
     * @param extra cantidad de turnos a omitir.
     */
    //public void incrementarTurnosOmitidos(int extra) { this.turnosOmitidos += extra;}

    /**
     * Se obtiene si el jugador ha robado al menos una carta del mazo central.
     * @return {@code true} si ha robado o {@code false} si no ha robado.
     */
    public boolean isCartaRobada() {
        return cartaRobada;
    }

    /**
     * Establece que el jugador a robado o no alguna carta del mazo central.
     * @param cartaRobada {@code true} si ha robado o {@code false} si no ha robado.
     */
    public void setCartaRobada(boolean cartaRobada) {
        this.cartaRobada = cartaRobada;
    }

    /**
     * Se obtiene si el jugador ha usado al menos una carta del mazo personal.
     * @return {@code true} si ha usado o {@code false} si no ha usado almenos una carta.
     */
    public boolean isCartaUsada() {
        return cartaUsada;
    }

    /**
     * Establece que el jugador a usado o no alguna carta del mazo personal.
     * @param cartaUsada  {@code true} si ha usado o {@code false} si no ha usado almenos una carta.
     */
    public void setCartaUsada(boolean cartaUsada) {
        this.cartaUsada = cartaUsada;
    }
    
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
    
    public void sumarCasillasExtra(int extra){this.casillasExtra += extra;}

    /**
     * Se obtiene si el jugador ya usó una carta de ataque durante su turno.
     * @return {@code true} si la lanzó. {@code false} si no la ha lanzado.
     */
    public boolean isAtaqueUsado() {return ataqueUsado;}

    /**
     * Establece si el jugador ya usó una carta de ataque durante su turno.
     * @param ataqueUsado bandera que modifica el estado.
     */
    public void setAtaqueUsado(boolean ataqueUsado) { this.ataqueUsado = ataqueUsado;}

    /**
     * Se obtiene si el jugador ya usó una carta de defensa durante su turno.
     * @return {@code true} si la lanzó. {@code false} si no la ha lanzado.
     */
    public boolean isDefensaUsada() {return defensaUsada;}

    /**
     * Establece si el jugador ya usó una carta de defensa durante su turno.
     * @param defensaUsada bandera que modifica el estado.
     */
    public void setDefensaUsada(boolean defensaUsada) {this.defensaUsada = defensaUsada;}

    /**
     * Se obtiene la cantidad de cartas que el jugador ha robado del mazo central.
     * @return número de cartas robadas del mazo central.
     */
    public int getCartasRobadas() {return cartasRobadas;}

    /**
     * Establece la cantidad de cartas que el jugador ha robado del mazo central.
     * @param cartasRobadas número de cartas robadas del mazo central.
     */
    public void setCartasRobadas(int cartasRobadas) {this.cartasRobadas = cartasRobadas;}
    
    /**Reiniciar el estado de ataque, defensa, número 
     * de cartas robadas y usadas al final del turno.
     */
     public void reiniciarTurnoTactico(){
         this.ataqueUsado = false;
         this.defensaUsada = false;
         this.cartasRobadas = 0;
         this.cartaRobada = false;
         this.cartaUsada = false;
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