package model;

public class Spaceship {

    private Planet planetOn;
    private double fuel;

    public Spaceship(double fuel){
        planetOn = null;
    }

    public Planet getPlanetOn() {
        return planetOn;
    }

    public void setPlanetOn(Planet planetOn) {
        this.planetOn = planetOn;
    }

    public double getFuel() {
        return fuel;
    }

    public void setFuel(double fuel) {
        this.fuel = fuel;
    }
}
