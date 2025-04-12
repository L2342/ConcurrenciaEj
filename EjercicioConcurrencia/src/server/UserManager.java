/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package server;

import java.io.IOException;
import java.net.Socket;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
/**
 *
 * @author samue
 */



public class UserManager {
    private ConcurrentHashMap<String, Socket> usuarios = new ConcurrentHashMap<>();
    public boolean agregarUsuario(String nombreUsuario, Socket socket) {
        if (!usuarios.containsKey(nombreUsuario)) {
            usuarios.put(nombreUsuario, socket);
            broadcast("Nuevo usuario conectado: " + nombreUsuario);
            enviarListaUsuariosIndividual(socket);  
            notificarListaUsuarios();
            return true;
        }
        return false;
    }

    public String getUsuarioPorSocket(Socket socket) {
        for (var entry : usuarios.entrySet()) {
            if (entry.getValue().equals(socket)) {
                return entry.getKey();
            }
        }
        return null;
    }
    public void broadcast(String mensaje) {
        usuarios.values().forEach(socket -> {
            try {
                socket.getOutputStream().write((mensaje + "\n").getBytes());
                socket.getOutputStream().flush();
            } catch (IOException e) {
            }
        });
    }
    public boolean removerUsuarioPorSocket(String nombreUsuario, Socket socket) {
        String usuario = getUsuarioPorSocket(socket);
        if (usuario != null) {
            usuarios.remove(usuario);
            broadcast("El usuario " + nombreUsuario + " se ha desconectado.");
            notificarListaUsuarios(); 
            return true;
        }
        return false;
    }
    

    public boolean contieneUsuario(String nombreUsuario) {
        return usuarios.containsKey(nombreUsuario);
    }
    public Set<String> getListaUsuarios() {
        
        return usuarios.keySet();
    }
    
    public void notificarListaUsuarios() {
        String lista = String.join(",", getListaUsuarios());
        broadcast("/users " + lista); 
    }
    private void enviarListaUsuariosIndividual(Socket socket) {
    try {
        String lista = String.join(",", getListaUsuarios());
        socket.getOutputStream().write(("/users " + lista + "\n").getBytes());
        socket.getOutputStream().flush();
    } catch (IOException e) {
        e.printStackTrace();
    }
}

    public boolean removerUsuarioPorSocket(Socket socket) {
        String usuario = getUsuarioPorSocket(socket);
        if (usuario != null) {
            usuarios.remove(usuario);
            broadcast("El usuario " + usuario + " se ha desconectado.");
            notificarListaUsuarios(); 
            return true;
        }
        return false;
    }

}

