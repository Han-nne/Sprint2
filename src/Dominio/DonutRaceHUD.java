/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Dominio;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

/**
 * Clase encargada de renderizar el HUD (Heads-Up Display) específico del minijuego
 * {@code Donut Race}, mostrando en tiempo real la información de estado de cada embarcación.
 * <p>
 * Dibuja los paneles semitransparentes correspondientes a cada jugador, reflejando:
 * <ul>
 *   <li>La munición disponible frente a la munición total permitida por la dificultad.</li>
 *   <li>La velocidad de navegación actual (con formato de un decimal y separador de coma), 
 *       reflejando de forma dinámica los efectos de ralentización por colisiones.</li>
 * </ul>
 * </p>
 * <p>
 * Esta clase colabora  con los objetos {@link Barco} para consultar su estado.
 * </p>
 * 
 * @author emmanuelarce
 * @see Barco
 * @see DonutRace
 */
public class DonutRaceHUD {
    
    /**Barco del jugador 1, ubicado en el extremo izquierdo*/
    private final Barco barco1;
    
    /**Barco del jugador 2, ubicado en el extremo izquierdo*/
    private final Barco barco2;

    /**
     * Construye un renderizador de HUD para {@code Donut Race} asociado a las dos
     * embarcaciones en competencia.
     * <p>
     * No se validan nulos en la construcción; si alguno de los barcos es {@code null},
     * el método de dibujo se abortará de forma segura.
     * </p>
     * 
     * @param barco1 embarcación del jugador 1
     * @param barco2 embarcación del jugador 2
     */
    public DonutRaceHUD(Barco barco1, Barco barco2) {
        this.barco1 = barco1;
        this.barco2 = barco2;
    }
    
    /**
     * Dibuja los paneles de estado (munición y velocidad) en la parte superior del panel,
     * ubicados de manera simétrica y alineados con las banderas de cada tripulación.
     * <p>
     * La velocidad del barco se procesa matemáticamente para garantizar un único dígito 
     * decimal formateado con coma (ej: {@code 1,5x}).
     * </p>
     * 
     * @param g contexto gráfico sobre el cual se realiza el renderizado,
     *           obtenido desde el método {@code paintComponent} o el orquestador.
     */
    public void dibujar(Graphics g){
        if(this.barco1 == null || this.barco2 == null){
            return;
        }
        
        int posYHUD = 65;
        
        g.setFont(new  Font("Segoe UI", Font.BOLD, 12));
        
        double velocidadBarco1 = Math.round(barco1.getVelocidadEmbarcacion() * 10.0) / 10.0;
        String velocidadBarco1String = String.valueOf(velocidadBarco1).replace('.', ',');
        
        String barco1HUD = "Mun: " + barco1.getMunicionObstaculoLanzados()+ "/" + barco1.getMunicionTotalObstaculosLanzados()
                + "  |  Vel: " + velocidadBarco1String + "x";
        g.setColor(new Color(0,0,0,120));
        g.fillRoundRect(12, posYHUD, 175, 22, 8, 8);
        g.setColor(Color.CYAN);
        g.drawString(barco1HUD, 20, posYHUD + 15);
        
        double velocidadBarco2 = Math.round(barco2.getVelocidadEmbarcacion() * 10.0) / 10.0;
        String velocidadBarco2String = String.valueOf(velocidadBarco2).replace('.', ',');
        
        String barco2HUD = "Mun: " + barco2.getMunicionObstaculoLanzados()+ "/" + barco2.getMunicionTotalObstaculosLanzados()
                + "  |  Vel: " + velocidadBarco2String + "x";
        int posXBarco2HUD = 1280 - 12 - 175;
        g.setColor(new Color(0,0,0,120));
        g.fillRoundRect(posXBarco2HUD, posYHUD, 150, 22, 8, 8);
        g.setColor(Color.CYAN);
        g.drawString(barco2HUD, posXBarco2HUD + 8, posYHUD + 15);
    } //dibujar

    public Barco getBarco1() {
        return barco1;
    }

    public Barco getBarco2() {
        return barco2;
    }
    
} // fin clase
