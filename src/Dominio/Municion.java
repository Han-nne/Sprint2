/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Dominio;

/**
 * Representa la munición disponible para un barco.
 * <p>
 * Mantiene la cantidad inicial (que no cambia durante la partida) y la
 * cantidad restante, que disminuye cada vez que se dispara.
 * </p>
 *
 * @author johan
 */
public class Municion {

    /** Cantidad inicial de munición; no cambia durante la partida. */
    private final int total;

    /** Cantidad de munición restante. */
    private int actual;

    /**
     * Crea un depósito de munición completo con la cantidad indicada.
     *
     * @param total cantidad inicial de munición
     */
    public Municion(int total) {
        if (total < 0) {

        }
        this.total = total;
        this.actual = total;
    }

    /**
     * Gasta una unidad de munición si hay disponible.
     *
     * @return {@code true} si se pudo consumir una unidad; {@code false} si
     *         no queda munición
     */
    public boolean consumir() {
        if (actual > 0) {
            actual--;
            return true;
        }
        return false;
    }

    /**
     * Indica si aún queda munición.
     *
     * @return {@code true} si queda al menos una unidad; {@code false} en
     *         caso contrario
     */
    public boolean tieneMunicion() {
        return actual > 0;
    }

    /**
     * Obtiene la munición restante.
     *
     * @return cantidad de munición restante
     */
    public int getActual() {
        return actual;
    }

    /**
     * Establece la munición restante.
     *
     * @param actual nueva cantidad de munición restante
     */
    public void setActual(int actual) {
        this.actual = actual;
    }

    /**
     * Obtiene la munición inicial.
     *
     * @return cantidad inicial de munición
     */
    public int getTotal() {
        return total;
    }
}
