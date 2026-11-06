/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Dominio;

import java.awt.Graphics;
import java.awt.event.KeyEvent;

public class GroggyRing implements Juego {

    private static final int TAM = JugadorBola.TAM;   // 128
    private static final int FILAS = 5;
    private static final int COLUMNAS = 10;
    
    private final int puntuacionVictoria ;

    private Casilla zonaJuego[][];
    private JugadorBola bolaJugador1;
    private JugadorBola bolaJugador2;

    private JugadorBola turnoActual;   // a quién le toca
    private JugadorBola ganador;       // null mientras nadie gane
    private boolean terminado = false;

    public GroggyRing(Tripulacion tripulacion1, Tripulacion tripulacion2, ConfiguracionGroggyRing configuracion) {
       this.puntuacionVictoria = configuracion.getPuntuacionVictoria();
       this.bolaJugador1 = new JugadorBola(tripulacion1);
       this.bolaJugador2 = new JugadorBola(tripulacion2);
       this.zonaJuego = new Casilla[FILAS][COLUMNAS];
       iniciarJuego();
    }

   private void iniciarJuego() {
       crearZonaJuego(0, 0);
      reiniciarPosiciones();
      turnoActual = bolaJugador1;
}
   
   private void reiniciarPosiciones() {
       liberar(bolaJugador1);
      liberar(bolaJugador2);
      colocar(bolaJugador1, 2, 1);
      colocar(bolaJugador2, 2, COLUMNAS - 2);
}
   
   private void liberar(JugadorBola bola) {
       Casilla actual = zonaJuego[bola.getFila()][bola.getColumna()];
       if (actual.getJugadorBola() == bola) {
          actual.setJugadorBola(null);
       }
}

    public void crearZonaJuego(int dx, int dy) {
        for (int i = 0; i < zonaJuego.length; i++) {
            for (int j = 0; j < zonaJuego[i].length; j++) {
                if (i == 2 && (j == 0 || j == COLUMNAS - 1)) {
                    CasillaNeumatico neumatico = new CasillaNeumatico(dx + j * TAM, dy + i * TAM);
                    neumatico.setDueno(j == 0 ? bolaJugador1 : bolaJugador2);
                    zonaJuego[i][j] = neumatico;
                } else {
                    zonaJuego[i][j] = new CasillaCampo(dx + j * TAM, dy + i * TAM);
                }
            }
        }
    }

    /** Pone una bola en una casilla y lo registra en la casilla. */
    private void colocar(JugadorBola bola, int fila, int columna) {
        bola.setPosicion(fila, columna);
        zonaJuego[fila][columna].setJugadorBola(bola);
    }

  private void intentarMover(JugadorBola bola, int dFila, int dCol) {
    if (terminado || bola != turnoActual) {
        return;
    }

    int nuevaFila = bola.getFila() + dFila;
    int nuevaCol = bola.getColumna() + dCol;

    if (nuevaFila < 0 || nuevaFila >= FILAS || nuevaCol < 0 || nuevaCol >= COLUMNAS) {
        return;
    }

    Casilla destino = zonaJuego[nuevaFila][nuevaCol];

    if (destino.getJugadorBola() != null) {
        return;
    }

    zonaJuego[bola.getFila()][bola.getColumna()].setJugadorBola(null);
    colocar(bola, nuevaFila, nuevaCol);

    JugadorBola rival = (bola == bolaJugador1) ? bolaJugador2 : bolaJugador1;

    if (destino instanceof CasillaNeumatico
            && ((CasillaNeumatico) destino).getDueno() != bola) {

        bola.sumarAnotacion();

        if (bola.getAnotaciones() >= puntuacionVictoria) {
            ganador = bola;
            terminado = true;
        } else {
            reiniciarPosiciones();
            turnoActual = rival;   // empieza quien recibió la anotación
        }
        return;
    }

    turnoActual = rival;
}
  
    @Override
    public void actualizar() {
        // juego por turnos 
    }

    @Override
    public void dibujar(Graphics g) {
        for (int i = 0; i < zonaJuego.length; i++) {
            for (int j = 0; j < zonaJuego[i].length; j++) {
                zonaJuego[i][j].dibujar(g);
            }
        }
        bolaJugador1.dibujar(g);
        bolaJugador2.dibujar(g);
    }

    @Override
    public void procesarTeclaPresionada(int e) {
        switch (e) {
            // Jugador 1: WASD
            case KeyEvent.VK_W: intentarMover(bolaJugador1, -1, 0); break;
            case KeyEvent.VK_S: intentarMover(bolaJugador1, 1, 0);  break;
            case KeyEvent.VK_A: intentarMover(bolaJugador1, 0, -1); break;
            case KeyEvent.VK_D: intentarMover(bolaJugador1, 0, 1);  break;
            // Jugador 2: flechas
            case KeyEvent.VK_UP:    intentarMover(bolaJugador2, -1, 0); break;
            case KeyEvent.VK_DOWN:  intentarMover(bolaJugador2, 1, 0);  break;
            case KeyEvent.VK_LEFT:  intentarMover(bolaJugador2, 0, -1); break;
            case KeyEvent.VK_RIGHT: intentarMover(bolaJugador2, 0, 1);  break;
            default: break;
        }
    }

    @Override
    public void procesarTeclaLiberada(int e) {
        // no se usa en este juego
    }

    @Override
    public boolean haTerminado() {
        return terminado;
    }

    public JugadorBola getGanador() {
        return ganador;
    }

    @Override
    public boolean listoParaAvanzar() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}