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
public class CombatJugador {
    
    private int barraPoder;
    private int valorInicialBarra;
    private boolean primerIntento;
    private int longitudSecuencia;
    private ArrayList<Integer> secuenciaActual;
    private ArrayList<Integer> teclasPermitidas;
    private int indiceProgreso;
    private Random generadorAleatorio;

    public CombatJugador(int valorInicialBarra, ArrayList<Integer> teclasPermitidas, int longitudSecuencia) {
        this.valorInicialBarra = valorInicialBarra;
        this.barraPoder = valorInicialBarra;
        this.primerIntento = true;
        this.longitudSecuencia = longitudSecuencia;
        this.secuenciaActual = new ArrayList<>();
        this.teclasPermitidas = teclasPermitidas;
        this.indiceProgreso = 0;
        this.generadorAleatorio = new Random();
        generarNuevaSecuencia();
        
    }
 // getter y setters
    public int getBarraPoder() {
        return barraPoder;
    }

    public void setBarraPoder(int barraPoder) {
        this.barraPoder = barraPoder;
    }

    public boolean isPrimerIntento() {
        return primerIntento;
    }

    public void setPrimerIntento(boolean primerIntento) {
        this.primerIntento = primerIntento;
    }

    public int getLongitudSecuencia() {
        return longitudSecuencia;
    }

    public void setLongitudSecuencia(int longitudSecuencia) {
        this.longitudSecuencia = longitudSecuencia;
    }

    public ArrayList<Integer> getSecuenciaActual() {
        return secuenciaActual;
    }

    public void setSecuenciaActual(ArrayList<Integer> secuenciaActual) {
        this.secuenciaActual = secuenciaActual;
    }

    public ArrayList<Integer> getTeclasPermitidas() {
        return teclasPermitidas;
    }

    public void setTeclasPermitidas(ArrayList<Integer> teclasPermitidas) {
        this.teclasPermitidas = teclasPermitidas;
    }

    public int getIndiceProgreso() {
        return indiceProgreso;
    }

    public void setIndiceProgreso(int indiceProgreso) {
        this.indiceProgreso = indiceProgreso;
    }

    public Random getGeneradorAleatorio() {
        return generadorAleatorio;
    }

    public void setGeneradorAleatorio(Random generadorAleatorio) {
        this.generadorAleatorio = generadorAleatorio;
    }

    public int getValorInicialBarra() {
        return valorInicialBarra;
    }

    public void setValorInicialBarra(int valorInicialBarra) {
        this.valorInicialBarra = valorInicialBarra;
    }

    private void generarNuevaSecuencia() {
        getSecuenciaActual().clear();
        for (int i = 0; i < getLongitudSecuencia(); i++) {
            
            int indiceAleatorio = getGeneradorAleatorio().nextInt(getTeclasPermitidas().size());
            
            int teclaElegida = getTeclasPermitidas().get(indiceAleatorio);
            
            getSecuenciaActual().add(teclaElegida);
        } // fin de for
        
        setIndiceProgreso(0);
        
    }
    
    public boolean perteneceTeclaJugador(int keyCode){
        return getTeclasPermitidas().contains(keyCode);
    }
    
    public void procesarIntento(int keyCode){
        int teclaEsperada = getSecuenciaActual().get(getIndiceProgreso());
        
        if (keyCode == teclaEsperada) {
            setIndiceProgreso(getIndiceProgreso() + 1);
            
            if (getIndiceProgreso() >= getSecuenciaActual().size()) {
                aciertoSecuencia();
                generarNuevaSecuencia();
            } 
        } else {
            falloSecuencia();
            generarNuevaSecuencia();
        }

    }
    

    private void aciertoSecuencia(){
        if (isPrimerIntento()) {
            setBarraPoder(getValorInicialBarra());
            setPrimerIntento(false);
        } else {
            setBarraPoder(getBarraPoder() + 5);
        }
        
    }
    
    private void falloSecuencia() {
        setPrimerIntento(false);
        setBarraPoder(Math.max(0, getBarraPoder() - 5));
    }


}
