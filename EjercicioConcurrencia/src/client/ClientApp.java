package client; 
import java.io.*;
import java.net.Socket;

public class ClientApp {
    public static void main(String[] args) {
        try {
            // Conecta al servidor en localhost y puerto 12345 (cambia según tu configuración)
            Socket socket = new Socket("localhost", 12345);
            System.out.println("Conectado al servidor.");

            // Streams para enviar y recibir mensajes
            BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter output = new PrintWriter(socket.getOutputStream(), true);

            // Hilo para leer mensajes del servidor
            Thread listener = new Thread(() -> {
                try {
                    String mensaje;
                    while ((mensaje = input.readLine()) != null) {
                        System.out.println("Servidor: " + mensaje);
                    }
                } catch (IOException e) {
                    System.out.println("Desconectado del servidor.");
                }
            });
            listener.start();

            // Hilo principal para enviar mensajes
            BufferedReader consoleInput = new BufferedReader(new InputStreamReader(System.in));
            String mensajeUsuario;
            System.out.println("Escribe tus comandos (por ejemplo, /login <nombre>):");
            while ((mensajeUsuario = consoleInput.readLine()) != null) {
                output.println(mensajeUsuario);
                if (mensajeUsuario.equalsIgnoreCase("/exit")) {
                    break; // Salir del cliente
                }
            }

            // Cierre de recursos
            socket.close();
            consoleInput.close();
            System.out.println("Cliente desconectado.");
        } catch (IOException e) {
            System.err.println("Error al conectar al servidor: " + e.getMessage());
        }
    }
}
