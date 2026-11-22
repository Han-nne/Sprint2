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
 * Se encarga de dibujar la interfaz (HUD) del minijuego de combate.
 * Esta clase no contiene lógica de juego: solo toma el estado de los dos
 * jugadores ({@link CombatBarraJugador}), el tiempo restante y los puntajes
 * de cada tripulación, y los pinta sobre un {@link Graphics}.
 *
 * @author Keithy Carrillo
 * 
 * @see Combat
 * @see CombatBarraJugador
 */
public class CombatHUD {
    
     /** Fuente usada para el reloj de la cuenta regresiva. */
    private final Font fuenteTitulo;
    /** Fuente usada para los textos de los puntos de la barra y nombre del jugador. */
    private final Font fuenteTexto;
    /** Fuente usada para el símbolo dentro de cada tecla de la secuencia. */
    private final Font fuenteTeclas;
    
   /** Crea el HUD e inicializa las tres fuentes que se usarán al dibujar.*/
    public CombatHUD() {
        this.fuenteTitulo = new Font("Arial", Font.BOLD, 28);
        this.fuenteTexto = new Font("Arial", Font.BOLD, 20);
        this.fuenteTeclas = new Font("Monospaced", Font.BOLD, 24);
    }

     /**
     * Dibuja el HUD completo: el reloj, los paneles de ambos jugadores y las
     * calaveras de error (si corresponde).
     * 
     * El ancho de la pantalla se obtiene del área de recorte
     * ({@link Graphics#getClipBounds()}); si no está disponible se asume
     * un ancho de 1280 píxeles. El reloj se dibuja con una sombra negra
     * para que sea legible sobre cualquier fondo.
     *
     * @param g               contexto gráfico sobre el que se dibuja
     * @param jugador1        estado del jugador 1 (panel izquierdo, azul)
     * @param jugador2        estado del jugador 2 (panel derecho, rojo)
     * @param nombreJugador1  nombre que se muestra en el panel del jugador 1
     * @param nombreJugador2  nombre que se muestra en el panel del jugador 2
     * @param tiempoRestante  segundos restantes que se muestran en el reloj
     * @param terminado       indica si la partida terminó (actualmente no se usa al dibujar)
     * @param jugador1PTS     puntaje de la tripulación del jugador 1
     * @param jugador2PTS     puntaje de la tripulación del jugador 2
     */
    public void dibujar(Graphics g, CombatBarraJugador jugador1, CombatBarraJugador jugador2, String nombreJugador1, String nombreJugador2, int tiempoRestante, double jugador1PTS, double jugador2PTS){
        
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
    
   
    /**
     * Dibuja el panel de un jugador: fondo semitransparente, barra de poder
     * dividida en bloques, texto con los puntos, nombre y controles,
     * secuencia de teclas y puntaje de la tripulación.
     * 
     * La barra está formada por 20 bloques de 15 píxeles.
     * Cada bloque representa 5 puntos de poder, por lo que la barra asume una
     * escala de 0 a 100. Los bloques activos se pintan con el color del jugador
     * y el resto en gris oscuro.
     *
     * @param g          contexto gráfico sobre el que se dibuja
     * @param jugador    estado del jugador que se va a dibujar
     * @param titulo     texto que se muestra bajo la barra (nombre y controles)
     * @param x          coordenada X de la esquina superior izquierda del panel
     * @param y          coordenada Y de la esquina superior izquierda del panel
     * @param colorBarra color de los bloques activos de la barra y del título
     * @param jugadorPTS puntaje de la tripulación, que se muestra redondeado
     *                   en un recuadro debajo de la secuencia de teclas
     */
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
        Color colorFondoPuntaje = new Color(0, 0, 0, 120);
        Font fuentePuntaje = new Font("Segoe UI", Font.BOLD, 12);
        g.setColor(colorFondoPuntaje);
        g.fillRoundRect(x - 10, yPTS, 150, 22, 8, 8);
        g.setFont(fuentePuntaje);
        g.drawString("Pts: " + Math.round(jugadorPTS), x - 2 , yPTS + 15);        
    }
    
    /**
     * Dibuja una calavera con figuras básicas
     * para avisar visualmente que el jugador se equivocó. Si {@code mostrar}
     * es {@code false}, no dibuja nada.
     * 
     * La calavera se compone de un cráneo y mandíbula blancos, dos ojos y una
     * nariz negros, y tres líneas que simulan los dientes.
     *
     * @param g       contexto gráfico sobre el que se dibuja
     * @param mostrar {@code true} para dibujar la calavera, {@code false} para omitirla
     * @param x       coordenada X de la esquina superior izquierda de la calavera
     * @param y       coordenada Y de la esquina superior izquierda de la calavera
     * @see CombatBarraJugador#isMostrarCalavera()
     */
    private void dibujarCalavera(Graphics g, boolean mostrar, int x, int y){
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

     /**
     * Dibuja la secuencia de teclas que el jugador debe presionar, una casilla
     * por tecla, separadas 65 píxeles horizontalmente.
     * 
     * El color de cada casilla indica el progreso:
     * Verde: Tecla acertada.
     * Blanco: Tecla que se debe presionar ahora.
     * Gris claro: Teclas pendientes.
     *
     * @param g         contexto gráfico sobre el que se dibuja
     * @param secuencia códigos de tecla ({@link KeyEvent}) de la secuencia actual
     * @param progreso  índice de la tecla que toca presionar (las anteriores ya se acertaron)
     * @param startX    coordenada X de la primera casilla
     * @param startY    coordenada Y de las casillas
     */
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

    /**
     * Convierte el código de una tecla en el símbolo que se muestra dentro de
     * la casilla.
     *
     * @param keyCode código de la tecla ({@link KeyEvent#VK_W}, {@link KeyEvent#VK_UP}, etc.)
     * @return "W", "A", "D", "↑", "←" o "→" según la tecla; "?" si la tecla no es reconocida
     */
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
    
    /**Getter y setters*/
     /**
     * Obtiene la fuente utilizada para el reloj de cuenta regresiva.
     *
     * @return fuente del título
     */
    public Font getFuenteTitulo() {
        return fuenteTitulo;
    }
    /**
     * Obtiene la fuente utilizada para los textos de barra de poder y nombres.
     *
     * @return fuente de texto
     */
    public Font getFuenteTexto() {
        return fuenteTexto;
    }
    /**
     * Obtiene la fuente utilizada para los símbolos de las teclas.
     *
     * @return fuente de las teclas
     */
    public Font getFuenteTeclas() {
        return fuenteTeclas;
    }
}
