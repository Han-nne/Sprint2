/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package GUI;

import Dominio.ConfiguracionGeneral;
import Dominio.Torneo;
import Dominio.Tripulacion;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;
import java.util.List;
import javax.swing.JPanel;
import javax.swing.Timer;

/**
 * Panel gráfico donde se desarrolla el torneo. Dibuja el fondo marino y los
 * elementos del juego, y recibe el teclado para controlar a los jugadores.
 * <p>
 * Crea un {@link Torneo} con las dos tripulaciones y la configuración recibidas.
 * Un {@link Timer} de Swing, que se ejecuta cada 16 ms (aprox. 60 cuadros por
 * segundo), actualiza la lógica del torneo y vuelve a pintar el panel. Las teclas
 * presionadas y liberadas se reenvían al torneo, que las interpreta según el
 * minijuego en curso.
 * </p>
 * <p>
 * Tiene un tamaño preferido de 1280x720 píxeles y se muestra dentro de
 * {@link JIFVentanaTorneo}.
 * </p>
 *
 * @author johan
 * @see Torneo
 * @see JIFVentanaTorneo
 */
public class JPAreaTorneo extends JPanel implements KeyListener {

    /** Lógica del torneo: administra los minijuegos, sus estados y el dibujo. */
    private Torneo torneo ;
   
     /** Temporizador que actualiza el torneo y repinta el panel cada 16 ms. */
    private final Timer timer;
   
     /**
     * Construye el panel del torneo: define su tamaño y foco, registra el
     * teclado, carga la imagen de fondo, crea el {@link Torneo} e inicia el
     * temporizador del ciclo de juego.
     * <p>
     * Si ocurre un error de lectura al cargar los recursos, se muestra un mensaje
     * al usuario; en ese caso el panel queda sin inicializar por completo.
     * </p>
     *
     * @param tripulacion1 tripulación del primer jugador
     * @param tripulacion2 tripulación del segundo jugador
     * @param configuraciones configuraciones de los minijuegos definidas por el
     *                        administrador
     * @throws IOException declarada por el constructor; actualmente los errores de
     *                     lectura se capturan y se muestran en un mensaje
     */
    public JPAreaTorneo( Tripulacion tripulacion1 , Tripulacion tripulacion2 , List <ConfiguracionGeneral> configuraciones) throws IOException {
       
        this.setPreferredSize(new Dimension(1280, 720));
        this.addKeyListener(this);
        
        this.torneo= new Torneo(tripulacion1, tripulacion2 , configuraciones);
        timer = new Timer(16, e -> {
            this.torneo.actualizarJuego();
            repaint();
        });
        
        timer.start();
        this.setFocusable(true);
        this.requestFocusInWindow();
    }
    
//    public void detenerTimer(){
//        timer.stop();
//    }

      /**
     * Pinta el panel: primero la imagen de fondo ajustada al tamaño actual y
     * luego los elementos del torneo.
     *
     * @param g contexto gráfico sobre el que se dibuja
     */
    @Override
    protected void paintComponent(Graphics g) {
        this.torneo.dibujar(g);
        
    } // paintComponent

      /**
     * Evento de tecla escrita. No se usa en este panel.
     *
     * @param e evento de teclado
     */
    @Override
    public void keyTyped(KeyEvent e) {
        
    }

      /**
     * Reenvía al torneo el código de la tecla presionada.
     *
     * @param e evento de teclado de la tecla presionada
     */
    @Override
    public void keyPressed(KeyEvent e) {
        torneo.procesarTeclaPresionada(e.getKeyCode());
    }

     /**
     * Reenvía al torneo el código de la tecla liberada.
     *
     * @param e evento de teclado de la tecla liberada
     */
    @Override
    public void keyReleased(KeyEvent e) {
        torneo.procesarTeclaLiberada(e.getKeyCode());
    }
} //fin clase
