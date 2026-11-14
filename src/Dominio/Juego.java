/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package Dominio;

import java.awt.Graphics;

/**
 * Contrato que deben cumplir los minijuegos del torneo.
 * <p>
 * Define el ciclo básico de un minijuego: actualizar su estado, dibujarse,
 * reaccionar al teclado e informar cuándo terminó, de modo que el torneo pueda
 * controlar cualquier minijuego de la misma manera.
 * </p>
 *
 * @author johan
 * @see DonutRace
 * @see GroggyRing
 * @see Combat
 */
public interface Juego {

    /**
     * Actualiza el estado del juego. Se invoca en cada ciclo del juego.
     */
    void actualizar();

    /**
     * Dibuja todos los elementos del juego.
     *
     * @param g contexto gráfico sobre el cual dibujar
     */
    void dibujar(Graphics g);

    /**
     * Procesa una tecla presionada.
     *
     * @param e código de la tecla presionada (constante de {@link java.awt.event.KeyEvent})
     */
    void procesarTeclaPresionada(int e);

    /**
     * Procesa una tecla liberada.
     *
     * @param e código de la tecla liberada (constante de {@link java.awt.event.KeyEvent})
     */
    void procesarTeclaLiberada(int e);

    /**
     * Indica si el juego terminó y el torneo puede avanzar.
     *
     * @return {@code true} si el juego terminó y se puede avanzar con el torneo;
     *         {@code false} si el juego sigue en curso
     */
    boolean haTerminado();
}

