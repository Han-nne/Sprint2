/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Dominio;

import java.awt.Graphics;

/**
 *
 * @author johan
 */
public class GroggyRing {
    
   private  Casilla zonaJuego[][];

    public GroggyRing() {
           this.zonaJuego=new Casilla[5][10];
           crearZonaJuego(0, 0);
    }
    
     public void dibujarZonaJuego(Graphics g){
        for (int i = 0; i < this.zonaJuego.length; i++) {
            for (int j = 0; j < this.zonaJuego.length; j++) {
                this.zonaJuego[i][j].dibujar(g);
            } // for j
        } // for i
    } // dibujar
     
     private void crearZonaJuego(int dx, int dy) {
        for (int i = 0; i < this.zonaJuego.length; i++) {
            for (int j = 0; j < this.zonaJuego[i].length; j++) {
                if ( i == 2 && ( j == 0 || j== 9 ) ){
                    this.zonaJuego[i][j] = new CasillaNeumatico(dx+j*128, dy+i*128);
                }else{
                    this.zonaJuego[i][j] = new CasillaCampo(dx+j*128, dy+i*128);
                }
            } // for j
        } // for i
    } // crearZonaJuego
   
      
}
