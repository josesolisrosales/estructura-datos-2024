package org.print3d.UI;

import org.print3d.Objects.FilamentType;
import org.print3d.ObjectManager;
import org.print3d.Objects.Printer;

import org.print3d.DataStructures.DoubleLinkedList;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PrinterPanel extends JPanel {
    private ObjectManager objectManager;
    private DefaultTableModel printerTableModel;
    private JTable table;
    private JComboBox<String> stateComboBox;

    public PrinterPanel(ObjectManager objectManager) {
        this.objectManager = objectManager;
        setLayout(new BorderLayout());

        createTable();
        createInputPanel();
    }

    private void createTable() {
        printerTableModel = new DefaultTableModel(
                new Object[]{"Marca", "Tipos de Filamento Compatibles", "Dimensiones (mm)", "Estado"},
                0
        );
        table = new JTable(printerTableModel);
        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);
        updatePrinterTable();
    }

    private void createInputPanel() {
        JPanel inputPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(2, 2, 2, 2);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JTextField brandField = new JTextField(10);
        JList<FilamentType> filamentTypeList = new JList<>(objectManager.getFilamentTypes().toArray(new FilamentType[0]));
        filamentTypeList.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        JScrollPane filamentTypeScrollPane = new JScrollPane(filamentTypeList);
        JTextField dimensionXField = new JTextField(5);
        JTextField dimensionYField = new JTextField(5);
        JTextField dimensionZField = new JTextField(5);
        stateComboBox = new JComboBox<>(new String[]{"libre", "ocupada", "inoperable"});

        gbc.gridx = 0;
        gbc.gridy = 0;
        inputPanel.add(new JLabel("Marca:"), gbc);
        gbc.gridx = 1;
        inputPanel.add(brandField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        inputPanel.add(new JLabel("Tipos de Filamento:"), gbc);
        gbc.gridx = 1;
        gbc.gridheight = 2;
        inputPanel.add(filamentTypeScrollPane, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridheight = 1;
        inputPanel.add(new JLabel("Dimensiones (X/Y/Z):"), gbc);
        JPanel dimensionPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 0));
        dimensionPanel.add(dimensionXField);
        dimensionPanel.add(dimensionYField);
        dimensionPanel.add(dimensionZField);
        gbc.gridx = 1;
        inputPanel.add(dimensionPanel, gbc);

        gbc.gridx = 0;
        gbc.gridy = 4;
        inputPanel.add(new JLabel("Estado:"), gbc);
        gbc.gridx = 1;
        inputPanel.add(stateComboBox, gbc);

        JButton addButton = new JButton("Agregar");
        JButton removeButton = new JButton("Eliminar");

        gbc.gridx = 0;
        gbc.gridy = 5;
        inputPanel.add(addButton, gbc);
        gbc.gridx = 1;
        inputPanel.add(removeButton, gbc);

        add(inputPanel, BorderLayout.SOUTH);

        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String brand = brandField.getText();
                    DoubleLinkedList<FilamentType> selectedTypes = new DoubleLinkedList<>();
                    for (FilamentType type : filamentTypeList.getSelectedValuesList()) {
                        selectedTypes.add(type);
                    }
                    int dimensionX = Integer.parseInt(dimensionXField.getText());
                    int dimensionY = Integer.parseInt(dimensionYField.getText());
                    int dimensionZ = Integer.parseInt(dimensionZField.getText());
                    String state = (String) stateComboBox.getSelectedItem();

                    Printer newPrinter = new Printer(brand, selectedTypes, dimensionX, dimensionY, dimensionZ, state);
                    objectManager.addPrinter(newPrinter);
                    updatePrinterTable();
                    clearInputFields(brandField, dimensionXField, dimensionYField, dimensionZField);
                    filamentTypeList.clearSelection();
                    stateComboBox.setSelectedIndex(0);
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(PrinterPanel.this, "Por favor, ingrese valores numéricos válidos para las dimensiones.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        removeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Printer removedPrinter = objectManager.removePrinter();
                if (removedPrinter != null) {
                    updatePrinterTable();
                    JOptionPane.showMessageDialog(PrinterPanel.this, "Impresora eliminada: " + removedPrinter.getBrand(), "Impresora Eliminada", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(PrinterPanel.this, "No hay impresoras para eliminar.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }

    private void updatePrinterTable() {
        printerTableModel.setRowCount(0);
        for (Printer printer : objectManager.getPrinters()) {
            printerTableModel.addRow(new Object[]{
                    printer.getBrand(),
                    printer.getCompatibleFilamentTypesString(),
                    printer.getDimensionX() + "x" + printer.getDimensionY() + "x" + printer.getDimensionZ(),
                    printer.getState()
            });
        }
    }

    private void clearInputFields(JTextField... fields) {
        for (JTextField field : fields) {
            field.setText("");
        }
    }
}