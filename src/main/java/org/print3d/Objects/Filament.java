package org.print3d.Objects;

import java.time.LocalDate;

public class Filament {
    private String brand;
    private FilamentType filamentType;
    private String color;
    private float diameter;
    private float weight;
    private LocalDate purchaseDate;
    private LocalDate nextDryingCycle;

    public Filament(String brand, FilamentType filamentType, String color, float diameter, float weight, LocalDate purchaseDate) {
        this.brand = brand;
        this.filamentType = filamentType;
        this.color = color;
        this.diameter = diameter;
        this.weight = weight;
        this.purchaseDate = purchaseDate;
        this.nextDryingCycle = calculateNextDryingCycle();
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public FilamentType getFilamentType() {
        return filamentType;
    }

    public void setFilamentType(FilamentType filamentType) {
        this.filamentType = filamentType;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public float getDiameter() {
        return diameter;
    }

    public void setDiameter(float diameter) {
        this.diameter = diameter;
    }

    public float getWeight() {
        return weight;
    }

    public void setWeight(float weight) {
        this.weight = weight;
    }

    public LocalDate getPurchaseDate() {
        return purchaseDate;
    }

    public void setPurchaseDate(LocalDate purchaseDate) {
        this.purchaseDate = purchaseDate;
    }

    private LocalDate calculateNextDryingCycle() {
        return calculateNextDryingCycleRecursive(this.purchaseDate, this.filamentType.getDaysBetweenDryCycles());
    }

    private LocalDate calculateNextDryingCycleRecursive(LocalDate currentDate, int daysBetweenDryCycles) {
        LocalDate nextDryingCycle = currentDate.plusDays(daysBetweenDryCycles);
        if (nextDryingCycle.isBefore(LocalDate.now())) {
            return calculateNextDryingCycleRecursive(nextDryingCycle, daysBetweenDryCycles);
        }
        return nextDryingCycle;
    }

    public LocalDate getNextDryingCycle() {
        return nextDryingCycle;
    }

    @Override
    public String toString() {
        return brand + " - " + color + " - " + diameter + "mm - " + weight + "g";
    }
}
