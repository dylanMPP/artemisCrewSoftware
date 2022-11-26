package model;

public class Planet {

    private String name;
    private boolean visited;

    public Planet(String name, boolean visited){
        this.name = name;
        this.visited = visited;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isVisited() {
        return visited;
    }

    public void setVisited(boolean visited) {
        this.visited = visited;
    }
}

