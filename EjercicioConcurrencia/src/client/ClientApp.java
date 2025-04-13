package client;

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

                    // Si es un mensaje de notificación de lista de usuarios
                    if (mensaje.contains("LISTA_USUARIOS:")) {
                        // Extraer la parte después de "LISTA_USUARIOS:"
                        String listaUsuarios = mensaje.substring(mensaje.indexOf("LISTA_USUARIOS:") + "LISTA_USUARIOS:".length());
                        String[] usuarios = listaUsuarios.split(",");

                        // Crear lista y notificar al observador (GUI)
                        java.util.List<String> listaUsuariosList = new java.util.ArrayList<>();
                        for (String usuario : usuarios) {
                            listaUsuariosList.add(usuario.trim());
                        }
                        gui.update(listaUsuariosList);
                    } else {
                        // Es un mensaje normal
                        gui.mostrarMensaje(mensaje);
                    }
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

//hola hola
