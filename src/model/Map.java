package model;

import java.util.ArrayList;

public class Map {
    private ArrayList<Planet> planets;

    public Map(){
        planets = new ArrayList<>();
    }

    public void addPlanet(Planet planet){
        planets.add(planet);
    }

    public ArrayList<Planet> getPlanets() {
        return planets;
    }

    public void setPlanets(ArrayList<Planet> planets) {
        this.planets = planets;
    }
}
