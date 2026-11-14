/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Dominio;

import java.awt.Graphics;
import java.io.IOException;
import java.util.List;

/**
 * La clase {@code Torneo} coordina la secuencia de minijuegos que disputan dos
 * tripulaciones: {@link DonutRace}, {@link GroggyRing} y {@link Combat}, en ese
 * orden.
 * <p>
 * Mantiene el minijuego en curso y delega en él la actualización, el dibujo y el
 * manejo de teclado y ratón. Cuando un minijuego termina, inicia el siguiente
 * con la configuración que le corresponde, y dibuja sobre cada minijuego las
 * banderas de las tripulaciones mediante {@link DibujarBandera}.
 * </p>
 * <p>
 * El minijuego en curso y su posición en la secuencia solo cambian dentro de
 * esta clase, de modo que siempre son coherentes entre sí.
 * </p>
 *
 * @see Juego
 * @see ConfiguracionGeneral
 * @see DibujarBandera
 */
public class Torneo {

    /** Posición de {@link DonutRace} en la secuencia de minijuegos. */
    private static final int JUEGO_DONUT_RACE = 0;

    /** Posición de {@link GroggyRing} en la secuencia de minijuegos. */
    private static final int JUEGO_GROGGY_RING = 1;

    /** Posición de {@link Combat} en la secuencia de minijuegos. */
    private static final int JUEGO_COMBAT = 2;

    /** Minijuego que se está disputando actualmente. */
    private Juego juegoActual;

    /** Posición del minijuego actual en la secuencia del torneo (desde 0). */
    private int indiceJuego = 0;

    /** Tripulación del jugador 1. */
    private final Tripulacion tripulacion1;

    /** Tripulación del jugador 2. */
    private final Tripulacion tripulacion2;

    /**
     * Configuraciones de los minijuegos, en el orden en que se disputan (lista
     * inmutable): la posición 0 corresponde a Donut Race, la 1 a Groggy Ring y la
     * 2 a Combat.
     */
    private final List<ConfiguracionGeneral> configuraciones;

    /** Dibujador de las banderas y nombres de las tripulaciones. */
    private final DibujarBandera dibujarBandera;

    /**
     * Crea un torneo entre dos tripulaciones e inicia el primer minijuego.
     * <p>
     * La lista de configuraciones se copia, por lo que modificar la lista original
     * después de crear el torneo no lo afecta.
     * </p>
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
     * Avanza al siguiente minijuego de la secuencia. Si ya se disputaron todos,
     * cierra la aplicación.
     * <p>
     * Es privado: solo se invoca cuando el minijuego actual termina, de modo que
     * no se pueda saltar un minijuego desde fuera de la clase.
     * </p>
     *
     * @throws IOException si ocurre un error al cargar los recursos del siguiente minijuego
     */
    private void siguienteJuego() throws IOException {
        if (indiceJuego + 1 < configuraciones.size()) {
            // Aquí pantalla que muestra la puntuación general del torneo de cada tripulación
            iniciarJuego(indiceJuego + 1);
        } else {
            // Aquí poner resultado final del torneo
            System.exit(0);
        }
    }

    /**
     * Actualiza el minijuego actual y, si este terminó, avanza al siguiente.
     *
     * @throws IOException si ocurre un error al cargar los recursos del siguiente minijuego
     */
    public void actualizarJuego() throws IOException {
        juegoActual.actualizar();
        if (juegoActual.haTerminado()) {
            siguienteJuego();
        }
    }

    /**
     * Dibuja el minijuego actual y, encima, las banderas y nombres de las
     * tripulaciones.
     *
     * @param g contexto gráfico sobre el cual dibujar
     */
    public void dibujar(Graphics g) {
        juegoActual.dibujar(g);
        dibujarBandera.dibujar(g);
    }

    /**
     * Envía una tecla presionada al minijuego actual.
     *
     * @param e código de la tecla presionada (constante de {@link java.awt.event.KeyEvent})
     */
    public void procesarTeclaPresionada(int e) {
        juegoActual.procesarTeclaPresionada(e);
    }

    /**
     * Envía una tecla liberada al minijuego actual.
     *
     * @param e código de la tecla liberada (constante de {@link java.awt.event.KeyEvent})
     */
    public void procesarTeclaLiberada(int e) {
        juegoActual.procesarTeclaLiberada(e);
    }

    /**
     * Envía un clic del ratón al minijuego actual. Solo {@link GroggyRing} usa el
     * ratón; en los demás minijuegos el clic se ignora.
     *
     * @param x coordenada X del clic en el panel
     * @param y coordenada Y del clic en el panel
     * @throws IOException si ocurre un error al cargar la imagen de una carta robada
     */
    public void procesarClic(int x, int y) throws IOException {
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

}