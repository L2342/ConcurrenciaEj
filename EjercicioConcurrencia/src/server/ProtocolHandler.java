

package server;

import java.net.Socket;

public class ProtocolHandler {
    
    public String procesarInput(String input, UserManager userManager, Socket clientSocket) {
        String[] tokens = input.split(" ", 2);
        String comando = tokens[0];


        String response = switch (comando) {
            case "/login" -> manejarLogin(tokens, userManager, clientSocket);
            case "/logout" -> manejarLogout(tokens, userManager, clientSocket);
            case "/msg" -> manejarMensaje(tokens, userManager);
            case "/users" -> {
                userManager.notificarListaUsuarios();
                yield ""; // 
            }
            default -> null; // <- Esto es clave: si no es comando, puede ser mensaje normal
        };
        
        

       
        if (response == null && !input.startsWith("/")) {
           
            userManager.broadcast(input);
            return null; 
        }

        return response;
    }
    private String manejarLogin(String[] tokens, UserManager userManager, Socket clientSocket) {
        if(tokens.length>1){
            String nombreUsuario = tokens[1].trim();
            if (userManager.contieneUsuario(nombreUsuario)) {
                return "Error: Nombre de usuario no disponible.";
            }else{
                userManager.agregarUsuario(nombreUsuario, clientSocket);
                return "OK: Te has logueado al servidor como " + nombreUsuario + ".";
            }
        }else{
            return "Error: Se requiere un nombre de usuario para ingresar al servidor.";
        }
    }
    private String manejarLogout(String[] tokens, UserManager userManager, Socket clientSocket) {
        if (tokens.length > 1) {
            String nombreUsuario = tokens[1].trim();
            userManager.removerUsuarioPorSocket(nombreUsuario, clientSocket);
            return "OK: Has salido del servidor.";
        } else {
            return "Error: Se requiere un nombre de usuario para salir.";
        }
    }
    private String manejarMensaje(String[] tokens, UserManager userManager) {
    if(tokens.length>1){
        String[] mensajeInput=tokens[1].split(":", 2); 
        if(mensajeInput.length>1){
            String autor = mensajeInput[0].trim();
            String mensaje = mensajeInput[1].trim();
            // Broadcast del mensaje
            String mensajeFormateado = autor + ": " + mensaje;
            userManager.broadcast(mensajeFormateado);
            return "[✔]";
        } else {
            return "Error con el formato de envio";
        }
    } else {
        return "Error: No se puede enviar un mensaje vacío.";
    }
    
}

}