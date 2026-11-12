/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Dominio;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import utility.TileSetManagerGroggyRing;

/**
 * Minijuego por turnos "Groggy Ring" para dos jugadores.
 * <p>
 * Cada jugador controla una bola sobre un tablero de {@value #FILAS} filas por
 * {@value #COLUMNAS} columnas. En los extremos de la fila central hay un
 * neumático por jugador; una bola anota un punto al llegar al neumático del
 * rival. Los jugadores se alternan el turno después de cada movimiento válido.
 * Gana quien primero alcance la puntuación de victoria configurada.
 * </p>
 * <p>
 * Controles: jugador 1 con W/A/S/D y jugador 2 con las flechas del teclado.
 * </p>
 *
 * @see Juego
 * @see JugadorBola
 * @see Casilla
 * @see ConfiguracionGroggyRing
 */
public class GroggyRing implements Juego {

    /** Tamaño en píxeles de cada casilla. */
    private static final int TAM = Casilla.TAM;

    /** Número de filas del tablero. */
    private static final int FILAS = 9;

    /** Número de columnas del tablero. */
    private static final int COLUMNAS = 16;

    /** Fila central del tablero, donde están los neumáticos y las posiciones iniciales. */
    private static final int FILA_CENTRO = FILAS / 2;

    /** Duración (en ciclos de actualización) de la pausa que muestra el marcador tras un punto. */
    private static final int CICLOS_PAUSA = 90;

    /** Imagen de fondo del juego. */
    private final BufferedImage imagen;

    /** Coordenada X de la esquina superior izquierda del tablero en pantalla. */
    private static final int ORIGEN_X = 130;

    /** Coordenada Y de la esquina superior izquierda del tablero en pantalla. */
    private static final int ORIGEN_Y = 80;

    /** Matriz de casillas que forman el tablero (indexada por [fila][columna]). */
    private final Casilla[][] zonaJuego;

    /** Bola controlada por el jugador 1 (lado izquierdo, teclas W/A/S/D). */
    private final JugadorBola bolaJugador1;

    /** Bola controlada por el jugador 2 (lado derecho, teclas de flechas). */
    private final JugadorBola bolaJugador2;
    
    /**Mazo central de GroggyRing*/
    private final Mazo mazo;

    /** Cantidad de puntos necesarios para ganar el juego. */
    private final int puntuacionVictoria;

    /** Bola a la que le corresponde mover en el turno actual. */
    private JugadorBola turnoActual;

    /** Bola que hizo el último punto. */
    private JugadorBola anotador;

    /** Bola ganadora del juego; es {@code null} hasta que alguien gane. */
    private JugadorBola ganador;

    /** Ciclos restantes de pausa; mayor que 0 mientras se muestra el marcador. */
    private int pausa = 0;

    /** Indica si el juego ya finalizó. */
    private boolean terminado = false;
    
    /**Dibuja la imagen de la pelota del jugador correspondient*/
    private GroggyRingHUD hud;
    
    /**HUD especializado en las cartas y el mazo.*/
    private GroggyRingCartasHUD cartasHUD;
    

    public GroggyRing(Tripulacion tripulacion1, Tripulacion tripulacion2, ConfiguracionGroggyRing configuracion) throws IOException {
       this.puntuacionVictoria = configuracion.getPuntuacionVictoria();
       this.imagen = TileSetManagerGroggyRing.cargarImagenFondoGroggyRing();
       this.bolaJugador1 = new JugadorBola(tripulacion1 , TileSetManagerGroggyRing.cargarImagenGroggyRing().getSubimage(144, 0 , TAM, TAM));
       this.bolaJugador2 = new JugadorBola(tripulacion2 , TileSetManagerGroggyRing.cargarImagenGroggyRing().getSubimage(216, 0 , TAM, TAM));
       this.hud = new GroggyRingHUD(bolaJugador1, bolaJugador2);
       
       this.mazo = new Mazo();
       this.cartasHUD = new GroggyRingCartasHUD(bolaJugador1, bolaJugador2, mazo);
       
       this.zonaJuego = new Casilla[FILAS][COLUMNAS];
       iniciarJuego();
    }

    /**
     * Inicializa el tablero, coloca las bolas en sus posiciones iniciales
     * y asigna el primer turno al jugador 1.
     */
    private void iniciarJuego() throws IOException {
        crearZonaJuego();
        reiniciarPosiciones();
        turnoActual = bolaJugador1;
    }

    /**
     * Crea todas las casillas del tablero. Las casillas extremas de la fila
     * central son neumáticos (cada uno pertenece a un jugador); el resto son
     * casillas de campo. Las casillas se crean en coordenadas locales y
     * {@link #dibujar(Graphics)} se encarga de desplazarlas.
     */
    private void crearZonaJuego() throws IOException {
        for (int i = 0; i < FILAS; i++) {
            for (int j = 0; j < COLUMNAS; j++) {
                if (i == FILA_CENTRO && (j == 0 || j == COLUMNAS - 1)) {
                    CasillaNeumatico neumatico = new CasillaNeumatico(j * TAM, i * TAM);
                    neumatico.setDueno(j == 0 ? bolaJugador1 : bolaJugador2);
                    zonaJuego[i][j] = neumatico;
                } else {
                    zonaJuego[i][j] = new CasillaCampo(j * TAM, i * TAM);
                }
            }
        }
    }

    /**
     * Devuelve ambas bolas a sus posiciones iniciales en la fila central:
     * el jugador 1 en la columna 1 y el jugador 2 en la penúltima columna.
     */
    private void reiniciarPosiciones() {
        liberar(bolaJugador1);
        liberar(bolaJugador2);
        colocar(bolaJugador1, FILA_CENTRO, 1);
        colocar(bolaJugador2, FILA_CENTRO, COLUMNAS - 2);
    }

    /**
     * Libera la casilla que ocupa actualmente la bola, siempre que la casilla
     * efectivamente la contenga.
     *
     * @param bola bola cuya casilla actual se desea liberar
     */
    private void liberar(JugadorBola bola) {
        Casilla actual = zonaJuego[bola.getFila()][bola.getColumna()];
        if (actual.getJugadorBola() == bola) {
            actual.setJugadorBola(null);
        }
    }

    /**
     * Coloca una bola en la posición indicada, actualizando tanto la bola
     * como la casilla destino.
     *
     * @param bola    bola a colocar
     * @param fila    fila de destino
     * @param columna columna de destino
     */
    private void colocar(JugadorBola bola, int fila, int columna) {
        bola.setPosicion(fila, columna);
        zonaJuego[fila][columna].setJugadorBola(bola);
    }

    /**
     * Obtiene la bola rival de la bola dada.
     *
     * @param bola bola de referencia
     * @return la bola del otro jugador
     */
    private JugadorBola rivalDe(JugadorBola bola) {
        return (bola == bolaJugador1) ? bolaJugador2 : bolaJugador1;
    }

    /**
     * Intenta mover una bola una casilla en la dirección indicada.
     * <p>
     * El movimiento se ignora si el juego terminó, si hay una pausa activa,
     * si no es el turno de la bola, si el destino está fuera del tablero o si
     * la casilla destino está ocupada. Si la bola llega al neumático del rival,
     * se anota un punto y se inicia la pausa del marcador (y, si se alcanza la
     * puntuación de victoria, se define el ganador). En caso contrario, el
     * turno pasa al rival.
     * </p>
     *
     * @param bola  bola que se intenta mover
     * @param dFila desplazamiento en filas (-1, 0 o 1)
     * @param dCol  desplazamiento en columnas (-1, 0 o 1)
     */
    private void intentarMover(JugadorBola bola, int dFila, int dCol) {
        if (terminado || pausa > 0 || bola != turnoActual) {
            return;
        }

        int nuevaFila = bola.getFila() + dFila;
        int nuevaCol = bola.getColumna() + dCol;

        if (nuevaFila < 0 || nuevaFila >= FILAS || nuevaCol < 0 || nuevaCol >= COLUMNAS) {
            return;
        }

        Casilla destino = zonaJuego[nuevaFila][nuevaCol];
        if (destino.getJugadorBola() != null) {
            return;
        }

        zonaJuego[bola.getFila()][bola.getColumna()].setJugadorBola(null);
        colocar(bola, nuevaFila, nuevaCol);

        if (destino instanceof CasillaNeumatico
                && ((CasillaNeumatico) destino).getDueno() != bola) {

            bola.sumarAnotacion();
            anotador = bola;
            pausa = CICLOS_PAUSA;
            if (bola.getAnotaciones() >= puntuacionVictoria) {
                ganador = bola;   // el juego acaba cuando termine la pausa
            }
            return;
        }

        turnoActual = rivalDe(bola);
    }

    /**
     * Extrae y remueve una carta del mazo, y se le agrega al mazo personal del jugador.
     * @param jugador jugador que robó la carta.
     */
    private void robarCarta(JugadorBola jugador){
        if(terminado || pausa > 0 || jugador != turnoActual) return;
        Carta cartaRobada = mazo.robarCarta();
        jugador.guardarCartaEnMazoPersonal(cartaRobada);
    }
    
    /**
     * Se detecta la posición del rival, le quita el turno (excepto que tenga el escudo activo)
     * y descarta la carta.
     * @param atacante jugador que lanzó la carta de ataque.
     * @param rival jugador que recibirá los efectos de la carta lanzada.
     */
    private void usarCartaAtaque(JugadorBola atacante, JugadorBola rival){
        int filaRival = rival.getFila();
        int colRival = rival.getColumna();
        
        if(rival.isEscudoActivo()){
            rival.setEscudoActivo(false);
        }else {
            rival.setTurnoOmitido(true);
        }
    }
    
    /**
     * Se establece una protección de daño al jugador.
     * @param jugador jugador que lanzó la carta de defensa.
     */
    private void usarCartaDefensa(JugadorBola jugador){ jugador.setEscudoActivo(true);}
    
    /**
     * Actualiza el estado del juego en cada ciclo. Mientras hay una pausa
     * activa la va decrementando; al terminar, finaliza el juego si hay un
     * ganador, o reinicia las posiciones y cede el turno a quien recibió el
     * punto.
     */
    @Override
    public void actualizar() {
        if (pausa > 0) {
            pausa--;
            if (pausa == 0) {
                if (ganador != null) {
                    terminado = true;
                } else {
                    reiniciarPosiciones();
                    turnoActual = rivalDe(anotador);   // empieza quien recibió el punto
                }
            }
        }
    }

    /**
     * Dibuja el fondo, el tablero, las bolas y, si hay una pausa activa, el
     * marcador. El contexto gráfico se traslada al origen del tablero y se
     * restaura al finalizar.
     *
     * @param g contexto gráfico sobre el cual dibujar
     */
    @Override
    public void dibujar(Graphics g) {
        g.drawImage( this.imagen, 0 , 0 , 1280, 720 , null);
        g.translate(ORIGEN_X, ORIGEN_Y);   // el tablero empieza aquí
        for (int i = 0; i < FILAS; i++) {
            for (int j = 0; j < COLUMNAS; j++) {
                zonaJuego[i][j].dibujar(g);
            }
        }
        bolaJugador1.dibujar(g);
        bolaJugador2.dibujar(g);

        if (pausa > 0) {
            dibujarMarcador(g);
        }

        g.translate(-ORIGEN_X, -ORIGEN_Y); // restaurar para lo que se dibuje después (banderas)
        this.hud.dibujar(g);
        this.cartasHUD.dibujar(g);
    }

    /**
     * Dibuja el cuadro del marcador centrado sobre el tablero, con el mensaje
     * del punto (o de victoria) y la puntuación de ambos jugadores.
     *
     * @param g contexto gráfico (ya trasladado al origen del tablero)
     */
    private void dibujarMarcador(Graphics g) {
        int anchoTablero = COLUMNAS * TAM;
        int altoTablero = FILAS * TAM;
        int anchoCaja = 420;
        int altoCaja = 160;
        int x = (anchoTablero - anchoCaja) / 2;
        int y = (altoTablero - altoCaja) / 2;

        g.setColor(new Color(0, 0, 0, 180));
        g.fillRoundRect(x, y, anchoCaja, altoCaja, 30, 30);

        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.BOLD, 28));
        String titulo = (ganador != null)
                ? "¡" + nombre(anotador) + " gana el juego!"
                : "¡Punto para " + nombre(anotador) + "!";
        centrarTexto(g, titulo, anchoTablero / 2, y + 55);

        g.setFont(new Font("Arial", Font.BOLD, 56));
        centrarTexto(g, bolaJugador1.getAnotaciones() + "  -  " + bolaJugador2.getAnotaciones(),
                anchoTablero / 2, y + 125);
    }

    /**
     * Dibuja un texto centrado horizontalmente respecto a una coordenada X.
     *
     * @param g       contexto gráfico
     * @param texto   texto a dibujar
     * @param centroX coordenada X sobre la cual se centra el texto
     * @param baseY   coordenada Y de la línea base del texto
     */
    private void centrarTexto(Graphics g, String texto, int centroX, int baseY) {
        FontMetrics fm = g.getFontMetrics();
        g.drawString(texto, centroX - fm.stringWidth(texto) / 2, baseY);
    }

    /**
     * Obtiene el nombre de la tripulación asociada a una bola.
     *
     * @param bola bola de la cual se quiere el nombre de su tripulación
     * @return nombre de la tripulación de la bola
     */
    private String nombre(JugadorBola bola) {

        return (bola == bolaJugador1) ? this.bolaJugador1.getTripulacion().getNombreTripulacion() : this.bolaJugador2.getTripulacion().getNombreTripulacion();
    }

    /**
     * Procesa una tecla presionada y mueve la bola correspondiente.
     * <ul>
     *   <li>Jugador 1: W (arriba), S (abajo), A (izquierda), D (derecha).</li>
     *   <li>Jugador 2: flechas arriba, abajo, izquierda y derecha.</li>
     * </ul>
     *
     * @param e código de la tecla presionada (constante de {@link KeyEvent})
     */
    @Override
    public void procesarTeclaPresionada(int e) {
        switch (e) {
            case KeyEvent.VK_W: intentarMover(bolaJugador1, -1, 0); break;
            case KeyEvent.VK_S: intentarMover(bolaJugador1, 1, 0);  break;
            case KeyEvent.VK_A: intentarMover(bolaJugador1, 0, -1); break;
            case KeyEvent.VK_D: intentarMover(bolaJugador1, 0, 1);  break;
            case KeyEvent.VK_UP:    intentarMover(bolaJugador2, -1, 0); break;
            case KeyEvent.VK_DOWN:  intentarMover(bolaJugador2, 1, 0);  break;
            case KeyEvent.VK_LEFT:  intentarMover(bolaJugador2, 0, -1); break;
            case KeyEvent.VK_RIGHT: intentarMover(bolaJugador2, 0, 1);  break;
            default: break;
        }
    }

    /**
     * Procesa una tecla liberada. Este juego no reacciona a la liberación de teclas.
     *
     * @param e código de la tecla liberada
     */
    @Override
    public void procesarTeclaLiberada(int e) { }

    /**
     * Indica si el juego ha terminado.
     *
     * @return {@code true} si ya hay un ganador y terminó la pausa final;
     *         {@code false} en caso contrario
     */
    @Override
    public boolean haTerminado() { return terminado; }

    /**
     * Obtiene la bola ganadora del juego.
     *
     * @return la bola ganadora, o {@code null} si todavía nadie ha ganado
     */
    public JugadorBola getGanador() { return ganador; }
}