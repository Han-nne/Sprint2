/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package GUI;

import Dominio.Tripulacion;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.JButton;
import javax.swing.JPanel;

/**
 * Panel que se muestra al finalizar el torneo. Presenta al campeón y el
 * marcador final con el mismo estilo visual que {@code DibujarMarcador}
 * (velo oscuro, texto dorado para el ganador y marcador grande), y ofrece
 * tres opciones: jugar de nuevo, volver al menú principal o salir del sistema.
 * <p>
 * Los textos se pintan directamente en {@link #paintComponent(Graphics)} y los
 * botones se posicionan en {@link #doLayout()} en proporción al tamaño del
 * panel.
 * </p>
 *
 * @author johan
 */
public class JPFinTorneo extends JPanel {

    /** Color del velo oscuro semitransparente que cubre el juego. */
    private static final Color COLOR_FONDO = new Color(0, 0, 0, 170);

    /** Color dorado usado para resaltar al campeón. */
    private static final Color COLOR_DESTACADO = new Color(255, 215, 0);

    /** Ancho de cada botón. */
    private static final int ANCHO_BOTON = 240;

    /** Alto de cada botón. */
    private static final int ALTO_BOTON = 44;

    /** Separación horizontal entre botones. */
    private static final int SEPARACION = 24;

    /** Botón para reiniciar el torneo. */
    private final JButton btnJugarDeNuevo;

    /** Botón para volver al menú principal. */
    private final JButton btnMenuPrincipal;

    /** Botón para cerrar la aplicación. */
    private final JButton btnSalirSistema;

    /** Tripulación del jugador 1. */
    private Tripulacion t1;

    /** Tripulación del jugador 2. */
    private Tripulacion t2;

    /** Minijuegos ganados por la tripulación 1. */
    private int puntos1;

    /** Minijuegos ganados por la tripulación 2. */
    private int puntos2;

    /**
     * Crea el panel de fin de torneo.
     *
     * @param t1 tripulación del jugador 1
     * @param t2 tripulación del jugador 2
     * @param puntos1 minijuegos ganados por la tripulación 1
     * @param puntos2 minijuegos ganados por la tripulación 2
     */
    public JPFinTorneo(Tripulacion t1, Tripulacion t2, int puntos1, int puntos2) {
        this.t1 = t1;
        this.t2 = t2;
        this.puntos1 = puntos1;
        this.puntos2 = puntos2;

        // No opaco: el velo con transparencia se pinta en paintComponent.
        setOpaque(false);
        setLayout(null);
        setBounds(0, 0, 1280, 720);

        btnJugarDeNuevo = crearBoton("Jugar de nuevo");
        btnMenuPrincipal = crearBoton("Menú Principal");
        btnSalirSistema = crearBoton("Salir sistema");

        add(btnJugarDeNuevo);
        add(btnMenuPrincipal);
        add(btnSalirSistema);
    }

    /**
     * Crea un botón con el estilo común de este panel.
     *
     * @param texto texto del botón
     * @return botón configurado
     */
    private JButton crearBoton(String texto) {
        JButton boton = new JButton(texto);
        boton.setFont(new Font("SansSerif", Font.BOLD, 18));
        boton.setPreferredSize(new Dimension(ANCHO_BOTON, ALTO_BOTON));
        boton.setFocusPainted(false);
        return boton;
    }

    /**
     * Coloca los tres botones en una fila centrada, en la parte inferior.
     */
    @Override
    public void doLayout() {
        int total = ANCHO_BOTON * 3 + SEPARACION * 2;
        int x = (getWidth() - total) / 2;
        int y = getHeight() * 84 / 100;
        btnJugarDeNuevo.setBounds(x, y, ANCHO_BOTON, ALTO_BOTON);
        btnMenuPrincipal.setBounds(x + ANCHO_BOTON + SEPARACION, y, ANCHO_BOTON, ALTO_BOTON);
        btnSalirSistema.setBounds(x + (ANCHO_BOTON + SEPARACION) * 2, y, ANCHO_BOTON, ALTO_BOTON);
    }

    /**
     * Pinta el velo, el título, el campeón y el marcador final.
     *
     * @param g contexto gráfico del componente
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g.create();
        try {
            int ancho = getWidth();
            int alto = getHeight();
            int centroX = ancho / 2;

            g2.setRenderingHint(java.awt.RenderingHints.KEY_TEXT_ANTIALIASING,
                    java.awt.RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

            g2.setColor(COLOR_FONDO);
            g2.fillRect(0, 0, ancho, alto);

            g2.setColor(Color.WHITE);
            g2.setFont(new Font("SansSerif", Font.BOLD, 36));
            centrado(g2, "TORNEO FINALIZADO", centroX, alto * 24 / 100);

            String textoGanador;
            if (puntos1 > puntos2) {
                textoGanador = "¡Campeón: " + t1.getNombreTripulacion() + "!";
            } else if (puntos2 > puntos1) {
                textoGanador = "¡Campeón: " + t2.getNombreTripulacion() + "!";
            } else {
                textoGanador = "El torneo termina en empate";
            }
            g2.setColor(COLOR_DESTACADO);
            g2.setFont(new Font("SansSerif", Font.BOLD, 48));
            centrado(g2, textoGanador, centroX, alto * 38 / 100);

            g2.setColor(Color.WHITE);
            g2.setFont(new Font("SansSerif", Font.BOLD, 96));
            centrado(g2, puntos1 + " - " + puntos2, centroX, alto * 58 / 100);

            g2.setFont(new Font("SansSerif", Font.PLAIN, 28));
            centrado(g2, t1.getNombreTripulacion(), ancho / 4, alto * 68 / 100);
            centrado(g2, t2.getNombreTripulacion(), ancho * 3 / 4, alto * 68 / 100);
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

    /**
     * Actualiza el marcador mostrado y vuelve a pintar el panel.
     *
     * @param t1 tripulación del jugador 1
     * @param t2 tripulación del jugador 2
     * @param puntos1 minijuegos ganados por la tripulación 1
     * @param puntos2 minijuegos ganados por la tripulación 2
     */
    public void actualizarMarcador(Tripulacion t1, Tripulacion t2, int puntos1, int puntos2) {
        this.t1 = t1;
        this.t2 = t2;
        this.puntos1 = puntos1;
        this.puntos2 = puntos2;
        repaint();
    }

    /**
     * @return botón para jugar de nuevo
     */
    public JButton getBtnJugarDeNuevo() {
        return btnJugarDeNuevo;
    }

    /**
     * @return botón para volver al menú principal
     */
    public JButton getBtnMenuPrincipal() {
        return btnMenuPrincipal;
    }

    /**
     * @return botón para salir del sistema
     */
    public JButton getBtnSalirSistema() {
        return btnSalirSistema;
    }
}

