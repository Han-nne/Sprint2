/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Dominio;
 
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import static utility.TileSetManagerDonutRace.cargarImagenDonutRace;
 
/**
 * La clase {@code Isla} representa la isla central del escenario, un obstáculo
 * fijo e inmóvil con el que chocan los barcos.
 * <p>
 * Extiende de {@link ObjetoMaritimo}. Su imagen (300x300 píxeles) se obtiene del
 * tileset del juego y su radio de colisión es de 140. A diferencia de otros
 * objetos, sobrescribe {@link #getX()} y {@link #getY()} para que la posición
 * usada en las colisiones corresponda al centro de la isla y no a la esquina
 * superior izquierda de la imagen.
 * </p>
 * <p>
 * {@link DonutRace} se encarga de crearla y registrarla en el motor de colisiones.
 * </p>
 *
 * @author johan
 * @see ObjetoMaritimo
 * @see DonutRace
 */
public class Isla extends ObjetoMaritimo {

    /** Radio de colisión de la isla, en píxeles. */
    private static final double RADIO_COLISION = 140;

    /** Posición (en X y en Y) de la imagen de la isla dentro del tileset, en píxeles. */
    private static final int ORIGEN_TILESET = 10;

    /** Ancho y alto de la imagen de la isla, en píxeles. */
    private static final int TAM_IMAGEN = 300;

    /** Ajuste, en píxeles, que se resta al centro de la imagen para obtener el centro de colisión. */
    private static final double AJUSTE_CENTRO = 25;

    /**
     * Construye la isla en la posición indicada. Establece el radio de colisión
     * en 140 y carga su imagen (300x300 píxeles) desde el tileset.
     *
     * @param posX posición de dibujo en el eje X (esquina superior izquierda)
     * @param posY posición de dibujo en el eje Y (esquina superior izquierda)
     * @throws IOException si ocurre un error al cargar la imagen del tileset
     */
    public Isla(int posX, int posY) throws IOException {
        super(posX, posY);
        this.radio = RADIO_COLISION;
        BufferedImage tileSet = cargarImagenDonutRace();
        this.imagen = tileSet.getSubimage(ORIGEN_TILESET, ORIGEN_TILESET, TAM_IMAGEN, TAM_IMAGEN);
    }

    /**
     * Dibuja la isla en su posición.
     *
     * @param g contexto gráfico sobre el que se dibuja
     */
    @Override
    public void dibujar(Graphics g) {
        g.drawImage(imagen, (int) this.posX, (int) this.posY, null);
    }

    /**
     * Define la reacción ante un choque. La isla no hace nada al ser tocada.
     *
     * @param otro objeto {@link Colisionable} con el que se produjo el choque
     */
    @Override
    public void accionChoque(Colisionable otro) {
        // La isla es fija e inmóvil: no reacciona a los choques.
    }

    /**
     * Obtiene la coordenada X del centro de colisión de la isla: la posición de
     * dibujo más la mitad del ancho de la imagen, menos 25 píxeles de ajuste.
     *
     * @return la coordenada X usada para detectar colisiones
     */
    @Override
    public double getX() {
        return this.posX + (this.imagen.getWidth() / 2.0) - AJUSTE_CENTRO;
    }

    /**
     * Obtiene la coordenada Y del centro de colisión de la isla: la posición de
     * dibujo más la mitad del alto de la imagen, menos 25 píxeles de ajuste.
     *
     * @return la coordenada Y usada para detectar colisiones
     */
    @Override
    public double getY() {
        return this.posY + (this.imagen.getHeight() / 2.0) - AJUSTE_CENTRO;
    }

    /**
     * Obtiene el radio de colisión de la isla en el eje X.
     *
     * @return el radio de la isla
     */
    @Override
    public double getRadioX() {
        return radio;
    }

    /**
     * Obtiene el radio de colisión de la isla en el eje Y.
     *
     * @return el radio de la isla
     */
    @Override
    public double getRadioY() {
        return radio;
    }

} // fin clase
