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
 * <p>
 * El estado del efecto solo cambia mediante {@link #aplicar()} y
 * {@link #registrarDesplazamiento(double)}, de modo que siempre es coherente.
 * </p>
 *
 * @author johan
 * @see Barco
 */
public class EfectoRalentizacion {

    /** Fracción de la velocidad base que se conserva mientras dura el efecto (75%). */
    public static final double FACTOR_VELOCIDAD = 0.75;

    /** Distancia, en píxeles, que debe recorrer el barco para recuperar su velocidad. */
    public static final double DISTANCIA_RECUPERACION = 75.0;

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
     * @param velocidadBase velocidad normal del barco, sin efectos; debe ser
     *                      un número mayor que cero
     * @throws IllegalArgumentException si {@code velocidadBase} es menor o igual
     *                                  a cero, {@code NaN} o infinito
     */
    public EfectoRalentizacion(double velocidadBase) {
        if (!(velocidadBase > 0) || Double.isInfinite(velocidadBase)) {
            throw new IllegalArgumentException("La velocidad base debe ser un número mayor que cero.");
        }
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
     * @param pixeles distancia, en píxeles, recorrida por el barco; no puede ser negativa
     * @throws IllegalArgumentException si {@code pixeles} es negativo
     */
    public void registrarDesplazamiento(double pixeles) {
        if (pixeles < 0) {
            throw new IllegalArgumentException("El desplazamiento no puede ser negativo.");
        }
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

}