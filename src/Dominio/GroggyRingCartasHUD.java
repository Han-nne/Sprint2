/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Dominio;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

/**
 * Clase encargada de renderizar todos los elementos visuales de interfaz  (HUD)
 * de GroggyRing: mazo central y las cartas almacenadas en el mazo personal
 * de cada jugador en la parte inferior de la pantalla.
 * 
 * @author emmanuelarce
 * @see GroggyRing
 * @see Carta
 * @see Mazo
 */
public class GroggyRingCartasHUD {
    
    /** Datos del jugador 1*/
    private final JugadorBola jugador1;
    
    /** Datos del jugador 2*/
    private final JugadorBola jugador2;
    
    /**Datos del mazo del juego*/
    private final Mazo mazo;

    /**
     * Construye el renderizado HUD de GroggyRing.
     * @param jugador1 bola y datos del jugador 1.
     * @param jugador2 bola y datos del jugador 2.
     * @param mazo mazo central del juego.
     */
    public GroggyRingCartasHUD(JugadorBola jugador1, JugadorBola jugador2, Mazo mazo) {
        this.jugador1 = jugador1;
        this.jugador2 = jugador2;
        this.mazo = mazo;
    }
    
    /**
     * Dibuja los elementos de la interfaz al lado inferior y lateral de la pantalla.
     * @param g contexto gráfico en el que se dibuja.
     */
    public void dibujar(Graphics g){
        if(jugador1 == null || jugador2 == null || mazo == null) return;
        
        dibujarPanelJugador(g, 130, 660, jugador1, true);
        dibujarMazoCentral(g, 590, 660);
        dibujarPanelJugador(g, 810, 660, jugador2, false);
    }
    
    /**
     * Dibuja el panel de un jugador: un cuadro semitransparente y las 5 cartas en su mano.
     * 
     * @param g contexto gráfico en el que se dibuja 
     * @param x ubicación horizontal de la información en pantalla
     * @param y ubicación vertical de la información en pantalla
     * @param jugador información del jugador correspondiente
     * @param esJugador1 bandera en caso de que algún jugador sea {@code null}
     */
    public void dibujarPanelJugador(Graphics g, int x, int y, JugadorBola jugador, boolean esJugador1){
        g.setColor(new Color(0,0,0,140));
        g.fillRoundRect(x, y - 35, 340, 60, 12, 12);
        
        g.setColor(Color.WHITE);
        g.setFont(new Font("Segoe UI", Font.BOLD, 14));
        String nombreTripulacion = jugador.getTripulacion() != null
                ? jugador.getTripulacion().getNombreTripulacion()
                : (esJugador1 ? "Jugador 1 ": "Jugador 2");
        
        g.drawString(nombreTripulacion + " | Mazo personal: ", x + 10, y - 15);
        
        int cartaAncho = 60;
        int cartaAlto = 45;
        int separacion = 8;
        
        for (int i = 0; i < 5; i++) {
            int cartaX = x + (i * cartaAncho + separacion);
            int cartaY = y + 5;
            
            g.setColor(new Color(255,255,255, 40));
            g.drawRoundRect(cartaX, cartaY, cartaAncho, cartaAlto, 6, 6);
               
        }
    } // dibujarPanelJugador
    
    /**
     * Dibuja la representación visual del mazo central y cuántas cartas quedan.
     * 
     * @param g contexto en el que se dibuja
     * @param x ubicación horizontal del mazo en pantalla
     * @param y ubicación vertical del mazo en pantalla
     */
    public void dibujarMazoCentral(Graphics g, int x, int y){
        //Fondo del mazo
        g.setColor(new Color(40,40,40,180));
        g.fillRoundRect(x, y - 25, 100, 75, 10, 10);
        
        g.setColor(Color.ORANGE);
        g.drawRoundRect(x, y - 25, 100, 75, 10, 10);
        
        //Texto del mazo
        g.setColor(Color.WHITE);
        g.setFont(new Font("Segoe UI", Font.BOLD, 12));
        g.drawString("MAZO",  x + 30, y - 5);
        
        g.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        g.drawString("Restantes: " + mazo.getCartasRestantes(), x + 12, y + 20);
        g.drawString("(52 Totales)", x + 18, y + 38);
    } // dibujarMazoCentral
    
} // fin clase