package org.print3d.UI;

import org.print3d.ObjectManager;
import org.print3d.User;

import javax.swing.*;
import java.awt.*;

public class App extends JFrame {
    private User adminUser = new User("admin", "admin");
    private LoginPanel loginPanel;
    private JTabbedPane tabbedPane;
    private ObjectManager objectManager;

    public App() {
        setTitle("3D Printing Inventory");
        setSize(1300, 800);
        setMinimumSize(new Dimension(1300, 800));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        objectManager = new ObjectManager();

        loginPanel = new LoginPanel(this);
        createTabbedPane();

        add(loginPanel);
    }

    private void createTabbedPane() {
        tabbedPane = new JTabbedPane();

        tabbedPane.addTab("Tipos de Filamento", new FilamentTypePanel(objectManager));
        tabbedPane.addTab("Filamentos", new FilamentPanel(objectManager));
        tabbedPane.addTab("Impresoras", new PrinterPanel(objectManager));
        tabbedPane.addTab("Boquillas", new NozzlePanel(objectManager));
        tabbedPane.addTab("Impresiones", new PrintPanel(objectManager));
    }

    private JPanel createTabPanel(String content) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.add(new JLabel(content), BorderLayout.CENTER);
        return panel;
    }

    public void showMainInterface() {
        getContentPane().remove(loginPanel);
        getContentPane().add(tabbedPane);
        revalidate();
        repaint();
    }

    public boolean authenticateUser(String username, String password) {
        return adminUser.authenticate(username, password);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new App().setVisible(true);
            }
        });
    }
}