/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Dominio;

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
    private Timer temporiador;
    

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

    public Timer getTemporiador() {
        return temporiador;
    }

    public void setTemporiador(Timer temporiador) {
        this.temporiador = temporiador;
    }
    
    
}
