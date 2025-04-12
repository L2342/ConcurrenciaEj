package server;

import java.io.IOException;
import java.net.Socket;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;


//Clase para gestionar los usuarios conectados al servidor.
public class UserManager {
    private ConcurrentHashMap<String, Socket> usuarios = new ConcurrentHashMap<>();
    public boolean agregarUsuario(String nombreUsuario, Socket socket) { //Agrega un usuario al sistema si el nombre no está en uso.
        if (!usuarios.containsKey(nombreUsuario)) {
            usuarios.put(nombreUsuario, socket);
            // Al agregar un nuevo usuario, notificar a todos los demás usuarios
            broadcast("Nuevo usuario conectado: " + nombreUsuario);
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
    public boolean contieneUsuario(String nombreUsuario) {
        return usuarios.containsKey(nombreUsuario);
    }
    public Set<String> getListaUsuarios() {
        return usuarios.keySet();
    }
    public boolean removerUsuarioPorSocket(String nombreUsuario, Socket socket) {
    String usuario = getUsuarioPorSocket(socket);
    if (usuario != null) {
        usuarios.remove(usuario);
        broadcast("El usuario " + nombreUsuario + " se ha desconectado.");
        return true;
    }
    return false;
}

// Método nuevo que solo requiere socket
public boolean removerUsuarioPorSocket(Socket socket) {
    String usuario = getUsuarioPorSocket(socket);
    if (usuario != null) {
        usuarios.remove(usuario);
        broadcast("El usuario " + usuario + " se ha desconectado.");
        return true;
    }
    return false;

    
}
}
