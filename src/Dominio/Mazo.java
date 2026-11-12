/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Dominio;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Representa el mazo central de cartas en Groggy Ring.
 * <p>
 * Administra una colección de 52 cartas con una distribución equilibrada
 * entre ataques, defensas y desplazamientos. Utiliza {@link Collections#shuffle}
 * para garantizar la aleatoriedad y cuenta con un mecanismo de reciclaje automático
 * que reinicializa y bajara el mazo cuando este se vacía por completo.
 * <p>
 * 
 * @author emmanuelarce
 * @see Carta
 * @see CartaTipo
 */
public class Mazo {
    
    /** Lista interna que almacena las cartas activas.*/
    private final List<Carta> cartas;
    
    /**
     * Contruye un mazo nuevo, lo inicializa con 52 cartas
     * y aplica un barajado aleatorio inicial.
     */
    public Mazo(){
        this.cartas = new ArrayList<>();
        inicializarYBarajar();
    }
    
    
    /**
     * Llena el mazo con exactamente 52 cartas distribuidas en:
     * <ul>
     * <li> 20 cartas de Ataque (15 de daño)</li>
     * <li> 16 cartas de Defensa (protección contra daño)</li>
     * <li> 16 cartas de Desplazamiento (casilla extra de movimiento)</li>
     * </ul>
     * y aplica {@link Collections#shuffle} para desordenarlas.
     */
    public void inicializarYBarajar(){
        cartas.clear();
        
        for (int i = 0; i < 20; i++) {
            cartas.add(new Carta("Ataque Rápido", CartaTipo.ATAQUE, "Resta 15 de vida al rival.", 15));
        }
        
        for (int i = 0; i < 16; i++) {
            cartas.add(new Carta("Escudo Protecto", CartaTipo.DEFENSA, "Anula el daño del siguiente ataque.", 1));
        }
        
        for (int i = 0; i < 16; i++) {
            cartas.add(new Carta("Impulso Extra", CartaTipo.DESPLAZAMIENTO, "Suma una casilla de movimiento.", 1));
        }
        Collections.shuffle(cartas);
    }
    
    /**
     * Extrae y remueve la primera carta del mazo (estilo BlackJack).
     * <p>
     * Si el mazo se encuentra vacío tras el consumo de las 52 cartas, 
     * ejecuta automáticamente a lógica de reciclaje, rellenándolo y barajándolo de nuevo.
     * <p>
     * 
     * @return  La primera {@link Carta} disponible para robar.
     */
    public Carta robarCarta(){
        if(cartas.isEmpty()) inicializarYBarajar();
        return cartas.remove(0);
    }
    
    /**
     * Obtiene la cantidad de cartas que quedan en el mazo.
     * @return Número de cartas restantes.
     */
    public int getCartasRestantes(){
        return cartas.size();
    }
} // fin clase
