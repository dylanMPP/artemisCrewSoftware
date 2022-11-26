package com.example.artemiscrewsoftware.graph;

import graph.Graph;
import graph.GraphNode;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class GraphTest {
    public Graph<Integer> graph;
    void stageScene(){
        graph = new Graph<>("adjacency_list",0,0);


    }

    void stageScene2(){
        graph.addVertex(new GraphNode<>(1,4));
        graph.addVertex(new GraphNode<>(2,3));
        graph.addVertex(new GraphNode<>(3,3));
        graph.addVertex(new GraphNode<>(4,3));
        graph.addVertex(new GraphNode<>(5,3));
        graph.addVertex(new GraphNode<>(6,5));

        graph.addEdge(100,graph.graphNodesAdjacencyList.get(0),graph.graphNodesAdjacencyList.get(1));
        graph.addEdge(50,graph.graphNodesAdjacencyList.get(1),graph.graphNodesAdjacencyList.get(2));
        graph.addEdge(200,graph.graphNodesAdjacencyList.get(1),graph.graphNodesAdjacencyList.get(3));
        graph.addEdge(300,graph.graphNodesAdjacencyList.get(2),graph.graphNodesAdjacencyList.get(4));


    }

    @Test
    void addVertex1() {
        stageScene();
        assertEquals(1,graph.graphNodesAdjacencyList.size());

        graph.addVertex(new GraphNode<>(1,1));
        graph.addVertex(new GraphNode<>(2,2));
        graph.addVertex(new GraphNode<>(3,3));

        assertEquals(4,4);
    }

    @Test
    void addVertex2() {
        stageScene();
        assertEquals(1,graph.graphNodesAdjacencyList.size());

        graph.addVertex(new GraphNode<>(4,1));
        graph.addVertex(new GraphNode<>(5,2));
        graph.addVertex(new GraphNode<>(6,3));

        assertEquals(4,4);

        graph.addVertex(new GraphNode<>(6,4));
        assertEquals(1,graph.graphNodesAdjacencyList.get(0).getValue());
    }

    @Test
    void addVertex3() {
        stageScene();
        assertEquals(0,graph.graphNodesAdjacencyList.size());
        stageScene2();
        assertEquals(5,graph.graphNodesAdjacencyList.size());
    }

    @Test
    void addEdge() {
        stageScene();
        stageScene2();

        assertEquals(2, graph.searchVertex(2).getEdgesAdjacencyList().size());

    }
    @Test
    void addEdge2() {
        stageScene();
        stageScene2();

        assertEquals(2, graph.searchVertex(2).getEdgesAdjacencyList().size());
        assertEquals(1, graph.searchVertex(1).getEdgesAdjacencyList().size());

    }

    @Test
    void addEdge3() {
        stageScene();
        stageScene2();

        assertEquals(2, graph.searchVertex(2).getEdgesAdjacencyList().size());
        assertEquals(1, graph.searchVertex(1).getEdgesAdjacencyList().size());
        assertEquals(0, graph.searchVertex(4).getEdgesAdjacencyList().size());
    }


    @Test
    void searchVertex() {
        stageScene();
        stageScene2();
        assertEquals(2,graph.searchVertex(2).getValue());
        assertEquals(5,graph.searchVertex(5).getValue());
    }
    @Test
    void searchVertex2() {
        stageScene();
        stageScene2();
        assertEquals(2,graph.searchVertex(2).getValue());
        assertEquals(5,graph.searchVertex(5).getValue());
        assertNull(graph.searchVertex(10));
        assertNull(graph.searchVertex(20));
    }
    @Test
    void searchVertex3() {
        stageScene();
        stageScene2();
        assertEquals(1,graph.searchVertex(1).getValue());
        assertEquals(2,graph.searchVertex(2).getValue());
        assertEquals(3,graph.searchVertex(3).getValue());
        assertEquals(4,graph.searchVertex(4).getValue());
        assertEquals(5,graph.searchVertex(5).getValue());
        assertNull(graph.searchVertex(10));
        assertNull(graph.searchVertex(20));
    }

    @Test
    void searchEdge() {
        stageScene();
        stageScene2();

        assertEquals(100, graph.searchEdge(1,2).getWeight());
        assertEquals(50, graph.searchEdge(2,3).getWeight());

    }
    @Test
    void searchEdge2() {
        stageScene();
        stageScene2();
        assertEquals(100, graph.searchEdge(1,2).getWeight());
        assertEquals(50, graph.searchEdge(2,3).getWeight());
        assertEquals(100, graph.searchEdge(1,2).getWeight());
        assertEquals(300, graph.searchEdge(3,5).getWeight());
    }
    @Test
    void searchEdge3() {
        stageScene();
        stageScene2();
        assertEquals(100, graph.searchEdge(1,2).getWeight());
        assertEquals(50, graph.searchEdge(2,3).getWeight());
        assertEquals(100, graph.searchEdge(1,2).getWeight());
        assertEquals(300, graph.searchEdge(3,5).getWeight());
        assertNull( graph.searchEdge(4,3));
        assertNull( graph.searchEdge(3,1));

    }

    @Test
    void BFS() {

    }

    @Test
    void DFS() {
        stageScene();
        stageScene2();
        assertEquals(2, graph.DFS());
        graph.addVertex(new GraphNode<>(7,3));
        graph.addVertex(new GraphNode<>(8,6));
        graph.addEdge(700,graph.graphNodesAdjacencyList.get(6),graph.graphNodesAdjacencyList.get(7));
        assertEquals(3, graph.DFS());
    }
    @Test
    void DFS2() {
        stageScene();

        assertEquals(0, graph.DFS()); // Is no conected
        stageScene();
        stageScene2();
        assertEquals(2, graph.DFS());

    }
    @Test
    void DFS3() {
        stageScene();
        assertEquals(0, graph.DFS()); // Is no conected
        graph.addVertex(new GraphNode<>(8,6));
        graph.addVertex(new GraphNode<>(9,6));
        graph.addVertex(new GraphNode<>(10,6));
        graph.addEdge(200,graph.searchVertex(8),graph.searchVertex(9));
        assertEquals(2, graph.DFS());
    }

    @Test
    void dijkstra() {
        stageScene();
        stageScene2();

        graph.dijkstra(graph.searchVertex(2));
        graph.addEdge(100,graph.graphNodesAdjacencyList.get(1),graph.graphNodesAdjacencyList.get(2));
        graph.addEdge(50,graph.graphNodesAdjacencyList.get(2),graph.graphNodesAdjacencyList.get(4));
        graph.addEdge(200,graph.graphNodesAdjacencyList.get(3),graph.graphNodesAdjacencyList.get(1));
        graph.addEdge(300,graph.graphNodesAdjacencyList.get(0),graph.graphNodesAdjacencyList.get(3));


        assertEquals("4",graph.getShortestPathTo(graph.searchVertex(4)));

    }

    @Test
    void dijkstra2() {
        stageScene();
        stageScene2();

        graph.dijkstra(graph.searchVertex(3));
        graph.addEdge(100,graph.graphNodesAdjacencyList.get(1),graph.graphNodesAdjacencyList.get(2));
        graph.addEdge(25,graph.graphNodesAdjacencyList.get(2),graph.graphNodesAdjacencyList.get(4));
        graph.addEdge(200,graph.graphNodesAdjacencyList.get(3),graph.graphNodesAdjacencyList.get(1));
        graph.addEdge(100,graph.graphNodesAdjacencyList.get(0),graph.graphNodesAdjacencyList.get(3));


        assertEquals("3",graph.getShortestPathTo(graph.searchVertex(5)));

    }

    @Test
    void floydWarshall() {
        stageScene();
        stageScene2();

    }

    @Test
    void prim() {
        stageScene();
        stageScene2();
        assertEquals(650, graph.prim(graph.searchVertex(1)));
    }
    @Test
    void prim2() {
        stageScene();
        stageScene2();
        assertEquals(650, graph.prim(graph.searchVertex(1)));
        assertEquals(450, graph.prim(graph.searchVertex(4)));


    }


    @Test
    void kruskal() {
        stageScene();
        Integer[] edges = {2,4,5};
        Integer[] edges1 = {1,2,3};
        Integer[] edges2 = {4,1,8};
        Integer[] vertices = {1,null};
        Integer[] vertices1 = {2,null};
        Integer[] vertices2 = {4,null};

        ArrayList<Integer[]> edgesarray = new ArrayList<>();
        ArrayList<Integer[]> verticesarray = new ArrayList<>();

        edgesarray.add(edges);
        edgesarray.add(edges1);
        edgesarray.add(edges2);
        verticesarray.add(vertices);
        verticesarray.add(vertices1);
        verticesarray.add(vertices2);

        ArrayList<Integer[]> arrayExpected = new ArrayList<>();

        ArrayList<Integer[]> array = graph.kruskal(verticesarray,edgesarray);
        String path = "";
        for (int i = 0; i < array.size(); i++) {
            for (int j = 0; j < array.get(i).length; j++) {
                path += array.get(i)[j];

            }

        }

        String pathFinal = "123245"; // This is the minimal path possible

        assertEquals(pathFinal,path);


    }
    @Test
    void kruskal2() {
        stageScene();
        Integer[] edges = {2,4,5};
        Integer[] edges1 = {1,2,3};
        Integer[] edges2 = {4,1,8};
        Integer[] edges3 = {4,6,3};
        Integer[] edges4 = {6,5,4};
        Integer[] vertices = {1,null};
        Integer[] vertices1 = {2,null};
        Integer[] vertices2 = {4,null};
        Integer[] vertices3 = {5,null};
        Integer[] vertices4 = {6,null};

        ArrayList<Integer[]> edgesarray = new ArrayList<>();
        ArrayList<Integer[]> verticesarray = new ArrayList<>();

        edgesarray.add(edges);
        edgesarray.add(edges1);
        edgesarray.add(edges2);
        edgesarray.add(edges3);
        edgesarray.add(edges4);

        verticesarray.add(vertices);
        verticesarray.add(vertices1);
        verticesarray.add(vertices2);
        verticesarray.add(vertices3);
        verticesarray.add(vertices4);

        ArrayList<Integer[]> arrayExpected = new ArrayList<>();

        ArrayList<Integer[]> array = graph.kruskal(verticesarray,edgesarray);
        String path = "";
        for (int i = 0; i < array.size(); i++) {
            for (int j = 0; j < array.get(i).length; j++) {
                path += array.get(i)[j];

            }

        }

        String pathFinal = "463123654245"; // This is the minimal path possible

        assertEquals(pathFinal,path);


    }
}
