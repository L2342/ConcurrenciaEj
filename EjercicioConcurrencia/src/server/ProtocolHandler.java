/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package server;

import java.net.Socket;

/**
 *
 * @author samue
 */
public class ProtocolHandler {
     public void procesarInput(String input, UserManager Usermanager, Socket clientSocket){
         String[] tokens = input.split(" ",2);
         String comando = tokens[0];
         String response = "Error, Comando desconocido";
         
         switch(comando)
         {
             case "/login":
                 if(tokens.length>1){
                     String nombreUsuario = tokens[1];
                     
                 }
         }
     }
}
