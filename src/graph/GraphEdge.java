package graph;

public class GraphEdge <V>{

    private GraphNode<V> to;
    private int weight;

    public GraphEdge(GraphNode<V> to, int weight){
        this.to = to;
        this.weight = weight;
    }

    // gets and sets
    public GraphNode<V> getTo() {
        return to;
    }

    public void setTo(GraphNode<V> to) {
        this.to = to;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }
}


