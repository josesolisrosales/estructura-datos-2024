package org.print3d.Objects;

public class FilamentType {
    private String type;
    private double extrusionTemp;
    private double bedTemp;
    private Double ambientTemp; // Optional
    private double dryingTemp;
    private int dryingTime;
    private int daysBetweenDryCycles;
    private double printingSpeed;

    public FilamentType(String type, double extrusionTemp, double bedTemp, double dryingTemp,
                        int dryingTime, int daysBetweenDryCycles, double printingSpeed, Double ambientTemp) {
        this.type = type;
        this.extrusionTemp = extrusionTemp;
        this.bedTemp = bedTemp;
        this.ambientTemp = ambientTemp;
        this.dryingTemp = dryingTemp;
        this.dryingTime = dryingTime;
        this.daysBetweenDryCycles = daysBetweenDryCycles;
        this.printingSpeed = printingSpeed;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public double getExtrusionTemp() {
        return extrusionTemp;
    }

    public void setExtrusionTemp(double extrusionTemp) {
        this.extrusionTemp = extrusionTemp;
    }

    public double getBedTemp() {
        return bedTemp;
    }

    public void setBedTemp(double bedTemp) {
        this.bedTemp = bedTemp;
    }

    public Double getAmbientTemp() {
        return ambientTemp;
    }

    public void setAmbientTemp(Double ambientTemp) {
        this.ambientTemp = ambientTemp;
    }

    public double getDryingTemp() {
        return dryingTemp;
    }

    public void setDryingTemp(double dryingTemp) {
        this.dryingTemp = dryingTemp;
    }

    public int getDryingTime() {
        return dryingTime;
    }

    public void setDryingTime(int dryingTime) {
        this.dryingTime = dryingTime;
    }

    public int getDaysBetweenDryCycles() {
        return daysBetweenDryCycles;
    }

    public void setDaysBetweenDryCycles(int daysBetweenDryCycles) {
        this.daysBetweenDryCycles = daysBetweenDryCycles;
    }

    public double getPrintingSpeed() {
        return printingSpeed;
    }

    public void setPrintingSpeed(double printingSpeed) {
        this.printingSpeed = printingSpeed;
    }

    @Override
    public String toString() {
        return type;
    }
}
