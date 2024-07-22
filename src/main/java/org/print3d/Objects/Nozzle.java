package org.print3d.Objects;

public class Nozzle {
    private String material;
    private float diameter;

    public Nozzle(String material, float diameter) {
        this.material = material;
        this.diameter = diameter;
    }

    public String getMaterial() {
        return material;
    }

    public float getDiameter() {
        return diameter;
    }

    @Override
    public String toString() {
        return material + " - " + diameter + "mm";
    }
}