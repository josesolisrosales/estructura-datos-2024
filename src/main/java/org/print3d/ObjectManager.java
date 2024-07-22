package org.print3d;

import org.print3d.Objects.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Stack;
import java.util.Queue;

public class ObjectManager {
    private final DoubleLinkedList<FilamentType> filamentTypes;
    private final DoubleLinkedList<Filament> filaments;
    private final Stack<Printer> printers;
    private final Queue<Nozzle> nozzles;
    private final CircularList<Print> prints;

    public ObjectManager() {
        printers = new Stack<>();
        filamentTypes = new DoubleLinkedList<>();
        filaments = new DoubleLinkedList<>();
        nozzles = new LinkedList<>();
        prints = new CircularList<>(10);
        initializeData();
    }

    private void initializeData() {

        FilamentType pla = new FilamentType("PLA", 200, 60, 45, 4, 30, 50, null);
        FilamentType abs = new FilamentType("ABS", 230, 100, 60, 6, 45, 35, 30.0);
        FilamentType petg = new FilamentType("PETG", 240, 70, 50, 5, 60, 40, null);

        // Doble lista de tipos de filamentos
        filamentTypes.add(pla);
        filamentTypes.add(abs);
        filamentTypes.add(petg);

        // Doble lista de filamentos
        filaments.add(new Filament("Brand1", pla, "Red", 1.75f, 1000f, LocalDate.now().minusDays(30)));
        filaments.add(new Filament("Brand2", abs, "Blue", 2.85f, 750f, LocalDate.now().minusDays(15)));
        filaments.add(new Filament("Brand3", petg, "Green", 1.75f, 1000f, LocalDate.now().minusDays(7)));


        ArrayList<FilamentType> allTypes = new ArrayList<>();
        allTypes.add(pla);
        allTypes.add(abs);
        allTypes.add(petg);

        // Pila de impresoras
        printers.push(new Printer("Prusa i3 MK3S+", allTypes, 250, 210, 210, "libre"));
        printers.push(new Printer("Creality Ender 3 V2", allTypes, 220, 220, 250, "ocupada"));
        printers.push(new Printer("Anycubic Mega X", allTypes, 300, 300, 305, "inoperable"));

        // Cola de boquillas
        nozzles.offer(new Nozzle("Brass", 0.4f));
        nozzles.offer(new Nozzle("Steel", 0.6f));
        nozzles.offer(new Nozzle("Brass", 0.4f));
        nozzles.offer(new Nozzle("Brass", 0.4f));
        nozzles.offer(new Nozzle("Steel", 0.6f));
        nozzles.offer(new Nozzle("Brass", 0.4f));
        nozzles.offer(new Nozzle("Brass", 0.4f));
        nozzles.offer(new Nozzle("Steel", 0.6f));
        nozzles.offer(new Nozzle("Brass", 0.4f));

        if (!printers.isEmpty() && !filaments.isEmpty()) {
            Printer printer = printers.peek();
            Filament filament = filaments.toArray(new Filament[0])[0];
            Print print1 = new Print("Test Print 1", "TP001", printer, filament);
            Print print2 = new Print("Test Print 2", "TP002", printer, filament);
            print1.completePrint();
            prints.add(print1);
            prints.add(print2);
        }

    }

    public DoubleLinkedList<FilamentType> getFilamentTypes() {
        return filamentTypes;
    }

    public DoubleLinkedList<Filament> getFilaments() {
        return filaments;
    }

    public void addFilamentType(FilamentType filamentType) {
        filamentTypes.add(filamentType);
    }

    public void removeFilamentType(FilamentType filamentType) {
        filamentTypes.remove(filamentType);
    }

    public void addFilament(Filament filament) {
        filaments.add(filament);
    }

    public void removeFilament(Filament filament) {
        filaments.remove(filament);
    }

    public Stack<Printer> getPrinters() {
        return printers;
    }

    public void addPrinter(Printer printer) {
        printers.push(printer);
    }

    public Printer removePrinter() {
        if (!printers.isEmpty()) {
            return printers.pop();
        }
        return null;
    }

    public Queue<Nozzle> getNozzles() {
        return nozzles;
    }

    public void addNozzle(Nozzle nozzle) {
        nozzles.offer(nozzle);
    }

    public Nozzle removeNozzle() {
        return nozzles.poll();
    }

    public CircularList<Print> getPrints() {
        return prints;
    }

    public void addPrint(Print print) {
        prints.add(print);
    }
}