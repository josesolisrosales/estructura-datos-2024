package org.print3d.UI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginPanel extends JPanel {
    private App app;
    private JTextField userField;
    private JPasswordField passField;
    private JLabel messageLabel;

    public LoginPanel(App app) {
        this.app = app;
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);

        JLabel userLabel = new JLabel("Usuario:");
        userField = new JTextField(15);
        JLabel passLabel = new JLabel("Contraseña:");
        passField = new JPasswordField(15);
        JButton loginButton = new JButton("Iniciar sesión");
        messageLabel = new JLabel();

        gbc.gridx = 0;
        gbc.gridy = 0;
        add(userLabel, gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;
        add(userField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        add(passLabel, gbc);

        gbc.gridx = 1;
        gbc.gridy = 1;
        add(passField, gbc);

        gbc.gridx = 1;
        gbc.gridy = 2;
        add(loginButton, gbc);

        gbc.gridx = 1;
        gbc.gridy = 3;
        add(messageLabel, gbc);

        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = userField.getText();
                String password = new String(passField.getPassword());

                if (app.authenticateUser(username, password)) {
                    app.showMainInterface();
                } else {
                    messageLabel.setText("Credenciales inválidas. Intente nuevamente.");
                }
            }
        });
    }
}