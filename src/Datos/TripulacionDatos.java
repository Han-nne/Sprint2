/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Datos;

import Dominio.Tripulacion;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import utility.GestorArchivo;

/**
 * Clase de acceso a datos para las {@link Tripulacion}es, que se guardan en un
 * archivo de texto cuya ruta está en {@link GestorArchivo#RUTATRIPULACIONES}.
 * <p>
 * Cada tripulación ocupa una línea del archivo, con tres campos separados por
 * {@code " ; "}:
 * </p>
 * <pre>
 * nombreTripulacion ; rutaImagen ; puntaje
 * </pre>
 * <p>
 * Por ejemplo:
 * </p>
 * <pre>
 * Los Piratas ; C:\Users\johan\banderas\piratas.png ; 12.5
 * </pre>
 * <p>
 * Se guarda la <b>ruta</b> de la imagen de la bandera y no la imagen en sí; al
 * leer el archivo, cada {@link Tripulacion} carga su bandera a partir de esa
 * ruta. Si la imagen ya no existe, la tripulación se lee igual pero sin
 * bandera.
 * </p>
 *
 * @author johan
 * @see Tripulacion
 * @see GestorArchivo
 */
public class TripulacionDatos {
 
    /** Separador de campos en cada línea: nombre ; rutaImagen ; puntaje. */
    private static final String SEPARADOR = " ; ";
 
    /**
     * Construye el objeto de acceso a datos de tripulaciones. No requiere
     * parámetros, pues la ubicación del archivo se toma de
     * {@link GestorArchivo#RUTATRIPULACIONES}.
     */
    public TripulacionDatos() {
    }
 
    /**
     * Agrega una tripulación al final del archivo.
     * <p>
     * Escribe el nombre, la ruta de la imagen de la bandera y el puntaje. Si el
     * archivo no existe se crea, y si ya tiene contenido la nueva tripulación se
     * escribe en una línea aparte. Este método no verifica si el nombre ya
     * existe; esa validación corresponde a quien lo llama.
     * </p>
     *
     * @param tripulacion tripulación que se desea guardar
     * @throws IOException si ocurre un error al escribir en el archivo
     */
    public void registrarTripulacion(Tripulacion tripulacion) throws IOException {
        File archivo = new File(GestorArchivo.RUTATRIPULACIONES);
        boolean tieneContenido = archivo.exists() && archivo.length() > 0;
 
        try (FileWriter fileWriter = new FileWriter(archivo, true)) {
            if (tieneContenido) {
                fileWriter.write("\n");
            }
            fileWriter.write(formatear(tripulacion));
        }
    }// registrarTripulacion
 
    /**
     * Lee todas las tripulaciones guardadas en el archivo, con su puntaje.
     * <p>
     * Las líneas vacías o con menos de tres campos se ignoran. Si el puntaje de
     * una línea no es un número válido, esa tripulación se carga con puntaje 0.
     * </p>
     *
     * @return lista con las tripulaciones leídas, o una lista vacía si el
     *         archivo no existe
     * @throws IOException si ocurre un error al leer el archivo
     */
    public ArrayList<Tripulacion> listarTripulaciones() throws IOException {
        ArrayList<Tripulacion> tripulaciones = new ArrayList<>();
 
        File archivo = new File(GestorArchivo.RUTATRIPULACIONES);
        if (!archivo.exists()) {
            return tripulaciones;
        }
 
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(archivo))) {
            String linea;
            while ((linea = bufferedReader.readLine()) != null) {
                if (linea.isBlank()) {
                    continue;
                }
                String[] datos = linea.split(SEPARADOR, 3);
                if (datos.length < 3) {
                    continue; // línea con formato incorrecto
                }
 
                Tripulacion tripulacion = new Tripulacion(datos[0].trim(), datos[1].trim());
                try {
                    tripulacion.setPuntaje(Double.parseDouble(datos[2].trim()));
                } catch (NumberFormatException ex) {
                    // puntaje dañado: se queda en 0
                }
                tripulaciones.add(tripulacion);
            }
        }
        return tripulaciones;
    }//listarTripulaciones
 
    /**
     * Actualiza el puntaje de una tripulación ya guardada.
     * <p>
     * Busca en el archivo la tripulación con el mismo nombre que la recibida y,
     * si la encuentra, le asigna el nuevo puntaje y reescribe el archivo
     * completo con {@link #rescribirArchivo(java.util.ArrayList)}. Solo se
     * actualiza el puntaje; el nombre y la bandera quedan como estaban.
     * </p>
     *
     * @param tripulacion tripulación con el nombre a buscar y el puntaje nuevo
     * @return {@code true} si se encontró y actualizó la tripulación;
     *         {@code false} si no existe ninguna con ese nombre
     * @throws IOException si ocurre un error al leer o escribir el archivo
     */
    public boolean actualizar(Tripulacion tripulacion) throws IOException {
        boolean encontrado = false;
        ArrayList<Tripulacion> tripulaciones = this.listarTripulaciones();
        for (Tripulacion tripulacionActual : tripulaciones) {
            if (tripulacionActual.getNombreTripulacion().equals(tripulacion.getNombreTripulacion())) {
                tripulacionActual.setPuntaje(tripulacion.getPuntaje());
                encontrado = true;
                break;
            }
        }
        if (encontrado) {
            this.rescribirArchivo(tripulaciones);
        }
        return encontrado;
    }
 
    /**
     * Reemplaza el contenido del archivo por la lista de tripulaciones
     * recibida, una por línea y sin línea vacía al final.
     *
     * @param tripulaciones tripulaciones que quedarán guardadas en el archivo
     * @throws IOException si ocurre un error al escribir en el archivo
     */
    private void rescribirArchivo(ArrayList<Tripulacion> tripulaciones) throws IOException {
        File archivo = new File(GestorArchivo.RUTATRIPULACIONES);
 
        try (FileWriter fileWriter = new FileWriter(archivo)) {
            for (int i = 0; i < tripulaciones.size(); i++) {
                fileWriter.write(formatear(tripulaciones.get(i)));
                if (i < tripulaciones.size() - 1) {
                    fileWriter.write("\n");
                }
            }
        } // el close() ocurre UNA vez, al terminar el for
    }
 
    /**
     * Construye la línea de texto de una tripulación con el formato
     * {@code nombre ; rutaImagen ; puntaje}.
     *
     * @param t tripulación que se desea convertir a texto
     * @return la línea lista para escribirse en el archivo, sin salto de línea
     */
    private String formatear(Tripulacion t) {
        return t.getNombreTripulacion() + SEPARADOR
                + t.getRutaImagen() + SEPARADOR
                + t.getPuntaje();
    }
}
