/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Dominio;
 
/**
 * La enumeración {@code ColorBarco} define los colores disponibles para un
 * {@link Barco}.
 * <p>
 * El color determina qué sprites del tileset se cargan para el barco. En
 * {@link DonutRace}, el jugador 1 usa {@link #AZUL} y el jugador 2 usa
 * {@link #ROJO}.
 * </p>
 *
 * @author johan
 * @see Barco
 * @see DonutRace
 */
public enum ColorBarco {
 
    /** Barco azul (jugador 1). */
    AZUL,
 
    /** Barco rojo (jugador 2). */
    ROJO;
}
 