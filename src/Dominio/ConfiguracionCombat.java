/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Dominio;

/**
 * Clase hija que controla los parametros del minijuego Combat,
 *   heredando de la clase padre ConfiguracionGeneral.
 *
 * @author Keithy Carrillo
 */
public class ConfiguracionCombat extends ConfiguracionGeneral {
    private int tiempoRonda;
    private int valorInicialBarra;
    private boolean tecladoCompartido;

    
    /**
 * Sobrescritura del metodo nivelFacil
 *  Define los valores de los parametros para el nivel facil del minijuego Combat
 * 
    */
    @Override
    public void nivelFacil() {
        this.tiempoRonda = 60;
        this.valorInicialBarra = 100;
        this.tecladoCompartido = true;
    }

    /**
 * Sobrescritura del metodo nivelMedio
 *  Define los valores de los parametros para el nivel facil del minijuego Combat
 * 
    */
    @Override
    public void nivelNormal() {
        this.tiempoRonda = 45;
        this.valorInicialBarra = 100;
        this.tecladoCompartido = true;
    }

    /**
 * Sobrescritura del metodo nivelDificil
 *  Define los valores de los parametros para el nivel facil del minijuego Combat
 * 
    */
    @Override
    public void nivelDificil() {
        this.tiempoRonda = 30;
        this.valorInicialBarra = 100;
        this.tecladoCompartido = true;
    }

    /**
     * Obtiene el tiempo de la ronda configurado.
     * 
     * @return Retorna un número entero con el valor del tiempo de la ronda.
     */
    public int getTiempoRonda() {
        return tiempoRonda;
    }
    
    public void setTiempoRonda(int tiempoRonda) {    
        this.tiempoRonda = tiempoRonda;
    }

    /**
     * Obtiene el valor inicial de la barra de poder configurado.
     * 
     * @return Retorna un número entero con el valor inicial de la barra de poder.
     */
    public int getValorInicialBarra() {
        return valorInicialBarra;
    }

    /**
     * Obtiene el modo de juego configurado.
     * 
     * @return Retorna un valor boolean con el modo de juego.
     */
    public boolean isTecladoCompartido() {
        return tecladoCompartido;
    }

    public void setValorInicialBarra(int valorInicialBarra) {
        this.valorInicialBarra = valorInicialBarra;
    }

    public void setTecladoCompartido(boolean tecladoCompartido) {
        this.tecladoCompartido = tecladoCompartido;
    }

} // fin de clase
