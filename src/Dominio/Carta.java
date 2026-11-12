/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Dominio;

/**
 *  Representa una carta individual en Groggy Ring.
 * <p>
 *  Cada carta contiene un identificador de tipo, un nombre descriptivo, 
 *  una descripción textual y un valor numérico que determina la magnitud
 *  de su efecto (daño, protección o casillas de movimiento).
 * <p>
 * 
 * @author emmanuelarce
 * @see CartaTipo
 * @see GroggyRing
 */
public class Carta {
    
    /**Nombre identificativo de la carta. */
    private final String nombre;
    
    /**Categoría a la que pertenece la carta (Ataque, Defensa, Desplazamiento).*/
    private final CartaTipo tipo;
    
    /**Descripción breve del efecto de la carta.*/
    private final String descripcion;
    
    
    /**
     *  Construye una carta con sus atributos principales.
     * 
     * @param nombre nombre identificativo de la carta.
     * @param tipo categoría a la que pertenece {@link CartaTipo}.
     * @param descripcion explicación del efecto.
     */
    public Carta(String nombre, CartaTipo tipo, String descripcion) {
        this.nombre = nombre;
        this.tipo = tipo;
        this.descripcion = descripcion;
    }

    /**
     * Se obtiene el nombre de la carta.
     * @return nombre de la carta correspondiente.
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Se obtiene el tipo de carta (Ataque, Defensa, Desplazamiento).
     * @return tipo de la carta correspondiente.
     */
    public CartaTipo getTipo() {return tipo;}

    /**
     * Se obtiene la descripción textual de la carta.
     * @return información de la carta correspondiente.
     */
    public String getDescripcion() {return descripcion;}

    
    
} // fin clase
