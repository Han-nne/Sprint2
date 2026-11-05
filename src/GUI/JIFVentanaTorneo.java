package GUI;


import Dominio.ConfiguracionGeneral;
import Dominio.Tripulacion;
import java.io.IOException;
import java.util.List;
import javax.swing.JInternalFrame;

/**
 * Ventana interna ({@link JInternalFrame}) que contiene el área del torneo.
 * <p>
 * Crea un {@link JPAreaTorneo} con las dos tripulaciones y la configuración
 * recibidas, y lo agrega como contenido. Se muestra dentro del escritorio de
 * {@link JFVentanaPrincipal}.
 * </p>
 *
 * @author johan
 * @see JPAreaTorneo
 * @see JFVentanaPrincipal
 */
public class JIFVentanaTorneo extends JInternalFrame{

    /** Panel donde se desarrolla el torneo. */
    private JPAreaTorneo areaTorneo;

    /**
     * Construye la ventana del torneo y ajusta su tamaño al del área de juego.
     *
     * @param tripulacion1 tripulación del primer jugador
     * @param tripulacion2 tripulación del segundo jugador
     * @param configuraciones configuraciones de los minijuegos definidas por el
     *                        administrador
     * @throws IOException si ocurre un error al cargar los recursos del torneo
     */
    public JIFVentanaTorneo( Tripulacion tripulacion1 , Tripulacion tripulacion2 , List <ConfiguracionGeneral> configuraciones) throws IOException {
        this.areaTorneo=new JPAreaTorneo(tripulacion1 , tripulacion2 , configuraciones);
        this.add(this.areaTorneo);
        this.pack();
    }

}