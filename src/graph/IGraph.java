package graph;

public interface IGraph<V> {
    public boolean addVertex(GraphNode<V> vertex);
    public boolean addEdge(int weight, GraphNode<V> vertex, GraphNode<V> vertex2);
    public GraphNode<V> searchVertex(V value);
    public GraphEdge<V> searchEdge(V value, V value2);
}
