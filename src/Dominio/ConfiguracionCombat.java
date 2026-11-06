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
    
    private int valorInicialBarra;
    private boolean tecladoCompartido;

    
    /**
 * Sobrescritura del metodo nivelFacil
 *  Define los valores de los parametros para el nivel facil del minijuego Combat
 * 
    */
    @Override
    public void nivelFacil() {
        this.valorInicialBarra = 50;
        this.tecladoCompartido = true;
    }

    /**
 * Sobrescritura del metodo nivelMedio
 *  Define los valores de los parametros para el nivel facil del minijuego Combat
 * 
    */
    @Override
    public void nivelNormal() {
        this.valorInicialBarra = 75;
        this.tecladoCompartido = true;
    }

    /**
 * Sobrescritura del metodo nivelDificil
 *  Define los valores de los parametros para el nivel facil del minijuego Combat
 * 
    */
    @Override
    public void nivelDificil() {
        this.valorInicialBarra = 100;
        this.tecladoCompartido = true;
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
