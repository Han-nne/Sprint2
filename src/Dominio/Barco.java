/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Dominio;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import static java.lang.Math.max;
import java.util.ArrayList;
import java.util.List;
import utility.BarcoSprites;

/**
 * La clase {@code Barco} representa una embarcación controlable dentro del juego.
 * <p>
 * Extiende de {@link ObjetoMovil}, por lo que hereda su posición, radio e imagen,
 * y define el comportamiento propio de un barco: movimiento en cuatro direcciones,
 * cambio de sprite según la dirección, límites del área de juego, manejo de
 * munición y manejo de colisiones.
 * </p>
 * <p>
 * Al colisionar con un {@link Obstaculo} o un {@link ObstaculoLanzado}, el barco
 * sufre una ralentización del 25% de su velocidad (gestionada por
 * {@link EfectoRalentizacion}) que se mantiene hasta recorrer 75 píxeles, momento
 * en que recupera su velocidad original. Al colisionar con cualquier otro objeto
 * (excepto una {@link Meta}), el barco retrocede y se detiene.
 * </p>
 *
 * @author johan
 * @see ObjetoMovil
 * @see Colisionable
 * @see Tripulacion
 * @see ColorBarco
 * @see EfectoRalentizacion
 * @see Municion
 * @see Direccion
 */
public class Barco extends ObjetoMovil {

    /** Tripulación que maneja este barco. */
    private final Tripulacion tripulacion;

    /** Munición de obstáculos que el barco puede lanzar. */
    private final Municion municion;

    /** Dirección horizontal actual: -1 (izquierda), 0 (quieto) o 1 (derecha). */
    private double dirX = 0;

    /** Dirección vertical actual: -1 (arriba), 0 (quieto) o 1 (abajo). */
    private double dirY = 0;

    /**
     * Última dirección hacia la que se movió el barco. Determina hacia dónde
     * se lanzan los obstáculos. Inicialmente es {@link Direccion#DERECHA}.
     */
    private Direccion ultimaDireccion = Direccion.DERECHA;

    /** Indica si el barco ha cumplido la condición de victoria. */
    private boolean condicionVictoria = false;

    /** Efecto de ralentización que controla la velocidad actual del barco. */
    private final EfectoRalentizacion ralentizacion;

    /**
     * Sprites del barco según su dirección. Índices: 0 = arriba, 1 = derecha,
     * 2 = abajo, 3 = izquierda.
     */
    private  List<BufferedImage> imagenBarcos;

    /**
     * Construye un barco con la tripulación, velocidad, posición, munición y
     * color indicados. Establece el radio de colisión en 15 y carga los sprites
     * correspondientes al color; el sprite inicial es el que mira hacia la derecha.
     *
     * @param tripulacion tripulación que controla el barco
     * @param velocidadEmbarcacion velocidad base del barco, en píxeles por actualización
     * @param posX posición inicial en el eje X
     * @param posY posición inicial en el eje Y
     * @param municionObstaculosLanzados cantidad inicial de obstáculos que el barco puede lanzar
     * @param colorBarco color del barco, que determina los sprites que se cargan
     * @throws IOException si ocurre un error al cargar la imagen de los sprites
     */
    public Barco(Tripulacion tripulacion, double velocidadEmbarcacion, double posX, double posY,
                 int municionObstaculosLanzados, ColorBarco colorBarco) throws IOException {
        super(posX, posY);
        this.tripulacion = tripulacion;
        this.ralentizacion = new EfectoRalentizacion(velocidadEmbarcacion);
        this.municion = new Municion(municionObstaculosLanzados);
        this.radio = 15;

        this.imagenBarcos = new ArrayList<>();
        imagenBarco(colorBarco);
    }

    /**
     * Carga los sprites del barco según su color y establece como imagen
     * inicial la que mira hacia la derecha.
     *
     * @param colorBarco color del barco cuyos sprites se cargan
     * @throws IOException si ocurre un error al cargar las imágenes
     */
    private void imagenBarco(ColorBarco colorBarco) throws IOException {
        this.imagenBarcos = BarcoSprites.cargar(colorBarco);
        this.imagen = this.imagenBarcos.get(1);
    }

    /**
     * Obtiene la velocidad normal del barco, sin efectos de ralentización.
     *
     * @return la velocidad base, en píxeles por actualización
     */
    public double getVelocidadOriginal() {
        return ralentizacion.getVelocidadBase();
    }

    /**
     * Obtiene la velocidad actual del barco, teniendo en cuenta si está
     * ralentizado.
     *
     * @return la velocidad actual, en píxeles por actualización
     */
    public double getVelocidadEmbarcacion() {
        return ralentizacion.getVelocidadActual();
    }

    /**
     * Obtiene la cantidad de obstáculos que el barco aún puede lanzar.
     *
     * @return munición restante
     */
    public int getMunicionObstaculoLanzados() {
        return municion.getActual();
    }

    /**
     * Obtiene la cantidad inicial de obstáculos que el barco podía lanzar.
     *
     * @return munición inicial
     */
    public int getMunicionTotalObstaculosLanzados() {
        return municion.getTotal();
    }

    /**
     * Consume una unidad de munición para lanzar un obstáculo.
     *
     * @return {@code true} si había munición y se consumió una unidad;
     *         {@code false} si no quedaba munición
     */
    public boolean lanzar() {
        return municion.consumir();
    }

    /**
     * Indica si el barco ha cumplido la condición de victoria.
     *
     * @return {@code true} si el barco ganó; {@code false} en caso contrario
     */
    public boolean isCondicionVictoria() {
        return condicionVictoria;
    }

    /**
     * Obtiene la tripulación que maneja el barco.
     *
     * @return la {@link Tripulacion} asociada al barco
     */
    public Tripulacion getTripulacion() {
        return tripulacion;
    }

    /**
     * Dibuja el sprite actual del barco en su posición.
     *
     * @param g contexto gráfico sobre el que se dibuja
     */
    @Override
    public void dibujar(Graphics g) {
        g.drawImage(imagen, (int) this.posX, (int) this.posY, null);
    }

    /**
     * Define la reacción del barco al colisionar con otro objeto:
     * <ul>
     *   <li>Si es un {@link Obstaculo} o un {@link ObstaculoLanzado}: aplica el
     *       efecto de ralentización.</li>
     *   <li>Si es una {@link Meta}: no realiza ninguna acción por el momento.</li>
     *   <li>Cualquier otro objeto: revierte el último desplazamiento y detiene
     *       el barco en ambos ejes.</li>
     * </ul>
     *
     * @param otro objeto {@link Colisionable} con el que se produjo el choque
     */
    @Override
    public void accionChoque(Colisionable otro) {

        if (otro instanceof Obstaculo || otro instanceof ObstaculoLanzado) {
            this.ralentizacion.aplicar();
        } else if (otro instanceof Meta) {

        } else {
            double velocidad = ralentizacion.getVelocidadActual();
            this.posX -= dirX * velocidad;
            dirX = 0;
            this.posY -= dirY * velocidad;
            dirY = 0;
        }

    } // accionChoque

    /**
     * Actualiza el estado del barco en cada ciclo del juego.
     * <p>
     * Si el barco se mueve, desplaza su posición según la dirección y la velocidad
     * actual y registra el desplazamiento (al menos 1 píxel) en el efecto de
     * ralentización, que restaura la velocidad original cuando se acumulan 75
     * píxeles recorridos. Finalmente, restringe la posición a los límites del
     * área de juego (X entre 0 y 1230, Y entre 0 y 670).
     * </p>
     */
    public void actualizar() {
        if (dirX != 0 || dirY != 0) {
            double velocidad = ralentizacion.getVelocidadActual();
            posX += dirX * velocidad;
            posY += dirY * velocidad;

            ralentizacion.registrarDesplazamiento((int) max(1, velocidad));
        }

        if ((int) posX < 0) posX = 0;
        if ((int) posX > 1230) posX = 1230;
        if ((int) posY < 0) posY = 0;
        if ((int) posY > 670) posY = 670;
    }

    /**
     * Obtiene la componente horizontal de la dirección en la que se lanzará
     * un obstáculo, según la última dirección en la que se movió el barco
     * (establecida por {@link #moverDerecha()}, {@link #moverIzquierda()},
     * {@link #moverAdelante()} o {@link #moverAtras()}).
     *
     * @return -1 si la última dirección fue hacia la izquierda, 1 si fue hacia
     *         la derecha y 0 si fue vertical
     */
    public int getDireccionDisparoX() {
        return ultimaDireccion.getDx();
    } // getDireccionDisparoX

    /**
     * Obtiene la componente vertical de la dirección en la que se lanzará
     * un obstáculo, según la última dirección en la que se movió el barco
     * (establecida por {@link #moverDerecha()}, {@link #moverIzquierda()},
     * {@link #moverAdelante()} o {@link #moverAtras()}).
     *
     * @return -1 si la última dirección fue hacia arriba, 1 si fue hacia
     *         abajo y 0 si fue horizontal
     */
    public int getDireccionDisparoY() {
        return ultimaDireccion.getDy();
    } // getDireccionDisparoY

    /**
     * Orienta el barco hacia la dirección indicada: actualiza la dirección de
     * movimiento en ambos ejes, guarda la dirección como la última utilizada
     * (para el lanzamiento de obstáculos) y cambia el sprite al correspondiente.
     *
     * @param direccion dirección hacia la que se mueve el barco
     */
    private void mover(Direccion direccion) {
        this.dirX = direccion.getDx();
        this.dirY = direccion.getDy();
        this.ultimaDireccion = direccion;
        this.imagen = this.imagenBarcos.get(direccion.getIndiceSprite());
    }

    /**
     * Mueve el barco hacia arriba (dirección vertical -1), guarda esta dirección
     * como la última para determinar hacia dónde se lanza un obstáculo y cambia
     * el sprite al que mira hacia arriba.
     */
    @Override
    public void moverAdelante() {
        mover(Direccion.ARRIBA);
    }

    /**
     * Mueve el barco hacia abajo (dirección vertical 1), guarda esta dirección
     * como la última para determinar hacia dónde se lanza un obstáculo y cambia
     * el sprite al que mira hacia abajo.
     */
    @Override
    public void moverAtras() {
        mover(Direccion.ABAJO);
    }

    /**
     * Mueve el barco hacia la izquierda (dirección horizontal -1), guarda esta
     * dirección como la última para determinar hacia dónde se lanza un obstáculo
     * y cambia el sprite al que mira hacia la izquierda.
     */
    @Override
    public void moverIzquierda() {
        mover(Direccion.IZQUIERDA);
    }

    /**
     * Mueve el barco hacia la derecha (dirección horizontal 1), guarda esta
     * dirección como la última para determinar hacia dónde se lanza un obstáculo
     * y cambia el sprite al que mira hacia la derecha.
     */
    @Override
    public void moverDerecha() {
        mover(Direccion.DERECHA);
    }

    /**
     * Detiene el movimiento vertical del barco (dirección vertical en 0).
     */
    public void detenerVertical() {
        dirY = 0; // detiene el barco
    }

    /**
     * Detiene el movimiento horizontal del barco (dirección horizontal en 0).
     */
    public void detenerHorizontal() {
        dirX = 0; // detiene el barco
    }

    /**
     * Obtiene el radio de colisión del barco en el eje X.
     *
     * @return el radio del barco
     */
    @Override
    public double getRadioX() {
        return radio;
    }

    /**
     * Obtiene el radio de colisión del barco en el eje Y.
     *
     * @return el radio del barco
     */
    @Override
    public double getRadioY() {
        return radio;
    }

    public double getDirX() {
        return dirX;
    }

    public void setDirX(double dirX) {
        this.dirX = dirX;
    }

    public double getDirY() {
        return dirY;
    }

    public void setDirY(double dirY) {
        this.dirY = dirY;
    }

    public Direccion getUltimaDireccion() {
        return ultimaDireccion;
    }

    public void setUltimaDireccion(Direccion ultimaDireccion) {
        this.ultimaDireccion = ultimaDireccion;
    }

    public List<BufferedImage> getImagenBarcos() {
        return imagenBarcos;
    }

    public void setImagenBarcos(List<BufferedImage> imagenBarcos) {
        this.imagenBarcos = imagenBarcos;
    }
} // fin clase