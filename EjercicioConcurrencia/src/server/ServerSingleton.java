/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package server;

/**
 *
 * @author Samuel
 */
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
// Pille esta clase es pa poder implementar un servidor único que pueda aceptar múltiples conexiones.
public class ServerSingleton {
    private static ServerSingleton instancia;
    private ServerSocket serverSocket;
    private boolean isRunning = false;
    private UserManager userManager; 
    
    private ServerSingleton() {
        userManager = new UserManager();
    }

    public static ServerSingleton getInstancia() {
        if(instancia == null){
            instancia = new ServerSingleton();
        }
        return instancia;
    }
    public void startServer(int puerto) throws IOException{
        serverSocket  = new ServerSocket(puerto);
        isRunning = true;
        System.out.println("Servidor iniciado en el puerto " + puerto);
        while(isRunning){
            Socket clientSocket = serverSocket.accept();
            new Thread(new ClientHandler(clientSocket)).start();
        }
        
    }
    public void stopServer() throws IOException{
        isRunning = false;
        if(serverSocket!=null){
            serverSocket.close();
        }
    }
    public UserManager getUserManager() {
        return userManager;
    }
    
}
