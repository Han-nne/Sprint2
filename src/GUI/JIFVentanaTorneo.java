package GUI;


import Dominio.ConfiguracionGeneral;
import Dominio.Torneo;
import Dominio.Tripulacion;
import Negocios.TripulacionNegocios;
import java.awt.Dimension;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JInternalFrame;
import javax.swing.JLayeredPane;
import javax.swing.SwingUtilities;
import javax.swing.event.InternalFrameAdapter;
import javax.swing.event.InternalFrameEvent;

/**
 * Ventana interna ({@link JInternalFrame}) que contiene el área del torneo.
 * <p>
 * Crea un {@link JPAreaTorneo} con las dos tripulaciones y la configuración
 * recibidas, y lo agrega como contenido. Se muestra dentro del escritorio de
 * {@link JFVentanaPrincipal}.
 * </p>
 * <p>
 * Sobre el área del torneo se coloca, oculto, un {@link JPFinTorneo} que se
 * hace visible cuando el torneo termina y ofrece jugar de nuevo, volver al
 * menú principal o salir del sistema. Al finalizar (y al elegir cualquiera de
 * esas opciones) se guardan los resultados de ambas tripulaciones mediante
 * {@link TripulacionNegocios}.
 * </p>
 *
 * @author johan
 * @see JPAreaTorneo
 * @see JPFinTorneo
 * @see JFVentanaPrincipal
 */
public class JIFVentanaTorneo extends JInternalFrame {

    /** Panel donde se juega el torneo. */
    private final JPAreaTorneo areaTorneo;

    /** Panel con el resultado final y las opciones; permanece oculto hasta que el torneo termina. */
    private final JPFinTorneo panelFinTorneo;

    /** Capa de negocios usada para guardar los resultados de las tripulaciones. */
    private final TripulacionNegocios tripulacionNegocios;

    /** Tripulación del jugador 1. */
    private final Tripulacion tripulacion1;

    /** Tripulación del jugador 2. */
    private final Tripulacion tripulacion2;

    /** Configuraciones de los minijuegos del torneo; se reutilizan al jugar de nuevo. */
    private final List<ConfiguracionGeneral> configuraciones;

    /**
     * Crea la ventana del torneo usando una nueva instancia de
     * {@link TripulacionNegocios}.
     *
     * @param tripulacion1 tripulación del jugador 1
     * @param tripulacion2 tripulación del jugador 2
     * @param configuraciones configuraciones de los minijuegos del torneo
     * @throws IOException si ocurre un error al cargar los recursos del torneo
     */
    public JIFVentanaTorneo(Tripulacion tripulacion1, Tripulacion tripulacion2,
            List<ConfiguracionGeneral> configuraciones) throws IOException {
        this(tripulacion1, tripulacion2, configuraciones, new TripulacionNegocios());
    }

    /**
     * Crea la ventana del torneo con la capa de negocios indicada.
     * <p>
     * La ventana no es redimensionable, maximizable ni minimizable, pero sí
     * se puede cerrar; al cerrarla se detiene el ciclo de juego.
     * </p>
     *
     * @param tripulacion1 tripulación del jugador 1
     * @param tripulacion2 tripulación del jugador 2
     * @param configuraciones configuraciones de los minijuegos del torneo
     * @param tripulacionNegocios capa de negocios para guardar los resultados;
     *                            si es {@code null} se crea una nueva
     * @throws IOException si ocurre un error al cargar los recursos del torneo
     */
    public JIFVentanaTorneo(Tripulacion tripulacion1, Tripulacion tripulacion2,
            List<ConfiguracionGeneral> configuraciones, TripulacionNegocios tripulacionNegocios) throws IOException {
        super("Torneo", false, true, false, false);

        this.tripulacion1 = tripulacion1;
        this.tripulacion2 = tripulacion2;
        this.configuraciones = configuraciones;
        this.tripulacionNegocios = tripulacionNegocios != null ? tripulacionNegocios : new TripulacionNegocios();

        JLayeredPane layeredPane = new JLayeredPane();
        layeredPane.setPreferredSize(new Dimension(1280, 720));

        this.areaTorneo = new JPAreaTorneo(tripulacion1, tripulacion2, configuraciones);
        this.areaTorneo.setOnTorneoFinalizado(this::alFinalizarTorneo);

        this.panelFinTorneo = new JPFinTorneo(tripulacion1, tripulacion2, 0, 0);
        this.panelFinTorneo.setVisible(false);

        configurarBotones();

        layeredPane.add(this.areaTorneo, JLayeredPane.DEFAULT_LAYER);
        layeredPane.add(this.panelFinTorneo, JLayeredPane.PALETTE_LAYER);

        this.setContentPane(layeredPane);
        this.pack();

        addInternalFrameListener(new InternalFrameAdapter() {
            @Override
            public void internalFrameClosing(InternalFrameEvent e) {
                areaTorneo.detenerTimer();
            }
        });
    }

    /**
     * Asigna las acciones de los botones del panel de fin de torneo:
     * <ul>
     *   <li><b>Jugar de nuevo:</b> guarda los resultados, oculta el panel y reinicia el torneo.</li>
     *   <li><b>Menú Principal:</b> guarda los resultados, detiene el juego y cierra la ventana.</li>
     *   <li><b>Salir sistema:</b> guarda los resultados, detiene el juego y termina la aplicación.</li>
     * </ul>
     */
    private void configurarBotones() {
        panelFinTorneo.getBtnJugarDeNuevo().addActionListener(e -> {
            guardarResultadosTorneo();
            panelFinTorneo.setVisible(false);
            try {
                areaTorneo.reiniciarTorneo(tripulacion1, tripulacion2, configuraciones);
            } catch (IOException ex) {
                Logger.getLogger(JIFVentanaTorneo.class.getName()).log(Level.SEVERE,
                        "Error al reiniciar el torneo", ex);
            }
        });

        panelFinTorneo.getBtnMenuPrincipal().addActionListener(e -> {
            guardarResultadosTorneo();
            areaTorneo.detenerTimer();
            dispose();
        });

        panelFinTorneo.getBtnSalirSistema().addActionListener(e -> {
            guardarResultadosTorneo();
            areaTorneo.detenerTimer();
            System.exit(0);
        });
    }

    /**
     * Se ejecuta cuando el torneo termina: guarda los resultados, actualiza el
     * marcador del {@link JPFinTorneo} con los puntos finales y lo muestra.
     */
    private void alFinalizarTorneo() {
        guardarResultadosTorneo();
        Torneo torneo = areaTorneo.getTorneo();
        panelFinTorneo.actualizarMarcador(
                torneo.getTripulacion1(),
                torneo.getTripulacion2(),
                torneo.getPuntos1(),
                torneo.getPuntos2()
        );
        panelFinTorneo.setVisible(true);
        panelFinTorneo.requestFocusInWindow();
        SwingUtilities.invokeLater(panelFinTorneo::repaint);
    }

    /**
     * Guarda en la capa de negocios el estado actual de las dos tripulaciones
     * del torneo. Si no hay torneo activo no hace nada; si ocurre un error de
     * E/S, se registra en el log.
     */
    private void guardarResultadosTorneo() {
        Torneo torneo = areaTorneo.getTorneo();
        if (torneo == null) {
            return;
        }
        try {
            this.tripulacionNegocios.actualizarTripulacion(torneo.getTripulacion1());
            this.tripulacionNegocios.actualizarTripulacion(torneo.getTripulacion2());
        } catch (IOException ex) {
            Logger.getLogger(JIFVentanaTorneo.class.getName()).log(Level.SEVERE,
                    "Error al actualizar las tripulaciones en la capa de negocios", ex);
        }
    }
}
