/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Dominio;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Representa la bola que controla cada jugador en {@link GroggyRing}.
 * <p>
 * Guarda la posición de la bola en el tablero (fila y columna), su puntuación,
 * su mano de cartas y los indicadores que controlan lo que el jugador ya hizo en
 * su turno (robar y usar cartas) y los efectos que le afectan (escudo, turno
 * omitido, casillas extra).
 * </p>
 * <p>
 * El estado interno solo se modifica mediante métodos que mantienen sus reglas:
 * la mano nunca supera {@value #MAX_CARTAS_MANO} cartas, la puntuación solo
 * aumenta de uno en uno y la tripulación y la imagen no cambian después de la
 * construcción.
 * </p>
 *
 * @author johan
 * @see GroggyRing
 * @see Carta
 * @see Tripulacion
 */
public class JugadorBola {

    /** Tamaño en píxeles de cada casilla del tablero. */
    public static final int TAM = Casilla.TAM;

    /** Cantidad máxima de cartas que puede tener el jugador en su mazo personal. */
    public static final int MAX_CARTAS_MANO = 5;

    /** Tripulación a la que pertenece la bola. */
    private final Tripulacion tripulacion;

    /** Imagen con la que se dibuja la bola; si es {@code null} se dibuja un círculo rojo. */
    private final BufferedImage imagen;

    /** Lista de la mano de cartas del jugador. */
    private final List<Carta> mazoPersonal = new ArrayList<>();

    /** Bandera para protegerse de un ataque. */
    private boolean escudoActivo = false;

    /** Bandera del efecto de un ataque recibido: el jugador pierde su próximo turno. */
    private boolean turnoOmitido = false;

    /** Bandera que indica si el jugador tiene un turno extra. */
    private boolean turnoExtra = false;

    /**
     * Indica la cantidad de casillas extra que se puede mover el jugador
     * por la carta de desplazamiento. Nunca es negativa.
     */
    private int casillasExtra = 0;

    /** Indica si el jugador ya ha usado una carta de ataque en su turno. */
    private boolean ataqueUsado = false;

    /** Indica si el jugador ya ha usado una carta de defensa en su turno. */
    private boolean defensaUsada = false;

    /** Indica la cantidad de cartas que el jugador ha robado del mazo central en su turno. */
    private int cartasRobadas = 0;

    /** Bandera que indica si el jugador ha robado alguna carta del mazo central durante su turno. */
    private boolean cartaRobada = false;

    /** Bandera que indica si el jugador ha usado alguna carta del mazo personal durante su turno. */
    private boolean cartaUsada = false;

    /** Cantidad de puntos que ha anotado el jugador. */
    private int anotaciones = 0;

    /** Fila de la casilla que ocupa la bola en el tablero. */
    private int fila;

    /** Columna de la casilla que ocupa la bola en el tablero. */
    private int columna;

    /**
     * Crea una bola para la tripulación indicada, con la imagen dada.
     *
     * @param tripulacion tripulación a la que pertenece la bola; no puede ser {@code null}
     * @param imagen imagen con la que se dibuja la bola; si es {@code null} se
     *               dibuja un círculo rojo temporal
     */
    public JugadorBola(Tripulacion tripulacion, BufferedImage imagen) {
        this.tripulacion = tripulacion;
        this.imagen = imagen;
    }

    /**
     * Obtiene la mano de cartas del jugador como una vista de solo lectura.
     * <p>
     * La lista devuelta no se puede modificar: para agregar o quitar cartas se
     * debe usar {@link #guardarCartaEnMazoPersonal(Carta)},
     * {@link #usarCartaEnMazoPersonal(Carta)} o {@link #vaciarMazoPersonal()}.
     * Refleja los cambios posteriores de la mano.
     * </p>
     *
     * @return vista de solo lectura de la mano correspondiente al jugador
     */
    public List<Carta> getMazoPersonal() {
        return Collections.unmodifiableList(mazoPersonal);
    }

    /**
     * Agrega una carta al mazo personal si hay espacio dentro del límite de
     * {@value #MAX_CARTAS_MANO} cartas. Si la mano está llena, no hace nada.
     *
     * @param carta carta que se agrega al mazo personal; no puede ser {@code null}
     * @throws NullPointerException si {@code carta} es {@code null}
     */
    public void guardarCartaEnMazoPersonal(Carta carta) {
        if (mazoPersonal.size() < MAX_CARTAS_MANO) {
            mazoPersonal.add(carta);
        }
    }

    /**
     * Elimina una carta del mazo personal (porque ya fue usada).
     *
     * @param carta carta que se elimina del mazo personal
     */
    public void usarCartaEnMazoPersonal(Carta carta) {
        mazoPersonal.remove(carta);
    }

    /**
     * Elimina todas las cartas del mazo personal.
     */
    public void vaciarMazoPersonal() {
        mazoPersonal.clear();
    }

    /**
     * Indica si el jugador está protegido por una carta.
     *
     * @return {@code true} si la protección está activada;
     *         {@code false} si está desactivada
     */
    public boolean isEscudoActivo() {
        return escudoActivo;
    }

    /**
     * Establece si el jugador está protegido.
     *
     * @param escudoActivo {@code true} para proteger al jugador;
     *                     {@code false} para quitarle la protección
     */
    public void setEscudoActivo(boolean escudoActivo) {
        this.escudoActivo = escudoActivo;
    }

    /**
     * Indica si el jugador ha sido afectado por una carta de ataque y debe
     * omitir su turno.
     *
     * @return {@code true} si debe omitir su turno; {@code false} en caso contrario
     */
    public boolean isTurnoOmitido() {
        return turnoOmitido;
    }

    /**
     * Establece si el jugador debe omitir su turno por un ataque.
     *
     * @param turnoOmitido {@code true} para omitir el turno del jugador;
     *                     {@code false} para permitirlo
     */
    public void setTurnoOmitido(boolean turnoOmitido) {
        this.turnoOmitido = turnoOmitido;
    }

    /**
     * Indica si el jugador tiene un turno extra.
     *
     * @return {@code true} si tiene turno extra; {@code false} en caso contrario
     */
    public boolean isTurnoExtra() {
        return turnoExtra;
    }

    /**
     * Establece si el jugador tiene un turno extra.
     *
     * @param turnoExtra {@code true} para otorgar un turno extra;
     *                   {@code false} para quitarlo
     */
    public void setTurnoExtra(boolean turnoExtra) {
        this.turnoExtra = turnoExtra;
    }

    /**
     * Indica si el jugador ha robado al menos una carta del mazo central en su turno.
     *
     * @return {@code true} si ha robado; {@code false} si no ha robado
     */
    public boolean isCartaRobada() {
        return cartaRobada;
    }

    /**
     * Registra que el jugador robó una carta del mazo central: aumenta en uno la
     * cantidad de cartas robadas y marca que ya robó en este turno. Así los dos
     * indicadores siempre se mantienen coherentes.
     */
    public void registrarCartaRobada() {
        cartasRobadas++;
        cartaRobada = true;
    }

    /**
     * Indica si el jugador ha usado al menos una carta del mazo personal en su turno.
     *
     * @return {@code true} si ha usado una carta; {@code false} si no ha usado ninguna
     */
    public boolean isCartaUsada() {
        return cartaUsada;
    }

    /**
     * Establece si el jugador ha usado alguna carta del mazo personal en su turno.
     *
     * @param cartaUsada {@code true} si ha usado una carta;
     *                   {@code false} si no ha usado ninguna
     */
    public void setCartaUsada(boolean cartaUsada) {
        this.cartaUsada = cartaUsada;
    }

    /**
     * Obtiene cuántas casillas extra puede moverse el jugador (efecto de la
     * carta de desplazamiento).
     *
     * @return número de casillas extra a moverse; nunca es negativo
     */
    public int getCasillasExtra() {
        return casillasExtra;
    }

    /**
     * Establece cuántas casillas extra se puede mover el jugador (efecto de la
     * carta de desplazamiento).
     *
     * @param casillasExtra número de casillas extra a moverse
     */
    public void setCasillasExtra(int casillasExtra) {
        this.casillasExtra = casillasExtra;
    }

    /**
     * Suma casillas extra a las que ya puede moverse el jugador.
     *
     * @param extra cantidad de casillas extra que se suman.
     */
    public void sumarCasillasExtra(int extra) {
        this.casillasExtra += extra;
    }

    /**
     * Indica si el jugador ya usó una carta de ataque durante su turno.
     *
     * @return {@code true} si la usó; {@code false} si no la ha usado
     */
    public boolean isAtaqueUsado() {
        return ataqueUsado;
    }

    /**
     * Establece si el jugador ya usó una carta de ataque durante su turno.
     *
     * @param ataqueUsado {@code true} si ya la usó; {@code false} en caso contrario
     */
    public void setAtaqueUsado(boolean ataqueUsado) {
        this.ataqueUsado = ataqueUsado;
    }

    /**
     * Indica si el jugador ya usó una carta de defensa durante su turno.
     *
     * @return {@code true} si la usó; {@code false} si no la ha usado
     */
    public boolean isDefensaUsada() {
        return defensaUsada;
    }

    /**
     * Establece si el jugador ya usó una carta de defensa durante su turno.
     *
     * @param defensaUsada {@code true} si ya la usó; {@code false} en caso contrario
     */
    public void setDefensaUsada(boolean defensaUsada) {
        this.defensaUsada = defensaUsada;
    }

    /**
     * Obtiene la cantidad de cartas que el jugador ha robado del mazo central
     * en su turno.
     *
     * @return número de cartas robadas del mazo central
     */
    public int getCartasRobadas() {
        return cartasRobadas;
    }

    /**
     * Reinicia el estado táctico del jugador: ataque y defensa usados, número de
     * cartas robadas y las banderas de carta robada y carta usada. Se invoca al
     * comenzar el turno del jugador. No modifica la mano, el escudo, el turno
     * omitido ni las casillas extra.
     */
    public void reiniciarTurnoTactico() {
        this.ataqueUsado = false;
        this.defensaUsada = false;
        this.cartasRobadas = 0;
        this.cartaRobada = false;
        this.cartaUsada = false;
    }

    /**
     * Obtiene la cantidad de puntos que ha anotado el jugador.
     *
     * @return puntuación actual del jugador
     */
    public int getAnotaciones() {
        return anotaciones;
    }

    /**
     * Suma un punto a la puntuación del jugador.
     */
    public void sumarAnotacion() {
        anotaciones++;
    }

    /**
     * Establece la casilla que ocupa la bola en el tablero. Este método solo
     * actualiza la bola; el tablero ({@link GroggyRing}) se encarga de actualizar
     * también la casilla.
     *
     * @param fila fila de la casilla; no puede ser negativa
     * @param columna columna de la casilla
     */
    public void setPosicion(int fila, int columna) {
        this.fila = fila;
        this.columna = columna;
    }

    /**
     * Obtiene la fila de la casilla que ocupa la bola.
     *
     * @return la fila actual de la bola
     */
    public int getFila() {
        return fila;
    }

    /**
     * Obtiene la columna de la casilla que ocupa la bola.
     *
     * @return la columna actual de la bola
     */
    public int getColumna() {
        return columna;
    }

    /**
     * Obtiene la coordenada X de la bola en el tablero, derivada de su columna.
     *
     * @return la coordenada X, en píxeles locales del tablero
     */
    public double getPosX() {
        return columna * TAM;
    }

    /**
     * Obtiene la coordenada Y de la bola en el tablero, derivada de su fila.
     *
     * @return la coordenada Y, en píxeles locales del tablero
     */
    public double getPosY() {
        return fila * TAM;
    }

    /**
     * Obtiene la tripulación a la que pertenece la bola.
     *
     * @return la {@link Tripulacion} de la bola
     */
    public Tripulacion getTripulacion() {
        return tripulacion;
    }

    /**
     * Obtiene la imagen con la que se dibuja la bola.
     *
     * @return la imagen de la bola, o {@code null} si no tiene
     */
    public BufferedImage getImagen() {
        return imagen;
    }

    /**
     * Dibuja la bola en su posición del tablero. Si no tiene imagen, dibuja un
     * círculo rojo temporal.
     *
     * @param g contexto gráfico (ya trasladado al origen del tablero) sobre el
     *          cual dibujar
     */
    public void dibujar(Graphics g) {
        int x = (int) getPosX();
        int y = (int) getPosY();
       g.drawImage(imagen, x, y, TAM, TAM, null);
    }
}