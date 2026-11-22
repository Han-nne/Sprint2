/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Dominio;

import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.util.List;

/**
 * La clase {@code Torneo} coordina la secuencia de minijuegos que disputan dos
 * tripulaciones: {@link DonutRace}, {@link GroggyRing} y {@link Combat}, en ese
 * orden.
 * <p>
 * Mantiene el minijuego en curso y delega en él la actualización, el dibujo y el
 * manejo de teclado y ratón. Cuando un minijuego termina, lleva la cuenta del
 * marcador y muestra la pantalla de resultado (dibujada por
 * {@link DibujarMarcador}) hasta que un jugador presione la barra espaciadora;
 * entonces inicia el siguiente minijuego con la configuración que le corresponde.
 * Sobre cada minijuego dibuja las banderas de las tripulaciones mediante
 * {@link DibujarBandera}.
 * </p>
 * <p>
 * Tras el último minijuego el torneo pasa a estado finalizado
 * ({@link #isFinalizado()}). El anuncio del campeón no lo hace esta clase ni
 * {@link DibujarMarcador}, sino la interfaz gráfica ({@code JPFinTorneo}).
 * </p>
 *
 * @see Juego
 * @see ConfiguracionGeneral
 * @see DibujarBandera
 * @see DibujarMarcador
 */
public class Torneo {

    /** Posición de {@link DonutRace} en la secuencia de minijuegos. */
    private static final int JUEGO_DONUT_RACE = 0;

    /** Posición de {@link GroggyRing} en la secuencia de minijuegos. */
    private static final int JUEGO_GROGGY_RING = 1;

    /** Posición de {@link Combat} en la secuencia de minijuegos. */
    private static final int JUEGO_COMBAT = 2;

    /** Nombres de los minijuegos, en el orden de la secuencia (para la pantalla de resultado). */
    private static final String[] NOMBRES_JUEGOS = {"Donut Race", "Groggy Ring", "Combat"};

    /** Minijuego que se está disputando actualmente. */
    private Juego juegoActual;

    /** Indica si el torneo ya terminó (se disputaron todos los minijuegos). */
    private boolean finalizado = false;

    /** Posición del minijuego actual en la secuencia del torneo (desde 0). */
    private int indiceJuego = 0;

    /** Tripulación del jugador 1. */
    private final Tripulacion tripulacion1;

    /** Tripulación del jugador 2. */
    private final Tripulacion tripulacion2;

    /** Minijuegos ganados por la tripulación 1. */
    private int puntos1 = 0;

    /** Minijuegos ganados por la tripulación 2. */
    private int puntos2 = 0;

    /** Indica si se está mostrando la pantalla de resultado del minijuego que terminó. */
    private boolean mostrandoResultado = false;

    /** Se activa al presionar espacio en la pantalla de resultado; {@link #actualizarJuego()} lo consume. */
    private boolean continuarPendiente = false;

    /** Ganador del último minijuego disputado; {@code null} si fue empate. */
    private Tripulacion ganadorUltimoJuego;

    /**
     * Configuraciones de los minijuegos, en el orden en que se disputan (lista
     * inmutable): la posición 0 corresponde a Donut Race, la 1 a Groggy Ring y la
     * 2 a Combat.
     */
    private final List<ConfiguracionGeneral> configuraciones;

    /** Dibujador de las banderas y nombres de las tripulaciones. */
    private final DibujarBandera dibujarBandera;

    /** Dibujador de la pantalla de resultado y marcador. */
    private final DibujarMarcador dibujarMarcador;

    /**
     * Crea un torneo entre dos tripulaciones e inicia el primer minijuego.
     *
     * @param tripulacion1 tripulación del jugador 1.
     * @param tripulacion2 tripulación del jugador 2.
     * @param configuraciones configuraciones de los minijuegos, en el orden
     *                        Donut Race, Groggy Ring y Combat.
     * @throws IOException si ocurre un error al cargar los recursos del primer minijuego
     */
    public Torneo(Tripulacion tripulacion1, Tripulacion tripulacion2, List<ConfiguracionGeneral> configuraciones) throws IOException {
        this.tripulacion1 = tripulacion1;
        this.tripulacion2 = tripulacion2;
        this.configuraciones = List.copyOf(configuraciones);
        this.dibujarBandera = new DibujarBandera(tripulacion1, tripulacion2);
        this.dibujarMarcador = new DibujarMarcador(tripulacion1, tripulacion2);
        iniciarJuego(JUEGO_DONUT_RACE);
    }

    /**
     * Crea el minijuego que corresponde al índice indicado y lo establece como
     * el juego actual. El juego actual y el índice solo se actualizan si el
     * minijuego se crea correctamente.
     *
     * @param indice posición del minijuego en la secuencia (0 = Donut Race,
     *               1 = Groggy Ring, 2 = Combat)
     * @throws IOException si ocurre un error al cargar los recursos del minijuego
     * @throws IllegalArgumentException si no existe un minijuego para ese índice
     * @throws ClassCastException si la configuración en esa posición no es del
     *                            tipo que corresponde al minijuego
     */
    private void iniciarJuego(int indice) throws IOException {
        Juego nuevoJuego;
        switch (indice) {
            case JUEGO_DONUT_RACE:
                nuevoJuego = new DonutRace(tripulacion1, tripulacion2,
                        (ConfiguracionDonutRace) configuraciones.get(JUEGO_DONUT_RACE));
                break;
            case JUEGO_GROGGY_RING:
                nuevoJuego = new GroggyRing(tripulacion1, tripulacion2,
                        (ConfiguracionGroggyRing) configuraciones.get(JUEGO_GROGGY_RING));
                break;
            case JUEGO_COMBAT:
                nuevoJuego = new Combat(tripulacion1, tripulacion2,
                        (ConfiguracionCombat) configuraciones.get(JUEGO_COMBAT));
                break;
            default:
                throw new IllegalArgumentException("Juego inexistente: " + indice);
        }
        this.juegoActual = nuevoJuego;
        this.indiceJuego = indice;
    }

    /**
     * Registra el resultado del minijuego que acaba de terminar: suma el punto al
     * ganador (si no hubo empate) y activa la pantalla de resultado.
     */
    private void registrarResultado() {
        ganadorUltimoJuego = juegoActual.getGanador();
        if (ganadorUltimoJuego == tripulacion1) {
            puntos1++;
            tripulacion1.ajustarPuntaje(750);
        } else if (ganadorUltimoJuego == tripulacion2) {
            puntos2++;
            tripulacion2.ajustarPuntaje(750);
        }
        mostrandoResultado = true;
        continuarPendiente = false;
    }

    /**
     * Cierra la pantalla de resultado y avanza al siguiente minijuego, o termina
     * el torneo si ya se disputaron todos.
     *
     * @throws IOException si ocurre un error al cargar los recursos del siguiente minijuego
     */
    private void siguienteJuego() throws IOException {
        mostrandoResultado = false;
        continuarPendiente = false;
        if (indiceJuego + 1 < configuraciones.size()) {
            iniciarJuego(indiceJuego + 1);
        } else {
            this.finalizado = true;
        }
    }

    /**
     * Actualiza el minijuego actual. Cuando termina, muestra el resultado y espera
     * a que se presione la barra espaciadora para avanzar al siguiente minijuego.
     *
     * @throws IOException si ocurre un error al cargar los recursos del siguiente minijuego
     */
    public void actualizarJuego() throws IOException {
        if (finalizado) {
            return;
        }
        if (mostrandoResultado) {
            if (continuarPendiente) {
                siguienteJuego();
            }
            return;
        }
        juegoActual.actualizar();
        if (juegoActual.haTerminado()) {
            registrarResultado();
        }
    }

    /**
     * Dibuja el minijuego actual y, encima, las banderas y nombres de las
     * tripulaciones. Si hay una pantalla de resultado activa, la dibuja encima de todo.
     *
     * @param g contexto gráfico sobre el cual dibujar
     */
    public void dibujar(Graphics g) {
        juegoActual.dibujar(g);
        dibujarBandera.dibujar(g);
        if (mostrandoResultado) {
            boolean esUltimo = indiceJuego + 1 >= configuraciones.size();
            String siguiente = esUltimo ? null : NOMBRES_JUEGOS[indiceJuego + 1];
            dibujarMarcador.dibujar(g, NOMBRES_JUEGOS[indiceJuego], ganadorUltimoJuego,
                    puntos1, puntos2, siguiente);
        }
    }

    /**
     * Envía una tecla presionada al minijuego actual. Mientras se muestra la
     * pantalla de resultado, la barra espaciadora indica que se desea continuar y
     * el resto de teclas se ignoran.
     *
     * @param e código de la tecla presionada (constante de {@link java.awt.event.KeyEvent})
     */
    public void procesarTeclaPresionada(int e) {
        if (mostrandoResultado) {
            if (e == KeyEvent.VK_SPACE) {
                continuarPendiente = true;
            }
            return;
        }
        if (finalizado) {
            return;
        }
        juegoActual.procesarTeclaPresionada(e);
    }

    /**
     * Envía una tecla liberada al minijuego actual (se ignora mientras se
     * muestra la pantalla de resultado).
     *
     * @param e código de la tecla liberada (constante de {@link java.awt.event.KeyEvent})
     */
    public void procesarTeclaLiberada(int e) {
        if (mostrandoResultado || finalizado) {
            return;
        }
        juegoActual.procesarTeclaLiberada(e);
    }

    /**
     * Envía un clic del ratón al minijuego actual. Solo {@link GroggyRing} usa el
     * ratón; en los demás minijuegos el clic se ignora, y también mientras se
     * muestra la pantalla de resultado.
     *
     * @param x coordenada X del clic en el panel
     * @param y coordenada Y del clic en el panel
     * @throws IOException si ocurre un error al cargar la imagen de una carta robada
     */
    public void procesarClic(int x, int y) throws IOException {
        if (mostrandoResultado || finalizado) {
            return;
        }
        if (juegoActual instanceof GroggyRing) {
            ((GroggyRing) juegoActual).procesarClic(x, y);
        }
    }

    /**
     * Obtiene el minijuego que se está disputando actualmente.
     *
     * @return el {@link Juego} en curso
     */
    public Juego getJuegoActual() {
        return juegoActual;
    }

    /**
     * Obtiene la posición del minijuego actual en la secuencia del torneo.
     *
     * @return el índice del juego actual (0 = Donut Race, 1 = Groggy Ring, 2 = Combat)
     */
    public int getIndiceJuego() {
        return indiceJuego;
    }

    /**
     * Indica si el torneo ya terminó (se disputaron todos los minijuegos y se
     * cerró la pantalla del último resultado).
     *
     * @return {@code true} si el torneo finalizó
     */
    public boolean isFinalizado() {
        return finalizado;
    }

    /**
     * Obtiene los minijuegos ganados por la tripulación 1.
     *
     * @return puntos de la tripulación 1
     */
    public int getPuntos1() {
        return puntos1;
    }

    /**
     * Obtiene los minijuegos ganados por la tripulación 2.
     *
     * @return puntos de la tripulación 2
     */
    public int getPuntos2() {
        return puntos2;
    }

    /**
     * Obtiene la tripulación del jugador 1.
     *
     * @return tripulación 1
     */
    public Tripulacion getTripulacion1() {
        return tripulacion1;
    }

    /**
     * Obtiene la tripulación del jugador 2.
     *
     * @return tripulación 2
     */
    public Tripulacion getTripulacion2() {
        return tripulacion2;
    }
}