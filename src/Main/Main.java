/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package Main;

import GUI.JFVentanaPrincipal;

/**
 *
 * @author johan
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
       java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                new JFVentanaPrincipal().setVisible(true);
            }
        });
    }
}
