/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Cliente;


import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataOutputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JButton;

/**
 *
 * @author fabio.montoya
 */
public class ThradEnviar implements Runnable {

    private final Ventana ventana;
    private final Socket conexion;
    private DataOutputStream salida;

    public final void enviarData(JButton boton, String num) {
        boton.addActionListener((ActionEvent ae) -> {
//            boton.setBackground(Color.blue);
            boton.setIcon(new ImageIcon(getClass().getResource("/imagenes/Circulo.png")));
            boton.setDisabledIcon(new ImageIcon(getClass().getResource("/imagenes/Circulo.png")));
            boton.setEnabled(false);
            try {
                salida.writeUTF(num);
                salida.flush();//garantiza el envio de datos
            } catch (IOException ex) {
                Logger.getLogger(ThradEnviar.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
    }

    public ThradEnviar(Ventana ventana, Socket conexion) {
        this.ventana = ventana;
        this.conexion = conexion;
        try {
            for (int i = 1; i < 10; i++) {
                Class clase = Class.forName("Cliente.Ventana");
                Field campo = clase.getDeclaredField("btn" + i);
                JButton boton = (JButton) campo.get(ventana);
                enviarData(boton, String.valueOf(i));
            }

        } catch (ClassNotFoundException | NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException ex) {
            Logger.getLogger(ThradEnviar.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void run() {
        try {
            salida = new DataOutputStream(conexion.getOutputStream());
        } catch (IOException ex) {
            Logger.getLogger(ThradEnviar.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
