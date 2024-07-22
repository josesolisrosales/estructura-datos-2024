package org.print3d.UI;

import org.print3d.Objects.FilamentType;
import org.print3d.ObjectManager;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class FilamentTypePanel extends JPanel {
    private ObjectManager objectManager;
    private DefaultTableModel filamentTypeTableModel;
    private JTable table;

    public FilamentTypePanel(ObjectManager objectManager) {
        this.objectManager = objectManager;
        setLayout(new BorderLayout());

        createTable();
        createInputPanel();
    }

    private void createTable() {
        filamentTypeTableModel = new DefaultTableModel(
                new Object[]{"Tipo", "Temp. Extrusión", "Temp. Cama", "Temp. Ambiente", "Temp. Secado", "Tiempo Secado", "Días entre Ciclos", "Velocidad Impresión"},
                0
        );
        table = new JTable(filamentTypeTableModel);
        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);
        updateFilamentTypeTable();
    }

    private void createInputPanel() {
        JPanel inputPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(2, 2, 2, 2);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JTextField typeField = new JTextField(10);
        JTextField extrusionTempField = new JTextField(5);
        JTextField bedTempField = new JTextField(5);
        JTextField ambientTempField = new JTextField(5);
        JTextField dryingTempField = new JTextField(5);
        JTextField dryingTimeField = new JTextField(5);
        JTextField daysBetweenCyclesField = new JTextField(5);
        JTextField printingSpeedField = new JTextField(5);

        // First row
        gbc.gridy = 0;
        gbc.gridx = 0;
        inputPanel.add(new JLabel("Tipo:"), gbc);
        gbc.gridx = 1;
        inputPanel.add(typeField, gbc);
        gbc.gridx = 2;
        inputPanel.add(new JLabel("Temp. Extrusión:"), gbc);
        gbc.gridx = 3;
        inputPanel.add(extrusionTempField, gbc);
        gbc.gridx = 4;
        inputPanel.add(new JLabel("Temp. Cama:"), gbc);
        gbc.gridx = 5;
        inputPanel.add(bedTempField, gbc);

        // Second row
        gbc.gridy = 1;
        gbc.gridx = 0;
        inputPanel.add(new JLabel("Temp. Ambiente:"), gbc);
        gbc.gridx = 1;
        inputPanel.add(ambientTempField, gbc);
        gbc.gridx = 2;
        inputPanel.add(new JLabel("Temp. Secado:"), gbc);
        gbc.gridx = 3;
        inputPanel.add(dryingTempField, gbc);
        gbc.gridx = 4;
        inputPanel.add(new JLabel("Tiempo Secado:"), gbc);
        gbc.gridx = 5;
        inputPanel.add(dryingTimeField, gbc);

        // Third row
        gbc.gridy = 2;
        gbc.gridx = 0;
        inputPanel.add(new JLabel("Días entre Ciclos:"), gbc);
        gbc.gridx = 1;
        inputPanel.add(daysBetweenCyclesField, gbc);
        gbc.gridx = 2;
        inputPanel.add(new JLabel("Velocidad Impresión:"), gbc);
        gbc.gridx = 3;
        inputPanel.add(printingSpeedField, gbc);

        JButton addButton = new JButton("Agregar");
        JButton removeButton = new JButton("Eliminar");

        gbc.gridx = 4;
        inputPanel.add(addButton, gbc);
        gbc.gridx = 5;
        inputPanel.add(removeButton, gbc);

        add(inputPanel, BorderLayout.SOUTH);

        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    FilamentType newType = new FilamentType(
                            typeField.getText(),
                            Double.parseDouble(extrusionTempField.getText()),
                            Double.parseDouble(bedTempField.getText()),
                            Double.parseDouble(dryingTempField.getText()),
                            Integer.parseInt(dryingTimeField.getText()),
                            Integer.parseInt(daysBetweenCyclesField.getText()),
                            Double.parseDouble(printingSpeedField.getText()),
                            ambientTempField.getText().isEmpty() ? null : Double.parseDouble(ambientTempField.getText())
                    );
                    objectManager.addFilamentType(newType);
                    updateFilamentTypeTable();
                    clearInputFields(typeField, extrusionTempField, bedTempField, ambientTempField,
                            dryingTempField, dryingTimeField, daysBetweenCyclesField, printingSpeedField);
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(FilamentTypePanel.this, "Por favor, ingrese valores numéricos válidos.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        removeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = table.getSelectedRow();
                if (selectedRow != -1) {
                    FilamentType typeToRemove = objectManager.getFilamentTypes().toArray(new FilamentType[0])[selectedRow];
                    objectManager.removeFilamentType(typeToRemove);
                    updateFilamentTypeTable();
                } else {
                    JOptionPane.showMessageDialog(FilamentTypePanel.this, "Por favor, seleccione un tipo de filamento para eliminar.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }


    private void updateFilamentTypeTable() {
        filamentTypeTableModel.setRowCount(0);
        for (FilamentType type : objectManager.getFilamentTypes().toArray(new FilamentType[0])) {
            filamentTypeTableModel.addRow(new Object[]{
                    type.getType(),
                    type.getExtrusionTemp(),
                    type.getBedTemp(),
                    type.getAmbientTemp(),
                    type.getDryingTemp(),
                    type.getDryingTime(),
                    type.getDaysBetweenDryCycles(),
                    type.getPrintingSpeed()
            });
        }
    }

    private void clearInputFields(JTextField... fields) {
        for (JTextField field : fields) {
            field.setText("");
        }
    }
}