/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Dominio;

import java.awt.Graphics;
import java.io.IOException;
import java.util.List;

public class Torneo {

    private Juego juegoActual;
    private int indiceJuego = 0;

    private final Tripulacion tripulacion1;
    private final Tripulacion tripulacion2;
    private final List<ConfiguracionGeneral> configuraciones;
    private final DibujarBandera dibujarBandera;

    public Torneo(Tripulacion t1, Tripulacion t2, List<ConfiguracionGeneral> configuraciones) throws IOException {
        this.tripulacion1 = t1;
        this.tripulacion2 = t2;
        this.configuraciones = configuraciones;
        this.dibujarBandera = new DibujarBandera(t1, t2);
        iniciarJuego(0);
    }

    private void iniciarJuego(int indice) throws IOException {
        this.indiceJuego = indice;
        switch (indice) {
    case 0:
        juegoActual = new DonutRace(tripulacion1, tripulacion2,
                (ConfiguracionDonutRace) configuraciones.get(0));
        break;
    case 1:
        juegoActual = new GroggyRino(tripulacion1, tripulacion2,
                (ConfiguracionGroggyRing) configuraciones.get(1));
        break;
//    case 2:
//        juegoActual = new Combat(tripulacion1, tripulacion2,
//                (ConfiguracionCombat) configuraciones.get(2));
//        break;
    default:
        throw new IllegalArgumentException("Juego inexistente: " + indice);
}
    }

    public void siguienteJuego() throws IOException {
        if (indiceJuego + 1 < configuraciones.size()) {
            //Aqui pantalla que muestra la puntuacion general del torneo de cada tripulacion
            iniciarJuego(indiceJuego + 1);
        }
        // Aqui poner resultado final del torneo 
    }

    public void actualizarJuego() throws IOException {
        juegoActual.actualizar();
           juegoActual.actualizar();
        if (juegoActual.haTerminado()) {
            try {
                siguienteJuego();
            } catch (IOException ex) {
            }
        }
    }
    public void dibujar(Graphics g) {
        juegoActual.dibujar(g);
        dibujarBandera.dibujar(g);
    }

    public void procesarTeclaPresionada(int e) { juegoActual.procesarTeclaPresionada(e); }
    public void procesarTeclaLiberada(int e)   { juegoActual.procesarTeclaLiberada(e); }

    public Juego getJuegoActual() {
        return juegoActual;
    }

    public void setJuegoActual(Juego juegoActual) {
        this.juegoActual = juegoActual;
    }

    public int getIndiceJuego() {
        return indiceJuego;
    }

    public void setIndiceJuego(int indiceJuego) {
        this.indiceJuego = indiceJuego;
    }
    
    
}