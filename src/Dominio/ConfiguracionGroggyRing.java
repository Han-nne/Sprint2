/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Dominio;

/**
 * Clase hija que controla los parametros del minijuego Groggy Ring,
 *   heredando de la clase padre ConfiguracionGeneral.
 * 
 * @author Keithy Carrillo
 */
public class ConfiguracionGroggyRing extends ConfiguracionGeneral {
    
    private int puntuacionVictoria;
    
/**
 * Sobrescritura del metodo nivelFacil
 *  Define los valores de los parametros para el nivel facil del minijuego Groggy Ring
 * 
    */
    @Override
    public void nivelFacil() {
        this.puntuacionVictoria = 5;
    }
    
  /**
 * Sobrescritura del metodo nivelMedio
 *  Define los valores de los parametros para el nivel facil del minijuego Groggy Ring
 * 
    */
    @Override
    public void nivelNormal() {
        this.puntuacionVictoria = 10;
    }
    
    /**
 * Sobrescritura del metodo nivelDificil
 *  Define los valores de los parametros para el nivel facil del minijuego Groggy Ring
 * 
    */
    @Override
    public void nivelDificil() {
        this.puntuacionVictoria = 15;
    }
    
    /**
     * Obtiene la puntuacion de victoria configurada.
     * 
     * @return Retorna un número entero con la puntuacion de victoria.
     */
    public int getPuntuacionVictoria() {
        return puntuacionVictoria;
    }
    
} // fin de la clase