/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package client;

/**
 *
 * @author Daniela F
 */
public class InputSanitizer {

    
    public static String sanitizeInput(String input) {
        if (input == null) return "";
     
        return input
            .replaceAll("[<>\"';/*]", "")
            .replaceAll("\\s{2,}", " ")
            .trim();
    }

 
    public static boolean isSafe(String input) {
        return !input.matches(".*[<>\"';].*");
    }
}

