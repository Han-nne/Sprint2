/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Dominio;

import java.awt.Graphics;
import java.io.IOException;
import static java.lang.Math.sqrt;
import static utility.TileSetManagerDonutRace.cargarImagenDonutRace;

/**
 * La clase {@code Pista} representa el área elíptica del escenario dentro de la
 * cual pueden desplazarse los objetos móviles del juego (barcos y obstáculos
 * lanzados).
 * <p>
 * Extiende de {@link ObjetoMaritimo}, por lo que hereda su posición e imagen. La
 * imagen se recorta del tilesheet de DonutRace y se dibuja desde la esquina
 * superior izquierda de la pantalla; el centro de la elipse se deduce a partir
 * del tamaño de dicha imagen.
 * </p>
 * <p>
 * La pista es una zona y no un objeto con el que se pueda chocar, por lo que
 * <b>no debe registrarse en el {@link MotorColision}</b>. Los métodos de
 * {@link Colisionable} se implementan únicamente para cumplir el contrato de la
 * clase padre.
 * </p>
 *
 * @author johan
 * @see ObjetoMaritimo
 * @see Colisionable
 * @see DonutRace
 */
public class Pista extends ObjetoMaritimo {

    /** Radio horizontal de la elipse que delimita la pista, en píxeles. */
    private final double radioX;

    /** Radio vertical de la elipse que delimita la pista, en píxeles. */
    private final double radioY;

    /**
     * Construye la pista con los radios indicados. La imagen se carga desde el
     * tilesheet y se ubica en la esquina superior izquierda (0, 0) de la pantalla.
     * <p>
     * Los radios deben coincidir con la elipse dibujada en la imagen; con la
     * imagen actual los valores son 635 (eje X) y 357 (eje Y).
     * </p>
     *
     * @param radioX radio horizontal de la elipse
     * @param radioY radio vertical de la elipse
     * @throws IOException si ocurre un error al cargar la imagen del tilesheet
     */
    public Pista(double radioX, double radioY) throws IOException {
        super(0, 0);
        this.radioX = radioX;
        this.radioY = radioY;
        this.imagen = cargarImagenDonutRace()
                .getSubimage(420, 0, 1280, 720);
    }

    /**
     * Obtiene la coordenada X del centro de la elipse, calculada como la
     * posición de la imagen más la mitad de su ancho.
     *
     * @return el centro de la pista en el eje X
     */
    private double centroX() {
        return posX + imagen.getWidth() / 2.0;
    }

    /**
     * Obtiene la coordenada Y del centro de la elipse, calculada como la
     * posición de la imagen más la mitad de su alto.
     *
     * @return el centro de la pista en el eje Y
     */
    private double centroY() {
        return posY + imagen.getHeight() / 2.0;
    }

    /**
     * Indica si un punto se encuentra dentro de la elipse de la pista, usando la
     * ecuación {@code ((x - cx) / rx)² + ((y - cy) / ry)² <= 1}. Los puntos sobre
     * el borde se consideran dentro.
     *
     * @param x coordenada X del punto
     * @param y coordenada Y del punto
     * @return {@code true} si el punto está dentro de la pista; {@code false} en
     *         caso contrario
     */
    public boolean contiene(double x, double y) {
        double dx = (x - centroX()) / radioX;
        double dy = (y - centroY()) / radioY;
        return dx * dx + dy * dy <= 1.0;
    }

    /**
     * Corrige un punto para que no salga de la pista.
     * <p>
     * La elipse se reduce restando {@code margen} a cada radio, de modo que el
     * sprite completo (y no solo su centro) permanezca dentro. Si el punto queda
     * fuera de esa elipse reducida, se lleva de vuelta al borde conservando su
     * ángulo respecto al centro, lo que hace que el objeto se deslice por la pared
     * en lugar de quedarse pegado. Si ya está dentro, se devuelve sin cambios.
     * </p>
     *
     * @param x coordenada X del punto, normalmente el centro del objeto
     * @param y coordenada Y del punto, normalmente el centro del objeto
     * @param margen distancia que se resta a cada radio, normalmente la mitad del
     *               tamaño del sprite
     * @return un arreglo {@code {x, y}} con la posición ya corregida
     */
    public double[] limitar(double x, double y, double margen) {
        double rx = radioX - margen;
        double ry = radioY - margen;
        double dx = x - centroX();
        double dy = y - centroY();
        double valor = (dx * dx) / (rx * rx) + (dy * dy) / (ry * ry);

        if (valor > 1.0) {
            double factor = 1.0 / sqrt(valor);
            return new double[]{centroX() + dx * factor, centroY() + dy * factor};
        }
        return new double[]{x, y};
    }

    /**
     * Dibuja la imagen de la pista en su posición. Debe dibujarse antes que el
     * resto de elementos del juego para que quede por debajo de ellos.
     *
     * @param g contexto gráfico sobre el que se dibuja
     */
    @Override
    public void dibujar(Graphics g) {
        g.drawImage(imagen, (int) posX, (int) posY, null);
    }

    /**
     * Obtiene la posición del centro de la elipse en el eje X. Sobrescribe el
     * comportamiento heredado, que retorna la esquina de la imagen.
     *
     * @return el centro de la pista en el eje X
     */
    @Override
    public double getX() {
        return centroX();
    }

    /**
     * Obtiene la posición del centro de la elipse en el eje Y. Sobrescribe el
     * comportamiento heredado, que retorna la esquina de la imagen.
     *
     * @return el centro de la pista en el eje Y
     */
    @Override
    public double getY() {
        return centroY();
    }

    /**
     * Obtiene el radio horizontal de la elipse de la pista.
     *
     * @return el radio en el eje X
     */
    @Override
    public double getRadioX() {
        return radioX;
    }

    /**
     * Obtiene el radio vertical de la elipse de la pista.
     *
     * @return el radio en el eje Y
     */
    @Override
    public double getRadioY() {
        return radioY;
    }

    /**
     * No realiza ninguna acción: la pista es una zona y no reacciona a choques.
     * Se implementa solo para cumplir el contrato de {@link Colisionable}.
     *
     * @param otro objeto {@link Colisionable} con el que se produjo el choque
     */
    @Override
    public void accionChoque(Colisionable otro) {
        // La pista no reacciona a choques.
    }
}