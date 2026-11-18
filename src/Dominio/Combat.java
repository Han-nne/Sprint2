/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Dominio;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import javax.swing.Timer;
import utility.TileSetManagerCombat;

/**
 *
 * @author Keithy Carrillo
 */
public class Combat implements Juego {
    
    private final Tripulacion tripulacion1;
    private final Tripulacion tripulacion2;
    private final ConfiguracionCombat configuracionCombat;
    private final BufferedImage fondo;
    private final CombatHUD hudCombat;
    
    private CombatBarraJugador jugadorCombat1;
    private CombatBarraJugador jugadorCombat2;
    private Tripulacion ganador;
    private int tiempoRestanteCombat;
    private boolean partidaTerminadaCombat;
    private boolean continuarTorneo;
    private String mensajeResultado;
    private Timer temporizadorCombat;

    
    public Combat(Tripulacion tripulacion1, Tripulacion tripulacion2, ConfiguracionCombat configuracionCombat) throws IOException {
        this.tripulacion1 = tripulacion1;
        this.tripulacion2 = tripulacion2;
        this.configuracionCombat = configuracionCombat;
        this.hudCombat = new CombatHUD();
        this.fondo = TileSetManagerCombat.cargarImagenFondoCombat();
        iniciarJuego();
    }

    
    private void iniciarJuego() {
        ArrayList<Integer> controlesJugador1 = new ArrayList<>();
        controlesJugador1.add(KeyEvent.VK_W);
        controlesJugador1.add(KeyEvent.VK_A);
        controlesJugador1.add(KeyEvent.VK_D);
          
        ArrayList<Integer> controlesJugador2 = new ArrayList<>();
        controlesJugador2.add(KeyEvent.VK_UP);
        controlesJugador2.add(KeyEvent.VK_RIGHT);
        controlesJugador2.add(KeyEvent.VK_LEFT);

        int valorInicial = this.configuracionCombat.getValorInicialBarra();
        int longitudSecuencia = 4;
        
        this.jugadorCombat1 = new CombatBarraJugador(valorInicial, controlesJugador1, longitudSecuencia);
        this.jugadorCombat2 = new CombatBarraJugador(valorInicial, controlesJugador2, longitudSecuencia);
        
        this.ganador = null;
        this.tiempoRestanteCombat = 45;
        this.partidaTerminadaCombat = false;
        this.continuarTorneo = false;
        this.mensajeResultado = "";
        
        if (this.temporizadorCombat != null) {
            this.temporizadorCombat.stop();
        }
        
        this.temporizadorCombat = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!partidaTerminadaCombat) {
                    tiempoRestanteCombat--;
                    if (tiempoRestanteCombat <= 0) {
                        tiempoRestanteCombat = 0;
                        temporizadorCombat.stop();
                        finalizarRondaPorJugador();
                    }
                }
            }
        });
        this.temporizadorCombat.start();
    }
        
        
    @Override
    public void actualizar(){
        
    }
  
    @Override
    public void procesarTeclaPresionada(int keyCode){
        if (this.partidaTerminadaCombat) {
            this.continuarTorneo = true;
            return;
        }
        
        if (this.configuracionCombat.isTecladoCompartido()) {
            if (this.jugadorCombat1.perteneceTeclaJugador(keyCode)) {
                if (this.jugadorCombat1.procesarIntento(keyCode)) {
                    this.jugadorCombat2.falloSecuencia();
                }
            } else if (this.jugadorCombat2.perteneceTeclaJugador(keyCode)) {
                if (this.jugadorCombat2.procesarIntento(keyCode)) {
                    this.jugadorCombat1.falloSecuencia();
                }
            }
            
            
            if (this.jugadorCombat1.getBarraPoder() <= 0 || this.jugadorCombat2.getBarraPoder()<= 0) {
                finalizarRondaPorJugador();
            }
        }
    }
    
    
    @Override
    public void procesarTeclaLiberada(int keyCode){
    }
    
    
    public void finalizarRondaPorJugador(){
        this.partidaTerminadaCombat = true;
        
        if (this.temporizadorCombat != null) {
            this.temporizadorCombat.stop();
        }
        
        int poderJugador1 = this.jugadorCombat1.getBarraPoder();
        int poderJugador2 = this.jugadorCombat2.getBarraPoder();
        
        if (poderJugador1 > poderJugador2) {
            this.ganador = this.tripulacion1;
            this.mensajeResultado = "El ganador es " + this.tripulacion1.getNombreTripulacion();
        } else if (poderJugador2 > poderJugador1) {
            this.ganador = this.tripulacion2;
            this.mensajeResultado = "El ganador es " + this.tripulacion2.getNombreTripulacion();
        } else {
            this.ganador = null;
            this.mensajeResultado = "Empataron con " + poderJugador1 + " puntos";
        }
    }
    
    
    @Override
    public void dibujar(Graphics g){
        
        Rectangle limites = g.getClipBounds();
        int ancho = (limites != null && limites.width > 0) ? limites.width : 1280;
        int alto = (limites != null && limites.height > 0) ? limites.height : 720;
        
        if (this.fondo != null) {
            g.drawImage(this.fondo, 0, 0, ancho, alto, null);
        }
        
        
        if (this.jugadorCombat1!= null && this.jugadorCombat2!= null) {
            this.hudCombat.dibujar(g, 
                    this.jugadorCombat1, this.jugadorCombat2,
                    this.tripulacion1.getNombreTripulacion(),
                    this.tripulacion2.getNombreTripulacion(),
                    this.tiempoRestanteCombat, this.partidaTerminadaCombat,
                    this.mensajeResultado);
        }
        
    }
    
    @Override
    public boolean haTerminado() {
        return this.continuarTorneo;
    }

    
    public boolean listoParaAvanzar() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public Tripulacion getGanador() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
}
