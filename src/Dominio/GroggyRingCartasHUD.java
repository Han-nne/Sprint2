/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Dominio;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import utility.GroggyRingCartas;

/**
 * Clase encargada de renderizar los elementos visuales de interfaz (HUD) de
 * GroggyRing relacionados con las cartas: el mazo central y los paneles con las
 * ranuras de la mano de cada jugador, en la parte inferior de la pantalla.
 * <p>
 * Las cartas que cada jugador tiene en su mano las dibuja {@link GroggyRing};
 * esta clase dibuja el fondo del panel, el nombre de la tripulación y las
 * ranuras vacías donde se ubican.
 * </p>
 *
 * @author emmanuelarce
 * @see GroggyRing
 * @see Carta
 * @see Mazo
 */
public class GroggyRingCartasHUD {

    /** Ubicación horizontal del panel del jugador 1. */
    private static final int PANEL_J1_X = 130;

    /** Ubicación horizontal del panel del jugador 2. */
    private static final int PANEL_J2_X = 810;

    /** Ubicación horizontal del mazo central. */
    private static final int MAZO_X = 590;

    /** Ubicación vertical base de los paneles y del mazo. */
    private static final int BASE_Y = 660;

    /** Ancho del panel de cada jugador. */
    private static final int ANCHO_PANEL = 340;

    /** Alto del panel de cada jugador. */
    private static final int ALTO_PANEL = 60;

    /** Ancho del mazo central. */
    private static final int ANCHO_MAZO = 100;

    /** Alto del mazo central. */
    private static final int ALTO_MAZO = 85;

    /** Cantidad de ranuras de cartas que se dibujan en el panel de cada jugador. */
    private static final int NUM_RANURAS = 5;

    /** Ancho de cada ranura de carta. */
    private static final int RANURA_ANCHO = 60;

    /** Alto de cada ranura de carta. */
    private static final int RANURA_ALTO = 45;

    /** Separación inicial de las ranuras respecto al borde izquierdo del panel. */
    private static final int RANURA_SEPARACION = 8;

    /** Color semitransparente de fondo del panel de cada jugador. */
    private static final Color COLOR_FONDO_PANEL = new Color(0, 0, 0, 140);

    /** Color semitransparente del contorno de las ranuras de cartas. */
    private static final Color COLOR_RANURA = new Color(255, 255, 255, 40);

    /** Color semitransparente de fondo del mazo central. */
    private static final Color COLOR_FONDO_MAZO = new Color(40, 40, 40, 180);

    /** Fuente del nombre de la tripulación en el panel. */
    private static final Font FUENTE_PANEL = new Font("Segoe UI", Font.BOLD, 14);

    /** Fuente del texto "MAZO" que se muestra si no se puede cargar el sprite. */
    private static final Font FUENTE_MAZO = new Font("Segoe UI", Font.BOLD, 12);

    /** Fuente del contador de cartas restantes. */
    private static final Font FUENTE_RESTANTES = new Font("Segoe UI", Font.PLAIN, 10);

    /** Datos del jugador 1. */
    private final JugadorBola jugador1;

    /** Datos del jugador 2. */
    private final JugadorBola jugador2;

    /** Datos del mazo del juego. */
    private final Mazo mazo;

    /** Sprite del mazo central; se carga una sola vez y se reutiliza en cada dibujo. */
    private BufferedImage spriteMazo;

    /** Indica si ya se intentó cargar el sprite del mazo, para no repetir el intento en cada dibujo. */
    private boolean spriteMazoIntentado = false;

    /**
     * Construye el renderizado HUD de las cartas de GroggyRing.
     * <p>
     * No se validan nulos en la construcción; si alguno de los parámetros es
     * {@code null}, {@link #dibujar(Graphics)} no dibujará nada.
     * </p>
     *
     * @param jugador1 bola y datos del jugador 1
     * @param jugador2 bola y datos del jugador 2
     * @param mazo mazo central del juego
     */
    public GroggyRingCartasHUD(JugadorBola jugador1, JugadorBola jugador2, Mazo mazo) {
        this.jugador1 = jugador1;
        this.jugador2 = jugador2;
        this.mazo = mazo;
    }

    /**
     * Dibuja los elementos de la interfaz en la parte inferior de la pantalla:
     * el panel del jugador 1 a la izquierda, el mazo central en el centro y el
     * panel del jugador 2 a la derecha. Si alguno de los jugadores o el mazo es
     * {@code null}, no dibuja nada.
     *
     * @param g contexto gráfico en el que se dibuja
     */
    public void dibujar(Graphics g) {
        if (jugador1 == null || jugador2 == null || mazo == null) {
            return;
        }

        dibujarPanelJugador(g, PANEL_J1_X, BASE_Y, jugador1);
        dibujarMazoCentral(g, MAZO_X, BASE_Y);
        dibujarPanelJugador(g, PANEL_J2_X, BASE_Y, jugador2);
    }

    /**
     * Dibuja el panel de un jugador: un cuadro semitransparente, el nombre de su
     * tripulación y {@value #NUM_RANURAS} ranuras vacías que marcan el lugar de
     * las cartas de su mano.
     * <p>
     * Este método modifica la fuente y el color del contexto gráfico recibido, y
     * no los restaura al finalizar.
     * </p>
     *
     * @param g contexto gráfico en el que se dibuja
     * @param x ubicación horizontal del panel en pantalla
     * @param y ubicación vertical base del panel en pantalla
     * @param jugador información del jugador correspondiente
     */
    private void dibujarPanelJugador(Graphics g, int x, int y, JugadorBola jugador) {
        g.setColor(COLOR_FONDO_PANEL);
        g.fillRoundRect(x, y - 35, ANCHO_PANEL, ALTO_PANEL, 12, 12);

        g.setColor(Color.WHITE);
        g.setFont(FUENTE_PANEL);
        String nombreTripulacion = jugador.getTripulacion().getNombreTripulacion();
        g.drawString(nombreTripulacion + " | Mazo personal: ", x + 10, y - 15);

        for (int i = 0; i < NUM_RANURAS; i++) {
            int cartaX = x + (i * RANURA_ANCHO + RANURA_SEPARACION);
            int cartaY = y + 5;

            g.setColor(COLOR_RANURA);
            g.drawRoundRect(cartaX, cartaY, RANURA_ANCHO, RANURA_ALTO, 6, 6);
        }
    } // dibujarPanelJugador

    /**
     * Dibuja el mazo central utilizando un sprite del tileset y muestra cuántas
     * cartas quedan. Si el sprite no se puede cargar, se dibuja el texto "MAZO".
     * <p>
     * Este método modifica la fuente y el color del contexto gráfico recibido, y
     * no los restaura al finalizar.
     * </p>
     *
     * @param g contexto en el que se dibuja
     * @param x ubicación horizontal del mazo en pantalla
     * @param y ubicación vertical base del mazo en pantalla
     */
    private void dibujarMazoCentral(Graphics g, int x, int y) {
        // Fondo del mazo
        g.setColor(COLOR_FONDO_MAZO);
        g.fillRoundRect(x, y - 25, ANCHO_MAZO, ALTO_MAZO, 10, 10);

        g.setColor(Color.ORANGE);
        g.drawRoundRect(x, y - 25, ANCHO_MAZO, ALTO_MAZO, 10, 10);

        BufferedImage sprite = obtenerSpriteMazo();
        if (sprite != null) {
            g.drawImage(sprite, x + 18, y - 18, null);
        } else {
            g.setColor(Color.WHITE);
            g.setFont(FUENTE_MAZO);
            g.drawString("MAZO", x + 30, y - 5);
        }

        g.setColor(Color.WHITE);
        g.setFont(FUENTE_RESTANTES);
        g.drawString("Restantes: " + mazo.getCartasRestantes(), x + 18, y + 55);
    } // dibujarMazoCentral

    /**
     * Obtiene el sprite del mazo central. La primera vez lo carga; si la carga
     * falla, registra el error una sola vez y devuelve {@code null} en las
     * llamadas siguientes, evitando leer el archivo y escribir el error en cada
     * cuadro del juego.
     *
     * @return el sprite del mazo, o {@code null} si no se pudo cargar
     */
    private BufferedImage obtenerSpriteMazo() {
        if (!spriteMazoIntentado) {
            spriteMazoIntentado = true;
            try {
                spriteMazo = GroggyRingCartas.cargarSpriteMazoCentral();
            } catch (IOException ex) {
                Logger.getLogger(GroggyRingCartasHUD.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return spriteMazo;
    } // obtenerSpriteMazo

} // fin clase