/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package client;

/**
 *
 * @author Daniela F
 */

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class LoginWindow extends JFrame {
    private JTextField usernameField;
    private JButton loginButton;

    public LoginWindow() {
        setTitle("Login - Chat");
        setSize(300, 120);
        setLayout(new BorderLayout());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        usernameField = new JTextField();
        loginButton = new JButton("Entrar");

        add(new JLabel("Nombre de usuario:"), BorderLayout.NORTH);
        add(usernameField, BorderLayout.CENTER);
        add(loginButton, BorderLayout.SOUTH);

        loginButton.addActionListener(e -> {
        String nombreUsuario = usernameField.getText().trim();

        if (!InputSanitizer.isSafe(nombreUsuario)) {
            JOptionPane.showMessageDialog(this, "El nombre contiene caracteres no permitidos (como <, >, ;, etc).");
            return;
        }

        nombreUsuario = InputSanitizer.sanitizeInput(nombreUsuario);

        if (!nombreUsuario.isEmpty()) {
            try {
                new ClientApp(nombreUsuario);
                dispose(); // solo cerramos si fue exitoso
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(this, "Error: Nombre de usuario no disponible.");
            }
        } else {
            JOptionPane.showMessageDialog(this, "El nombre no puede estar vacío");
        }
    });
    
    }
}
