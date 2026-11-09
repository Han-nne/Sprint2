/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Dominio;

import java.io.IOException;

/**
 * La clase {@code LanzadorObstaculo} se encarga de crear los
 * {@link ObstaculoLanzado} que dispara un {@link Barco}.
 * <p>
 * Calcula la posición inicial del obstáculo (justo delante del barco, según la
 * dirección en la que apunta), su dirección y su velocidad de desplazamiento.
 * No descuenta munición: eso lo hace {@link Barco#lanzar()}, y registrar el
 * obstáculo en el juego y en el motor de colisiones es responsabilidad de
 * {@link DonutRace}.
 * </p>
 * <p>
 * Es una clase de utilidad: todos sus métodos son estáticos.
 * </p>
 *
 * @author johan
 * @see Barco
 * @see ObstaculoLanzado
 * @see DonutRace
 */
public class LanzadorObstaculo {

    /**
     * Factor por el que se multiplica la velocidad original del barco para
     * obtener la velocidad del obstáculo lanzado.
     */
    private static final double FACTOR_VELOCIDAD = 2.0;

    /**
     * Desplazamiento, en píxeles, desde la esquina del barco hasta su centro.
     * Se usa en el eje en el que el barco no apunta.
     */
    private static final int OFFSET_CENTRO = 12;

    /**
     * Desplazamiento, en píxeles, para que el obstáculo nazca detrás del
     * origen del barco: se aplica al disparar hacia la izquierda o hacia
     * arriba.
     */
    private static final int OFFSET_ATRAS = -25;

    /**
     * Desplazamiento, en píxeles, para que el obstáculo nazca delante del
     * barco: se aplica al disparar hacia la derecha o hacia abajo.
     */
    private static final int OFFSET_ADELANTE = 55;

    /**
     * Constructor privado: la clase es de utilidad y no se instancia.
     */
    private LanzadorObstaculo() {
    }

    /**
     * Crea un obstáculo lanzado a partir del barco indicado.
     * <p>
     * La dirección se toma de {@link Barco#getDireccionDisparoX()} y
     * {@link Barco#getDireccionDisparoY()}, es decir, la última dirección en la
     * que se movió el barco. Si el barco apunta en horizontal, el obstáculo
     * nace a su izquierda o derecha (a la altura de su centro); si apunta en
     * vertical, nace arriba o abajo (centrado horizontalmente). La velocidad es
     * el doble de la velocidad original del barco, sin importar si está
     * ralentizado.
     * </p>
     *
     * @param barco barco que lanza el obstáculo
     * @return un nuevo {@link ObstaculoLanzado}, listo para agregarse al juego
     * @throws IOException si ocurre un error al cargar la imagen del obstáculo
     */
    public static ObstaculoLanzado crear(Barco barco) throws IOException {
        int dirX = barco.getDireccionDisparoX();
        int dirY = barco.getDireccionDisparoY();

        double posX = barco.getX() + OFFSET_CENTRO;
        double posY = barco.getY() + OFFSET_CENTRO;
        if (dirX != 0) posX = barco.getX() + (dirX < 0 ? OFFSET_ATRAS : OFFSET_ADELANTE);
        if (dirY != 0) posY = barco.getY() + (dirY < 0 ? OFFSET_ATRAS : OFFSET_ADELANTE);

        double velocidad = barco.getVelocidadOriginal() * FACTOR_VELOCIDAD;
        return new ObstaculoLanzado(posX, posY, dirX, dirY, velocidad);
    }
}