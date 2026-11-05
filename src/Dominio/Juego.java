/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package Dominio;

import java.awt.Graphics;

/**
 *
 * @author johan
 */
public interface Juego {
     void actualizar();
    void dibujar(Graphics g);
    void procesarTeclaPresionada(int e);
    void procesarTeclaLiberada(int e);
    boolean haTerminado();   
}
