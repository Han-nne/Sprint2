/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Dominio;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;

/**
 * Dibuja en la parte superior de un panel las banderas y los nombres de dos
 * tripulaciones: la primera a la izquierda y la segunda a la derecha.
 *
 * @see Tripulacion
 */
public class DibujarBandera {

    /** Ancho fijo del panel sobre el que se dibuja, en píxeles. */
    private static final int ANCHO_PANEL = 1280;

    /** Tamaño (ancho y alto) con el que se dibuja cada bandera, en píxeles. */
    private static final int TAMANNO_BANDERA = 45;

    /** Margen entre el borde del panel y el contenido, en píxeles. */
    private static final int MARGEN = 12;

    /** Espacio entre la bandera y el nombre de la tripulación, en píxeles. */
    private static final int ESPACIO_TEXTO = 10;

    /** Relleno adicional que se suma al ancho de cada caja de fondo, en píxeles. */
    private static final int RELLENO_CAJA = 25;

    /** Diámetro de las esquinas redondeadas de las cajas de fondo, en píxeles. */
    private static final int ARCO_CAJA = 12;

    /** Fuente utilizada para dibujar los nombres. */
    private static final Font FUENTE = new Font("Segoe UI", Font.BOLD, 14);

    /** Color semitransparente de fondo de las cajas. */
    private static final Color COLOR_FONDO = new Color(0, 0, 0, 140);

    /**
     * Tripulación que se dibuja en el lado izquierdo del panel.
     */
    private final Tripulacion tripulacion1;

    /**
     * Tripulación que se dibuja en el lado derecho del panel.
     */
    private final Tripulacion tripulacion2;

    /**
     * Crea un dibujador asociado a dos tripulaciones.
     * <p>
     * No se valida que los parámetros sean distintos de {@code null}; si
     * alguno lo es, {@link #dibujar(Graphics)} no dibujará nada.
     * </p>
     *
     * @param tripulacion1 tripulación que se mostrará a la izquierda
     * @param tripulacion2 tripulación que se mostrará a la derecha
     */
    public DibujarBandera(Tripulacion tripulacion1, Tripulacion tripulacion2) {
        this.tripulacion1 = tripulacion1;
        this.tripulacion2 = tripulacion2;
    }

    /**
     * Dibuja las banderas y los nombres de las dos tripulaciones en la parte
     * superior del panel.
     * <p>
     * La primera tripulación se ubica a la izquierda, con la bandera antes del
     * nombre. La segunda se ubica a la derecha, con el nombre antes de la
     * bandera, de modo que ambas queden alineadas hacia los extremos. El ancho
     * de cada caja de fondo se calcula según la longitud del nombre, usando la
     * fuente "Segoe UI" en negrita de 14 puntos.
     * </p>
     * <p>
     * Si alguna de las tripulaciones es {@code null}, el método termina sin
     * dibujar nada. Si una tripulación no tiene bandera
     * ({@code getBandera() == null}), se dibuja únicamente su caja y su
     * nombre.
     * </p>
     * <p>
     * El lado derecho se posiciona tomando un ancho de panel fijo de 1280
     * píxeles. Este método modifica la fuente y el color del contexto gráfico
     * recibido, y no los restaura al finalizar.
     * </p>
     *
     * @param g contexto gráfico sobre el cual se realiza el dibujo,
     *          normalmente el obtenido en {@code paintComponent}
     */
    public void dibujar(Graphics g) {
        if (this.tripulacion1 == null || this.tripulacion2 == null) {
            return;
        }

        g.setFont(FUENTE);
        FontMetrics fm = g.getFontMetrics();
        int yTexto = MARGEN + (TAMANNO_BANDERA / 2) + 5;

        // Tripulación de la izquierda: bandera y luego nombre
        String nombre1 = this.tripulacion1.getNombreTripulacion();
        int anchoTexto1 = fm.stringWidth(nombre1);
        int anchoCaja1 = TAMANNO_BANDERA + anchoTexto1 + RELLENO_CAJA;

        g.setColor(COLOR_FONDO);
        g.fillRoundRect(MARGEN - 5, MARGEN - 5, anchoCaja1, TAMANNO_BANDERA + 10, ARCO_CAJA, ARCO_CAJA);

        if (this.tripulacion1.getBandera() != null) {
            g.drawImage(this.tripulacion1.getBandera(), MARGEN, MARGEN, TAMANNO_BANDERA, TAMANNO_BANDERA, null);
        }

        g.setColor(Color.WHITE);
        g.drawString(nombre1, MARGEN + TAMANNO_BANDERA + ESPACIO_TEXTO, yTexto);

        // Tripulación de la derecha: nombre y luego bandera
        String nombre2 = this.tripulacion2.getNombreTripulacion();
        int anchoTexto2 = fm.stringWidth(nombre2);
        int anchoCaja2 = TAMANNO_BANDERA + anchoTexto2 + RELLENO_CAJA;

        int xBandera2 = ANCHO_PANEL - MARGEN - TAMANNO_BANDERA;
        int xTexto2 = xBandera2 - anchoTexto2 - ESPACIO_TEXTO;
        int xCaja2 = xTexto2 - ESPACIO_TEXTO;

        g.setColor(COLOR_FONDO);
        g.fillRoundRect(xCaja2, MARGEN - 5, anchoCaja2, TAMANNO_BANDERA + 10, ARCO_CAJA, ARCO_CAJA);

        g.setColor(Color.WHITE);
        g.drawString(nombre2, xTexto2, yTexto);

        if (this.tripulacion2.getBandera() != null) {
            g.drawImage(this.tripulacion2.getBandera(), xBandera2, MARGEN, TAMANNO_BANDERA, TAMANNO_BANDERA, null);
        }
    }

    /**
     * Obtiene la tripulación que se dibuja en el lado izquierdo del panel.
     *
     * @return la primera {@link Tripulacion}, o {@code null} si no se asignó
     */
    public Tripulacion getTripulacion1() {
        return tripulacion1;
    }

    /**
     * Obtiene la tripulación que se dibuja en el lado derecho del panel.
     *
     * @return la segunda {@link Tripulacion}, o {@code null} si no se asignó
     */
    public Tripulacion getTripulacion2() {
        return tripulacion2;
    }
}