  /*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Dominio;


import static Dominio.ColorBarco.AZUL;
import static Dominio.ColorBarco.ROJO;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import static java.awt.event.KeyEvent.VK_A;
import static java.awt.event.KeyEvent.VK_D;
import static java.awt.event.KeyEvent.VK_DOWN;
import static java.awt.event.KeyEvent.VK_L;
import static java.awt.event.KeyEvent.VK_LEFT;
import static java.awt.event.KeyEvent.VK_R;
import static java.awt.event.KeyEvent.VK_RIGHT;
import static java.awt.event.KeyEvent.VK_S;
import static java.awt.event.KeyEvent.VK_UP;
import static java.awt.event.KeyEvent.VK_W;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.util.ArrayList;
import utility.TileSetManagerDonutRace;

/**
 * La clase {@code DonutRace} es la clase principal de la lógica del juego: crea
 * y coordina todos los elementos del escenario y centraliza el ciclo de dibujo,
 * actualización y manejo de teclado.
 * <p>
 * Contiene dos {@link Barco} (uno por jugador), la {@link Meta}, la
 * {@link ZonaSalida}, la {@link Isla} y una lista de {@link Obstaculo}. Todos los
 * objetos se registran en un {@link MotorColision}, que resuelve los choques en
 * cada actualización.
 * </p>
 * <p>
 * Controles: el jugador 1 usa las teclas W, A, S, D y el jugador 2 usa las
 * flechas del teclado.
 * </p>
 *
 * @author johan
 * @see Barco
 * @see Obstaculo
 * @see MotorColision
 */
public class DonutRace implements Juego{
    
   /** Imagen de fondo (el mar)  */
    private final BufferedImage imagen;
 
    /** Barco del jugador 1 (azul), controlado con W, A, S, D. */
    private final Barco barcoJugador1;
 
    /** Barco del jugador 2 (rojo), controlado con las flechas. */
    private final Barco barcoJugador2;
    
/**Bandera que determina si el juego concluyó o sigue en curso.*/
    private boolean juegoTerminado = false;
    
    /**Bandera booleana que determina si se debe avanzar en el torneo.
     * <li> false = DonutRace sigue dibujandose.
     * <li> true = DonutRace deja de dibujarse y se avanza en el torneo.
     */
    private boolean listoParaAvanzar = false;
    
    /**Mensaje a visualizar cuando se detemine el ganador.*/
    private String mensajeGanador = "";
    
    /** Pista de la carrera. */
    private final Pista pista;
 
    /** Meta de la carrera. */
    private final Meta meta;
 
    /** Zona de salida de los barcos. */
    private final ZonaSalida salida;
 
    /** Isla fija del escenario. */
    private final Isla isla;
 
    /** Motor encargado de detectar y resolver las colisiones entre objetos. */
    private final MotorColision motorColision;
    
    private final ConfiguracionDonutRace configuracionDonut;
    
    private final DonutRaceHUD donutRaceHUD;
 
    /** Obstáculos activos presentes en el escenario. */
    private final ArrayList <Obstaculo> obstaculos;
 
    /** Obstáculos lanzados activos presentes en el escenario. */
    private final ArrayList <ObstaculoLanzado> obstaculosLanzados;
    
    /**
     * Construye una partida con las dos tripulaciones indicadas. Crea los barcos
     * (velocidad 2.0; azul en (680, 570) y rojo en (680, 600)), la meta, la zona
     * de salida y la isla, y luego inicia el juego con {@link #iniciarJuego()}.
     *
     * @param tripulacion1 tripulación del jugador 1
     * @param tripulacion2 tripulación del jugador 2
     * @param configuracionDonutRace
     * @throws IOException si ocurre un error al cargar las imágenes de los objetos
     */
    public DonutRace( Tripulacion tripulacion1 , Tripulacion tripulacion2 , ConfiguracionDonutRace configuracionDonutRace ) throws IOException {
         
        this.configuracionDonut = configuracionDonutRace;
        this.imagen = TileSetManagerDonutRace.cargarImagenMar();
        
         
        this.pista = new Pista(635, 357);
        this.isla = new Isla(470, 230);
        this.meta = new Meta(595, 525);
        this.salida = new ZonaSalida(640, 525);
        
        this.barcoJugador1 = new Barco(tripulacion1, configuracionDonutRace.getVelocidadBarcos(),
                                                     680 , 570 , configuracionDonutRace.getObstaculosLanzados() , AZUL);
        this.barcoJugador2 = new Barco(tripulacion2, configuracionDonutRace.getVelocidadBarcos(),  
                                                     680 , 600 , configuracionDonutRace.getObstaculosLanzados(), ROJO);
 
        this.obstaculos = new ArrayList<>();
        this.obstaculosLanzados = new ArrayList<>();
        this.donutRaceHUD = new DonutRaceHUD(barcoJugador1, barcoJugador2);
        this.motorColision = new MotorColision();
 
        iniciarJuego();
 
    }
 
    /**
     * Inicia el juego: genera los obstáculos con la dificultad seleccionada y registra
     * todos los objetos en el motor de colisiones.
     *
     * @throws IOException si ocurre un error al cargar la imagen de los obstáculos
     */
   private void iniciarJuego() throws IOException{
       obstaculos.addAll(GeneradorObstaculo.generar(configuracionDonut.getObstaculosFijos()));
       registrarObjetosColisionable();
    }
 
    /**
     * Registra en el {@link MotorColision} los barcos, la meta, la zona de salida,
     * la isla y todos los obstáculos y obstáculos lanzados actuales, para que participen en la
     * detección de colisiones.
     */
    private void registrarObjetosColisionable(){
        this.motorColision.registrarObjeto(barcoJugador1);
        this.motorColision.registrarObjeto(barcoJugador2);
        this.motorColision.registrarObjeto(meta);
        this.motorColision.registrarObjeto(salida);
        this.motorColision.registrarObjeto(isla);
        for (Obstaculo obstaculo : obstaculos) {
            this.motorColision.registrarObjeto(obstaculo);
        }
        for (ObstaculoLanzado lanzado : obstaculosLanzados) {
            this.motorColision.registrarObjeto(lanzado);
        }
        
    } // registrarObjetosColisionable
 
    /**
     * Agrega un obstáculo a la lista del juego. Nota: no lo registra en el motor
     * de colisiones; para eso se debe usar {@link #registrarObjetosColisionable()}.
     *
     * @param obstaculo obstáculo que se agrega
     */
    public void AddObstaculos( Obstaculo obstaculo ){
        this.obstaculos.add(obstaculo);
    }
 
    /**
     * Elimina un obstáculo de la lista del juego. Nota: no lo quita del motor de
     * colisiones.
     *
     * @param ob obstáculo que se elimina
     */
    public void removeObstaculo(Obstaculo ob){
        this.obstaculos.remove(ob);
    }
 
    /**
     * Dibuja todos los elementos del juego: barcos, meta, zona de salida, isla, 
     * obstáculos y obstáculos lanzados.
     *
     * @param g contexto gráfico sobre el que se dibuja
     */
    @Override
    public void dibujar(Graphics g) {
       g.drawImage( this.imagen, 0 , 0 , 1280, 720 , null);
       isla.dibujar(g);
       meta.dibujar(g);
       salida.dibujar(g);
       pista.dibujar(g);
       barcoJugador1.dibujar(g);
       barcoJugador2.dibujar(g);
        for (Obstaculo obstaculo : obstaculos) {
            obstaculo.dibujar(g);
        }
        
        for (ObstaculoLanzado lanzado: obstaculosLanzados) {
            lanzado.dibujar(g);
        }
        
        this.donutRaceHUD.dibujar(g, juegoTerminado, mensajeGanador);
        
    } // dibujar
 
    /**
     * Actualiza el estado del juego en cada ciclo: mueve los barcos, resuelve las
     * colisiones con el {@link MotorColision} y elimina de la lista (y del motor)
     * los obstáculos que quedaron inactivos tras ser golpeados.
     */
    @Override
    public void actualizar(){
        if(juegoTerminado) return;
        barcoJugador1.actualizar();
        barcoJugador2.actualizar();
        
        barcoJugador1.limitarA(pista);
        barcoJugador2.limitarA(pista);
        
        for (ObstaculoLanzado lanzado : obstaculosLanzados) {
            lanzado.mover();
        }
        this.motorColision.resolverColision();
 
        obstaculos.removeIf((Obstaculo obstaculo) -> {
            if (!obstaculo.isActivo()) {
                this.motorColision.eliminarObjeto(obstaculo);
                return true;
            }
            return false;
        });
 
        obstaculosLanzados.removeIf((ObstaculoLanzado lanzado) -> {
            if (!lanzado.isActivo()) {
                this.motorColision.eliminarObjeto(lanzado);
                return true;
            }
            return false;
        });
    } // actualizar
 
    /**
     * Método dedicado a getionar los parámetros del obstáculo lanzado (móvil). 
     * <p>
     * Valida que el barco aún pueda lanzar obstáculos según {@link  lanzar} de {@code Barco}.
     * <p>
     * Se calcula su posición, dirección vertical y horizontal y velocidad de desplazamiento, 
     * se establece una imagen (25x25) distinta para diferenciarlos de un obstáculo fijo, y 
     * es añadido al ArrayList de Obstaculo.
     * @param barco barco que lanzó el obstáculo
     */
    private void lanzarObstaculos(Barco barco) {
    if (!barco.lanzar()) return;
    try {
        ObstaculoLanzado lanzado = LanzadorObstaculo.crear(barco);
        obstaculosLanzados.add(lanzado);
        motorColision.registrarObjeto(lanzado);
    } catch (IOException e) {
        throw new UncheckedIOException(e); 
    }
}

   /**
    * Procesa una tecla presionada y pone en movimiento el barco correspondiente o lanza un obstáculo:
    * W, A, S, D, R (disparo) para el jugador 1 y las flechas direccionales, CONTROL (Disparo) para el jugador 2. 
    * Las demás teclas se ignoran.
    *
    * @param e código de la tecla presionada (constante de {@link KeyEvent})
    */
    @Override
   public void procesarTeclaPresionada(int e) {
 
       if(juegoTerminado){
           if(e == KeyEvent.VK_SPACE) listoParaAvanzar = true;
           return;
       }
        switch (e) {
            case VK_W: barcoJugador1.moverAdelante(); break;
            case VK_S: barcoJugador1.moverAtras(); break;
            case VK_A: barcoJugador1.moverIzquierda(); break;
            case VK_D: barcoJugador1.moverDerecha(); break;
            case VK_R: lanzarObstaculos(barcoJugador1); break;
            case VK_UP: barcoJugador2.moverAdelante(); break;
            case VK_DOWN: barcoJugador2.moverAtras(); break;
            case VK_LEFT: barcoJugador2.moverIzquierda(); break;
            case VK_RIGHT: barcoJugador2.moverDerecha(); break;
            case VK_L: lanzarObstaculos(barcoJugador2); break;
            default: break;
        } //switch
    } //procesarTeclaPresionada
 
   /**
    * Procesa una tecla liberada y detiene el movimiento del barco en el eje
    * correspondiente (vertical para W, S, arriba y abajo; horizontal para A, D,
    * izquierda y derecha). Las demás teclas se ignoran.
    *
    * @param e código de la tecla liberada (constante de {@link KeyEvent})
    */
    @Override
   public void procesarTeclaLiberada(int e) {
 
        switch (e) {
            case VK_W: barcoJugador1.detenerVertical(); break;
            case VK_S: barcoJugador1.detenerVertical(); break;
            case VK_A: barcoJugador1.detenerHorizontal(); break;
            case VK_D: barcoJugador1.detenerHorizontal(); break;
            case VK_UP: barcoJugador2.detenerVertical(); break;
            case VK_DOWN: barcoJugador2.detenerVertical(); break;
            case VK_LEFT: barcoJugador2.detenerHorizontal();break;
            case VK_RIGHT: barcoJugador2.detenerHorizontal(); break;
            default: break;
        } //switch
    } //procesarTeclaLiberada

   /**
    * Determina si el juego ya tiene un ganador para avanzar con el torneo.
    * @return Retorna true para avanzar con el torneo o false para mantenerse en el juego en curso.
    */
    @Override
    public boolean haTerminado() {
      
        if(!juegoTerminado){
             boolean jugador1CruzoMeta = barcoJugador1.isCondicionVictoria();
             boolean jugador2CruzoMeta = barcoJugador2.isCondicionVictoria();
             
             if(jugador1CruzoMeta || jugador2CruzoMeta){
                determinarGanador(jugador1CruzoMeta, jugador2CruzoMeta);
                juegoTerminado = true;
            }
        }
       
        return juegoTerminado && listoParaAvanzar;
    } //haTerminado
 
    /**
     * Procesa los barcos que cruzaron la meta para determinar el ganador.
     * <p>
     * En caso de empate, se determina por la menor cantidad de obstáculos colisionados.
     * @param jugador1CruzoMeta Indica si el barco correspondiente cruzó o no la meta.
     * @param jugador2CruzoMeta Indica si el barco correspondiente cruzó o no la meta.
     */
    private void determinarGanador(boolean jugador1CruzoMeta, boolean jugador2CruzoMeta){
        String jugador1 = barcoJugador1.getTripulacion().getNombreTripulacion();
        String jugador2 = barcoJugador2.getTripulacion().getNombreTripulacion();
        
        if(jugador1CruzoMeta && !jugador2CruzoMeta){
            mensajeGanador = "Ganador: " + jugador1 + "!!";
        }else if (!jugador1CruzoMeta && jugador2CruzoMeta){
            mensajeGanador = "Ganador: " + jugador2 + "!!";
        }else{
            int colisionesJugador1 = barcoJugador1.getObstaculosColisionados();
            int colisionesJugador2 = barcoJugador2.getObstaculosColisionados();
            
            if(colisionesJugador1 < colisionesJugador2){
                mensajeGanador = "!!Empate en meta!! Ganador por menos choques: " + jugador1;
            }else if(colisionesJugador2 < colisionesJugador1){
                mensajeGanador = "!!Empate en meta!! Ganador por menos choques: " + jugador2;
            }else{
                mensajeGanador = "!!Empate absoluto!!";
            }
        }
        
    } //determinarGanador

} //fin clase 