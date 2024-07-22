package org.print3d.Objects;

import java.util.ArrayList;

public class Printer {
    private String brand;
    private ArrayList<FilamentType> compatibleFilamentTypes;
    private int dimensionX;
    private int dimensionY;
    private int dimensionZ;
    private String state;

    public Printer(String brand, ArrayList<FilamentType> compatibleFilamentTypes, int dimensionX, int dimensionY, int dimensionZ, String state) {
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

    public ArrayList<FilamentType> getCompatibleFilamentTypes() {
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
}