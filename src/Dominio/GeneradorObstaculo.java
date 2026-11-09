/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Dominio;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * La clase {@code GeneradorObstaculo} crea los obstáculos fijos de la carrera
 * según la dificultad seleccionada.
 * <p>
 * Las posiciones de cada dificultad se guardan como datos (pares {@code {x, y}})
 * en arreglos constantes, de modo que modificar una dificultad o agregar
 * obstáculos no requiere cambiar la lógica de generación.
 * </p>
 * <p>
 * Es una clase de utilidad: todos sus métodos son estáticos. {@link DonutRace}
 * la usa al iniciar la partida para poblar el escenario.
 * </p>
 *
 * @author johan
 * @see Obstaculo
 * @see DonutRace
 */
public class GeneradorObstaculo {

    /**
     * Posiciones de los obstáculos en dificultad Fácil (7 obstáculos).
     * Cada fila es un par {@code {x, y}}.
     */
    private static final int[][] FACIL = {
        {200, 150}, {273, 570}, {430, 350}, {700, 130},
        {900, 150}, {1000, 80}, {1000, 325}
    };

    /**
     * Posiciones de los obstáculos en dificultad Normal (15 obstáculos).
     * Cada fila es un par {@code {x, y}}.
     */
    private static final int[][] NORMAL = {
        {115, 350}, {200, 150}, {273, 570}, {326, 130}, {450, 450},
        {532, 80},  {590, 390}, {674, 170}, {793, 300}, {800, 450},
        {838, 680}, {887, 530}, {900, 150}, {1000, 80}, {1100, 325}
    };

    /**
     * Posiciones de los obstáculos en dificultad Difícil (20 obstáculos).
     * Cada fila es un par {@code {x, y}}.
     */
    private static final int[][] DIFICIL = {
        {115, 350}, {150, 500}, {200, 150}, {273, 570}, {326, 130},
        {380, 280}, {450, 450}, {532, 80},  {590, 390}, {674, 170},
        {720, 600}, {793, 300}, {800, 450}, {838, 680}, {887, 530},
        {900, 150}, {950, 250}, {1000, 80}, {1100, 325}, {1150, 500}
    };

    /**
     * Constructor privado: la clase es de utilidad y no se instancia.
     */
    private GeneradorObstaculo() {
    }

    /**
     * Crea los obstáculos fijos correspondientes a la dificultad indicada.
     * <p>
     * Devuelve 7 obstáculos para {@code FACIL}, 15 para {@code NORMAL} y 20
     * para {@code DIFICIL}. Cada llamada genera objetos nuevos, por lo que la
     * lista devuelta puede modificarse sin afectar a otras partidas.
     * </p>
     *
     * @param dificultad dificultad de los obstáculos fijos seleccionada en la
     *                   configuración de la partida
     * @return lista de obstáculos nuevos, ubicados según la dificultad
     * @throws IOException si ocurre un error al cargar la imagen de un obstáculo
     */
    public static List<Obstaculo> generar(Dificultad dificultad) throws IOException {
        int[][] posiciones;
        switch (dificultad) {
            case FACIL:
                posiciones = FACIL;
                break;
            case NORMAL:
                posiciones = NORMAL;
                break;
            default:
                posiciones = DIFICIL;
                break;
        }

        List<Obstaculo> obstaculos = new ArrayList<>();
        for (int[] p : posiciones) {
            obstaculos.add(new Obstaculo(p[0], p[1]));
        }
        return obstaculos;
    }
}
