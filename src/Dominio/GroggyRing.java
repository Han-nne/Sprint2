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
import java.util.List;
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
 * En su turno, cada jugador debe robar una carta del mazo central (o tener la
 * mano llena) y usar una carta de su mazo personal antes de poder mover su bola.
 * </p>
 * <p>
 * Controles: jugador 1 con W/A/S/D y jugador 2 con las flechas del teclado. Las
 * cartas se roban y se usan con el ratón, mediante {@link #procesarClic(int, int)}.
 * </p>
 * <p>
 * Todo el estado de la partida es privado: el exterior solo interactúa con el
 * juego mediante los métodos de {@link Juego} y {@link #procesarClic(int, int)}.
 * </p>
 *
 * @see Juego
 * @see JugadorBola
 * @see Casilla
 * @see ConfiguracionGroggyRing
 */
public class GroggyRing implements Juego {

    /** Ancho del panel de juego, en píxeles. */
    private static final int ANCHO_PANEL = 1280;

    /** Alto del panel de juego, en píxeles. */
    private static final int ALTO_PANEL = 720;

    /** Ubicación horizontal del panel de cartas del jugador 1. */
    private static final int PANEL_J1_X = 130;

    /** Ubicación horizontal del panel de cartas del jugador 2. */
    private static final int PANEL_J2_X = 810;

    /** Ubicación vertical del panel de cartas. */
    private static final int PANEL_CARTAS_Y = 665;

    /** Ancho del panel de cartas de cada jugador. */
    private static final int ANCHO_PANEL_CARTAS = 340;

    /** Ubicación horizontal del panel del mazo central. */
    private static final int MAZO_CENTRAL_X = 590;

    /** Ubicación vertical del panel del mazo central. */
    private static final int MAZO_CENTRAL_Y = 635;

    /** Ancho del mazo central. */
    private static final int MAZO_ANCHO = 100;

    /** Alto del mazo central. */
    private static final int MAZO_ALTO = 75;

    /** Dimensión vertical de las cartas del juego. */
    private static final int CARTAS_ALTO = 60;

    /** Dimensión horizontal de las cartas del juego. */
    private static final int CARTAS_ANCHO = 45;

    /** Espacio entre cada carta del mazo personal de cada jugador. */
    private static final int CARTAS_SEPARACION = 8;

    /** Cantidad máxima de cartas que puede tener un jugador en su mazo personal. */
    private static final int MAX_CARTAS_MANO = JugadorBola.MAX_CARTAS_MANO;

    /** Cantidad máxima de cartas que un jugador puede robar por turno. */
    private static final int MAX_CARTAS_ROBADAS = 2;

    /** Ancho total que ocupan las cartas de una mano completa, con sus separaciones. */
    private static final int ANCHO_TOTAL_CARTAS =
            (MAX_CARTAS_MANO * CARTAS_ANCHO) + ((MAX_CARTAS_MANO - 1) * CARTAS_SEPARACION);

    /** Margen horizontal que centra una mano completa dentro del panel de cartas. */
    private static final int MARGEN_PANEL_CARTAS_X = (ANCHO_PANEL_CARTAS - ANCHO_TOTAL_CARTAS) / 2;

    /** Margen que sobresale la sombra respecto a las cartas, en píxeles. */
    private static final int MARGEN_SOMBRA = 3;

    /** Color semitransparente que oscurece las cartas del jugador que no tiene el turno. */
    private static final Color COLOR_SOMBRA_CARTAS = new Color(0, 0, 0, 160);

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

    /** Coordenada X de la esquina superior izquierda del tablero en pantalla. */
    private static final int ORIGEN_X = 130;

    /** Coordenada Y de la esquina superior izquierda del tablero en pantalla. */
    private static final int ORIGEN_Y = 80;

    /** Ancho del cuadro del marcador, en píxeles. */
    private static final int ANCHO_MARCADOR = 420;

    /** Alto del cuadro del marcador, en píxeles. */
    private static final int ALTO_MARCADOR = 160;

    /** Color semitransparente de fondo del marcador. */
    private static final Color COLOR_FONDO_MARCADOR = new Color(0, 0, 0, 180);

    /** Fuente del título del marcador. */
    private static final Font FUENTE_TITULO_MARCADOR = new Font("Arial", Font.BOLD, 28);

    /** Fuente de la puntuación del marcador. */
    private static final Font FUENTE_PUNTAJE_MARCADOR = new Font("Arial", Font.BOLD, 56);

    /** Imagen de fondo del juego. */
    private final BufferedImage imagen;

    /** Matriz de casillas que forman el tablero (indexada por [fila][columna]). */
    private final Casilla[][] zonaJuego;

    /** Bola controlada por el jugador 1 (lado izquierdo, teclas W/A/S/D). */
    private final JugadorBola bolaJugador1;

    /** Bola controlada por el jugador 2 (lado derecho, teclas de flechas). */
    private final JugadorBola bolaJugador2;

    /** Mazo central de GroggyRing, del que los jugadores roban cartas. */
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

    /** HUD que dibuja la información de cada jugador (imagen de su bola, nombre y puntuación). */
    private final GroggyRingHUD hud;

    /** HUD especializado en las cartas y el mazo. */
    private final GroggyRingCartasHUD cartasHUD;

    /**
     * Construye una partida de Groggy Ring con las dos tripulaciones indicadas.
     * Carga las imágenes, crea las bolas, el mazo y los HUD, y deja el tablero
     * listo con {@link #iniciarJuego()}.
     *
     * @param tripulacion1 tripulación del jugador 1
     * @param tripulacion2 tripulación del jugador 2
     * @param configuracion configuración que define la puntuación de victoria
     * @throws IOException si ocurre un error al cargar las imágenes del juego
     */
    public GroggyRing(Tripulacion tripulacion1, Tripulacion tripulacion2,
                      ConfiguracionGroggyRing configuracion) throws IOException {
        this.puntuacionVictoria = configuracion.getPuntuacionVictoria();
        this.imagen = TileSetManagerGroggyRing.cargarImagenFondoGroggyRing();
        this.bolaJugador1 = new JugadorBola(tripulacion1,
                TileSetManagerGroggyRing.cargarImagenGroggyRing().getSubimage(144, 0, TAM, TAM));
        this.bolaJugador2 = new JugadorBola(tripulacion2,
                TileSetManagerGroggyRing.cargarImagenGroggyRing().getSubimage(216, 0, TAM, TAM));
        this.hud = new GroggyRingHUD(bolaJugador1, bolaJugador2);

        this.mazo = new Mazo();
        this.cartasHUD = new GroggyRingCartasHUD(bolaJugador1, bolaJugador2, mazo);

        this.zonaJuego = new Casilla[FILAS][COLUMNAS];
        iniciarJuego();
    }

    /**
     * Inicializa el tablero, coloca las bolas en sus posiciones iniciales
     * y asigna el primer turno al jugador 1.
     *
     * @throws IOException si ocurre un error al cargar las imágenes de las casillas
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
     *
     * @throws IOException si ocurre un error al cargar las imágenes de las casillas
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
     * También vacía el mazo personal de cada jugador.
     */
    private void reiniciarPosiciones() {
        liberar(bolaJugador1);
        liberar(bolaJugador2);
        colocar(bolaJugador1, FILA_CENTRO, 1);
        colocar(bolaJugador2, FILA_CENTRO, COLUMNAS - 2);

        bolaJugador1.vaciarMazoPersonal();
        bolaJugador2.vaciarMazoPersonal();
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
 * Calcula la casilla a la que llegaría una bola al moverse en una dirección,
 * avanzando casilla por casilla hasta completar los pasos disponibles
 * (1 más las casillas extra acumuladas por cartas de desplazamiento).
 * <p>
 * El recorrido se detiene en la última casilla libre cuando ocurre alguna de
 * estas situaciones: el siguiente paso saldría del tablero, la siguiente casilla
 * está ocupada por otra bola, o la bola acaba de llegar al neumático del rival
 * (en cuyo caso se detiene sobre él para poder anotar).
 * </p>
 *
 * @param bola  bola que se desea mover
 * @param dFila desplazamiento en filas (-1, 0 o 1)
 * @param dCol  desplazamiento en columnas (-1, 0 o 1)
 * @return arreglo {@code {fila, columna}} con la casilla de destino, o
 *         {@code null} si la bola no puede avanzar ni una sola casilla
 */
private int[] calcularDestino(JugadorBola bola, int dFila, int dCol) {
    int pasos = 1 + bola.getCasillasExtra();
    int fila = bola.getFila();
    int col = bola.getColumna();

    for (int i = 0; i < pasos; i++) {
        int siguienteFila = fila + dFila;
        int siguienteCol = col + dCol;

        // Borde del tablero
        if (siguienteFila < 0 || siguienteFila >= FILAS
                || siguienteCol < 0 || siguienteCol >= COLUMNAS) {
            break;
        }

        Casilla siguiente = zonaJuego[siguienteFila][siguienteCol];

        // Casilla ocupada por la otra bola
        if (siguiente.getJugadorBola() != null) {
            break;
        }

        fila = siguienteFila;
        col = siguienteCol;

        // Si llegó al neumático del rival, se detiene ahí para anotar
        if (siguiente instanceof CasillaNeumatico
                && ((CasillaNeumatico) siguiente).getDueno() != bola) {
            break;
        }
    }

    // No pudo avanzar ni una casilla
    if (fila == bola.getFila() && col == bola.getColumna()) {
        return null;
    }
    return new int[] {fila, col};
}

/**
 * Intenta mover una bola en la dirección indicada, avanzando tantas casillas
 * como pueda hasta un máximo de 1 más las casillas extra que le hayan dado las
 * cartas. Si hay un obstáculo o el borde del tablero antes de completar todos
 * los pasos, la bola avanza hasta la última casilla libre.
 * <p>
 * El movimiento se ignora si el juego terminó, si hay una pausa activa,
 * si no es el turno de la bola, si el jugador aún no robó una carta (a menos
 * que tenga la mano llena), si aún no usó una carta, o si la bola no puede
 * avanzar ni una casilla (en ese caso no se gastan las casillas extra ni el
 * turno). Si la bola llega al neumático del rival, se anota un punto y se inicia
 * la pausa del marcador (y, si se alcanza la puntuación de victoria, se define
 * el ganador). En caso contrario, el turno pasa al rival, salvo que este tenga
 * el turno omitido.
 * </p>
 *
 * @param bola  bola que se intenta mover
 * @param dFila desplazamiento en filas (-1, 0 o 1)
 * @param dCol  desplazamiento en columnas (-1, 0 o 1)
 * @see #calcularDestino(JugadorBola, int, int)
 */
private void intentarMover(JugadorBola bola, int dFila, int dCol) {
    if (terminado || pausa > 0 || bola != turnoActual) {
        return;
    }

    boolean manoLlena = bola.getMazoPersonal().size() >= MAX_CARTAS_MANO;
    if (!manoLlena && !bola.isCartaRobada()) return;
    if (!bola.isCartaUsada()) return;

    int[] posicionDestino = calcularDestino(bola, dFila, dCol);
    if (posicionDestino == null) {
        return;   // bloqueada: no se mueve y conserva sus casillas extra
    }

    Casilla destino = zonaJuego[posicionDestino[0]][posicionDestino[1]];

    zonaJuego[bola.getFila()][bola.getColumna()].setJugadorBola(null);
    colocar(bola, posicionDestino[0], posicionDestino[1]);

    bola.setCasillasExtra(0);

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

    JugadorBola siguienteTurno = rivalDe(bola);
    if (siguienteTurno.isTurnoOmitido()) {
        siguienteTurno.setTurnoOmitido(false);
        turnoActual = bola;
    } else {
        turnoActual = siguienteTurno;
    }
    turnoActual.reiniciarTurnoTactico();
}
    
    /**
     * Extrae una carta del mazo central y la agrega al mazo personal del jugador.
     * <p>
     * Se ignora si el juego terminó, si hay una pausa activa, si no es el turno
     * del jugador, si ya robó {@value #MAX_CARTAS_ROBADAS} cartas en este turno o
     * si su mazo personal está lleno.
     * </p>
     *
     * @param jugador jugador que roba la carta
     * @throws IOException si ocurre un error al cargar la imagen de la carta
     */
    private void robarCarta(JugadorBola jugador) throws IOException {
        if (terminado || pausa > 0 || jugador != turnoActual) return;

        if (jugador.getCartasRobadas() >= MAX_CARTAS_ROBADAS
                || jugador.getMazoPersonal().size() >= MAX_CARTAS_MANO) return;

        Carta cartaRobada = mazo.robarCarta();
        jugador.guardarCartaEnMazoPersonal(cartaRobada);
        jugador.registrarCartaRobada();
    }

    /**
     * Permite al jugador usar una carta de su mazo personal. Es privado: solo se
     * invoca desde {@link #procesarClic(int, int)}, de modo que las reglas del
     * turno no se puedan saltar desde fuera de la clase.
     * <p>
     * Se ignora si el juego terminó, si hay una pausa activa, si no es el turno
     * del jugador o si ya usó una carta del mismo tipo (ataque o defensa) en este turno.
     * </p>
     *
     * @param jugador el jugador que usa la carta
     * @param carta la carta específica que desea jugar
     */
    private void usarCarta(JugadorBola jugador, Carta carta) {
        if (terminado || pausa > 0 || jugador != turnoActual) return;

        if (carta instanceof CartaAtaque) {
            if (jugador.isAtaqueUsado()) return;
            jugador.setAtaqueUsado(true);

        } else if (carta instanceof CartaDefensa) {
            if (jugador.isDefensaUsada()) return;
            jugador.setDefensaUsada(true);
        }

        JugadorBola rival = rivalDe(jugador);
        carta.aplicarEfecto(jugador, rival);
        jugador.usarCartaEnMazoPersonal(carta);

        jugador.setCartaUsada(true);
    }

    /**
     * Actualiza el estado del juego en cada ciclo. Mientras hay una pausa
     * activa la va decrementando; al terminar, finaliza el juego si hay un
     * ganador, o reinicia las posiciones y cede el turno a quien recibió el
     * punto, reiniciando también el estado táctico de su turno (cartas robadas
     * y usadas) para que pueda robar y usar cartas con normalidad.
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
                    turnoActual.reiniciarTurnoTactico();
                }
            }
        }
    }

    /**
     * Dibuja el fondo, el tablero, las bolas y, si hay una pausa activa, el
     * marcador. El contexto gráfico se traslada al origen del tablero y se
     * restaura al finalizar. Después dibuja los HUD y las cartas de los jugadores.
     *
     * @param g contexto gráfico sobre el cual dibujar
     */
    @Override
    public void dibujar(Graphics g) {
        g.drawImage(this.imagen, 0, 0, ANCHO_PANEL, ALTO_PANEL, null);
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
        this.dibujarCartasJugadores(g);
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
        int x = (anchoTablero - ANCHO_MARCADOR) / 2;
        int y = (altoTablero - ALTO_MARCADOR) / 2;

        g.setColor(COLOR_FONDO_MARCADOR);
        g.fillRoundRect(x, y, ANCHO_MARCADOR, ALTO_MARCADOR, 30, 30);

        g.setColor(Color.WHITE);
        g.setFont(FUENTE_TITULO_MARCADOR);
        String titulo = (ganador != null)
                ? "¡" + nombre(anotador) + " gana el juego!"
                : "¡Punto para " + nombre(anotador) + "!";
        centrarTexto(g, titulo, anchoTablero / 2, y + 55);

        g.setFont(FUENTE_PUNTAJE_MARCADOR);
        centrarTexto(g, bolaJugador1.getAnotaciones() + "  -  " + bolaJugador2.getAnotaciones(),
                anchoTablero / 2, y + 125);
    }

    /**
     * Dibuja las cartas que cada jugador tiene almacenadas en su mazo personal y
     * oscurece las del jugador que no tiene el turno.
     *
     * @param g contexto gráfico en el que se dibuja
     */
    private void dibujarCartasJugadores(Graphics g) {

        List<Carta> manoJugador1 = bolaJugador1.getMazoPersonal();
        for (int i = 0; i < manoJugador1.size(); i++) {
            int cartaX = posicionXCarta(PANEL_J1_X, i);
            manoJugador1.get(i).dibujar(g, cartaX, PANEL_CARTAS_Y, CARTAS_ANCHO, CARTAS_ALTO);
        }

        List<Carta> manoJugador2 = bolaJugador2.getMazoPersonal();
        for (int i = 0; i < manoJugador2.size(); i++) {
            int cartaX = posicionXCarta(PANEL_J2_X, i);
            manoJugador2.get(i).dibujar(g, cartaX, PANEL_CARTAS_Y, CARTAS_ANCHO, CARTAS_ALTO);
        }

        g.setColor(COLOR_SOMBRA_CARTAS);
        int anchoSombra = ANCHO_TOTAL_CARTAS + (2 * MARGEN_SOMBRA);
        int altoSombra = CARTAS_ALTO + (2 * MARGEN_SOMBRA);
        int sombraY = PANEL_CARTAS_Y - MARGEN_SOMBRA;

        int panelSinTurnoX = (turnoActual == bolaJugador1) ? PANEL_J2_X : PANEL_J1_X;
        int sombraX = panelSinTurnoX + MARGEN_PANEL_CARTAS_X - MARGEN_SOMBRA;
        g.fillRoundRect(sombraX, sombraY, anchoSombra, altoSombra, 8, 8);

    }

    /**
     * Calcula la coordenada X de una carta dentro del panel de cartas de un jugador.
     *
     * @param panelX coordenada X del panel de cartas del jugador
     * @param indice posición de la carta dentro de su mazo personal (desde 0)
     * @return coordenada X en la que se dibuja la carta
     */
    private int posicionXCarta(int panelX, int indice) {
        return panelX + MARGEN_PANEL_CARTAS_X + (indice * (CARTAS_ANCHO + CARTAS_SEPARACION));
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
        return bola.getTripulacion().getNombreTripulacion();
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
    public boolean haTerminado() {
        return terminado;
    }

    /**
     * Procesa un clic del ratón según las coordenadas (x, y) de la pantalla.
     * Detecta si se hizo clic en el mazo central para robar una carta, o sobre
     * una carta del mazo personal del jugador en turno para usarla.
     *
     * @param x coordenada X del clic en el panel
     * @param y coordenada Y del clic en el panel
     * @throws IOException si ocurre un error al cargar la imagen de la carta robada
     */
    public void procesarClic(int x, int y) throws IOException {

        if (terminado || pausa > 0) return;

        if (estaDentro(x, y, MAZO_CENTRAL_X, MAZO_CENTRAL_Y, MAZO_ANCHO, MAZO_ALTO)) {
            robarCarta(turnoActual);
            return;
        }

        int baseX = (turnoActual == bolaJugador1) ? PANEL_J1_X : PANEL_J2_X;
        List<Carta> mazoPersonal = turnoActual.getMazoPersonal();

        for (int i = 0; i < mazoPersonal.size(); i++) {
            int cartaX = posicionXCarta(baseX, i);

            if (estaDentro(x, y, cartaX, PANEL_CARTAS_Y, CARTAS_ANCHO, CARTAS_ALTO)) {
                Carta cartaSeleccionada = mazoPersonal.get(i);
                usarCarta(turnoActual, cartaSeleccionada);
                return;
            }
        }
    } // procesarClic

    /**
     * Método auxiliar para detectar si un clic cae dentro de un rectángulo, como
     * la hitbox de una carta o del mazo central.
     *
     * @param clicX coordenada horizontal del clic en pantalla
     * @param clicY coordenada vertical del clic en pantalla
     * @param rectX coordenada horizontal de la esquina superior izquierda del rectángulo
     * @param rectY coordenada vertical de la esquina superior izquierda del rectángulo
     * @param ancho dimensión horizontal del rectángulo
     * @param alto dimensión vertical del rectángulo
     * @return {@code true} si el clic está dentro del rectángulo (bordes incluidos);
     *         {@code false} en caso contrario
     */
    private boolean estaDentro(int clicX, int clicY, int rectX, int rectY, int ancho, int alto) {
        return clicX >= rectX && clicX <= (rectX + ancho)
                && clicY >= rectY && clicY <= (rectY + alto);
    }

    /**
     * Obtiene la bola ganadora del juego.
     *
     * @return la bola ganadora, o {@code null} si todavía nadie ha ganado
     */
    public JugadorBola getGanador() {
        return ganador;
    }
}