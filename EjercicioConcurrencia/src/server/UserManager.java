package server;

import java.io.IOException;
import java.net.Socket;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import common.ClientObserver;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class UserManager {
    private ConcurrentHashMap<String, Socket> usuarios = new ConcurrentHashMap<>();
    private List<ClientObserver> observers = new CopyOnWriteArrayList<>();  // Lista de observadores

    // Método para agregar un nuevo usuario
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

    
    public void notificarListaUsuarios() {
        List<String> listaUsuarios = new ArrayList<>(getListaUsuarios());  
        for (ClientObserver observer : observers) {
            observer.update(listaUsuarios);  
        }
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
                e.printStackTrace();
            }
        });
    }

    public void removerUsuarioPorSocket(Socket clientsocket) {
        String usuario = getUsuarioPorSocket(clientsocket);
        if (usuario != null) {
            usuarios.remove(usuario);
            broadcast("El usuario " + usuario + " se ha desconectado.");
            notificarListaUsuarios();  
        }
    }

    public Set<String> getListaUsuarios() {
        return usuarios.keySet();
    }

    public void addObserver(ClientObserver observer) {
        observers.add(observer);
    }

    public void removeObserver(ClientObserver observer) {
        observers.remove(observer);
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

    public boolean contieneUsuario(String nombreUsuario) {
        return usuarios.containsKey(nombreUsuario);
    }

}
