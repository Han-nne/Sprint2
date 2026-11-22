/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Dominio;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import javax.swing.Timer;
import utility.TileSetManagerCombat;

/**
 * Minijuego de combate entre dos tripulaciones del torneo.
 *
 * Dos jugadores compiten por mantener una barra de poder más alta que la
 * del rival:
 *   El jugador 1 usa las teclas W, A, D y el jugador 2 las
 *    flechas ↑, ←, →
 * 
 * Cada jugador debe presionar en orden una secuencia aleatoria de
 *       4 teclas (ver {@link CombatBarraJugador}).
 * Cuando un jugador completa una secuencia, la barra de su rival
 *    baja y su tripulación suma puntos; cuando se equivoca, baja su
 *       propia barra y aparece una calavera.
 *  La ronda termina cuando se acaban los 45 segundos o cuando la barra
 *       de algún jugador llega a 0. Gana quien tenga más poder; si hay
 *       igualdad, es empate.
 * 
 * Según la configuración ({@link ConfiguracionCombat#isTecladoCompartido()})
 * el combate tiene dos modos:
 * 
 *   Teclado compartido: Ambos jugadores juegan al mismo tiempo,
 *       cada uno con sus teclas.
 *   Por turnos: Los jugadores se alternan; solo se aceptan las
 *       teclas del jugador al que le toca, y el turno pasa al otro cuando
 *       completa o falla una secuencia.
 * 
 *  Implementa {@link Juego}, por lo que se integra al ciclo de actualización,
 *   dibujo y manejo de teclado del resto de juegos. El dibujo del HUD se delega
 *   en {@link CombatHUD}.
 * 
 * 
 * @author Keithy Carrillo
 * 
 * @see CombatBarraJugador
 * @see CombatHUD
 * @see Juego
 */
public class Combat implements Juego {
    /** Primera tripulación en combate (jugador 1, teclas W, A, D). */
    private final Tripulacion tripulacion1;
    /** Segunda tripulación en combate (jugador 2, teclas de flechas). */
    private final Tripulacion tripulacion2;
    /** Configuración del combate (valor inicial de la barra y modo de teclado). */
    private final ConfiguracionCombat configuracionCombat;
    /** Imagen de fondo del combate. */
    private final BufferedImage fondo;
    /** Componente que dibuja la interfaz (reloj, barras, secuencias, 
     *   puntajes y calaveras). */
    private final CombatHUD hudCombat;
    
    /** Estado de la barra de poder y la secuencia del jugador 1. */
    private CombatBarraJugador jugadorCombat1;
    /** Estado de la barra de poder y la secuencia del jugador 2. */
    private CombatBarraJugador jugadorCombat2;
    /** Tripulación ganadora de la ronda; {@code null} si aún no termina o 
     *  si hubo empate. */
    private Tripulacion ganador;
    /** Segundos que quedan para que termine la ronda. */
    private int tiempoRestanteCombat;
    /** Jugador al que le toca jugar en el modo por turnos (1 o 2). No se 
     *  usa con teclado compartido. */
    private int turnoActual;
    /** {@code true} cuando la ronda ya terminó y se conoce el resultado. */
    private boolean partidaTerminadaCombat;
    /** Temporizador de Swing que se ejecuta cada 1000 ms para descontar 
     *  un segundo del reloj. */
    private Timer temporizadorCombat;

    /**
     * Crea un combate entre dos tripulaciones, carga la imagen de fondo,
     * crea las barras de ambos jugadores e inicia la partida.
     *
     * @param tripulacion1        primera tripulación (jugador 1)
     * @param tripulacion2        segunda tripulación (jugador 2)
     * @param configuracionCombat configuración del combate
     * @throws IOException si no se puede cargar la imagen de fondo
     */
    public Combat(Tripulacion tripulacion1, Tripulacion tripulacion2, ConfiguracionCombat configuracionCombat) throws IOException {
        this.tripulacion1 = tripulacion1;
        this.tripulacion2 = tripulacion2;
        this.configuracionCombat = configuracionCombat;
        this.hudCombat = new CombatHUD();
        this.fondo = TileSetManagerCombat.cargarImagenFondoCombat();
        
        ArrayList<Integer> controlesJugador1 = new ArrayList<>();
        controlesJugador1.add(KeyEvent.VK_W);
        controlesJugador1.add(KeyEvent.VK_A);
        controlesJugador1.add(KeyEvent.VK_D);
          
        ArrayList<Integer> controlesJugador2 = new ArrayList<>();
        controlesJugador2.add(KeyEvent.VK_UP);
        controlesJugador2.add(KeyEvent.VK_RIGHT);
        controlesJugador2.add(KeyEvent.VK_LEFT);

        int valorInicial = this.configuracionCombat.getValorInicialBarra();
        int longitudSecuencia = 4;
        
        this.jugadorCombat1 = new CombatBarraJugador(this.tripulacion1, valorInicial, controlesJugador1, longitudSecuencia);
        this.jugadorCombat2 = new CombatBarraJugador(this.tripulacion2, valorInicial, controlesJugador2, longitudSecuencia);
        
        iniciarJuego();
    }

     /**
     * Prepara una partida nueva.
     * 
     * El temporizador se ejecuta cada segundo mientras la partida no haya
     * terminado: actualiza el tiempo de las calaveras de ambos jugadores y
     * descuenta un segundo del reloj. Al llegar a 0 se detiene y finaliza la
     * ronda con {@link #finalizarRondaPorJugador()}. Si ya existía un
     * temporizador previo, se detiene antes de crear el nuevo.
     * 
     */    
    private void iniciarJuego() {
        
        this.ganador = null;
        this.turnoActual = 1;
        this.tiempoRestanteCombat = 45;
        this.partidaTerminadaCombat = false;
        
        if (this.temporizadorCombat != null) {
            this.temporizadorCombat.stop();
        }
        
        this.temporizadorCombat = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!partidaTerminadaCombat) {
                    jugadorCombat1.actualizarTiempoCalavera();
                    jugadorCombat2.actualizarTiempoCalavera();
                    tiempoRestanteCombat--;
                    if (tiempoRestanteCombat <= 0) {
                        tiempoRestanteCombat = 0;
                        temporizadorCombat.stop();
                        finalizarRondaPorJugador();
                    }
                }
            }
        });
        this.temporizadorCombat.start();
    }
        
     /**
     * Actualiza la lógica del juego en cada ciclo. Este combate no necesita
     * actualización por cuadro (el reloj lo maneja el {@link Timer}), por lo
     * que el método está vacío.
     */
    @Override
    public void actualizar(){
        
    }
  
    /**
     * Procesa una tecla presionada. Se ignora si la partida ya terminó o si
     * el tiempo se agotó.
     * 
     *   Teclado compartido: Identifica a qué jugador pertenece la
     *       tecla y la procesa en su secuencia. Si ese jugador completa la
     *       secuencia, su rival recibe la penalización de
     *       {@link CombatBarraJugador#falloSecuencia()}.
     *   Por turnos: Solo procesa la tecla si pertenece al jugador
     *       al que le toca. Igual que en el modo compartido, completar una
     *       secuencia penaliza al rival. Cuando el índice de progreso vuelve
     *       a 0 (secuencia completada o fallada) el turno pasa al otro jugador.
     * 
     * Después de procesar la tecla, si la barra de algún jugador llegó a 0,
     * finaliza la ronda.
     *
     * @param keyCode código de la tecla presionada ({@link KeyEvent})
     */
    @Override
    public void procesarTeclaPresionada(int keyCode) {
        if (this.partidaTerminadaCombat) {
            return;
        }

        if (this.tiempoRestanteCombat <= 0) {
            return;
        }

        if (this.configuracionCombat.isTecladoCompartido()) {

            if (this.jugadorCombat1.perteneceTeclaJugador(keyCode)) {
                if (this.jugadorCombat1.procesarIntento(keyCode)) {
                    this.jugadorCombat2.falloSecuencia();
                }
            } else if (this.jugadorCombat2.perteneceTeclaJugador(keyCode)) {
                if (this.jugadorCombat2.procesarIntento(keyCode)) {
                    this.jugadorCombat1.falloSecuencia();
                }
            }
        } else {

            if (this.turnoActual == 1 && this.jugadorCombat1.perteneceTeclaJugador(keyCode)) {
                if (this.jugadorCombat1.procesarIntento(keyCode)) {
                    this.jugadorCombat2.falloSecuencia();
                }
                if (this.jugadorCombat1.getIndiceProgreso() == 0) {
                    this.turnoActual = 2;
                }
            } else if (this.turnoActual == 2 && this.jugadorCombat2.perteneceTeclaJugador(keyCode)) {
                if (this.jugadorCombat2.procesarIntento(keyCode)) {
                    this.jugadorCombat1.falloSecuencia();
                }
                if (this.jugadorCombat2.getIndiceProgreso() == 0) {
                    this.turnoActual = 1;
                }
            }
        }

        if (this.jugadorCombat1.getBarraPoder() <= 0 || this.jugadorCombat2.getBarraPoder() <= 0) {
            finalizarRondaPorJugador();
        }
    }
    
    /**
     * Procesa una tecla liberada. El combate solo reacciona a pulsaciones,
     * por lo que este método no hace nada.
     *
     * @param keyCode código de la tecla liberada
     */
    @Override
    public void procesarTeclaLiberada(int keyCode){
    }
    
    
     /**
     * Termina la ronda: marca la partida como terminada, detiene el
     * temporizador y determina el resultado comparando las barras de poder.
     * 
     * Gana la tripulación con la barra más alta.
     *   Si ambas barras son iguales, no hay ganador ({@code ganador = null})
     *     y se guarda un mensaje de empate.
     * 
     * Se llama cuando se acaba el tiempo o cuando la barra de un jugador llega a 0.
     */
    private void finalizarRondaPorJugador(){
        this.partidaTerminadaCombat = true;
        
        if (this.temporizadorCombat != null) {
            this.temporizadorCombat.stop();
        }
        
        int poderJugador1 = this.jugadorCombat1.getBarraPoder();
        int poderJugador2 = this.jugadorCombat2.getBarraPoder();
        
    }
    
    /**
     * Dibuja el combate: primero la imagen de fondo ajustada al tamaño de la
     * pantalla y luego el HUD (reloj, barras de poder, secuencias de teclas,
     * puntajes de las tripulaciones y calaveras).
     * 
     * Si el área de recorte del {@link Graphics} no está disponible, se asume
     * un tamaño de 1280x720.
     * 
     *
     * @param g contexto gráfico sobre el que se dibuja
     */
    @Override
    public void dibujar(Graphics g){
        
        Rectangle limites = g.getClipBounds();
        int ancho = (limites != null && limites.width > 0) ? limites.width : 1280;
        int alto = (limites != null && limites.height > 0) ? limites.height : 720;
        
        if (this.fondo != null) {
            g.drawImage(this.fondo, 0, 0, ancho, alto, null);
        }
        
        
        if (this.jugadorCombat1!= null && this.jugadorCombat2!= null) {
            this.hudCombat.dibujar(g,
                    this.jugadorCombat1, this.jugadorCombat2,
                    this.tripulacion1.getNombreTripulacion(),
                    this.tripulacion2.getNombreTripulacion(),
                    this.tiempoRestanteCombat,
                    this.tripulacion1.getPuntaje(),
                    this.tripulacion2.getPuntaje());
        }
        
    }
    
    /**
     * Indica si este juego ya terminó. Es {@code true} en cuanto finaliza la
     * ronda, ya sea por tiempo o porque la barra de un jugador llegó a 0.
     *
     * @return {@code true} si la partida terminó
     */
    @Override
    public boolean haTerminado() {
        return this.partidaTerminadaCombat;
    }

    
    //Getters y setters
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
    /**
     * Obtiene la configuración activa del combate.
     *
     * @return configuración del combate
     */
    public ConfiguracionCombat getConfiguracionCombat() {
        return configuracionCombat;
    }
    /**
     * Obtiene la imagen de fondo del combate.
     *
     * @return imagen de fondo
     */
    public BufferedImage getFondo() {
        return fondo;
    }
    /**
     * Obtiene el componente encargado de dibujar el HUD.
     *
     * @return instancia de {@link CombatHUD}
     */
    public CombatHUD getHudCombat() {
        return hudCombat;
    }
    /**
     * Obtiene el estado de la barra de poder del jugador 1.
     *
     * @return barra del jugador 1
     */
    public CombatBarraJugador getJugadorCombat1() {
        return jugadorCombat1;
    }
    /**
     * Obtiene el estado de la barra de poder del jugador 2.
     *
     * @return barra del jugador 2
     */
    public CombatBarraJugador getJugadorCombat2() {
        return jugadorCombat2;
    }
    /**
     * Obtiene la tripulación ganadora del combate.
     *
     * @return la tripulación ganadora, o {@code null} si aún no termina o hubo empate
     */
    @Override
    public Tripulacion getGanador() {
        return this.ganador;
    }
    /**
     * Establece la tripulación ganadora del combate.
     *
     * @param ganador tripulación ganadora o {@code null} en caso de empate
     */
    public void setGanador(Tripulacion ganador) {
        this.ganador = ganador;
    }
    /**
     * Obtiene los segundos restantes del reloj de combate.
     *
     * @return segundos restantes
     */
    public int getTiempoRestanteCombat() {
        return tiempoRestanteCombat;
    }
    /**
     * Establece los segundos restantes del reloj de combate.
     *
     * @param tiempoRestanteCombat segundos restantes
     */
    public void setTiempoRestanteCombat(int tiempoRestanteCombat) {
        this.tiempoRestanteCombat = tiempoRestanteCombat;
    }
    /**
     * Obtiene el número de jugador que tiene el turno actual en modo por turnos.
     *
     * @return 1 para jugador 1 o 2 para jugador 2
     */
    public int getTurnoActual() {
        return turnoActual;
    }
    /**
     * Establece el jugador que tiene el turno actual.
     *
     * @param turnoActual 1 para jugador 1 o 2 para jugador 2
     */
    public void setTurnoActual(int turnoActual) {
        this.turnoActual = turnoActual;
    }
    /**
     * Indica si la ronda del combate ya finalizó.
     *
     * @return {@code true} si la ronda terminó
     */
    public boolean isPartidaTerminadaCombat() {
        return partidaTerminadaCombat;
    }
    /**
     * Establece si la ronda del combate ya finalizó.
     *
     * @param partidaTerminadaCombat nuevo estado de finalización
     */
    public void setPartidaTerminadaCombat(boolean partidaTerminadaCombat) {
        this.partidaTerminadaCombat = partidaTerminadaCombat;
    }
    /**
     * Obtiene el temporizador de Swing del combate.
     *
     * @return temporizador del combate
     */
    public Timer getTemporizadorCombat() {
        return temporizadorCombat;
    }
    /**
     * Establece el temporizador de Swing del combate.
     *
     * @param temporizadorCombat nuevo temporizador
     */
    public void setTemporizadorCombat(Timer temporizadorCombat) {
        this.temporizadorCombat = temporizadorCombat;
    }   
}