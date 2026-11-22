/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Dominio;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

/**
 *
 * @author Keithy Carrillo
 */
public class CombatHUD {
    
    private final Font fuenteTitulo;
    private final Font fuenteTexto;
    private final Font fuenteTeclas;
    private static final Font FUENTE_PUNTAJE = new Font("Segoe UI", Font.BOLD, 12);
    private static final Color COLOR_FONDO_PUNTAJE = new Color(0,0,0, 120);
    

    public CombatHUD() {
        this.fuenteTitulo = new Font("Arial", Font.BOLD, 28);
        this.fuenteTexto = new Font("Arial", Font.BOLD, 20);
        this.fuenteTeclas = new Font("Monospaced", Font.BOLD, 24);
    }

    public void dibujar(Graphics g, CombatBarraJugador jugador1, CombatBarraJugador jugador2, String nombreJugador1, 
            String nombreJugador2, int tiempoRestante, boolean terminado, String mensajeGanador,
            double jugador1PTS, double jugador2PTS){
        
        Rectangle limites = g.getClipBounds();
        int anchoPantalla = (limites != null && limites.width > 0) ? limites.width : 1280;
        
        g.setFont(this.fuenteTitulo);
        String textoReloj = String.valueOf(tiempoRestante);
        FontMetrics fmReloj = g.getFontMetrics();
        int xReloj = (anchoPantalla - fmReloj.stringWidth(textoReloj)) / 2;
        
        g.setColor(Color.BLACK);
        g.drawString(textoReloj, xReloj + 2, 52);
        g.setColor(Color.WHITE);
        g.drawString(textoReloj, xReloj, 50);
        
        Color colorAzul = new Color(0, 140, 255);
        Color colorRojo = new Color(220, 40, 40);
        
        dibujarPanelJugador(g, jugador1, nombreJugador1 + " (W, A, D)", 20, 95, colorAzul, jugador1PTS);
        dibujarPanelJugador(g, jugador2, nombreJugador2 + " (←, ↑, →)", anchoPantalla - 330, 95, colorRojo, jugador2PTS);
        
        dibujarCalavera(g, jugador1.isMostrarCalavera(), 325, 95);
        dibujarCalavera(g, jugador2.isMostrarCalavera(), anchoPantalla - 375, 95);

    }
    
   
    private void dibujarPanelJugador(Graphics g, CombatBarraJugador jugador, String titulo, int x, int y, Color colorBarra, double jugadorPTS){
        g.setColor(new Color(0, 0, 0, 160));
        g.fillRoundRect(x - 10, y - 8, 320, 100, 12, 12);
        
        int totalBloques = 20;
        int anchoBloque = 15;
        int altoBarra = 26;
        int bloquesActivos = jugador.getBarraPoder() / 5;
        
        for (int i = 0; i < totalBloques; i++) {
            int xBloque = x + (i * anchoBloque);
            
            if (i < bloquesActivos) {
                g.setColor(colorBarra);
            } else {
                g.setColor(Color.DARK_GRAY);
            }
            g.fillRect(xBloque, y, anchoBloque, altoBarra);
            
            g.setColor(Color.BLACK);
            g.drawRect(xBloque, y, anchoBloque, altoBarra);
        } // fin de for
        
        g.setColor(Color.WHITE);
        g.drawRect(x, y, 300, altoBarra);
        
        g.setFont(this.fuenteTexto);
        g.drawString("Barra de Poder: " + jugador.getBarraPoder() + " puntos", x, y + 48);
        
        g.setColor(colorBarra);
        g.drawString(titulo, x, y + 80);
        dibujarSecuenciaTeclas(g, jugador.getSecuenciaActual(), jugador.getIndiceProgreso(), x, y + 115);
        
        int yPTS = y + 176;
        g.setColor(COLOR_FONDO_PUNTAJE);
        g.fillRoundRect(x - 10, yPTS, 150, 22, 8, 8);
        g.setFont(FUENTE_PUNTAJE);
        g.setColor(Color.CYAN);
        g.drawString("Pts: " + Math.round(jugadorPTS), x - 2 , yPTS + 15);
        
        
    }
    
    
    public void dibujarCalavera(Graphics g, boolean mostrar, int x, int y){
        if (!mostrar) {
            return;
        }
        
        g.setColor(Color.WHITE);
        g.fillOval(x, y, 35, 30);
        g.fillRect(x + 8, y + 25, 19, 12);

        g.setColor(Color.BLACK);
        g.fillOval(x + 7, y + 10, 7, 8);
        g.fillOval(x + 21, y + 10, 7, 8);
        g.fillRect(x + 15, y + 18, 4, 5);

        g.drawLine(x + 11, y + 35, x + 11, y + 40);
        g.drawLine(x + 16, y + 35, x + 16, y + 40);
        g.drawLine(x + 21, y + 35, x + 21, y + 40);
    }


    private void dibujarSecuenciaTeclas(Graphics g, ArrayList<Integer> secuencia, int progreso, int startX, int startY) {
        g.setFont(this.fuenteTeclas);
        for (int i = 0; i < secuencia.size(); i++) {
            int posX = startX + (i * 65);
            
            if (i < progreso) {
                g.setColor(Color.GREEN);
            } else if (i == progreso) {
                g.setColor(Color.WHITE);
            } else {
                g.setColor(Color.LIGHT_GRAY);
            }
            
            g.fillRoundRect(posX, startY, 55, 55, 10, 10);
            g.setColor(Color.BLACK);
            g.drawRoundRect(posX, startY, 55, 55, 10, 10);
            g.drawString(obtenerSimboloTecla(secuencia.get(i)), posX + 19, startY + 36); 
            
        } // fin de for
    }

    private String obtenerSimboloTecla(int keyCode) {
        switch (keyCode) {
            case KeyEvent.VK_W:
                return "W";
            case KeyEvent.VK_A:
                return "A";
            case KeyEvent.VK_D:
                return "D";
            case KeyEvent.VK_UP:
                return "↑";
            case KeyEvent.VK_LEFT:
                return "←";
            case KeyEvent.VK_RIGHT:
                return "→";
            default:
                return "?";
        }
    }
}
