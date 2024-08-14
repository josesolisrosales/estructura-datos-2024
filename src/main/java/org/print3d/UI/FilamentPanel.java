package org.print3d.UI;

import org.print3d.Objects.Filament;
import org.print3d.Objects.FilamentType;
import org.print3d.ObjectManager;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class FilamentPanel extends JPanel {
    private ObjectManager objectManager;
    private DefaultTableModel filamentTableModel;
    private JTable table;
    private JComboBox<FilamentType> filamentTypeComboBox;

    public FilamentPanel(ObjectManager objectManager) {
        this.objectManager = objectManager;
        setLayout(new BorderLayout());

        createTable();
        createInputPanel();
    }

    private void createTable() {
        filamentTableModel = new DefaultTableModel(
                new Object[]{"Marca", "Tipo", "Color", "Diámetro", "Peso", "Fecha de Compra", "Próximo Secado"},
                0
        );
        table = new JTable(filamentTableModel);
        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);
        updateFilamentTable();
    }

    private void createInputPanel() {
        JPanel inputPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(2, 2, 2, 2);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JTextField brandField = new JTextField(10);
        filamentTypeComboBox = new JComboBox<>();
        JTextField colorField = new JTextField(10);
        JTextField diameterField = new JTextField(5);
        JTextField weightField = new JTextField(5);
        JTextField purchaseDateField = new JTextField(10);

        // First row
        gbc.gridy = 0;
        gbc.gridx = 0;
        inputPanel.add(new JLabel("Marca:"), gbc);
        gbc.gridx = 1;
        inputPanel.add(brandField, gbc);
        gbc.gridx = 2;
        inputPanel.add(new JLabel("Tipo:"), gbc);
        gbc.gridx = 3;
        inputPanel.add(filamentTypeComboBox, gbc);
        gbc.gridx = 4;
        inputPanel.add(new JLabel("Color:"), gbc);
        gbc.gridx = 5;
        inputPanel.add(colorField, gbc);

        // Second row
        gbc.gridy = 1;
        gbc.gridx = 0;
        inputPanel.add(new JLabel("Diámetro (mm):"), gbc);
        gbc.gridx = 1;
        inputPanel.add(diameterField, gbc);
        gbc.gridx = 2;
        inputPanel.add(new JLabel("Peso (kg):"), gbc);
        gbc.gridx = 3;
        inputPanel.add(weightField, gbc);
        gbc.gridx = 4;
        inputPanel.add(new JLabel("Fecha de Compra (YYYY-MM-DD):"), gbc);
        gbc.gridx = 5;
        inputPanel.add(purchaseDateField, gbc);

        JButton addButton = new JButton("Agregar");
        JButton removeButton = new JButton("Eliminar");

        // Third row
        gbc.gridy = 2;
        gbc.gridx = 4;
        inputPanel.add(addButton, gbc);
        gbc.gridx = 5;
        inputPanel.add(removeButton, gbc);

        add(inputPanel, BorderLayout.SOUTH);

        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    Filament newFilament = new Filament(
                            brandField.getText(),
                            (FilamentType) filamentTypeComboBox.getSelectedItem(),
                            colorField.getText(),
                            Float.parseFloat(diameterField.getText()),
                            Float.parseFloat(weightField.getText()),
                            LocalDate.parse(purchaseDateField.getText(), DateTimeFormatter.ISO_LOCAL_DATE)
                    );
                    objectManager.addFilament(newFilament);
                    updateFilamentTable();
                    clearInputFields(brandField, colorField, diameterField, weightField, purchaseDateField);
                    filamentTypeComboBox.setSelectedIndex(0);
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(FilamentPanel.this, "Por favor, ingrese valores numéricos válidos para el diámetro y el peso.", "Error", JOptionPane.ERROR_MESSAGE);
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(FilamentPanel.this, "Por favor, ingrese una fecha válida en formato YYYY-MM-DD.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        removeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = table.getSelectedRow();
                if (selectedRow != -1) {
                    Filament filamentToRemove = objectManager.getFilaments().toArray(new Filament[0])[selectedRow];
                    objectManager.removeFilament(filamentToRemove);
                    updateFilamentTable();
                } else {
                    JOptionPane.showMessageDialog(FilamentPanel.this, "Por favor, seleccione un filamento para eliminar.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        updateFilamentTypeComboBox();
    }

    private void updateFilamentTable() {
        filamentTableModel.setRowCount(0);
        for (Filament filament : objectManager.getFilaments().toArray(new Filament[0])) {
            filamentTableModel.addRow(new Object[]{
                    filament.getBrand(),
                    filament.getFilamentType(),
                    filament.getColor(),
                    filament.getDiameter(),
                    filament.getWeight(),
                    filament.getPurchaseDate(),
                    filament.getNextDryingCycle()
            });
        }
    }

    private void updateFilamentTypeComboBox() {
        filamentTypeComboBox.removeAllItems();
        for (FilamentType type : objectManager.getFilamentTypes().toArray(new FilamentType[0])) {
            filamentTypeComboBox.addItem(type);
        }
    }

    private void clearInputFields(JTextField... fields) {
        for (JTextField field : fields) {
            field.setText("");
        }
    }
}