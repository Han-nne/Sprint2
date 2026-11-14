  /*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Dominio;


import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.util.ArrayList;
import java.util.List;
import utility.TileSetManagerDonutRace;

/**
 * La clase {@code DonutRace} es la clase principal de la lógica del juego: crea
 * y coordina todos los elementos del escenario y centraliza el ciclo de dibujo,
 * actualización y manejo de teclado.
 * <p>
 * Contiene dos {@link Barco} (uno por jugador), la {@link Pista}, la {@link Meta},
 * la {@link ZonaSalida}, la {@link Isla} y listas de {@link Obstaculo} y
 * {@link ObstaculoLanzado}. Todos los objetos se registran en un
 * {@link MotorColision}, que resuelve los choques en cada actualización.
 * </p>
 * <p>
 * Controles: el jugador 1 se mueve con W, A, S, D y lanza obstáculos con R; el
 * jugador 2 se mueve con las flechas del teclado y lanza obstáculos con L.
 * </p>
 * <p>
 * Todo el estado de la partida es privado: el exterior solo interactúa con el
 * juego mediante los métodos de {@link Juego}.
 * </p>
 *
 * @author johan
 * @see Barco
 * @see Obstaculo
 * @see MotorColision
 * @see ConfiguracionDonutRace
 */
public class DonutRace implements Juego {

    /** Ancho del panel de juego, en píxeles. */
    private static final int ANCHO_PANEL = 1280;

    /** Alto del panel de juego, en píxeles. */
    private static final int ALTO_PANEL = 720;

    /** Posición inicial en X de ambos barcos. */
    private static final int BARCO_INICIO_X = 680;

    /** Posición inicial en Y del barco del jugador 1. */
    private static final int BARCO1_INICIO_Y = 570;

    /** Posición inicial en Y del barco del jugador 2. */
    private static final int BARCO2_INICIO_Y = 600;

    /** Imagen de fondo (el mar). */
    private final BufferedImage imagen;

    /** Barco del jugador 1 (azul), controlado con W, A, S, D. */
    private final Barco barcoJugador1;

    /** Barco del jugador 2 (rojo), controlado con las flechas. */
    private final Barco barcoJugador2;

    /** Bandera que determina si el juego concluyó o sigue en curso. */
    private boolean juegoTerminado = false;

    /**
     * Bandera que determina si se debe avanzar en el torneo.
     * <ul>
     *   <li>{@code false}: DonutRace sigue dibujándose.</li>
     *   <li>{@code true}: DonutRace deja de dibujarse y se avanza en el torneo.</li>
     * </ul>
     */
    private boolean listoParaAvanzar = false;

    /** Mensaje a visualizar cuando se determine el ganador. */
    private String mensajeGanador = "";

    /** Pista de la carrera. */
    private final Pista pista;

    /** Meta de la carrera. */
    private final Meta meta;

    /** Zona de salida de los barcos. */
    private final ZonaSalida salida;

    /** Isla fija del escenario. */
    private final Isla isla;

    /** Motor encargado de detectar y resolver las colisiones entre objetos. */
    private final MotorColision motorColision;

    /** Configuración del minijuego (velocidad, dificultad y obstáculos lanzables). */
    private final ConfiguracionDonutRace configuracionDonut;

    /** Interfaz de información (HUD) que muestra el estado de los barcos y el ganador. */
    private final DonutRaceHUD donutRaceHUD;

    /** Obstáculos fijos activos presentes en el escenario. */
    private final List<Obstaculo> obstaculos;

    /** Obstáculos lanzados activos presentes en el escenario. */
    private final List<ObstaculoLanzado> obstaculosLanzados;

    /**
     * Construye una partida con las dos tripulaciones indicadas. Crea los barcos
     * (azul en (680, 570) y rojo en (680, 600), con la velocidad y la munición
     * indicadas en la configuración), la pista, la meta, la zona de salida y la
     * isla, y luego inicia el juego con {@link #iniciarJuego()}.
     *
     * @param tripulacion1 tripulación del jugador 1
     * @param tripulacion2 tripulación del jugador 2
     * @param configuracionDonutRace configuración que define la velocidad de los
     *                               barcos, los obstáculos fijos y los obstáculos lanzables
     * @throws IOException si ocurre un error al cargar las imágenes de los objetos
     */
    public DonutRace(Tripulacion tripulacion1, Tripulacion tripulacion2,
                     ConfiguracionDonutRace configuracionDonutRace) throws IOException {

        this.configuracionDonut = configuracionDonutRace;
        this.imagen = TileSetManagerDonutRace.cargarImagenMar();

        this.pista = new Pista(635, 357);
        this.isla = new Isla(470, 230);
        this.meta = new Meta(595, 525);
        this.salida = new ZonaSalida(640, 525);

        this.barcoJugador1 = new Barco(tripulacion1, configuracionDonutRace.getVelocidadBarcos(),
                BARCO_INICIO_X, BARCO1_INICIO_Y, configuracionDonutRace.getObstaculosLanzados(),
                ColorBarco.AZUL);
        this.barcoJugador2 = new Barco(tripulacion2, configuracionDonutRace.getVelocidadBarcos(),
                BARCO_INICIO_X, BARCO2_INICIO_Y, configuracionDonutRace.getObstaculosLanzados(),
                ColorBarco.ROJO);

        this.obstaculos = new ArrayList<>();
        this.obstaculosLanzados = new ArrayList<>();
        this.donutRaceHUD = new DonutRaceHUD(barcoJugador1, barcoJugador2);
        this.motorColision = new MotorColision();

        iniciarJuego();

    }

    /**
     * Inicia el juego: genera los obstáculos con la dificultad seleccionada y registra
     * todos los objetos en el motor de colisiones.
     *
     * @throws IOException si ocurre un error al cargar la imagen de los obstáculos
     */
    private void iniciarJuego() throws IOException {
        obstaculos.addAll(GeneradorObstaculo.generar(configuracionDonut.getObstaculosFijos()));
        registrarObjetosColisionable();
    }

    /**
     * Registra en el {@link MotorColision} los barcos, la meta, la zona de salida,
     * la isla y todos los obstáculos y obstáculos lanzados actuales, para que participen en la
     * detección de colisiones.
     */
    private void registrarObjetosColisionable() {
        this.motorColision.registrarObjeto(barcoJugador1);
        this.motorColision.registrarObjeto(barcoJugador2);
        this.motorColision.registrarObjeto(meta);
        this.motorColision.registrarObjeto(salida);
        this.motorColision.registrarObjeto(isla);
        for (Obstaculo obstaculo : obstaculos) {
            this.motorColision.registrarObjeto(obstaculo);
        }
        for (ObstaculoLanzado lanzado : obstaculosLanzados) {
            this.motorColision.registrarObjeto(lanzado);
        }

    } // registrarObjetosColisionable

    /**
     * Agrega un obstáculo fijo al juego y lo registra en el motor de colisiones,
     * de modo que la lista del juego y el motor siempre se mantienen sincronizados.
     *
     * @param obstaculo obstáculo que se agrega
     */
    public void agregarObstaculo(Obstaculo obstaculo) {
        this.obstaculos.add(obstaculo);
        this.motorColision.registrarObjeto(obstaculo);
    }

    /**
     * Elimina un obstáculo fijo del juego y lo quita del motor de colisiones,
     * de modo que la lista del juego y el motor siempre se mantienen sincronizados.
     *
     * @param obstaculo obstáculo que se elimina
     */
    public void eliminarObstaculo(Obstaculo obstaculo) {
        if (this.obstaculos.remove(obstaculo)) {
            this.motorColision.eliminarObjeto(obstaculo);
        }
    }

    /**
     * Dibuja todos los elementos del juego: fondo, isla, meta, zona de salida, pista,
     * barcos, obstáculos, obstáculos lanzados y la interfaz de información (HUD).
     *
     * @param g contexto gráfico sobre el que se dibuja
     */
    @Override
    public void dibujar(Graphics g) {
        g.drawImage(this.imagen, 0, 0, ANCHO_PANEL, ALTO_PANEL, null);
        isla.dibujar(g);
        meta.dibujar(g);
        salida.dibujar(g);
        pista.dibujar(g);
        barcoJugador1.dibujar(g);
        barcoJugador2.dibujar(g);
        for (Obstaculo obstaculo : obstaculos) {
            obstaculo.dibujar(g);
        }

        for (ObstaculoLanzado lanzado : obstaculosLanzados) {
            lanzado.dibujar(g);
        }

        this.donutRaceHUD.dibujar(g, juegoTerminado, mensajeGanador);

    } // dibujar

    /**
     * Actualiza el estado del juego en cada ciclo: mueve los barcos y los limita a
     * la pista, mueve los obstáculos lanzados, resuelve las colisiones con el
     * {@link MotorColision} y elimina de las listas (y del motor) los obstáculos que
     * quedaron inactivos tras ser golpeados. Si el juego ya terminó, no hace nada.
     */
    @Override
    public void actualizar() {
        if (juegoTerminado) return;
        barcoJugador1.actualizar();
        barcoJugador2.actualizar();

        barcoJugador1.limitarA(pista);
        barcoJugador2.limitarA(pista);

        for (ObstaculoLanzado lanzado : obstaculosLanzados) {
            lanzado.mover();
        }
        this.motorColision.resolverColision();

        obstaculos.removeIf((Obstaculo obstaculo) -> {
            if (!obstaculo.isActivo()) {
                this.motorColision.eliminarObjeto(obstaculo);
                return true;
            }
            return false;
        });

        obstaculosLanzados.removeIf((ObstaculoLanzado lanzado) -> {
            if (!lanzado.isActivo()) {
                this.motorColision.eliminarObjeto(lanzado);
                return true;
            }
            return false;
        });
    } // actualizar

    /**
     * Gestiona el lanzamiento de un obstáculo móvil desde un barco.
     * <p>
     * Valida que el barco aún tenga munición según {@link Barco#lanzar()}. Si la
     * tiene, crea el obstáculo lanzado con {@link LanzadorObstaculo#crear(Barco)}
     * (que calcula su posición, dirección y velocidad, y le asigna una imagen
     * distinta a la de un obstáculo fijo), lo añade a la lista de obstáculos
     * lanzados y lo registra en el motor de colisiones.
     * </p>
     *
     * @param barco barco que lanzó el obstáculo
     * @throws UncheckedIOException si ocurre un error al cargar la imagen del obstáculo
     */
    private void lanzarObstaculos(Barco barco) {
        if (!barco.lanzar()) return;
        try {
            ObstaculoLanzado lanzado = LanzadorObstaculo.crear(barco);
            obstaculosLanzados.add(lanzado);
            motorColision.registrarObjeto(lanzado);
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    } // lanzarObstaculos

    /**
     * Procesa una tecla presionada y pone en movimiento el barco correspondiente o
     * lanza un obstáculo:
     * <ul>
     *   <li>Jugador 1: W, A, S, D para moverse y R para lanzar un obstáculo.</li>
     *   <li>Jugador 2: flechas direccionales para moverse y L para lanzar un obstáculo.</li>
     *   <li>Con el juego terminado, la barra espaciadora indica que se puede avanzar en el torneo.</li>
     * </ul>
     * Las demás teclas se ignoran.
     *
     * @param e código de la tecla presionada (constante de {@link KeyEvent})
     */
    @Override
    public void procesarTeclaPresionada(int e) {

        if (juegoTerminado) {
            if (e == KeyEvent.VK_SPACE) listoParaAvanzar = true;
            return;
        }
        switch (e) {
            case KeyEvent.VK_W: barcoJugador1.moverAdelante(); break;
            case KeyEvent.VK_S: barcoJugador1.moverAtras(); break;
            case KeyEvent.VK_A: barcoJugador1.moverIzquierda(); break;
            case KeyEvent.VK_D: barcoJugador1.moverDerecha(); break;
            case KeyEvent.VK_R: lanzarObstaculos(barcoJugador1); break;
            case KeyEvent.VK_UP: barcoJugador2.moverAdelante(); break;
            case KeyEvent.VK_DOWN: barcoJugador2.moverAtras(); break;
            case KeyEvent.VK_LEFT: barcoJugador2.moverIzquierda(); break;
            case KeyEvent.VK_RIGHT: barcoJugador2.moverDerecha(); break;
            case KeyEvent.VK_L: lanzarObstaculos(barcoJugador2); break;
            default: break;
        } // switch
    } // procesarTeclaPresionada

    /**
     * Procesa una tecla liberada y detiene el movimiento del barco en el eje
     * correspondiente (vertical para W, S, arriba y abajo; horizontal para A, D,
     * izquierda y derecha). Las demás teclas se ignoran.
     *
     * @param e código de la tecla liberada (constante de {@link KeyEvent})
     */
    @Override
    public void procesarTeclaLiberada(int e) {

        switch (e) {
            case KeyEvent.VK_W: barcoJugador1.detenerVertical(); break;
            case KeyEvent.VK_S: barcoJugador1.detenerVertical(); break;
            case KeyEvent.VK_A: barcoJugador1.detenerHorizontal(); break;
            case KeyEvent.VK_D: barcoJugador1.detenerHorizontal(); break;
            case KeyEvent.VK_UP: barcoJugador2.detenerVertical(); break;
            case KeyEvent.VK_DOWN: barcoJugador2.detenerVertical(); break;
            case KeyEvent.VK_LEFT: barcoJugador2.detenerHorizontal(); break;
            case KeyEvent.VK_RIGHT: barcoJugador2.detenerHorizontal(); break;
            default: break;
        } // switch
    } // procesarTeclaLiberada

    /**
     * Determina si el juego terminó y el torneo puede avanzar.
     * <p>
     * Si algún barco cruzó la meta, marca el juego como terminado y calcula el
     * ganador con {@link #determinarGanador(boolean, boolean)}. El torneo solo
     * avanza cuando, además, el jugador presionó la barra espaciadora.
     * </p>
     *
     * @return {@code true} para avanzar con el torneo; {@code false} para mantenerse
     *         en el juego en curso
     */
    @Override
    public boolean haTerminado() {

        if (!juegoTerminado) {
            boolean jugador1CruzoMeta = barcoJugador1.isCondicionVictoria();
            boolean jugador2CruzoMeta = barcoJugador2.isCondicionVictoria();

            if (jugador1CruzoMeta || jugador2CruzoMeta) {
                determinarGanador(jugador1CruzoMeta, jugador2CruzoMeta);
                juegoTerminado = true;
            }
        }

        return juegoTerminado && listoParaAvanzar;
    } // haTerminado

    /**
     * Procesa los barcos que cruzaron la meta para determinar el ganador y
     * construir el mensaje que se muestra en pantalla.
     * <p>
     * Si ambos barcos cruzaron la meta (empate en meta), gana el que tenga menor
     * cantidad de obstáculos colisionados; si también empatan en choques, el
     * resultado es un empate absoluto.
     * </p>
     *
     * @param jugador1CruzoMeta indica si el barco del jugador 1 cruzó la meta
     * @param jugador2CruzoMeta indica si el barco del jugador 2 cruzó la meta
     */
    private void determinarGanador(boolean jugador1CruzoMeta, boolean jugador2CruzoMeta) {
        String jugador1 = barcoJugador1.getTripulacion().getNombreTripulacion();
        String jugador2 = barcoJugador2.getTripulacion().getNombreTripulacion();

        if (jugador1CruzoMeta && !jugador2CruzoMeta) {
            mensajeGanador = "Ganador: " + jugador1 + "!!";
        } else if (!jugador1CruzoMeta && jugador2CruzoMeta) {
            mensajeGanador = "Ganador: " + jugador2 + "!!";
        } else {
            int colisionesJugador1 = barcoJugador1.getObstaculosColisionados();
            int colisionesJugador2 = barcoJugador2.getObstaculosColisionados();

            if (colisionesJugador1 < colisionesJugador2) {
                mensajeGanador = "!!Empate en meta!! Ganador por menos choques: " + jugador1;
            } else if (colisionesJugador2 < colisionesJugador1) {
                mensajeGanador = "!!Empate en meta!! Ganador por menos choques: " + jugador2;
            } else {
                mensajeGanador = "!!Empate absoluto!!";
            }
        }

    } // determinarGanador

} // fin clase