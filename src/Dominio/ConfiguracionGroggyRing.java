/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Dominio;

/**
 * Clase hija que controla los parámetros del minijuego Groggy Ring,
 * heredando de la clase padre {@link ConfiguracionGeneral}.
 * <p>
 * Los parámetros se establecen según el nivel de dificultad elegido
 * ({@link #nivelFacil()}, {@link #nivelNormal()} o {@link #nivelDificil()})
 * y también pueden ajustarse individualmente mediante su setter, que
 * valida el valor recibido.
 * </p>
 *
 * @author Keithy Carrillo
 * @see ConfiguracionGeneral
 * @see GroggyRing
 */
public class ConfiguracionGroggyRing extends ConfiguracionGeneral {

    /** Puntuación que se debe alcanzar para ganar. Debe ser mayor que cero. */
    private int puntuacionVictoria;

    /**
     * Sobrescritura del método {@code nivelFacil}.
     * Define los valores de los parámetros para el nivel fácil del minijuego Groggy Ring.
     */
    @Override
    public void nivelFacil() {
        this.puntuacionVictoria = 1;
    }

    /**
     * Sobrescritura del método {@code nivelNormal}.
     * Define los valores de los parámetros para el nivel normal del minijuego Groggy Ring.
     */
    @Override
    public void nivelNormal() {
        this.puntuacionVictoria = 2;
    }

    /**
     * Sobrescritura del método {@code nivelDificil}.
     * Define los valores de los parámetros para el nivel difícil del minijuego Groggy Ring.
     */
    @Override
    public void nivelDificil() {
        this.puntuacionVictoria = 3;
    }

    /**
     * Obtiene la puntuación de victoria configurada.
     *
     * @return un número entero con la puntuación de victoria
     */
    public int getPuntuacionVictoria() {
        return puntuacionVictoria;
    }

    /**
     * Establece la puntuación necesaria para ganar.
     *
     * @param puntuacionVictoria nueva puntuación de victoria; debe ser mayor que cero
     */
    public void setPuntuacionVictoria(int puntuacionVictoria) {
        this.puntuacionVictoria = puntuacionVictoria;
    }

} // fin de la clase