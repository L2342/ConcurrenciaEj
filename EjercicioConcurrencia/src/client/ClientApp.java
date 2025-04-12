/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package client;

/**
 *
 * @author Daniela F
 */

import javax.swing.*;
import java.io.*;
import java.net.Socket;

public class ClientApp {
    private Socket socket;
    private BufferedReader input;
    private PrintWriter output;
    private ChatClientGUI gui;

    public ClientApp(String nombreUsuario) throws IOException {
        socket = new Socket("localhost", 12345); 
        input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        output = new PrintWriter(socket.getOutputStream(), true);

       
        output.println("/login " + nombreUsuario);

        
        String respuestaLogin = input.readLine();
        if (respuestaLogin != null && respuestaLogin.startsWith("Error: Nombre de usuario no disponible")) {
            socket.close(); 
            throw new IOException("Nombre de usuario no disponible.");
        }

        
        gui = new ChatClientGUI(this, nombreUsuario);
        gui.setVisible(true);
        gui.mostrarMensaje(respuestaLogin); // Mostramos la respuesta "OK: Te has logueado..."

        
        new Thread(() -> {
            String mensaje;
            try {
                while ((mensaje = input.readLine()) != null) {
                    System.out.println("DEBUG - Mensaje recibido: " + mensaje);
                    gui.mostrarMensaje(mensaje);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
    }

    public void enviarMensaje(String mensaje) {
        output.println(mensaje);
    }

    public void cerrarConexion() {
        try {
            output.println("/quit");
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            String nombreUsuario = JOptionPane.showInputDialog(null, "Ingresa tu nombre de usuario:");
            if (nombreUsuario != null && !nombreUsuario.trim().isEmpty()) {
                try {
                    new ClientApp(nombreUsuario);
                } catch (IOException e) {
                    JOptionPane.showMessageDialog(null, "Error al conectar al servidor: " + e.getMessage());
                }
            }
        });
    }
}

//hola
