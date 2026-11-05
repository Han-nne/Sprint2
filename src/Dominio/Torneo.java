/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Dominio;

import java.awt.Graphics;
import java.io.IOException;
import java.util.List;

/**
 * La clase {@code Torneo} coordina el torneo de juegos y actúa como punto de
 * entrada desde la interfaz hacia la lógica del juego.
 * <p>
 * Recibe las dos tripulaciones que compiten y la lista de configuraciones de
 * los juegos, y delega en la partida activa (por ahora {@link DonutRace}) las
 * operaciones de actualización, dibujo y manejo del teclado. Además, sobre lo
 * que dibuja la partida superpone las banderas y los nombres de ambas
 * tripulaciones mediante {@link DibujarBandera}.
 * </p>
 *
 * @author johan
 * @see DonutRace
 * @see DibujarBandera
 * @see Tripulacion
 * @see ConfiguracionGeneral
 * @see ConfiguracionDonutRace
 */
public class Torneo {

    /**
     * Partida de DonutRace que se encuentra activa en el torneo.
     */
    private DonutRace donutRace;

    /**
     * Configuraciones de los juegos del torneo. La posición 0 corresponde a la
     * configuración de DonutRace.
     */
    private final List<ConfiguracionGeneral> configuraciones;

    /**
     * Dibujador de las banderas y los nombres de las dos tripulaciones. Se
     * crea una sola vez al construir el torneo y se dibuja encima de la
     * partida activa en cada ciclo.
     */
    private final DibujarBandera dibujarBandera;

    /**
     * Construye un torneo con las tripulaciones y configuraciones indicadas,
     * prepara el dibujador de banderas e inicia de inmediato la partida de
     * DonutRace usando la primera configuración de la lista (índice 0).
     *
     * @param tripulacion1 tripulación del jugador 1
     * @param tripulacion2 tripulación del jugador 2
     * @param configuraciones lista de configuraciones de los juegos; el
     * elemento en la posición 0 debe ser una {@link ConfiguracionDonutRace}
     * @throws IOException si ocurre un error al cargar las imágenes de los
     * objetos del juego
     * @throws ClassCastException si el primer elemento de la lista no es una
     * {@link ConfiguracionDonutRace}
     */
    public Torneo(Tripulacion tripulacion1, Tripulacion tripulacion2, List<ConfiguracionGeneral> configuraciones) throws IOException {
        this.configuraciones = configuraciones;
        this.dibujarBandera = new DibujarBandera( tripulacion1 , tripulacion2);
        iniciarJuegoDonutRace(tripulacion1, tripulacion2, (ConfiguracionDonutRace) configuraciones.get(0));
    }

    /**
     * Crea una nueva partida de {@link DonutRace} con las tripulaciones y la
     * configuración indicadas. Si ya existía una partida, es reemplazada por la
     * nueva.
     * <p>
     * No modifica el dibujador de banderas, que conserva las tripulaciones
     * recibidas al construir el torneo.
     * </p>
     *
     * @param tripulacion1 tripulación del jugador 1
     * @param tripulacion2 tripulación del jugador 2
     * @param configuracionDonutRace configuración de la partida (velocidad de
     * los barcos y cantidad de obstáculos fijos)
     * @throws java.io.IOException si ocurre un error al cargar las imágenes de
     * los objetos del juego
     */
    public void iniciarJuegoDonutRace(Tripulacion tripulacion1, Tripulacion tripulacion2, ConfiguracionDonutRace configuracionDonutRace) throws IOException {
        this.donutRace = new DonutRace(tripulacion1, tripulacion2, configuracionDonutRace);
    }

    /**
     * Actualiza el estado de la partida activa en cada ciclo del juego,
     * delegando en {@link DonutRace#actualizar()}.
     */
    public void actualizarJuego() {
        donutRace.actualizar();
    }

    /**
     * Dibuja los elementos del torneo: primero la partida activa, delegando en
     * {@link DonutRace#dibujar(Graphics)}, y después las banderas y nombres de
     * las tripulaciones con {@link DibujarBandera#dibujar(Graphics)}, de modo
     * que queden por encima del juego.
     *
     * @param g contexto gráfico sobre el que se dibuja
     */
    public void dibujar(Graphics g) {
        donutRace.dibujar(g);
        dibujarBandera.dibujar(g);
    }

    /**
     * Reenvía a la partida activa una tecla presionada, delegando en
     * {@link DonutRace#procesarTeclaPresionada(int)}.
     *
     * @param e código de la tecla presionada (constante de
     * {@link java.awt.event.KeyEvent})
     */
    public void procesarTeclaPresionada(int e) {
        donutRace.procesarTeclaPresionada(e);
    }

    /**
     * Reenvía a la partida activa una tecla liberada, delegando en
     * {@link DonutRace#procesarTeclaLiberada(int)}.
     *
     * @param e código de la tecla liberada (constante de
     * {@link java.awt.event.KeyEvent})
     */
    public void procesarTeclaLiberada(int e) {
        donutRace.procesarTeclaLiberada(e);
    }
} //fin clase
