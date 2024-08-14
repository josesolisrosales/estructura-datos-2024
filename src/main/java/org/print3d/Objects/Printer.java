package org.print3d.Objects;

import org.print3d.DataStructures.DoubleLinkedList;

public class Printer {
    private String brand;
    private DoubleLinkedList<FilamentType> compatibleFilamentTypes;
    private int dimensionX;
    private int dimensionY;
    private int dimensionZ;
    private String state;

    public Printer(String brand, DoubleLinkedList<FilamentType> compatibleFilamentTypes, int dimensionX, int dimensionY, int dimensionZ, String state) {
        this.brand = brand;
        this.compatibleFilamentTypes = compatibleFilamentTypes;
        this.dimensionX = dimensionX;
        this.dimensionY = dimensionY;
        this.dimensionZ = dimensionZ;
        this.state = state;
    }

    public String getBrand() {
        return brand;
    }

    public DoubleLinkedList<FilamentType> getCompatibleFilamentTypes() {
        return compatibleFilamentTypes;
    }

    public int getDimensionX() {
        return dimensionX;
    }

    public int getDimensionY() {
        return dimensionY;
    }

    public int getDimensionZ() {
        return dimensionZ;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    @Override
    public String toString() {
        return brand + " (" + dimensionX + "x" + dimensionY + "x" + dimensionZ + "mm)";
    }

    public String getCompatibleFilamentTypesString() {
        StringBuilder sb = new StringBuilder();
        for (FilamentType type : compatibleFilamentTypes) {
            if (sb.length() > 0) {
                sb.append(", ");
            }
            sb.append(type.getType());
        }
        return sb.toString();
    }
}