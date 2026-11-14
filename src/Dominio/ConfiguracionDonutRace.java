/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Dominio;

/**
 * Clase hija que controla los parámetros del minijuego Donut Race,
 * heredando de la clase padre {@link ConfiguracionGeneral}.
 * <p>
 * Los parámetros se establecen según el nivel de dificultad elegido
 * ({@link #nivelFacil()}, {@link #nivelNormal()} o {@link #nivelDificil()})
 * y también pueden ajustarse individualmente mediante sus setters, que
 * validan los valores recibidos.
 * </p>
 *
 * @author Keithy Carrillo
 * @see ConfiguracionGeneral
 * @see Dificultad
 */
public class ConfiguracionDonutRace extends ConfiguracionGeneral {

    /** Dificultad de los obstáculos fijos del recorrido. Nunca es {@code null} una vez asignada. */
    private Dificultad obstaculosFijos;

    /** Cantidad de obstáculos que cada barco puede lanzar. No puede ser negativa. */
    private int obstaculosLanzados;

    /** Velocidad base de los barcos, en píxeles por actualización. Debe ser mayor que cero. */
    private double velocidadBarcos;

    /**
     * Sobrescritura del método {@code nivelFacil}.
     * Define los valores de los parámetros para el nivel fácil del minijuego Donut Race.
     */
    @Override
    public void nivelFacil() {
        this.obstaculosFijos = Dificultad.FACIL;
        this.obstaculosLanzados = 5;
        this.velocidadBarcos = 1.5;
    }

    /**
     * Sobrescritura del método {@code nivelNormal}.
     * Define los valores de los parámetros para el nivel normal del minijuego Donut Race.
     */
    @Override
    public void nivelNormal() {
        this.obstaculosFijos = Dificultad.NORMAL;
        this.obstaculosLanzados = 7;
        this.velocidadBarcos = 2.0;
    }

    /**
     * Sobrescritura del método {@code nivelDificil}.
     * Define los valores de los parámetros para el nivel difícil del minijuego Donut Race.
     */
    @Override
    public void nivelDificil() {
        this.obstaculosFijos = Dificultad.DIFICIL;
        this.obstaculosLanzados = 10;
        this.velocidadBarcos = 3.0;
    }

    /**
     * Obtiene la dificultad de obstáculos fijos configurada.
     *
     * @return un enum {@link Dificultad} con la dificultad
     */
    public Dificultad getObstaculosFijos() {
        return obstaculosFijos;
    }

    /**
     * Obtiene la cantidad de obstáculos lanzados configurada.
     *
     * @return un número entero con los obstáculos lanzados
     */
    public int getObstaculosLanzados() {
        return obstaculosLanzados;
    }

    /**
     * Obtiene la velocidad de los barcos configurada.
     *
     * @return un valor {@code double} con la velocidad de los barcos
     */
    public double getVelocidadBarcos() {
        return velocidadBarcos;
    }

    /**
     * Establece la dificultad de los obstáculos fijos.
     *
     * @param obstaculosFijos nueva dificultad; no puede ser {@code null}
     */
    public void setObstaculosFijos(Dificultad obstaculosFijos) {
        this.obstaculosFijos = obstaculosFijos;
    }

    /**
     * Establece la cantidad de obstáculos que se pueden lanzar.
     *
     * @param obstaculosLanzados nueva cantidad; no puede ser negativa
     * @throws IllegalArgumentException si {@code obstaculosLanzados} es negativo
     */
    public void setObstaculosLanzados(int obstaculosLanzados) {
        if (obstaculosLanzados < 0) {
            throw new IllegalArgumentException("Los obstáculos lanzados no pueden ser negativos.");
        }
        this.obstaculosLanzados = obstaculosLanzados;
    }

    /**
     * Establece la velocidad base de los barcos.
     *
     * @param velocidadBarcos nueva velocidad, en píxeles por actualización;
     *                        debe ser un número finito mayor que cero
     */
    public void setVelocidadBarcos(double velocidadBarcos) {
        this.velocidadBarcos = velocidadBarcos;
    }
} // fin de la clase