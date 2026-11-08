/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Dominio;

import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import javax.swing.Timer;

/**
 *
 * @author Keithy Carrillo
 */
public class Combat {
    
    private Tripulacion tripulacion1;
    private Tripulacion tripulacion2;
    private ConfiguracionCombat configuracionCombat;
    private CombatJugador jugador1;
    private CombatJugador jugador2;
    private CombatHUD hud;
    private int tiempoRestante;
    private boolean partidaTerminada;
    private String mensajeResultado;
    private Timer temporizador;
    

    public Combat(Tripulacion tripulacion1, Tripulacion tripulacion2, ConfiguracionCombat configuracionCombat) {
        this.tripulacion1 = tripulacion1;
        this.tripulacion2 = tripulacion2;
        this.configuracionCombat = configuracionCombat;
        iniciarMinijuego();
    }

    private void iniciarMinijuego() {
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
        
        this.jugador1 = new CombatJugador(valorInicial, controlesJugador1, longitudSecuencia);
        this.jugador2 = new CombatJugador(valorInicial, controlesJugador2, longitudSecuencia);
        
        this.tiempoRestante = 45;
        this.partidaTerminada = false;
        this.mensajeResultado = "";
        
        if (getTemporizador() != null) {
            getTemporizador().stop();
        }
        
        setTemporizador(new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!isPartidaTerminada()) {
                    setTiempoRestante(getTiempoRestante() - 1);
                    if (getTiempoRestante() <= 0) {
                        setTiempoRestante(0);
                        getTemporizador().stop();
                        finalizarRondaPorJugador();
                    }
                }
            }
        }));
         getTemporizador().start();
    }
             
    public void actualizar(){
        
    }
    
    public void procesarTeclaSeleccionada(int keyCode){
        if (isPartidaTerminada()) {
            return;
        }
        
        if (getConfiguracionCombat().isTecladoCompartido()) {
            if (getJugador1().perteneceTeclaJugador(keyCode)) {
                getJugador1().procesarIntento(keyCode);
            } else if (getJugador2().perteneceTeclaJugador(keyCode)) {
                getJugador2().procesarIntento(keyCode);
            }
        }
    }
    
    public void procesarTeclaLiberada(int keyCode){
        
    }
    
    public void finalizarRondaPorJugador(){
        setPartidaTerminada(true);
        
        if (getTemporizador() != null) {
            getTemporizador().stop();
        }
        
        int poderJugador1 = getJugador1().getBarraPoder();
        int poderJugador2 = getJugador2().getBarraPoder();
        
        if (poderJugador1 > poderJugador2) {
            setMensajeResultado("El ganador es " + getTripulacion1().getNombreTripulacion());
        } else if (poderJugador2 > poderJugador1) {
            setMensajeResultado("El ganador es " + getTripulacion2().getNombreTripulacion());
        } else {
            setMensajeResultado("Empataron con " + poderJugador1 + " puntos");
        }
    }
    
    public void dibujar(Graphics g){
        getHud().dibujar(g, getJugador1(), getJugador2(), getTripulacion1().getNombreTripulacion(), getTripulacion2().getNombreTripulacion(), 
                getTiempoRestante(), isPartidaTerminada(), getMensajeResultado());
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

    public CombatJugador getJugador1() {
        return jugador1;
    }

    public void setJugador1(CombatJugador jugador1) {
        this.jugador1 = jugador1;
    }

    public CombatJugador getJugador2() {
        return jugador2;
    }

    public void setJugador2(CombatJugador jugador2) {
        this.jugador2 = jugador2;
    }

    public CombatHUD getHud() {
        return hud;
    }

    public void setHud(CombatHUD hud) {
        this.hud = hud;
    }

    public int getTiempoRestante() {
        return tiempoRestante;
    }

    public void setTiempoRestante(int tiempoRestante) {
        this.tiempoRestante = tiempoRestante;
    }

    public boolean isPartidaTerminada() {
        return partidaTerminada;
    }

    public void setPartidaTerminada(boolean partidaTerminada) {
        this.partidaTerminada = partidaTerminada;
    }

    public String getMensajeResultado() {
        return mensajeResultado;
    }

    public void setMensajeResultado(String mensajeResultado) {
        this.mensajeResultado = mensajeResultado;
    }

    public Timer getTemporizador() {
        return temporizador;
    }

    public void setTemporizador(Timer temporizador) {
        this.temporizador = temporizador;
    }
    
    
}
