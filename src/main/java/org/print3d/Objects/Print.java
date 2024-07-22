package org.print3d.Objects;

import java.time.LocalDateTime;

public class Print {
    private String name;
    private String id;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private Printer printer;
    private Filament filament;

    public Print(String name, String id, Printer printer, Filament filament) {
        this.name = name;
        this.id = id;
        this.startTime = LocalDateTime.now();
        this.printer = printer;
        this.filament = filament;
    }

    public void completePrint() {
        this.endTime = LocalDateTime.now();
    }

    public String getName() {
        return name;
    }

    public String getId() {
        return id;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public Printer getPrinter() {
        return printer;
    }

    public Filament getFilament() {
        return filament;
    }

    @Override
    public String toString() {
        return name + " (ID: " + id + ")";
    }
}