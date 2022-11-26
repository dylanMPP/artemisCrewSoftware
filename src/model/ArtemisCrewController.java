package model;

import graph.*;
import ui.ArtemisCrewManager;

import java.io.*;
import java.util.ArrayList;

public class ArtemisCrewController<V> {

    private Graph<V> graph;
    private String implementation;
    private Map map;
    private Spaceship spaceship;

    public ArtemisCrewController(String implementation){
        map = new Map();

        this.implementation = implementation;
        spaceship = new Spaceship(200000);


        if(implementation.equalsIgnoreCase("adjacency_list")){
            this.graph = new Graph<>("adjacency_list", 0,0);
        } else {
            this.graph = new Graph<>("adjacency_matrix", 50, 53);
        }
        //for (int i = 0; i < map.getPlanets().size(); i++) {
          //  if(map.getPlanets().get(i).getName().equalsIgnoreCase("Earth")){
            //    spaceship.setPlanetOn(map.getPlanets().get(i));
              //  break;
            //}
        //}
    } // constructor

    public String whereIsArtemis(){
        return this.spaceship.getPlanetOn().getName();
    }

    public double travel(V valueFrom, V value, double fuel){
        GraphNode<V> vertexFrom = searchVertex(valueFrom);
        GraphNode<V> vertexTo = searchVertex(value);

        graph.dijkstra(vertexFrom);
        ArrayList<GraphNode<V>> path = graph.getShortestPathTo(vertexTo);
        double consumedFuel = 0;
        int cont = 0;

        for (GraphNode<V> node:
             path) {

            if(cont < path.size()-1){
                cont++;
            }

            System.out.println(fuel);

            if(implementation.equalsIgnoreCase("adjacency_list")){
                for (int j = 0; j < node.getEdgesAdjacencyList().size(); j++) {
                    if(node.getEdgesAdjacencyList().get(j).getTo().equals(path.get(cont-1))){
                        consumedFuel+=node.getEdgesAdjacencyList().get(j).getWeight();
                    }
                }
            } // if

            if(implementation.equalsIgnoreCase("adjacency_matrix")){
                for (int i = 0; i < graph.getGraphNodesAdjacencyMatrix().length; i++) {
                    for (int j = 0; j < graph.getGraphNodesAdjacencyMatrix()[0].length; j++) {
                        if(i==path.get(cont-1).getPosition() && j==path.get(cont).getPosition()){
                            consumedFuel+=node.getEdgesAdjacencyList().get(j).getWeight();
                        }
                    }
                }
            } // if
        } // for

        return fuel-consumedFuel;
    }

    public ArrayList<GraphNode<V>> minimumPathFromOnePlanetToAnother(V value1, V value2){
        GraphNode<V> node1 = searchVertex(value1);
        GraphNode<V> node2 = searchVertex(value2);

        if(implementation.equalsIgnoreCase("adjacency_list")){
            graph.dijkstra(node1);
            ArrayList<GraphNode<V>> path = graph.getShortestPathTo(node2);
            return path;
        } else {
            ArrayList<GraphNode<V>> matrix = matrixToList();
            graph.setGraphNodesAdjacencyList(matrix);

            graph.dijkstra(node1);
            ArrayList<GraphNode<V>> path = graph.getShortestPathTo(node2);

            graph.setGraphNodesAdjacencyList(null);
            return path;
        }


        //String msg = "";

        //for (int i = 0; i < path.size(); i++) {
         //   msg += path.get(i).getValue();
        //}
        //return "";
    }

    public String canWeGo(V from, V to){
        V toGoPlanet = to;
        V departingPlanet = from;
        GraphNode<V> toGo = null;
        GraphNode<V> departing = null;
        int departingIndex = -1;

        if(searchVertex(from)!=null){
            departing = searchVertex(from);

            ArrayList<GraphNode<V>> matrixToList;
            if(this.implementation.equalsIgnoreCase("adjacency_matrix")){
                matrixToList = matrixToList();
                graph.setGraphNodesAdjacencyList(matrixToList);
            }

            ArrayList<GraphNode<Planet>> planets;

            if(implementation.equalsIgnoreCase("adjacency_list")){
                for (int i = 0; i < graph.getGraphNodesAdjacencyList().size(); i++) {
                    if(graph.getGraphNodesAdjacencyList().get(i).getValue().
                            equals(departingPlanet)){
                        departing = graph.getGraphNodesAdjacencyList().get(i);
                        departingIndex = i;
                    }

                    if(graph.getGraphNodesAdjacencyList().get(i).getValue().
                            equals(toGoPlanet)){
                        toGo = graph.getGraphNodesAdjacencyList().get(i);
                    }
                }  // for
            } else {
                System.out.println("length the graph nodes matrix 2"+graph.graphNodesMatrix2.length);
                for(int i = 0; i < graph.getGraphNodesMatrix2().length; i++){
                    if(graph.getGraphNodesMatrix2()[i][0].getValue().
                            equals(departingPlanet)){
                        System.out.println(i);
                        departing = graph.getGraphNodesMatrix2()[i][0];
                        departingIndex = i;
                    }

                    if(graph.getGraphNodesMatrix2()[i][0].getValue().
                            equals(toGoPlanet)){
                        System.out.println(i);
                        toGo = graph.getGraphNodesMatrix2()[i][0];
                    }
                }
            } // if else

            if(departingIndex!=-1 && toGo!=null){
                graph.BFS(departingIndex);

                if(implementation.equalsIgnoreCase("adjacency_list")){
                    for (int i = 0; i < graph.getGraphNodesAdjacencyList().size(); i++) {
                        if(graph.getGraphNodesAdjacencyList().get(i).equals(toGo)){
                            System.out.println(graph.getGraphNodesAdjacencyList().get(i).getColor());
                            if(graph.getGraphNodesAdjacencyList().get(i).getColor()
                                    .equalsIgnoreCase("G") || (graph.getGraphNodesAdjacencyList().get(i).getColor()
                                    .equalsIgnoreCase("B"))){
                                return "YES! You can go from the planet "+departingPlanet+" to the planet "+toGoPlanet;
                            } // if color
                        } // if equals
                    } // for
                } else {
                    ArrayList<GraphNode<V>> matrix = matrixToList();
                    graph.setGraphNodesAdjacencyList(matrix);

                    graph.BFS(departingIndex);

                    for (int i = 0; i < graph.graphNodesAdjacencyList.size(); i++) {
                        if(graph.graphNodesAdjacencyList.get(i).equals(toGo)){
                            System.out.println(graph.graphNodesAdjacencyList.get(i).getColor());
                            if(graph.graphNodesAdjacencyList.get(i).getColor()
                                    .equalsIgnoreCase("G") || (graph.graphNodesAdjacencyList.get(i).getColor()
                                    .equalsIgnoreCase("B"))){
                                return "YES! You can go from the planet "+departingPlanet+" to the planet "+toGoPlanet;
                            } // if color
                        } // if equals
                    } // for
                }

                if(implementation.equalsIgnoreCase("adjacency_matrix")){
                    graph.setGraphNodesAdjacencyList(null);
                }
                return "NO! You can't go from the planet "+departingPlanet+" to the planet "+toGoPlanet;
            } // if
        } else {
            return "";
        }
        return "";
    }

    public Planet searchPlanet(String name){
        for (int i = 0; i < map.getPlanets().size(); i++) {
            if(map.getPlanets().get(i).getName().equals(name)){
                return map.getPlanets().get(i);
            }
        }
        return null;
    }

    public String planetInfo(String name){
        for (int i = 0; i < map.getPlanets().size(); i++) {
            if(map.getPlanets().get(i).getName().equals(name)){

                if(map.getPlanets().get(i).isVisited()){
                    return "NAME: "+map.getPlanets().get(i).getName()+"\nVISITED: YES";
                } else {
                    return "NAME: "+map.getPlanets().get(i).getName()+"\nVISITED: NO";
                }
            }
        }
        return "";
    } // planet info

    public void addPlanet(String name) {
        Planet planet;

        if (name.equalsIgnoreCase("Earth")) {
            planet = new Planet(name, true);
        } else {
            planet = new Planet(name, false);
        }

        map.addPlanet(planet);
    } // add planet

    public boolean addVertex(V value){
        GraphNode<V> graphNode;

        if(this.graph==null){
            return false;
        }

        // If the implementation is the adjacency list, then I write as position -1, because the position
        // is only for matrix.
        if(this.implementation.equalsIgnoreCase("adjacency_list")){
            graphNode = new GraphNode<>(value, -1);
            return graph.addVertex(graphNode);
        } else {
            int availablePosition = availablePosition();

            if(availablePosition!=-1){
                graphNode = new GraphNode<>(value, availablePosition);
                return graph.addVertex(graphNode);
            }
        }
        return false;
    } // add vertex

    public int availablePosition(){
        if(this.graph==null){
            return -1;
        }
        return graph.availablePosition();
    }  // available position

    public boolean addEdge(int weight, V value, V value2){
        GraphNode<V> graphNode = null;
        GraphNode<V> graphNode2 = null;

        if(this.implementation.equalsIgnoreCase("adjacency_list")){
            for (int i = 0; i < this.graph.getGraphNodesAdjacencyList().size(); i++) {
                System.out.println("el value de matrix2 nodo es"+this.graph.getGraphNodesAdjacencyList().get(i));
                System.out.println("el value a comprar es"+value);
                if(this.graph.getGraphNodesAdjacencyList().get(i).getValue().equals(value)){
                    graphNode = this.graph.getGraphNodesAdjacencyList().get(i);
                }

                if(this.graph.getGraphNodesAdjacencyList().get(i).getValue().equals(value2)){
                    graphNode2 = this.graph.getGraphNodesAdjacencyList().get(i);
                }
            }
        } else {

            for (int i = 0; i < this.graph.getGraphNodesMatrix2().length; i++) {
                if(this.graph.getGraphNodesMatrix2()[i][0].getValue().equals(value)){
                    graphNode = this.graph.getGraphNodesMatrix2()[i][0];
                    graphNode.setPosition(i);
                }

                if(this.graph.getGraphNodesMatrix2()[i][0].getValue().equals(value2)){
                    graphNode2 = this.graph.getGraphNodesMatrix2()[i][0];
                    graphNode2.setPosition(i);
                }
            }
        }

        if(graphNode!=null && graphNode2!=null){
            System.out.println("añadiendo arista:"+weight);
            return graph.addEdge(weight, graphNode, graphNode2);
        } else {
            return false;
        }
    }// add edge

    public GraphNode<V> searchVertex(V value){
        return graph.searchVertex(value);
    } // search vertex

    public GraphEdge<V> searchEdge(V value, V value2){
        return graph.searchEdge(value, value2);
    }  // search edge

    public ArrayList<GraphNode<V>> matrixToList(){
        return graph.matrixToList();
    } // matrix to list

    // GETS AND SETS
    public Graph<V> getGraph() {
        return graph;
    }

    public void setGraph(Graph<V> graph) {
        this.graph = graph;
    }

    public String getImplementation() {
        return implementation;
    }

    public void setImplementation(String implementation) {
        this.implementation = implementation;
    }

    public Map getMap() {
        return map;
    }

    public void setMap(Map map) {
        this.map = map;
    }

    public Spaceship getSpaceship() {
        return spaceship;
    }

    public void setSpaceship(Spaceship spaceship) {
        this.spaceship = spaceship;
    }
}
