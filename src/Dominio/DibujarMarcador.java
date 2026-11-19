/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Dominio;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;

/**
 * La clase {@code DibujarMarcador} dibuja la pantalla que se muestra al terminar
 * cada minijuego: el ganador de ese juego y el marcador general del torneo.
 * <p>
 * Solo se encarga de la presentación: no conoce la secuencia de minijuegos ni
 * modifica ningún estado. Todos los datos le llegan como parámetros.
 * </p>
 * <p>
 * El campeón del torneo <b>no</b> se muestra aquí: esa responsabilidad
 * le corresponde a {@code JPFinTorneo}, que se presenta después del último
 * minijuego.
 * </p>
 *
 * @see Torneo
 * @see DibujarBandera
 */
public class DibujarMarcador {

    /** Ancho del panel por si el contexto gráfico no informa el área visible. */
    private static final int ANCHO_POR_DEFECTO = 1280;

    /** Alto del panel por si el contexto gráfico no informa el área visible. */
    private static final int ALTO_POR_DEFECTO = 720;

    /** Color del velo oscuro semitransparente que cubre el juego. */
    private static final Color COLOR_FONDO = new Color(0, 0, 0, 170);

    /** Color dorado usado para resaltar al ganador del minijuego. */
    private static final Color COLOR_DESTACADO = new Color(255, 215, 0);

    /** Tripulación del jugador 1. */
    private final Tripulacion tripulacion1;

    /** Tripulación del jugador 2. */
    private final Tripulacion tripulacion2;

    /**
     * Crea un dibujador de marcador para dos tripulaciones.
     *
     * @param tripulacion1 tripulación del jugador 1
     * @param tripulacion2 tripulación del jugador 2
     */
    public DibujarMarcador(Tripulacion tripulacion1, Tripulacion tripulacion2) {
        this.tripulacion1 = tripulacion1;
        this.tripulacion2 = tripulacion2;
    }

    /**
     * Dibuja la pantalla de resultado sobre lo que haya en el contexto gráfico.
     * <p>
     * Si {@code siguienteJuego} es {@code null}, significa que el juego que
     * terminó era el último; en ese caso solo se indica que se pasará a la
     * pantalla final, sin anunciar al campeón.
     * </p>
     *
     * @param g contexto gráfico sobre el cual dibujar
     * @param nombreJuego nombre del minijuego que acaba de terminar
     * @param ganadorJuego ganador de ese minijuego; {@code null} si fue empate
     * @param puntos1 minijuegos ganados por la tripulación 1
     * @param puntos2 minijuegos ganados por la tripulación 2
     * @param siguienteJuego nombre del siguiente minijuego; {@code null} si el
     *                       que terminó era el último
     */
    public void dibujar(Graphics g, String nombreJuego, Tripulacion ganadorJuego,
                        int puntos1, int puntos2, String siguienteJuego) {
        Graphics2D g2 = (Graphics2D) g.create();
        try {
            Rectangle area = g2.getClipBounds();
            int ancho = area != null ? area.width : ANCHO_POR_DEFECTO;
            int alto = area != null ? area.height : ALTO_POR_DEFECTO;
            int x0 = area != null ? area.x : 0;
            int y0 = area != null ? area.y : 0;
            int centroX = x0 + ancho / 2;

            g2.setColor(COLOR_FONDO);
            g2.fillRect(x0, y0, ancho, alto);

            g2.setColor(Color.WHITE);
            g2.setFont(new Font("SansSerif", Font.BOLD, 36));
            centrado(g2, nombreJuego + " terminado", centroX, y0 + alto * 24 / 100);

            String textoGanador = ganadorJuego == null
                    ? "¡Empate!"
                    : "Ganador: " + ganadorJuego.getNombreTripulacion();
            g2.setColor(COLOR_DESTACADO);
            g2.setFont(new Font("SansSerif", Font.BOLD, 48));
            centrado(g2, textoGanador, centroX, y0 + alto * 38 / 100);

            g2.setColor(Color.WHITE);
            g2.setFont(new Font("SansSerif", Font.BOLD, 96));
            centrado(g2, puntos1 + " - " + puntos2, centroX, y0 + alto * 58 / 100);

            g2.setFont(new Font("SansSerif", Font.PLAIN, 28));
            centrado(g2, tripulacion1.getNombreTripulacion(), x0 + ancho / 4, y0 + alto * 68 / 100);
            centrado(g2, tripulacion2.getNombreTripulacion(), x0 + ancho * 3 / 4, y0 + alto * 68 / 100);

            g2.setFont(new Font("SansSerif", Font.ITALIC, 28));
            g2.setColor(Color.LIGHT_GRAY);
            String textoInferior = siguienteJuego == null
                    ? "Último minijuego completado"
                    : "Siguiente: " + siguienteJuego;
            centrado(g2, textoInferior, centroX, y0 + alto * 80 / 100);

            g2.setColor(Color.WHITE);
            g2.setFont(new Font("SansSerif", Font.BOLD, 24));
            String accion = siguienteJuego == null ? "finalizar" : "continuar";
            centrado(g2, "Presiona ESPACIO para " + accion, centroX, y0 + alto * 92 / 100);
        } finally {
            g2.dispose();
        }
    }

    /**
     * Dibuja un texto centrado horizontalmente en la posición indicada.
     *
     * @param g2 contexto gráfico con la fuente y el color ya configurados
     * @param texto texto a dibujar
     * @param centroX coordenada X sobre la cual se centra el texto
     * @param y línea base vertical del texto
     */
    private void centrado(Graphics2D g2, String texto, int centroX, int y) {
        FontMetrics fm = g2.getFontMetrics();
        g2.drawString(texto, centroX - fm.stringWidth(texto) / 2, y);
    }
}
