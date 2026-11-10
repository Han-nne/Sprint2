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
 */
public class DibujarBandera {
 
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
     *
     * @param tripulacion1 tripulación que se mostrará a la izquierda
     * @param tripulacion2 tripulación que se mostrará a la derecha
     */
    public DibujarBandera( Tripulacion tripulacion1 , Tripulacion tripulacion2) {
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
     * <p>
     * Si alguna de las tripulaciones es {@code null}, el método termina sin
     * dibujar nada. Si una tripulación no tiene bandera
     * ({@code getBandera() == null}), se dibuja únicamente su caja y su
     * nombre.
     * <p>
     * El lado derecho se posiciona tomando un ancho de panel fijo de 1280
     * píxeles. Este método modifica la fuente y el color del contexto gráfico
     * recibido, y no los restaura al finalizar.
     *
     * @param g contexto gráfico sobre el cual se realiza el dibujo,
     *          normalmente el obtenido en {@code paintComponent}
     */
    public void dibujar( Graphics g){
           if ( this.tripulacion1 == null || this.tripulacion2 == null) {
            return;
        }

        int tamannoBandera = 45;
        int margen = 12;

        g.setFont(new Font("Segoe UI", Font.BOLD, 14));
        FontMetrics fm = g.getFontMetrics();

        String nombre1 = this.tripulacion1.getNombreTripulacion();
        int anchoTexto1 = fm.stringWidth(nombre1);
        int anchoCaja1 = tamannoBandera + anchoTexto1 + 25;

        g.setColor(new Color(0, 0, 0, 140));
        g.fillRoundRect(margen - 5, margen - 5, anchoCaja1, tamannoBandera + 10, 12, 12);

        if (this.tripulacion1.getBandera() != null) {
            g.drawImage(this.tripulacion1.getBandera(), margen, margen, tamannoBandera, tamannoBandera, null);
        }

        g.setColor(Color.WHITE);
        g.drawString(nombre1, margen + tamannoBandera + 10, margen + (tamannoBandera / 2) + 5);

        String nombre2 = this.tripulacion2.getNombreTripulacion();
        int anchoTexto2 = fm.stringWidth(nombre2);
        int anchoCaja2 = tamannoBandera + anchoTexto2 + 25;

        int xBandera2 = 1280 - margen - tamannoBandera;
        int xTexto2 = xBandera2 - anchoTexto2 - 10;
        int xCaja2 = xTexto2 - 10;

        g.setColor(new Color(0, 0, 0, 140));
        g.fillRoundRect(xCaja2, margen - 5, anchoCaja2, tamannoBandera + 10, 12, 12);

        g.setColor(Color.WHITE);
        g.drawString(nombre2, xTexto2, margen + (tamannoBandera / 2) + 5);

        if (tripulacion2.getBandera() != null) {
            g.drawImage(this.tripulacion2.getBandera(), xBandera2, margen, tamannoBandera, tamannoBandera, null);
        }
    }

    public Tripulacion getTripulacion1() {
        return tripulacion1;
    }

    public Tripulacion getTripulacion2() {
        return tripulacion2;
    }
}