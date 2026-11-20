/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Dominio;

import java.util.ArrayList;
import java.util.Random;

/**
 *
 * @author Keithy Carrillo
 */
public class CombatBarraJugador {
    
    private final int valorInicialBarra;
    private final int longitudSecuencia;
    private final ArrayList<Integer> secuenciaActual;
    private final ArrayList<Integer> teclasPermitidas;
    private final Random generadorAleatorio;
    
    private int barraPoder;
    private boolean primerIntento;
    private int indiceProgreso;
    private boolean mostrarCalavera;
    private int contadorCalavera;

    public CombatBarraJugador(int valorInicialBarra, ArrayList<Integer> teclasPermitidas, int longitudSecuencia) {
        this.valorInicialBarra = valorInicialBarra;
        this.barraPoder = valorInicialBarra;
        this.primerIntento = true;
        this.longitudSecuencia = longitudSecuencia;
        this.secuenciaActual = new ArrayList<>();
        this.teclasPermitidas = teclasPermitidas;
        this.generadorAleatorio = new Random();
        this.mostrarCalavera = false;
        this.contadorCalavera = 0;
        generarNuevaSecuencia();
    }
 // getter y setters
    public int getBarraPoder() {
        return barraPoder;
    }

    public ArrayList<Integer> getSecuenciaActual() {
        return secuenciaActual;
    }

    public int getIndiceProgreso() {
        return indiceProgreso;
    }

    public boolean isMostrarCalavera() {
        return mostrarCalavera;
    }
    
    
    private void generarNuevaSecuencia() {
        this.secuenciaActual.clear();
        for (int i = 0; i < this.longitudSecuencia; i++) {
            int indiceAleatorio = this.generadorAleatorio.nextInt(this.teclasPermitidas.size());
            this.secuenciaActual.add(this.teclasPermitidas.get(indiceAleatorio));
        } // fin de for
        this.indiceProgreso = 0;
    }
    
    public boolean perteneceTeclaJugador(int keyCode){
        return this.teclasPermitidas.contains(keyCode);
    }
    
    public boolean procesarIntento(int keyCode){
        int teclaEsperada = this.secuenciaActual.get(this.indiceProgreso);
        
        if (keyCode == teclaEsperada) {
            this.indiceProgreso++;
            
            if (this.indiceProgreso == this.secuenciaActual.size()) {
                aciertoSecuencia();
                this.mostrarCalavera = false;
                generarNuevaSecuencia();
                return true;
            } 
            return false;
        } else {
            this.primerIntento = false;
            falloSecuencia();
            this.mostrarCalavera = true;
            this.contadorCalavera = 2;
            generarNuevaSecuencia();
            return false;
        }
    }
    
    public void actualizarTiempoCalavera(){
        if (this.contadorCalavera > 0) {
            this.contadorCalavera --;
            if (this.contadorCalavera == 0) {
                this.mostrarCalavera = false;
            }
        }
    }

    private void aciertoSecuencia(){
        if (this.primerIntento) {
            this.barraPoder = this.valorInicialBarra;
            this.primerIntento = false;
        } else {
            this.barraPoder = Math.min(100, this.barraPoder + 5);
        }
        
    }
    
    public void falloSecuencia() {
        this.primerIntento = false;
        this.barraPoder = Math.max(0, this.barraPoder - 5);
    }
}