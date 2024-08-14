package org.print3d.UI;

import org.print3d.ObjectManager;
import org.print3d.Objects.FilamentType;
import org.print3d.Objects.Printer;

import javax.swing.*;
import java.awt.*;
import java.util.Set;

public class FilamentCompatibilityPanel extends JPanel {
    private ObjectManager objectManager;
    private JComboBox<String> filamentTypeComboBox;
    private JComboBox<String> printerComboBox;
    private JTextArea compatibilityTextArea;

    public FilamentCompatibilityPanel(ObjectManager objectManager) {
        this.objectManager = objectManager;
        setLayout(new BorderLayout());

        JPanel inputPanel = new JPanel(new FlowLayout());
        filamentTypeComboBox = new JComboBox<>();
        printerComboBox = new JComboBox<>();
        JButton checkCompatibilityButton = new JButton("Verificar Compatibilidad");

        inputPanel.add(new JLabel("Tipo de Filamento:"));
        inputPanel.add(filamentTypeComboBox);
        inputPanel.add(new JLabel("Impresora:"));
        inputPanel.add(printerComboBox);
        inputPanel.add(checkCompatibilityButton);

        compatibilityTextArea = new JTextArea(10, 30);
        compatibilityTextArea.setEditable(false);

        add(inputPanel, BorderLayout.NORTH);
        add(new JScrollPane(compatibilityTextArea), BorderLayout.CENTER);

        updateComboBoxes();

        checkCompatibilityButton.addActionListener(e -> checkCompatibility());
    }

    private void updateComboBoxes() {
        filamentTypeComboBox.removeAllItems();
        printerComboBox.removeAllItems();

        for (FilamentType type : objectManager.getFilamentTypes().toArray(new FilamentType[0])) {
            filamentTypeComboBox.addItem(type.getType());
        }

        for (Printer printer : objectManager.getPrinters()) {
            printerComboBox.addItem(printer.getBrand());
        }
    }

    private void checkCompatibility() {
        String selectedFilamentType = (String) filamentTypeComboBox.getSelectedItem();
        String selectedPrinter = (String) printerComboBox.getSelectedItem();

        if (selectedFilamentType != null && selectedPrinter != null) {
            boolean compatible = objectManager.areCompatible(selectedFilamentType, selectedPrinter);
            compatibilityTextArea.setText(String.format("Compatibilidad entre %s y %s: %s\n\n",
                    selectedFilamentType, selectedPrinter, compatible ? "Compatible" : "No compatible"));

            Set<String> compatiblePrinters = objectManager.getCompatiblePrinters(selectedFilamentType);
            compatibilityTextArea.append("Impresoras compatibles con " + selectedFilamentType + ":\n");
            for (String printer : compatiblePrinters) {
                compatibilityTextArea.append("- " + printer + "\n");
            }

            Set<String> compatibleFilaments = objectManager.getCompatibleFilamentTypes(selectedPrinter);
            compatibilityTextArea.append("\nTipos de filamento compatibles con " + selectedPrinter + ":\n");
            for (String filament : compatibleFilaments) {
                compatibilityTextArea.append("- " + filament + "\n");
            }
        }
    }
}