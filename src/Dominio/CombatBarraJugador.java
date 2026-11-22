/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Dominio;

import java.util.ArrayList;
import java.util.Random;

/**
 * Representa el estado de un jugador durante el minijuego de combate:
 * su barra de poder, la secuencia de teclas que debe acertar y el aviso
 * visual de error (calavera).
 * 
 * Cada jugador tiene un conjunto de teclas permitidas (por ejemplo W, A, D).
 * La clase genera una secuencia aleatoria de esas teclas y el jugador debe
 * presionarlas en orden:
 * 
 * Si presiona una tecla incorrecta, pierde 5 puntos de la barra, se
 *   activa la calavera durante 2 segundos y se genera una secuencia nueva. 
 * 
 * Si completa toda la secuencia, la barra sube (ver
 *       {@link #aciertoSecuencia()}), se resta 5 puntos al jugador contrario y se
 *       genera una secuencia nueva.
 * 
 * @author Keithy Carrillo
 * 
 * @see Combat
 * @see CombatHUD
 * 
 */
public class CombatBarraJugador {
    
    /** Tripulación a la que pertenece este jugador, recibe los puntos al 
     *  completar secuencias. Puede ser {@code null}. */
    private final Tripulacion tripulacion;
    /** Valor con el que la barra de poder empieza y al que vuelve en el 
     *  primer acierto limpio. */
    private final int valorInicialBarra;
    /** Cantidad de teclas que tiene cada secuencia. */
    private final int longitudSecuencia;
    /** Secuencia actual de códigos de tecla que el jugador debe presionar 
     *  en orden. */
    private final ArrayList<Integer> secuenciaActual;
    /** Códigos de tecla que pertenecen a este jugador y con los que se 
     *  arman las secuencias. */
    private final ArrayList<Integer> teclasPermitidas;
    /** Generador de números aleatorios usado para escoger las teclas de 
     *  la secuencia. */
    private final Random generadorAleatorio;
    
    /** Puntos actuales de la barra de poder (entre 0 y 100). */
    private int barraPoder;
    /** {@code true} mientras el jugador no haya fallado ni completado una 
     *  secuencia. */
    private boolean primerIntento;
    /** Posición dentro de la secuencia de la próxima tecla que se debe 
     *  presionar. */
    private int indiceProgreso;
    /** Segundos que le quedan a la calavera antes de ocultarse. */
    private int contadorCalavera;

    
    /**
     * Crea el estado de un jugador y genera su primera secuencia de teclas.
     *
     * @param tripulacion        tripulación del jugador, a la que se le suman puntos al acertar secuencias
     *                           (si es {@code null}, no se suman puntos)
     * @param valorInicialBarra  puntos con los que empieza la barra de poder
     * @param teclasPermitidas   códigos de tecla que pertenecen a este jugador
     * @param longitudSecuencia  cantidad de teclas de cada secuencia
     */
    public CombatBarraJugador(Tripulacion tripulacion, int valorInicialBarra, ArrayList<Integer> teclasPermitidas, int longitudSecuencia) {
        this.tripulacion = tripulacion;
        this.valorInicialBarra = valorInicialBarra;
        this.barraPoder = valorInicialBarra;
        this.primerIntento = true;
        this.longitudSecuencia = longitudSecuencia;
        this.secuenciaActual = new ArrayList<>();
        this.teclasPermitidas = teclasPermitidas;
        this.generadorAleatorio = new Random();
        this.contadorCalavera = 0;
        generarNuevaSecuencia();
    }
    
    /**
     * Genera una nueva secuencia aleatoria con las teclas permitidas y
     * reinicia el progreso a la primera tecla. La secuencia anterior se descarta.
     */
    private void generarNuevaSecuencia() {
        this.secuenciaActual.clear();
        for (int i = 0; i < this.longitudSecuencia; i++) {
            int indiceAleatorio = this.generadorAleatorio.nextInt(this.teclasPermitidas.size());
            this.secuenciaActual.add(this.teclasPermitidas.get(indiceAleatorio));
        } // fin de for
        this.indiceProgreso = 0;
    }
    
    /**
     * Indica si una tecla pertenece a este jugador. Sirve para saber a quién
     * corresponde cada pulsación cuando los dos jugadores usan el mismo teclado.
     *
     * @param keyCode código de la tecla presionada
     * @return {@code true} si la tecla está entre las teclas permitidas del jugador
     */
    public boolean perteneceTeclaJugador(int keyCode){
        return this.teclasPermitidas.contains(keyCode);
    }
    
     /**
     * Procesa una tecla presionada por el jugador contra la secuencia actual.
     * Tecla correcta: Avanza el progreso. Si era la última de la
     *       secuencia, llama a {@link #aciertoSecuencia()}, oculta la calavera
     *       y genera una secuencia nueva.
     * Tecla incorrecta: Marca que ya no es el primer intento,
     *       llama a {@link #falloSecuencia()} (resta puntos), activa la calavera
     *       por 2 segundos y genera una secuencia nueva.
     *
     * @param keyCode código de la tecla presionada
     * @return {@code true} únicamente si con esta tecla se completó la secuencia
     *         completa; {@code false} en cualquier otro caso (tecla intermedia o fallo)
     */
    public boolean procesarIntento(int keyCode){
        int teclaEsperada = this.secuenciaActual.get(this.indiceProgreso);
        
        if (keyCode == teclaEsperada) {
            this.indiceProgreso++;
            
            if (this.indiceProgreso == this.secuenciaActual.size()) {
                aciertoSecuencia();
                this.contadorCalavera = 0;
                generarNuevaSecuencia();
                return true;
            } 
            return false;
        } else {
            this.primerIntento = false;
            falloSecuencia();
            this.contadorCalavera = 2;
            generarNuevaSecuencia();
            return false;
        }
    }
    
     /**
     * Descuenta un segundo al contador de la calavera y la oculta cuando
     * llega a 0. Debe llamarse una vez por segundo (lo hace el temporizador
     * de {@link Combat}).
     */
    public void actualizarTiempoCalavera(){
        if (this.contadorCalavera > 0) {
            this.contadorCalavera --;
        }
    }

    /**
     * Actualiza la barra de poder y el puntaje cuando el jugador completa
     * una secuencia.
     * 
     *   Si es el primer intento (no ha fallado antes), la barra se deja en
     *       {@code valorInicialBarra} y se marca que ya no es el primer intento.
     *   En los demás casos la barra sube 5 puntos, con un máximo de 100.
     * 
     */
    private void aciertoSecuencia(){
        if (this.primerIntento) {
            this.barraPoder = this.valorInicialBarra;
            this.primerIntento = false;
        } else {
            this.barraPoder = Math.min(100, this.barraPoder + 5);
        }
        if(this.tripulacion != null){
            this.tripulacion.ajustarPuntaje(15);
        }
    }
    
    /**
     * Aplica la penalización por fallo: resta 5 puntos a la barra de poder sin
     * dejar que baje de 0, y marca que ya no es el primer intento.
     * 
     * Es pública porque {@link Combat} también la usa para penalizar a un
     * jugador cuando su rival completa una secuencia. En ese caso no se
     * activa la calavera, que solo aparece cuando el propio jugador se equivoca.
     * 
     */
    public void falloSecuencia() {
        this.primerIntento = false;
        this.barraPoder = Math.max(0, this.barraPoder - 5);
    }
    
    // Getter y setters
    /**
     * Indica si debe mostrarse la calavera de error junto al panel del jugador.
     *
     * @return {@code true} mientras queden segundos en el contador de la calavera
     */
    public boolean isMostrarCalavera() {
        return this.contadorCalavera > 0;
    }
    
    /**
     * Obtiene la tripulación asociada a este jugador.
     *
     * @return tripulación del jugador, o {@code null}
     */
    public Tripulacion getTripulacion() {
        return this.tripulacion;
    }

    /**
     * Obtiene el valor inicial configurado para la barra de poder.
     *
     * @return valor inicial de la barra
     */
    public int getValorInicialBarra() {
        return this.valorInicialBarra;
    }

    /**
     * Obtiene la cantidad de teclas que compone cada secuencia.
     *
     * @return longitud de la secuencia
     */
    public int getLongitudSecuencia() {
        return this.longitudSecuencia;
    }

    /**
     * Obtiene la secuencia actual de códigos de tecla.
     *
     * @return lista de códigos de tecla de la secuencia actual
     */
    public ArrayList<Integer> getSecuenciaActual() {
        return this.secuenciaActual;
    }

    /**
     * Obtiene las teclas permitidas para este jugador.
     *
     * @return lista de códigos de tecla permitidos
     */
    public ArrayList<Integer> getTeclasPermitidas() {
        return this.teclasPermitidas;
    }

    /**
     * Obtiene el generador aleatorio usado para crear las secuencias.
     *
     * @return generador aleatorio
     */
    public Random getGeneradorAleatorio() {
        return this.generadorAleatorio;
    }

    /**
     * Obtiene los puntos actuales de la barra de poder.
     *
     * @return puntos de poder (0 a 100)
     */
    public int getBarraPoder() {
        return this.barraPoder;
    }

    /**
     * Establece los puntos actuales de la barra de poder.
     *
     * @param barraPoder nuevos puntos de poder
     */
    public void setBarraPoder(int barraPoder) {
        this.barraPoder = barraPoder;
    }

    /**
     * Indica si el jugador sigue en su primer intento limpio.
     *
     * @return {@code true} si no ha fallado ni completado aún una secuencia
     */
    public boolean isPrimerIntento() {
        return this.primerIntento;
    }

    /**
     * Establece si el jugador sigue en su primer intento limpio.
     *
     * @param primerIntento nuevo estado de primer intento
     */
    public void setPrimerIntento(boolean primerIntento) {
        this.primerIntento = primerIntento;
    }

    /**
     * Obtiene el índice de la próxima tecla que debe presionar el jugador.
     *
     * @return posición actual dentro de la secuencia
     */
    public int getIndiceProgreso() {
        return this.indiceProgreso;
    }

    /**
     * Establece el índice de progreso dentro de la secuencia actual.
     *
     * @param indiceProgreso nueva posición de progreso
     */
    public void setIndiceProgreso(int indiceProgreso) {
        this.indiceProgreso = indiceProgreso;
    }

    /**
     * Obtiene los segundos restantes para mostrar la calavera de error.
     *
     * @return segundos restantes de la calavera
     */
    public int getContadorCalavera() {
        return this.contadorCalavera;
    }

    /**
     * Establece los segundos restantes para mostrar la calavera de error.
     *
     * @param contadorCalavera segundos de duración de la calavera
     */
    public void setContadorCalavera(int contadorCalavera) {
        this.contadorCalavera = contadorCalavera;
    }
    
    
}