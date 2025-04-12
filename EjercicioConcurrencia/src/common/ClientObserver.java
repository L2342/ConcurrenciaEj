/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package common;
import java.util.List;
/**
 *
 * @author Daniela F
 */
public interface ClientObserver {
    void update(List<String> users); // Método que recibirá la lista actualizada de usuarios
}

