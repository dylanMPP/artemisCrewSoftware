package graph;

import java.util.ArrayList;

public class GraphNode <V>{
    private V value;
    private ArrayList<GraphEdge<V>> edgesAdjacencyList;
    private int position;

    //attributes made because of the algorithms.
    private String color;
    private int distance;
    private GraphNode<V> predecessor;
    private int time;
    private int key;

    public GraphNode(V value, int position){
        this.value = value;
        this.position = position;
        edgesAdjacencyList = new ArrayList<>();

        this.color = null;
        this.distance = 0;
        this.predecessor = null;
    }

    public void addEdge(GraphEdge<V> edge) {
        edgesAdjacencyList.add(edge);
    }

    // gets and sets
    public int getPosition(){
        return this.position;
    }

    public void setPosition(int position){
        this.position = position;
    }

    public V getValue() {
        return value;
    }

    public void setValue(V value) {
        this.value = value;
    }

    public ArrayList<GraphEdge<V>> getEdgesAdjacencyList() {
        return edgesAdjacencyList;
    }

    public void setEdgesAdjacencyList(ArrayList<GraphEdge<V>> edgesAdjacencyList) {
        this.edgesAdjacencyList = edgesAdjacencyList;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public int getDistance() {
        return distance;
    }

    public void setDistance(int distance) {
        this.distance = distance;
    }

    public GraphNode<V> getPredecessor() {
        return predecessor;
    }

    public void setPredecessor(GraphNode<V> predecessor) {
        this.predecessor = predecessor;
    }

    public int getTime(){
        return this.time;
    }

    public void setTime(int time){
        this.time = time;
    }

    public int getKey() {
        return key;
    }

    public void setKey(int key) {
        this.key = key;
    }


}