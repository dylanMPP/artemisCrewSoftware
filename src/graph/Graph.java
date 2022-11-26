package graph;

import priorityQueue.PriorityQueue;
import priorityQueue.PriorityQueueNode;
import queue.Queue;
import queue.QueueNode;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class Graph<V> implements IGraph<V> {
    public ArrayList<GraphNode<V>> graphNodesAdjacencyList;
    public Integer[][] graphNodesAdjacencyMatrix;
    // I create a matrix of the nodes of the matrix, because I need to have a reference of its position
    // in the principalMatrix
    public GraphNode<V>[][] graphNodesMatrix2;
    public Queue<GraphNode<V>> queue;
    private int time;

    // The user choose the implementation
    public Graph(String implementation, int row, int column) {
        queue = new Queue<>();
        if (implementation.equalsIgnoreCase("adjacency_list")) {
            this.graphNodesAdjacencyList = new ArrayList<>();
        } else {
            this.graphNodesAdjacencyMatrix = new Integer[row][column];
            this.graphNodesMatrix2 = new GraphNode[row][1];
        }
    }

    @Override
    public boolean addVertex(GraphNode<V> vertex) {
        if (graphNodesAdjacencyList == null && graphNodesAdjacencyMatrix == null) {
            return false;
        }

        // I add the vertex, to the adjacency list or to the list of the nodes of the matrix.
        if (graphNodesAdjacencyList != null) {
            this.graphNodesAdjacencyList.add(vertex);
            return true;

        } else {
            // if all the size of the list of the nodes of the matrix is equals to the matrix length,
            // I mean, if all the positions of the matrix are occupied, then I can't add the vertex
            for (int i = 0; i < graphNodesMatrix2.length; i++) {
                if (graphNodesMatrix2[i][0] == null) {
                    graphNodesMatrix2[i][0] = vertex;
                    break;
                }
            }

            return true;
        }
    }

    @Override
    public boolean addEdge(int weight, GraphNode<V> vertex, GraphNode<V> vertex2) {
        GraphEdge<V> edge = new GraphEdge<>(vertex2, weight);

        if (graphNodesAdjacencyList == null && graphNodesAdjacencyMatrix == null) {
            return false;
        }

        // If the implementation is a list, I add the edge
        if (graphNodesAdjacencyList != null) {
            vertex.addEdge(edge);
            return true;
        } else {
            System.out.println("me agrega en "+vertex.getPosition()+"arista con"+weight);
            graphNodesAdjacencyMatrix[vertex.getPosition()][vertex2.getPosition()] = weight;
        } // if
        return false;
    }

    public int availablePosition() {
        for (int i = 0; i < this.graphNodesMatrix2.length; i++) {
            if (this.graphNodesAdjacencyMatrix[i][0] == null) {
                return i;
            }
        }
        return -1;
    }

    @Override
    public GraphNode<V> searchVertex(V value) {
        if (graphNodesAdjacencyList != null) {
            for (int i = 0; i < graphNodesAdjacencyList.size(); i++) {
                if (graphNodesAdjacencyList.get(i).getValue().equals(value)) {
                    return graphNodesAdjacencyList.get(i);
                }
            }
        } else if (graphNodesAdjacencyMatrix != null) {
            for (int i = 0; i < graphNodesMatrix2.length; i++) {
                if (graphNodesMatrix2[i][0].getValue().equals(value)) {
                    return graphNodesMatrix2[i][0];
                }
            }
        } // if
        System.out.println("retorna null, no lo encontró");
        return null;
    }

    @Override
    public GraphEdge<V> searchEdge(V value, V value2) {
        if (graphNodesAdjacencyList != null) {

            for (int i = 0; i < graphNodesAdjacencyList.size(); i++) {
                if (graphNodesAdjacencyList.get(i).getValue().equals(value)) {
                    for (int j = 0; j < graphNodesAdjacencyList.get(i).getEdgesAdjacencyList().size(); j++) {
                        if (graphNodesAdjacencyList.get(i).getEdgesAdjacencyList().get(j).getTo().getValue().equals(value2)) {
                            return graphNodesAdjacencyList.get(i).getEdgesAdjacencyList().get(j);
                        }
                    } // for
                }
            } // for

        } else if (graphNodesAdjacencyMatrix != null) {
            GraphNode<V> graphNode = searchVertex(value);
            GraphNode<V> graphNode2 = searchVertex(value2);

            if (graphNode != null && graphNode2 != null) {
                int position = graphNode.getPosition();
                int position2 = graphNode2.getPosition();

                int weight = graphNodesAdjacencyMatrix[position][position2];
                return new GraphEdge<>(graphNode2, weight);
            }
        } // if
        return null;
    }

    // BFS
    public void BFS(int index) {
        if (graphNodesAdjacencyList.get(index) != null) {
            System.out.println(graphNodesAdjacencyList.get(0));
            GraphNode<V> node = graphNodesAdjacencyList.get(index);

            for (int i = 0; i < graphNodesAdjacencyList.size(); i++) {
                if (!(i == index)) {
                    graphNodesAdjacencyList.get(i).setColor("W");
                    graphNodesAdjacencyList.get(i).setDistance(0);
                    graphNodesAdjacencyList.get(i).setPredecessor(null);
                }
            }

            System.out.println(node.getColor());
            node.setColor("G");
            node.setDistance(0);
            node.setPredecessor(null);

            QueueNode<GraphNode<V>> queueNode = new QueueNode<>(node);
            this.queue.offer(queueNode);

            while (!this.queue.isEmpty()) {
                GraphNode<V> auxiliar = this.queue.poll();

                for (GraphEdge<V> n :
                        auxiliar.getEdgesAdjacencyList()) {
                    GraphNode<V> n2 = n.getTo();
                    System.out.println(n2.getColor());
                    if (n2.getColor().equalsIgnoreCase("W")) {
                        n2.setColor("G");
                        n2.setDistance(auxiliar.getDistance() + 1);
                        n2.setPredecessor(auxiliar);
                        QueueNode<GraphNode<V>> queueNode2 = new QueueNode<>(n2);
                        System.out.println("offer");
                        this.queue.offer(queueNode2);
                    } // if
                } // for
                System.out.println("changing to black");
                auxiliar.setColor("B");
            } // while
        } // if
    }

    // DFS
    public int DFS() {
        boolean matrixFlag = false;

        if (this.graphNodesAdjacencyMatrix != null) {
            matrixFlag = true;
        }

        for (GraphNode<V> graphNode : graphNodesAdjacencyList) {
            graphNode.setPredecessor(null);
            graphNode.setColor("W");
            graphNode.setTime(0);
        }
        int trees = 0;
        int time = 0;
        for (GraphNode<V> graphNode : graphNodesAdjacencyList) {
            if (graphNode.getColor().equals("W")) {
                trees++;
                DFS(graphNode);
            }
        }
        if (matrixFlag) {
            this.graphNodesAdjacencyList = null;
        }
        return trees;
    }

    // DFS
    public void DFS(GraphNode<V> graphNode) {
        time++;
        graphNode.setTime(time);
        graphNode.setColor("G");
        for (GraphEdge<V> adjacencyEdge : graphNode.getEdgesAdjacencyList()) {
            if (adjacencyEdge.getTo().getColor().equals("W")) {
                adjacencyEdge.getTo().setPredecessor(graphNode);
                DFS(adjacencyEdge.getTo());
            }
        }
        graphNode.setColor("B");
        time++;
        graphNode.setTime(time);
    }

    public void dijkstra(GraphNode<V> sourceVertex) {
        sourceVertex.setDistance(0);
        PriorityQueue<GraphNode<V>, Integer> priorityQueue = new PriorityQueue<>(graphNodesAdjacencyList.size());
        PriorityQueueNode<GraphNode<V>, Integer> sourceNode = new PriorityQueueNode<>(sourceVertex,sourceVertex.getDistance());
        priorityQueue.insert(sourceNode);

        while (!priorityQueue.isEmpty()) {
            GraphNode<V> vertex = priorityQueue.extractMin();

            for (GraphEdge<V> edge : vertex.getEdgesAdjacencyList()) {
                GraphNode<V> v = edge.getTo();
//                Vertex u = edge.getStartVertex();
                int weight = edge.getWeight();
                int minDistance = vertex.getDistance() + weight;
                if (minDistance < v.getDistance()) {
                    priorityQueue.delete(vertex);
                    System.out.println("entro a setar predecess");
                    v.setPredecessor(vertex);
                    v.setDistance(minDistance);
                    PriorityQueueNode<GraphNode<V>, Integer> nodeV = new PriorityQueueNode<>(v,v.getDistance());
                    priorityQueue.insert(nodeV);
                }
            }
        }
    }

    public ArrayList<GraphNode<V>> getShortestPathTo(GraphNode<V> targetVerte) {
        ArrayList<GraphNode<V>> path = new ArrayList<>();

        for (GraphNode<V> vertex = targetVerte; vertex != null; vertex = vertex.getPredecessor()) {
            System.out.println(vertex.getValue());
            path.add(vertex);
        }

        Collections.reverse(path);
        return path;
    }

    public PriorityQueueNode<GraphNode<V>, Integer> searchPQNode
            (PriorityQueue<GraphNode<V>, Integer> priorityQueue, GraphEdge<V> graphNode) {

        for (PriorityQueueNode<GraphNode<V>, Integer> node :
                priorityQueue.getPriorityQueue()) {
            if (node.getElement().equals(graphNode)) {
                return node;
            }
        }

        return null;
    }

    // Floyd-Warshall
    public int[][] floydWarshall() {
        boolean matrixFlag = false;
        if (this.graphNodesAdjacencyMatrix != null) {
            matrixFlag = true;
        }

        int[][] dist = new int[graphNodesAdjacencyList.size()][graphNodesAdjacencyList.size()];

        for (int i = 0; i < dist.length; i++) {
            for (int j = 0; j < dist[0].length; j++) {
                dist[i][j] = 100000;
            }
        }

        for (GraphNode<V> v : graphNodesAdjacencyList) {
            dist[v.getKey()][v.getKey()] = 0;
        }

        for (GraphNode<V> v : graphNodesAdjacencyList) {
            for (GraphEdge<V> u : v.getEdgesAdjacencyList()) {
                dist[v.getKey()][u.getTo().getKey()] = u.getWeight();
            }
        }

        for (int k = 0; k < graphNodesAdjacencyList.size(); k++) {
            for (int i = 0; i < graphNodesAdjacencyList.size(); i++) {
                for (int j = 0; j < graphNodesAdjacencyList.size(); j++) {
                    if (dist[i][j] > (dist[i][k] + dist[k][j]) && ((dist[i][k]) != Integer.MAX_VALUE && ((dist[k][j]) != Integer.MAX_VALUE))) {
                        dist[i][j] = dist[i][k] + dist[k][j];
                    }
                }
            }
        }
        if (matrixFlag) {
            this.graphNodesAdjacencyList = null;
        }
        return dist;
    }

    // Prim
    public int prim(GraphNode<V> r) {
        boolean matrixFlag = false;

        if (this.graphNodesAdjacencyMatrix != null) {
            matrixFlag = true;
        }

        ArrayList<GraphNode<V>> graphNodes = new ArrayList<>();
        for (int i = 0; i < graphNodesAdjacencyList.size(); i++) {
            graphNodes.add(graphNodesAdjacencyList.get(i));
        }
        for (int i = 0; i < graphNodesAdjacencyList.size(); i++) {
            graphNodes.get(i).setColor("W");
            graphNodes.get(i).setDistance(Integer.MAX_VALUE);
            graphNodes.get(i).setKey(0);
        }

        Comparator<GraphNode<V>> comp = new Comparator<GraphNode<V>>() {
            @Override
            public int compare(GraphNode<V> o1, GraphNode<V> o2) {
                return o1.getDistance() - o2.getDistance();
            }
        };

        ArrayList<GraphNode<V>> q = new ArrayList<>();
        r.setDistance(0);
        r.setPredecessor(null);

        for (int i = 0; i < graphNodes.size(); i++) {
            q.add(graphNodes.get(i));
        }

        q.sort(comp);

        while (!q.isEmpty()) {
            GraphNode<V> u = q.remove(0);

            for (GraphEdge<V> i : u.getEdgesAdjacencyList()) {
                if (i.getTo().getColor().equals("W") && i.getWeight() < i.getTo().getDistance()) {
                    i.getTo().setDistance(i.getWeight());
                    i.getTo().setPredecessor(u);
                }
            }
            u.setColor("B");
            q.sort(comp);
        }

        int weight = 0;

        for (int i = 0; i < graphNodes.size(); i++) {
            graphNodes.get(i).setColor("W");
        }

        for (GraphNode<V> i : graphNodes) {
            if (i.getColor().equals("W")) {
                GraphNode<V> u = i;
                while (u.getPredecessor() != null) {
                    if (u.getColor().equals("W")) weight += u.getDistance();
                    u.setColor("B");
                    u = u.getPredecessor();
                }
            }
        }

        if (matrixFlag) {
            this.graphNodesAdjacencyList = null;
        }

        return weight;
    }

    // Kruskal
    public ArrayList<Integer[]> kruskal(ArrayList<Integer[]> nodes, ArrayList<Integer[]> edges) {
        edges = sortByWeight(edges);
        ArrayList<Integer[]> tree = new ArrayList<>();
        int path = 0;
        boolean matrixFlag = false;

        if (this.graphNodesAdjacencyMatrix != null) {
            matrixFlag = true;
        }

        for (Integer[] n :
                nodes) {
            n[1] = n[0];
        }

        for (Integer[] e :
                edges) {
            if (knowSet(nodes, e[0]) != (knowSet(nodes, e[1]))) {
                tree.add(e);
                path += e[2];

                if (knowSet(nodes, e[0]) < knowSet(nodes, e[1]) || knowSet(nodes, e[0]) == (knowSet(nodes, e[1]))) {
                    int previousSet = knowSet(nodes, e[1]);
                    setSet(nodes, e[1], knowSet(nodes, e[0]));
                    updateSets(nodes, previousSet, knowSet(nodes, e[0]));
                } else {
                    int previousSet = knowSet(nodes, e[0]);
                    setSet(nodes, e[0], knowSet(nodes, e[1]));
                    updateSets(nodes, previousSet, knowSet(nodes, e[1]));
                }
            }
        }

        if (matrixFlag) {
            this.graphNodesAdjacencyList = null;
        }

        return tree;
    }

    private static void updateSets(ArrayList<Integer[]> nodes, int previousSet, int newSet) {
        for (Integer[] n :
                nodes) {
            if (n[1].equals(previousSet)) {
                n[1] = newSet;
            }
        }
    }

    public static ArrayList<Integer[]> sortByWeight(ArrayList<Integer[]> edge) {
        ArrayList<Integer> weights = new ArrayList<>();

        for (Integer[] arr :
                edge) {
            weights.add(arr[2]);
        }

        Collections.sort(weights);

        for (int i = 0; i < weights.size(); i++) {
            for (int j = i; j < edge.size(); j++) {
                if (edge.get(j)[2].equals(weights.get(i))) {
                    Integer[] aux = edge.get(i);
                    edge.set(i, edge.get(j));
                    edge.set(j, aux);
                }
            }
        }
        return edge;
    }

    public static int knowSet(ArrayList<Integer[]> nodes, int node) {
        for (Integer[] n :
                nodes) {
            if (n[0].equals(node)) {
                return n[1];
            }
        }
        return -1;
    }

    public static void setSet(ArrayList<Integer[]> nodes, int node, int set) {
        for (Integer[] n :
                nodes) {
            if (n[0].equals(node)) {
                n[1] = set;
            }
        }
    } // Kruskal end

    public ArrayList<GraphNode<V>> matrixToList() {
        ArrayList<GraphNode<V>> nodes = new ArrayList<>();

        for (int i = 0; i < graphNodesMatrix2.length; i++) {
            nodes.add(graphNodesMatrix2[i][0]);
        }

        for (int i = 0; i < graphNodesAdjacencyMatrix.length; i++) {
            for (int j = 0; j < graphNodesAdjacencyMatrix[0].length; j++) {
                if (graphNodesAdjacencyMatrix[i][j] != null) {
                    GraphNode<V> to = null;

                    for (int k = 0; k < nodes.size(); k++) {
                        if (nodes.get(k).getPosition() == j) {
                            to = nodes.get(k);
                            break;
                        }
                    }

                    GraphEdge<V> edge = new GraphEdge<>(to, graphNodesAdjacencyMatrix[i][j]);

                    for (int k = 0; k < nodes.size(); k++) {
                        if (nodes.get(k).getPosition() == i) {
                            nodes.get(k).getEdgesAdjacencyList().add(edge);
                            break;
                        }
                    }
                }
            } // for
        } // for

        return nodes;
    } // from matrix to list

    // GETS AND SETS //
    public ArrayList<GraphNode<V>> getGraphNodesAdjacencyList() {
        return graphNodesAdjacencyList;
    }

    public void setGraphNodesAdjacencyList(ArrayList<GraphNode<V>> graphNodesAdjacencyList) {
        this.graphNodesAdjacencyList = graphNodesAdjacencyList;
    }

    public Integer[][] getGraphNodesAdjacencyMatrix() {
        return graphNodesAdjacencyMatrix;
    }

    public void setGraphNodesAdjacencyMatrix(Integer[][] graphNodesAdjacencyMatrix) {
        this.graphNodesAdjacencyMatrix = graphNodesAdjacencyMatrix;
    }

    public GraphNode<V>[][] getGraphNodesMatrix2() {
        return graphNodesMatrix2;
    }

    public void setGraphNodesMatrix2(GraphNode<V>[][] graphNodesMatrix2) {
        this.graphNodesMatrix2 = graphNodesMatrix2;
    }
}
