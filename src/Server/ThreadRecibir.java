/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Server;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author fabio.montoya
 */
public class ThreadRecibir implements Runnable{

    private final  Ventana ventana;
    private final Socket conexion;
    private DataInputStream entrada;
    private String msg;

    public ThreadRecibir(Ventana ventana, Socket conexion) {
        this.ventana = ventana;
        this.conexion = conexion;
    }
    @Override
    public void run() {
       
        try {
            entrada = new DataInputStream(conexion.getInputStream());
            while(true){
                msg = entrada.readUTF();
                this.ventana.pintarbuton(msg);
            
            }
        } catch (IOException ex) {
            Logger.getLogger(ThreadRecibir.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
