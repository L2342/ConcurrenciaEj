/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package client;

/**
 *
 * @author Daniela F
 */

import java.awt.*;
import java.io.*;
import javax.swing.*;

public class ChatClientGUI extends JFrame {

    private JTextArea textArea1;
    private JTextField textField1;
    private JButton sendButton;
    private JList<String> jList1;
    private ClientApp clientApp;
    private String nombreUsuario;

    public ChatClientGUI(ClientApp clientApp, String nombreUsuario) {
        this.clientApp = clientApp;
        this.nombreUsuario = nombreUsuario;
        initComponents();
    }

    private void enviarMensaje() {
        String texto = textField1.getText().trim();

        if (!texto.isEmpty()) {

            // Validación OWASP 
            if (!InputSanitizer.isSafe(texto)) {
                JOptionPane.showMessageDialog(this, "El mensaje contiene caracteres peligrosos (<, >, ;, etc).");
                return;
            }

            texto = InputSanitizer.sanitizeInput(texto);

            String mensajeFormateado;
            if (texto.startsWith("/")) {
                mensajeFormateado = texto;
            } else {
                mensajeFormateado = "/msg " + nombreUsuario + ": " + texto;
            }

            clientApp.enviarMensaje(mensajeFormateado);
            textField1.setText("");
        }
    }


    private void initComponents() {
        setTitle("Cliente Chat - " + nombreUsuario);
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);

        textArea1 = new JTextArea();
        textArea1.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(textArea1);
        scrollPane.setBounds(150, 10, 420, 280);
        add(scrollPane);

        textField1 = new JTextField();
        textField1.setBounds(10, 300, 390, 30);
        add(textField1);

        sendButton = new JButton("Enviar");
        sendButton.setBounds(480, 300, 90, 29);
        add(sendButton);

        JButton disconnectButton = new JButton("Salir");
        disconnectButton.setBounds(150, 330, 100, 30);
        disconnectButton.addActionListener(e -> cerrarConexion());
        add(disconnectButton);

        jList1 = new JList<>();
        JScrollPane listScroll = new JScrollPane(jList1);
        listScroll.setBounds(10, 10, 130, 280);
        add(listScroll);

        sendButton.addActionListener(e -> enviarMensaje());
        textField1.addActionListener(e -> enviarMensaje());

        JButton btnEmojis = new JButton("☺");
        btnEmojis.setBounds(400, 300, 80, 29);
        add(btnEmojis);

        String[] emojis = {"😊", "😄", "🙁", "😭", "👋", "👍", "👎", "❤️", "💲", "🔥", "❌", "✔️"};
        JPanel emojiPanel = new JPanel(new GridLayout(3, 4, 6, 6));

        for (String emoji : emojis) {
            JButton emojiBtn = new JButton(emoji);
            emojiBtn.setFont(new Font("Emoji", Font.PLAIN, 10));
            emojiBtn.setFocusPainted(false);
            emojiBtn.setBorder(null);
            emojiBtn.setContentAreaFilled(false);

            emojiBtn.addActionListener(e -> textField1.setText(textField1.getText() + emoji));
            emojiPanel.add(emojiBtn);
        }

        JPopupMenu emojiMenu = new JPopupMenu();
        emojiMenu.setLayout(new BorderLayout());
        emojiMenu.add(emojiPanel, BorderLayout.CENTER);

        btnEmojis.addActionListener(e -> {
            emojiMenu.show(btnEmojis, 0, btnEmojis.getHeight());
        });
    }

    private void cerrarConexion() {
        clientApp.cerrarConexion(); 
        dispose();                  
        System.exit(0);             
    }

    public void mostrarMensaje(String mensaje) {
        if (mensaje.startsWith("/users ")) {
            String[] usuarios = mensaje.substring(8).split(",");
            SwingUtilities.invokeLater(() -> {
                DefaultListModel<String> model = new DefaultListModel<>();
                for (String usuario : usuarios) {
                    model.addElement(usuario.trim());
                }
                jList1.setModel(model);
            });
        } else {
            SwingUtilities.invokeLater(() -> {
                textArea1.append(mensaje + "\n");
            });
        }
        
    }
}
