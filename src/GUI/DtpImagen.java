/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package GUI;

import java.awt.Graphics;
import java.awt.Image;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JDesktopPane;

/**
 *
 * @author johan
 */
public class DtpImagen extends JDesktopPane {
  private Image imagenFondo;

    /**
     *
     */
    public DtpImagen() {
        try {
            this.imagenFondo = ImageIO.read(getClass().getResource("/assets/ventanaPrincipal.jpg"));
            
        } catch (IOException e) {
            System.err.println("Error al cargar la imagen de fondo: ");
        }
    }

    /**
     *
     * @param g
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        
        if (imagenFondo != null) {
            g.drawImage(imagenFondo, 0, 0, getWidth(), getHeight(), this);
        }
    }
}
