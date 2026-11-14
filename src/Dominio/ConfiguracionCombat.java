/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Dominio;

/**
 * Clase hija que controla los parámetros del minijuego Combat,
 * heredando de la clase padre {@link ConfiguracionGeneral}.
 * <p>
 * Los parámetros se establecen según el nivel de dificultad elegido
 * ({@link #nivelFacil()}, {@link #nivelNormal()} o {@link #nivelDificil()})
 * y también pueden ajustarse individualmente mediante sus setters.
 * </p>
 *
 * @author Keithy Carrillo
 * @see ConfiguracionGeneral
 */
public class ConfiguracionCombat extends ConfiguracionGeneral {

    /** Valor inicial de la barra de poder. Debe ser mayor que cero. */
    private int valorInicialBarra;

    /** Indica si los jugadores comparten el mismo teclado. */
    private boolean tecladoCompartido;

    /**
     * Sobrescritura del método {@code nivelFacil}.
     * Define los valores de los parámetros para el nivel fácil del minijuego Combat.
     */
    @Override
    public void nivelFacil() {
        this.valorInicialBarra = 100;
        this.tecladoCompartido = true;
    }

    /**
     * Sobrescritura del método {@code nivelNormal}.
     * Define los valores de los parámetros para el nivel normal del minijuego Combat.
     */
    @Override
    public void nivelNormal() {
        this.valorInicialBarra = 100;
        this.tecladoCompartido = true;
    }

    /**
     * Sobrescritura del método {@code nivelDificil}.
     * Define los valores de los parámetros para el nivel difícil del minijuego Combat.
     */
    @Override
    public void nivelDificil() {
        this.valorInicialBarra = 100;
        this.tecladoCompartido = true;
    }

    /**
     * Obtiene el valor inicial de la barra de poder configurado.
     *
     * @return un número entero con el valor inicial de la barra de poder
     */
    public int getValorInicialBarra() {
        return valorInicialBarra;
    }

    /**
     * Indica si los jugadores comparten el mismo teclado.
     *
     * @return {@code true} si el teclado es compartido; {@code false} en caso contrario
     */
    public boolean isTecladoCompartido() {
        return tecladoCompartido;
    }

    /**
     * Establece el valor inicial de la barra de poder.
     *
     * @param valorInicialBarra nuevo valor inicial de la barra; debe ser mayor que cero
     */
    public void setValorInicialBarra(int valorInicialBarra) {
        this.valorInicialBarra = valorInicialBarra;
    }

    /**
     * Establece si los jugadores comparten el mismo teclado.
     *
     * @param tecladoCompartido {@code true} para teclado compartido;
     *                          {@code false} en caso contrario
     */
    public void setTecladoCompartido(boolean tecladoCompartido) {
        this.tecladoCompartido = tecladoCompartido;
    }

} // fin de clase
