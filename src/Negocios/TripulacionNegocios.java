/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Negocios;

import Datos.TripulacionDatos;
import Dominio.Tripulacion;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Capa de negocio para las {@link Tripulacion}es.
 * <p>
 * Aplica las reglas del sistema (por ejemplo, que no existan dos tripulaciones
 * con el mismo nombre) antes de delegar el guardado y la lectura en
 * {@link TripulacionDatos}.
 * </p>
 *
 * @author johan
 * @see TripulacionDatos
 * @see Tripulacion
 */
public class TripulacionNegocios {
 
    /** Acceso al archivo donde se guardan las tripulaciones. */
    private TripulacionDatos tripulacionDatos;
 
    /**
     * Construye la capa de negocio con su propio acceso a datos.
     */
    public TripulacionNegocios() {
        this.tripulacionDatos = new TripulacionDatos();
    }
 
    /**
     * Registra una tripulación nueva si su nombre no está en uso.
     *
     * @param tripulacion tripulación que se desea registrar
     * @return {@code true} si se registró; {@code false} si ya existe una
     *         tripulación con ese nombre
     * @throws IOException si ocurre un error al leer o escribir el archivo
     */
    public boolean registrarTripulacion(Tripulacion tripulacion) throws IOException {
        if (this.existeTripulacion(tripulacion.getNombreTripulacion())) {
            return false;
        }
        this.tripulacionDatos.registrarTripulacion(tripulacion);
        return true;
    }
 
    /**
     * Indica si ya existe una tripulación guardada con el nombre dado.
     *
     * @param nombre nombre de la tripulación que se desea buscar
     * @return {@code true} si hay una tripulación con ese nombre;
     *         {@code false} en caso contrario
     * @throws IOException si ocurre un error al leer el archivo
     */
    public boolean existeTripulacion(String nombre) throws IOException {
        for (Tripulacion tripulacionActual : this.listarTripulaciones()) {
            if (tripulacionActual.getNombreTripulacion().equals(nombre)) {
                return true;
            }
        }
        return false;
    }
 
    /**
     * Obtiene todas las tripulaciones guardadas.
     *
     * @return lista de tripulaciones, vacía si todavía no hay ninguna
     * @throws IOException si ocurre un error al leer el archivo
     */
    public ArrayList<Tripulacion> listarTripulaciones() throws IOException {
        return this.tripulacionDatos.listarTripulaciones();
    }
}