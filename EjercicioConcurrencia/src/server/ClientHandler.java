/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 *
 * @author samue
 */
// esta clase es pa poder manejar cada cliente
public class ClientHandler implements Runnable {
    private final Socket clientSocket;
    private final ProtocolHandler protocolHandler = new ProtocolHandler();
    private final UserManager userManager = ServerSingleton.getInstancia().getUserManager();
    private String nombreUsuario;
    
    public ClientHandler(Socket clientSocket) {
        this.clientSocket = clientSocket;
    }
    @Override
    public void run() {
        try (BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
             PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true)) {

            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                String response = protocolHandler.procesarInput(inputLine, userManager, clientSocket);
                if (inputLine.startsWith("/login")) {
                    String[] tokens = inputLine.split(" ", 2);
                    if (tokens.length > 1) {
                        nombreUsuario = tokens[1].trim(); 
                    }
                }
                out.println(response);
            }
        } catch (IOException e) {
            System.err.println("Conexión con cliente perdida.");
        } finally {
            userManager.removerUsuarioPorSocket(nombreUsuario, clientSocket);
            try {
                clientSocket.close();
            } catch (IOException e) {
            }
        }
    }
}