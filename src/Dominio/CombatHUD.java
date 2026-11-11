/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Dominio;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

/**
 *
 * @author Keithy Carrillo
 */
public class CombatHUD {
    
    private Font fuenteTitulo;
    private Font fuenteTexto;
    private Font fuenteTeclas;

    public CombatHUD() {
        this.fuenteTitulo = new Font("Arial", Font.BOLD, 28);
        this.fuenteTexto = new Font("Arial", Font.BOLD, 20);
        this.fuenteTeclas = new Font("Monospaced", Font.BOLD, 24);
    }

    //getters y setters
    public Font getFuenteTitulo() {
        return fuenteTitulo;
    }

    public void setFuenteTitulo(Font fuenteTitulo) {
        this.fuenteTitulo = fuenteTitulo;
    }

    public Font getFuenteTexto() {
        return fuenteTexto;
    }

    public void setFuenteTexto(Font fuenteTexto) {
        this.fuenteTexto = fuenteTexto;
    }

    public Font getFuenteTeclas() {
        return fuenteTeclas;
    }

    public void setFuenteTeclas(Font fuenteTeclas) {
        this.fuenteTeclas = fuenteTeclas;
    }
    
    public void dibujar(Graphics g, CombatJugador jugador1, CombatJugador jugador2, String nombreJugador1, String nombreJugador2, int tiempoRestante, boolean terminado, String mensajeGanador){
        g.setColor(new Color(28, 33, 43));
        g.fillRect(0, 0, 2000, 1200);
        
        g.setFont(getFuenteTitulo());
        g.setColor(Color.WHITE);
        g.drawString("Tiempo restante: " + tiempoRestante + " segundos", 350, 60);
        
        dibujarPanelJugador(g, jugador1, nombreJugador1 + "(W, A, D)", 20, 95, 80, 510, new Color(0, 180, 255));
        dibujarPanelJugador(g, jugador2, nombreJugador2 + "(←, ↑, →)", 930, 95, 930, 510, new Color(255, 115, 0));
        
        if (terminado) {
            g.setColor(new Color(0, 0, 0, 210));
            g.fillRoundRect(330, 250, 620, 130, 15, 15);
            g.setColor(Color.YELLOW);
            g.setFont(getFuenteTitulo());
            g.drawString(mensajeGanador, 370, 305);
            
            g.setColor(Color.WHITE);
            g.setFont(getFuenteTexto());
            g.drawString("Presione cualquier tecla para continuar", 410, 350);
        }
    }
    
    
    private void dibujarPanelJugador(Graphics g, CombatJugador jugador, String titulo, int x, int y, int par3, int par4, Color colorBarra){
        g.setColor(Color.DARK_GRAY);
        g.fillRect(x, y, 300, 26);
        
        g.setColor(colorBarra);
        int anchoRelleno = Math.min(300, jugador.getBarraPoder() * 3);
        g.fillRect(x, y, anchoRelleno, 26);
        
        g.setColor(Color.WHITE);
        g.drawRect(x, y, 300, 26);
        
        g.setFont(getFuenteTexto());
        g.drawString("Barra de Poder: " + jugador.getBarraPoder() + " puntos", x, y + 48);
        
        g.setColor(colorBarra);
    
        dibujarSecuenciaTeclas(g, jugador.getSecuenciaActual(), jugador.getIndiceProgreso(), x, y + 115);
        
    }


    private void dibujarSecuenciaTeclas(Graphics g, ArrayList<Integer> secuencia, int progreso, int startX, int startY) {
        g.setFont(getFuenteTeclas());
        for (int i = 0; i < secuencia.size(); i++) {
            int posX = startX + (i * 65);
            
            if (i < progreso) {
                g.setColor(new Color(46, 204, 113));
            } else if (i == progreso) {
                g.setColor(new Color(241, 196, 15));
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
