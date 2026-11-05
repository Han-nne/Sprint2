/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Dominio;

/**
 *
 *  Clase Padre abstracta que configura los parametros segun niveles de dificultad para los mini juegos del torneo.
 * 
 * @author Keithy Carrillo
 */
public class ConfiguracionGeneral {

    /**
     *
     */
    public ConfiguracionGeneral() {
    }
    
    /**
     * Configura los parámetros correspondientes al nivel facil.
     * Debe ser implementado por las clases hijas.
     */
    public void nivelFacil(){
        
    }
    
    /**
     * Configura los parámetros correspondientes al nivel medio.
     * Debe ser implementado por las clases hijas.
     */ 
    public void nivelNormal(){
        
    }
    
    /**
     * Configura los parámetros correspondientes al nivel dificil.
     * Debe ser implementado por las clases hijas.
     */
    public void nivelDificil(){
        
    }
    
    /**
     * Selecciona una nivel específico basándose en la opción elegida.
     * 
     * @param opcion es un número entero que representa el nivel de dificultad. 
     *  
     *  1 para Fácil, 2 para Medio y 3 para Difícil.
     */
    public void seleccionNivel(int opcion){
        switch (opcion) {
            case 1:
                nivelFacil();
                break;
            case 2:
                nivelNormal();
                break;
            case 3:
                nivelDificil();
                break;
            default:
                nivelFacil();
                break;
        }
 
    }
    
} // fin de clase
