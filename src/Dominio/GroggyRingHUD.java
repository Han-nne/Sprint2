/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Dominio;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

/**
 *
 * @author johan
 */
public class GroggyRingHUD {

    private static final int ANCHO_PANEL = 1280;
    private static final int TAMANNO_BANDERA = 45;
    private static final int MARGEN = 12;
    private static final int PADDING = 5;
    private static final int SEPARACION = 8; 
    
    private static final Font FUENTE_PUNTAJE = new Font("Segoe UI", Font.BOLD, 12);
    private static final int ANCHO_CAJAS_PTS = 120;
    private static final int ALTO_CAJAS_PTS = 22;

    private final JugadorBola jugadorBola1;
    private final JugadorBola jugadorBola2;

    public GroggyRingHUD(JugadorBola jugadorBola1, JugadorBola jugadorBola2) {
        this.jugadorBola1 = jugadorBola1;
        this.jugadorBola2 = jugadorBola2;
    }

    public void dibujar(Graphics g) {
        if (this.jugadorBola1 == null || this.jugadorBola2 == null) {
            return;
        }

        int anchoCaja = TAMANNO_BANDERA + PADDING * 2;
        int altoCaja = TAMANNO_BANDERA + PADDING * 2;

        int yCaja = (MARGEN - PADDING) + altoCaja + SEPARACION;
        int yImagen = yCaja + PADDING;

        int xImagen1 = MARGEN;
        dibujarCaja(g, xImagen1 - PADDING, yCaja, anchoCaja, altoCaja);
        dibujarImagen(g, jugadorBola1, xImagen1, yImagen);

        int xImagen2 = ANCHO_PANEL - MARGEN - TAMANNO_BANDERA;
        dibujarCaja(g, xImagen2 - PADDING, yCaja, anchoCaja, altoCaja);
        dibujarImagen(g, jugadorBola2, xImagen2, yImagen);
        
        int yPuntaje = yCaja + altoCaja + 6;
        g.setFont(FUENTE_PUNTAJE);
        //Puntaje jugador 1
        dibujarCaja(g, MARGEN, yPuntaje, ANCHO_CAJAS_PTS, ALTO_CAJAS_PTS);
        g.setColor(Color.CYAN);
        g.drawString("Pts: " + Math.round(jugadorBola1.getTripulacion().getPuntaje()), MARGEN + 8, yPuntaje + 15);
        
        //Puntaje jugador 2
        int xPuntaje2 = ANCHO_PANEL - MARGEN - ANCHO_CAJAS_PTS;
        dibujarCaja(g, xPuntaje2, yPuntaje, ANCHO_CAJAS_PTS, ALTO_CAJAS_PTS);
        g.setColor(Color.CYAN);
        g.drawString("Pts: " + Math.round(jugadorBola2.getTripulacion().getPuntaje()), xPuntaje2 + 8, yPuntaje + 15);
    }

    private void dibujarCaja(Graphics g, int x, int y, int ancho, int alto) {
        g.setColor(new Color(0, 0, 0, 140));
        g.fillRoundRect(x, y, ancho, alto, 12, 12);
    }

    private void dibujarImagen(Graphics g, JugadorBola jugador, int x, int y) {
        if (jugador.getImagen() != null) {
            g.drawImage(jugador.getImagen(), x, y, TAMANNO_BANDERA, TAMANNO_BANDERA, null);
        }
    }

    public JugadorBola getJugadorBola1() {
        return jugadorBola1;
    }

    public JugadorBola getJugadorBola2() {
        return jugadorBola2;
    }
}