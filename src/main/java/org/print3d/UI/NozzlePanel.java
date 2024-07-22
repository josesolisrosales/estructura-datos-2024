package org.print3d.UI;

import org.print3d.Objects.Nozzle;
import org.print3d.ObjectManager;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class NozzlePanel extends JPanel {
    private ObjectManager objectManager;
    private DefaultTableModel nozzleTableModel;
    private JTable table;

    public NozzlePanel(ObjectManager objectManager) {
        this.objectManager = objectManager;
        setLayout(new BorderLayout());

        createTable();
        createInputPanel();
    }

    private void createTable() {
        nozzleTableModel = new DefaultTableModel(
                new Object[]{"Material", "Diámetro (mm)"},
                0
        );
        table = new JTable(nozzleTableModel);
        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);
        updateNozzleTable();
    }

    private void createInputPanel() {
        JPanel inputPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(2, 2, 2, 2);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JTextField materialField = new JTextField(10);
        JTextField diameterField = new JTextField(5);

        gbc.gridx = 0;
        gbc.gridy = 0;
        inputPanel.add(new JLabel("Material:"), gbc);
        gbc.gridx = 1;
        inputPanel.add(materialField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        inputPanel.add(new JLabel("Diámetro (mm):"), gbc);
        gbc.gridx = 1;
        inputPanel.add(diameterField, gbc);

        JButton addButton = new JButton("Agregar");
        JButton removeButton = new JButton("Usar Boquilla");

        gbc.gridx = 0;
        gbc.gridy = 2;
        inputPanel.add(addButton, gbc);
        gbc.gridx = 1;
        inputPanel.add(removeButton, gbc);

        add(inputPanel, BorderLayout.SOUTH);

        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String material = materialField.getText();
                    float diameter = Float.parseFloat(diameterField.getText());

                    Nozzle newNozzle = new Nozzle(material, diameter);
                    objectManager.addNozzle(newNozzle);
                    updateNozzleTable();
                    clearInputFields(materialField, diameterField);
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(NozzlePanel.this, "Por favor, ingrese un valor numérico válido para el diámetro.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        removeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Nozzle removedNozzle = objectManager.removeNozzle();
                if (removedNozzle != null) {
                    updateNozzleTable();
                    JOptionPane.showMessageDialog(NozzlePanel.this, "Boquilla usada: " + removedNozzle.toString(), "Boquilla Usada", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(NozzlePanel.this, "No hay boquillas disponibles.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }

    private void updateNozzleTable() {
        nozzleTableModel.setRowCount(0);
        for (Nozzle nozzle : objectManager.getNozzles()) {
            nozzleTableModel.addRow(new Object[]{
                    nozzle.getMaterial(),
                    nozzle.getDiameter()
            });
        }
    }

    private void clearInputFields(JTextField... fields) {
        for (JTextField field : fields) {
            field.setText("");
        }
    }
}