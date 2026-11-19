package GUI;

import Dominio.ConfiguracionGeneral;
import Dominio.Torneo;
import Dominio.Tripulacion;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JPanel;
import javax.swing.Timer;

/**
 * Panel principal donde se desarrolla el torneo. Se encarga de conectar la
 * interfaz gráfica con la lógica de {@link Torneo}: ejecuta el ciclo de juego
 * con un {@link Timer} (aproximadamente 60 cuadros por segundo), dibuja el
 * estado actual y reenvía al torneo los eventos de teclado y de mouse.
 * <p>
 * Cuando el torneo termina, detiene el ciclo y notifica una única vez mediante
 * el {@link Runnable} configurado con {@link #setOnTorneoFinalizado(Runnable)}.
 * </p>
 *
 * @see Torneo
 */
public class JPAreaTorneo extends JPanel implements KeyListener, MouseListener {

    /** Torneo en curso, con toda la lógica y el dibujo de los minijuegos. */
    private Torneo torneo;

    /** Temporizador que avanza y repinta el juego en cada cuadro. */
    private Timer timer = null;

    /** Acción que se ejecuta una vez cuando el torneo finaliza. */
    private Runnable onTorneoFinalizado;

    /** Evita notificar más de una vez el fin del torneo. */
    private boolean notificacionEnviada = false;

    /**
     * Crea el panel, inicia un nuevo torneo y arranca el ciclo de juego.
     *
     * @param tripulacion1 tripulación del jugador 1
     * @param tripulacion2 tripulación del jugador 2
     * @param configuraciones configuraciones de los minijuegos que componen el torneo
     * @throws IOException si ocurre un error al cargar los recursos del torneo
     */
    public JPAreaTorneo(Tripulacion tripulacion1, Tripulacion tripulacion2, List<ConfiguracionGeneral> configuraciones) throws IOException {
        this.setPreferredSize(new Dimension(1280, 720));
        this.setBounds(0, 0, 1280, 720);
        this.addKeyListener(this);
        this.addMouseListener(this);

        this.torneo = new Torneo(tripulacion1, tripulacion2, configuraciones);
        timer = new Timer(16, e -> {
            try {
                this.torneo.actualizarJuego();
            } catch (IOException ex) {
                Logger.getLogger(JPAreaTorneo.class.getName()).log(Level.SEVERE, null, ex);
            }
            repaint();

            if (!notificacionEnviada && this.torneo.isFinalizado()) {
                notificacionEnviada = true;
                timer.stop();
                if (onTorneoFinalizado != null) {
                    onTorneoFinalizado.run();
                }
            }
        });

        timer.start();
        this.setFocusable(true);
        this.requestFocusInWindow();
    }

    /**
     * Descarta el torneo actual y comienza uno nuevo con los datos indicados.
     * También rehabilita la notificación de fin de torneo.
     *
     * @param tripulacion1 tripulación del jugador 1
     * @param tripulacion2 tripulación del jugador 2
     * @param configuraciones configuraciones de los minijuegos del nuevo torneo
     * @throws IOException si ocurre un error al cargar los recursos del torneo
     */
    public void reiniciarTorneo(Tripulacion tripulacion1, Tripulacion tripulacion2, List<ConfiguracionGeneral> configuraciones) throws IOException {
        timer.stop();
        this.torneo = new Torneo(tripulacion1, tripulacion2, configuraciones);
        this.notificacionEnviada = false;
        timer.start();
        this.setFocusable(true);
        this.requestFocusInWindow();
        repaint();
    }

    /**
     * Define la acción que se ejecuta cuando el torneo termina.
     *
     * @param onTorneoFinalizado acción a ejecutar; puede ser {@code null}
     */
    public void setOnTorneoFinalizado(Runnable onTorneoFinalizado) {
        this.onTorneoFinalizado = onTorneoFinalizado;
    }

    /**
     * Obtiene el torneo en curso.
     *
     * @return torneo actual
     */
    public Torneo getTorneo() {
        return torneo;
    }

    /**
     * Detiene el ciclo de juego (actualización y repintado).
     */
    public void detenerTimer() {
        timer.stop();
    }

    /**
     * Dibuja el estado actual del torneo.
     *
     * @param g contexto gráfico del componente
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (this.torneo != null) {
            this.torneo.dibujar(g);
        }
    }

    /**
     * No se utiliza.
     *
     * @param e evento de teclado
     */
    @Override
    public void keyTyped(KeyEvent e) {
    }

    /**
     * Reenvía al torneo la tecla que se acaba de presionar.
     *
     * @param e evento de teclado
     */
    @Override
    public void keyPressed(KeyEvent e) {
        if (torneo != null) {
            torneo.procesarTeclaPresionada(e.getKeyCode());
        }
    }

    /**
     * Reenvía al torneo la tecla que se acaba de soltar.
     *
     * @param e evento de teclado
     */
    @Override
    public void keyReleased(KeyEvent e) {
        if (torneo != null) {
            torneo.procesarTeclaLiberada(e.getKeyCode());
        }
    }

    /**
     * Reenvía al torneo las coordenadas del clic del mouse.
     *
     * @param e evento de mouse
     */
    @Override
    public void mouseClicked(MouseEvent e) {
        if (torneo != null) {
            try {
                torneo.procesarClic(e.getX(), e.getY());
            } catch (IOException ex) {
                Logger.getLogger(JPAreaTorneo.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    /**
     * No se utiliza.
     *
     * @param e evento de mouse
     */
    @Override
    public void mousePressed(MouseEvent e) {
    }

    /**
     * No se utiliza.
     *
     * @param e evento de mouse
     */
    @Override
    public void mouseReleased(MouseEvent e) {
    }

    /**
     * No se utiliza.
     *
     * @param e evento de mouse
     */
    @Override
    public void mouseEntered(MouseEvent e) {
    }

    /**
     * No se utiliza.
     *
     * @param e evento de mouse
     */
    @Override
    public void mouseExited(MouseEvent e) {
    }
}
