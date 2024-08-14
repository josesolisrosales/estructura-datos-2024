package org.print3d.UI;

import org.print3d.DataStructures.CircularList;
import org.print3d.Objects.Filament;
import org.print3d.ObjectManager;
import org.print3d.Objects.Print;
import org.print3d.Objects.Printer;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.format.DateTimeFormatter;

public class PrintPanel extends JPanel {
    private final ObjectManager objectManager;
    private DefaultTableModel printTableModel;
    private JTable table;

    public PrintPanel(ObjectManager objectManager) {
        this.objectManager = objectManager;
        setLayout(new BorderLayout());

        createTable();
        createInputPanel();
    }

    private void createTable() {
        printTableModel = new DefaultTableModel(
                new Object[]{"Nombre", "ID", "Impresora", "Filamento", "Inicio", "Fin", "Estado"},
                0
        );
        table = new JTable(printTableModel);
        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);
        updatePrintTable();
    }

    private void createInputPanel() {
        JPanel inputPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(2, 2, 2, 2);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JTextField nameField = new JTextField(10);
        JTextField idField = new JTextField(10);
        JComboBox<Printer> printerComboBox = new JComboBox<>(objectManager.getPrinters().toArray(new Printer[0]));
        JComboBox<Filament> filamentComboBox = new JComboBox<>(objectManager.getFilaments().toArray(new Filament[0]));

        gbc.gridx = 0;
        gbc.gridy = 0;
        inputPanel.add(new JLabel("Nombre:"), gbc);
        gbc.gridx = 1;
        inputPanel.add(nameField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        inputPanel.add(new JLabel("ID:"), gbc);
        gbc.gridx = 1;
        inputPanel.add(idField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        inputPanel.add(new JLabel("Impresora:"), gbc);
        gbc.gridx = 1;
        inputPanel.add(printerComboBox, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        inputPanel.add(new JLabel("Filamento:"), gbc);
        gbc.gridx = 1;
        inputPanel.add(filamentComboBox, gbc);

        JButton addButton = new JButton("Iniciar Impresión");
        JButton completeButton = new JButton("Completar Impresión");

        gbc.gridx = 0;
        gbc.gridy = 4;
        inputPanel.add(addButton, gbc);
        gbc.gridx = 1;
        inputPanel.add(completeButton, gbc);

        add(inputPanel, BorderLayout.SOUTH);

        addButton.addActionListener(e -> {
            String name = nameField.getText();
            String id = idField.getText();
            Printer printer = (Printer) printerComboBox.getSelectedItem();
            Filament filament = (Filament) filamentComboBox.getSelectedItem();

            if (name.isEmpty() || id.isEmpty() || printer == null || filament == null) {
                JOptionPane.showMessageDialog(PrintPanel.this, "Por favor, complete todos los campos.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            Print newPrint = new Print(name, id, printer, filament);
            objectManager.addPrint(newPrint);
            updatePrintTable();
            clearInputFields(nameField, idField);
            printerComboBox.setSelectedIndex(0);
            filamentComboBox.setSelectedIndex(0);
        });

        completeButton.addActionListener(e -> {
            int selectedRow = table.getSelectedRow();
            if (selectedRow != -1) {
                CircularList<Print> prints = objectManager.getPrints();
                Print print = prints.get(selectedRow);
                if (print.getEndTime() == null) {
                    print.completePrint();
                    updatePrintTable();
                } else {
                    JOptionPane.showMessageDialog(PrintPanel.this, "Esta impresión ya está completada.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(PrintPanel.this, "Por favor, seleccione una impresión para completar.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
    }

    private void updatePrintTable() {
        printTableModel.setRowCount(0);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        CircularList<Print> prints = objectManager.getPrints();
        for (int i = 0; i < prints.size(); i++) {
            Print print = prints.get(i);
            printTableModel.addRow(new Object[]{
                    print.getName(),
                    print.getId(),
                    print.getPrinter().getBrand(),
                    print.getFilament().getBrand() + " " + print.getFilament().getColor(),
                    print.getStartTime().format(formatter),
                    print.getEndTime() != null ? print.getEndTime().format(formatter) : "En progreso",
                    print.getEndTime() != null ? "Completado" : "En progreso"
            });
        }
    }

    private void clearInputFields(JTextField... fields) {
        for (JTextField field : fields) {
            field.setText("");
        }
    }
}