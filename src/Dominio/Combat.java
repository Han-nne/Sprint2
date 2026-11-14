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
    
    private Tripulacion tripulacion1;
    private Tripulacion tripulacion2;
    private ConfiguracionCombat configuracionCombat;
    private CombatBarraJugador jugadorCombat1;
    private CombatBarraJugador jugadorCombat2;
    private CombatHUD hudCombat;
    private int tiempoRestanteCombat;
    private boolean partidaTerminadaCombat;
    private boolean continuarTorneo;
    private String mensajeResultado;
    private Timer temporizadorCombat;
    private BufferedImage fondo;

    
    public Combat(Tripulacion tripulacion1, Tripulacion tripulacion2, ConfiguracionCombat configuracionCombat) throws IOException {
        this.tripulacion1 = tripulacion1;
        this.tripulacion2 = tripulacion2;
        this.configuracionCombat = configuracionCombat;
        this.hudCombat = new CombatHUD();
        this.fondo = TileSetManagerCombat.cargarImagenFondoCombat();
        iniciarJuego();
    }

    
    public void iniciarJuego() {
        ArrayList<Integer> controlesJugador1 = new ArrayList<>();
        controlesJugador1.add(KeyEvent.VK_W);
        controlesJugador1.add(KeyEvent.VK_A);
        controlesJugador1.add(KeyEvent.VK_D);
        
        ArrayList<Integer> controlesJugador2 = new ArrayList<>();
        controlesJugador2.add(KeyEvent.VK_UP);
        controlesJugador2.add(KeyEvent.VK_RIGHT);
        controlesJugador2.add(KeyEvent.VK_LEFT);

        int valorInicial = getConfiguracionCombat().getValorInicialBarra();
        int longitudSecuencia = 4;
        
        this.jugadorCombat1 = new CombatBarraJugador(valorInicial, controlesJugador1, longitudSecuencia);
        this.jugadorCombat2 = new CombatBarraJugador(valorInicial, controlesJugador2, longitudSecuencia);
        
        this.tiempoRestanteCombat = 45;
        this.partidaTerminadaCombat = false;
        this.continuarTorneo = false;
        this.mensajeResultado = "";
        
        if (getTemporizadorCombat() != null) {
            getTemporizadorCombat().stop();
        }
        
        setTemporizadorCombat(new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!isPartidaTerminadaCombat()) {
                    setTiempoRestanteCombat(getTiempoRestanteCombat() - 1);
                    if (getTiempoRestanteCombat() <= 0) {
                        setTiempoRestanteCombat(0);
                        getTemporizadorCombat().stop();
                        finalizarRondaPorJugador();
                    }
                }
            }
        }));
         getTemporizadorCombat().start();
    }
    
    @Override
    public void actualizar(){
        
    }
  
    @Override
    public void procesarTeclaPresionada(int keyCode){
        if (isPartidaTerminadaCombat()) {
            setContinuarTorneo(true);
            return;
        }
        
        if (getConfiguracionCombat().isTecladoCompartido()) {
            if (getJugadorCombat1().perteneceTeclaJugador(keyCode)) {
                boolean completoLaSecuencia = getJugadorCombat1().procesarIntento(keyCode);
                if (completoLaSecuencia) {
                    getJugadorCombat2().falloSecuencia();
                }
            } else if (getJugadorCombat2().perteneceTeclaJugador(keyCode)) {
                boolean completoLaSecuencia = getJugadorCombat2().procesarIntento(keyCode);
                if (completoLaSecuencia) {
                    getJugadorCombat1().falloSecuencia();
                }
            }
            
            if (getJugadorCombat1().getBarraPoder() <= 0 || getJugadorCombat2().getBarraPoder() <= 0) {
                finalizarRondaPorJugador();
            }
        }
    }
    
    
    @Override
    public void procesarTeclaLiberada(int keyCode){
    }
    
    
    public void finalizarRondaPorJugador(){
        setPartidaTerminadaCombat(true);
        
        if (getTemporizadorCombat() != null) {
            getTemporizadorCombat().stop();
        }
        
        int poderJugador1 = getJugadorCombat1().getBarraPoder();
        int poderJugador2 = getJugadorCombat2().getBarraPoder();
        
        if (poderJugador1 > poderJugador2) {
            setMensajeResultado("El ganador es " + getTripulacion1().getNombreTripulacion());
        } else if (poderJugador2 > poderJugador1) {
            setMensajeResultado("El ganador es " + getTripulacion2().getNombreTripulacion());
        } else {
            setMensajeResultado("Empataron con " + poderJugador1 + " puntos");
        }
    }
    
    
    @Override
    public void dibujar(Graphics g){
        
        Rectangle limites = g.getClipBounds();
        int ancho = (limites != null && limites.width > 0) ? limites.width : 1280;
        int alto = (limites != null && limites.height > 0) ? limites.height : 720;
        
        if (getFondo() != null) {
            g.drawImage(getFondo(), 0, 0, ancho, alto, null);
        }
        
        
        if (getJugadorCombat1()!= null && getJugadorCombat2()!= null) {
            getHudCombat().dibujar(g, getJugadorCombat1(), getJugadorCombat2(), getTripulacion1().getNombreTripulacion(), getTripulacion2().getNombreTripulacion(), 
                getTiempoRestanteCombat(), isPartidaTerminadaCombat(), getMensajeResultado());
        }
        
    }
    
    
    //getters y setters
    public Tripulacion getTripulacion1() {
        return tripulacion1;
    }

    public void setTripulacion1(Tripulacion tripulacion1) {
        this.tripulacion1 = tripulacion1;
    }

    public Tripulacion getTripulacion2() {
        return tripulacion2;
    }

    public void setTripulacion2(Tripulacion tripulacion2) {
        this.tripulacion2 = tripulacion2;
    }

    public ConfiguracionCombat getConfiguracionCombat() {
        return configuracionCombat;
    }

    public void setConfiguracionCombat(ConfiguracionCombat configuracionCombat) {
        this.configuracionCombat = configuracionCombat;
    }

    public CombatBarraJugador getJugadorCombat1() {
        return jugadorCombat1;
    }

    public void setJugadorCombat1(CombatBarraJugador jugadorCombat1) {
        this.jugadorCombat1 = jugadorCombat1;
    }

    public CombatBarraJugador getJugadorCombat2() {
        return jugadorCombat2;
    }

    public void setJugadorCombat2(CombatBarraJugador jugadorCombat2) {
        this.jugadorCombat2 = jugadorCombat2;
    }

    public CombatHUD getHudCombat() {
        return hudCombat;
    }

    public void setHudCombat(CombatHUD hudCombat) {
        this.hudCombat = hudCombat;
    }

    public int getTiempoRestanteCombat() {
        return tiempoRestanteCombat;
    }

    public void setTiempoRestanteCombat(int tiempoRestanteCombat) {
        this.tiempoRestanteCombat = tiempoRestanteCombat;
    }

    public boolean isPartidaTerminadaCombat() {
        return partidaTerminadaCombat;
    }

    public void setPartidaTerminadaCombat(boolean partidaTerminadaCombat) {
        this.partidaTerminadaCombat = partidaTerminadaCombat;
    }

    public String getMensajeResultado() {
        return mensajeResultado;
    }

    public void setMensajeResultado(String mensajeResultado) {
        this.mensajeResultado = mensajeResultado;
    }

    public Timer getTemporizadorCombat() {
        return temporizadorCombat;
    }

    public void setTemporizadorCombat(Timer temporizadorCombat) {
        this.temporizadorCombat = temporizadorCombat;
    }   

    public boolean isContinuarTorneo() {
        return continuarTorneo;
    }

    public void setContinuarTorneo(boolean continuarTorneo) {
        this.continuarTorneo = continuarTorneo;
    }

    public BufferedImage getFondo() {
        return fondo;
    }

    public void setFondo(BufferedImage fondo) {
        this.fondo = fondo;
    }

    @Override
    public boolean haTerminado() {
        return isContinuarTorneo();
    }

    
    public boolean listoParaAvanzar() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
}
