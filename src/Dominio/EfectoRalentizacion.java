/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Dominio;

/**
 * Representa un efecto temporal de ralentización aplicado a un barco.
 * <p>
 * Mientras el efecto está activo, la velocidad del barco se reduce a una
 * fracción de su velocidad base. El efecto termina automáticamente cuando
 * el barco ha recorrido una distancia determinada desde que fue aplicado.
 * </p>
 *
 * @author johan
 */
public class EfectoRalentizacion {

    /** Fracción de la velocidad base que se conserva mientras dura el efecto (75%). */
    private static final double FACTOR_VELOCIDAD = 0.75;

    /** Distancia, en píxeles, que debe recorrer el barco para recuperar su velocidad. */
    private static final double DISTANCIA_RECUPERACION = 75.0;

    /** Velocidad normal del barco, sin efectos. */
    private final double velocidadBase;

    /** Indica si el efecto está activo. */
    private boolean activo = false;

    /** Distancia recorrida desde que se aplicó el efecto. */
    private double distanciaRecorrida = 0;

    /**
     * Crea un efecto de ralentización inactivo para un barco con la
     * velocidad base indicada.
     *
     * @param velocidadBase velocidad normal del barco, sin efectos
     */
    public EfectoRalentizacion(double velocidadBase) {
        this.velocidadBase = velocidadBase;
    }

    /**
     * Activa el efecto de ralentización y reinicia la distancia recorrida.
     * Si el efecto ya estaba activo, el conteo de recuperación comienza
     * de nuevo.
     */
    public void aplicar() {
        activo = true;
        distanciaRecorrida = 0;
    }

    /**
     * Registra el desplazamiento realizado por el barco y desactiva el efecto
     * cuando la distancia acumulada alcanza la necesaria para recuperarse.
     * Si el efecto no está activo, no hace nada.
     *
     * @param pixeles distancia, en píxeles, recorrida por el barco
     */
    public void registrarDesplazamiento(double pixeles) {
        if (!activo) {
            return;
        }
        distanciaRecorrida += pixeles;
        if (distanciaRecorrida >= DISTANCIA_RECUPERACION) {
            activo = false;
            distanciaRecorrida = 0;
        }
    }

    /**
     * Obtiene la velocidad que el barco debe tener en este momento.
     *
     * @return la velocidad base reducida por el factor de ralentización si el
     *         efecto está activo; en caso contrario, la velocidad base
     */
    public double getVelocidadActual() {
        return activo ? velocidadBase * FACTOR_VELOCIDAD : velocidadBase;
    }

    /**
     * Obtiene la velocidad normal del barco, sin efectos.
     *
     * @return la velocidad base
     */
    public double getVelocidadBase() {
        return velocidadBase;
    }

    /**
     * Indica si el efecto de ralentización está activo.
     *
     * @return {@code true} si el efecto está activo; {@code false} en caso
     *         contrario
     */
    public boolean isActivo() {
        return activo;
    }

    /**
     * Obtiene la distancia recorrida desde que se aplicó el efecto.
     *
     * @return distancia recorrida, en píxeles
     */
    public double getDistanciaRecorrida() {
        return distanciaRecorrida;
    }

    /**
     * Establece la distancia recorrida desde que se aplicó el efecto.
     *
     * @param distanciaRecorrida nueva distancia recorrida, en píxeles
     */
    public void setDistanciaRecorrida(double distanciaRecorrida) {
        this.distanciaRecorrida = distanciaRecorrida;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }

    public static double getFACTOR_VELOCIDAD() {
        return FACTOR_VELOCIDAD;
    }

    public static double getDISTANCIA_RECUPERACION() {
        return DISTANCIA_RECUPERACION;
    }
    
}