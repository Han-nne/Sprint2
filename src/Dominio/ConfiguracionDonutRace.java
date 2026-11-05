/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Dominio;

/**
 *  Clase hija que controla los parametros del minijuego Donut Race,
 *   heredando de la clase padre ConfiguracionGeneral.
 * 
 * @author Keithy Carrillo
 */
public class ConfiguracionDonutRace extends ConfiguracionGeneral{
    
    private int obstaculosFijos;
    private int obstaculosLanzados;
    private double velocidadBarcos;
/**
 *  Sobrescritura del metodo nivelFacil
 *  Define los valores de los parametros para el nivel facil del minijuego Donut Race
 */
    @Override
    public void nivelFacil() {
        this.obstaculosFijos = 7;
        this.obstaculosLanzados = 5;
        this.velocidadBarcos = 1.5;
    }
  
 /**
 *  Sobrescritura del metodo nivelMedio
 *  Define los valores de los parametros para el nivel medio del minijuego Donut Race
 */  
    @Override
    public void nivelNormal() {
        this.obstaculosFijos = 15;
        this.obstaculosLanzados = 7;
        this.velocidadBarcos = 2.0;
    }
    
  /**
 *  Sobrescritura del metodo nivelDificil
 *  Define los valores de los parametros para el nivel dificil del minijuego Donut Race
 */
    @Override
    public void nivelDificil() {
        this.obstaculosFijos = 20;
        this.obstaculosLanzados = 10;
        this.velocidadBarcos = 3.0;
    }

    /**
     * Obtiene la cantidad de obstáculos fijos configurados.
     * 
     * @return Retorna un número entero con los obstáculos fijos.
     */
    public int getObstaculosFijos() {
        return obstaculosFijos;
    }

    /**
     * Obtiene la cantidad de obstáculos lanzados configurados.
     * 
     * @return Retorna un número entero con los obstáculos lanzados.
     */
    public int getObstaculosLanzados() {
        return obstaculosLanzados;
    }

    /**
     * Obtiene la velocidad de los barcos configurados.
     * 
     * @return Retorna un valor double con la velocidad de los barcos.
     */
    public double getVelocidadBarcos() {
        return velocidadBarcos;
    }
    
} // fin de la clase
