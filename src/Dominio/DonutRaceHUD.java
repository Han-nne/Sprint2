/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Dominio;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

/**
 * Clase encargada de renderizar el HUD (Heads-Up Display) específico del minijuego
 * {@code Donut Race}, mostrando en tiempo real la información de estado de cada embarcación.
 * <p>
 * Dibuja los paneles semitransparentes correspondientes a cada jugador, reflejando:
 * </p>
 * <ul>
 *   <li>La munición disponible frente a la munición total permitida por la dificultad.</li>
 *   <li>La velocidad de navegación actual (con formato de un decimal y separador de coma),
 *       reflejando de forma dinámica los efectos de ralentización por colisiones.</li>
 * </ul>
 * <p>
 * Esta clase colabora con los objetos {@link Barco} para consultar su estado; no
 * modifica los barcos en ningún momento.
 * </p>
 *
 * @author emmanuelarce
 * @see Barco
 * @see DonutRace
 */
public class DonutRaceHUD {

    /** Ancho fijo del panel sobre el que se dibuja, en píxeles. */
    private static final int ANCHO_PANEL = 1280;

    /** Margen entre el borde del panel y los paneles del HUD, en píxeles. */
    private static final int MARGEN = 12;

    /** Posición vertical de los paneles de estado, en píxeles. */
    private static final int POS_Y_HUD = 65;

    /** Ancho del panel de estado del jugador 1, en píxeles. */
    private static final int ANCHO_HUD_1 = 175;

    /** Ancho del panel de estado del jugador 2, en píxeles. */
    private static final int ANCHO_HUD_2 = 150;

    /** Desplazamiento horizontal usado para ubicar el panel del jugador 2 respecto al borde derecho. */
    private static final int DESPLAZAMIENTO_HUD_2 = 175;

    /** Alto de los paneles de estado, en píxeles. */
    private static final int ALTO_HUD = 22;

    /** Diámetro de las esquinas redondeadas de los paneles de estado, en píxeles. */
    private static final int ARCO_HUD = 8;

    /** Fuente de los textos de estado de los barcos. */
    private static final Font FUENTE_HUD = new Font("Segoe UI", Font.BOLD, 12);

    /** Color semitransparente de fondo de los paneles de estado. */
    private static final Color COLOR_FONDO_HUD = new Color(0, 0, 0, 120);

    /** Barco del jugador 1, cuyo estado se muestra en el extremo izquierdo. */
    private final Barco barco1;

    /** Barco del jugador 2, cuyo estado se muestra en el extremo derecho. */
    private final Barco barco2;

    /**
     * Construye un renderizador de HUD para {@code Donut Race} asociado a las dos
     * embarcaciones en competencia.
     * <p>
     * No se validan nulos en la construcción; si alguno de los barcos es {@code null},
     * el método de dibujo se abortará de forma segura.
     * </p>
     *
     * @param barco1 embarcación del jugador 1
     * @param barco2 embarcación del jugador 2
     */
    public DonutRaceHUD(Barco barco1, Barco barco2) {
        this.barco1 = barco1;
        this.barco2 = barco2;
    }

    /**
     * Dibuja los paneles de estado (munición y velocidad) en la parte superior del panel,
     * ubicados de manera simétrica y alineados con las banderas de cada tripulación.
     * <p>
     * La velocidad del barco se procesa matemáticamente para garantizar un único dígito
     * decimal formateado con coma (ej: {@code 1,5x}).
     * </p>
     *
     * @param g contexto gráfico sobre el cual se realiza el renderizado,
     *          obtenido desde el método {@code paintComponent} o el orquestador
     * @param juegoTerminado bandera para dibujar la pantalla de fin de carrera
     * @param mensajeGanador mensaje con el resultado de la carrera; si es {@code null}
     *                       se muestra vacío
     */
    public void dibujar(Graphics g, boolean juegoTerminado, String mensajeGanador) {
        if (this.barco1 == null || this.barco2 == null) {
            return;
        }

        g.setFont(FUENTE_HUD);

        // Panel del jugador 1 (izquierda)
        g.setColor(COLOR_FONDO_HUD);
        g.fillRoundRect(MARGEN, POS_Y_HUD, ANCHO_HUD_1, ALTO_HUD, ARCO_HUD, ARCO_HUD);
        g.setColor(Color.CYAN);
        g.drawString(textoEstado(barco1), 20, POS_Y_HUD + 15);

        // Panel del jugador 2 (derecha)
        int posXBarco2HUD = ANCHO_PANEL - MARGEN - DESPLAZAMIENTO_HUD_2;
        g.setColor(COLOR_FONDO_HUD);
        g.fillRoundRect(posXBarco2HUD, POS_Y_HUD, ANCHO_HUD_2, ALTO_HUD, ARCO_HUD, ARCO_HUD);
        g.setColor(Color.CYAN);
        g.drawString(textoEstado(barco2), posXBarco2HUD + 8, POS_Y_HUD + 15);
    } // dibujar

    /**
     * Construye el texto de estado de un barco con su munición restante, la munición
     * total y su velocidad actual (redondeada a un decimal y con coma como separador).
     *
     * @param barco barco cuyo estado se describe
     * @return texto con el formato {@code "Mun: 3/5  |  Vel: 1,5x"}
     */
    private String textoEstado(Barco barco) {
        double velocidad = Math.round(barco.getVelocidadEmbarcacion() * 10.0) / 10.0;
        String velocidadTexto = String.valueOf(velocidad).replace('.', ',');

        return "Mun: " + barco.getMunicionObstaculoLanzados() + "/" + barco.getMunicionTotalObstaculosLanzados()
                + "  |  Vel: " + velocidadTexto + "x";
    } // textoEstado

    /**
     * Obtiene el barco del jugador 1 asociado al HUD.
     *
     * @return el barco del jugador 1, o {@code null} si no se asignó
     */
    public Barco getBarco1() {
        return barco1;
    }

    /**
     * Obtiene el barco del jugador 2 asociado al HUD.
     *
     * @return el barco del jugador 2, o {@code null} si no se asignó
     */
    public Barco getBarco2() {
        return barco2;
    }

} // fin clase