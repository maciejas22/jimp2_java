package graphStructure;

import java.util.LinkedList;

public class Graph {
    private final int rows;
    private final int columns;
    public LinkedList<Vertex> adjacency_list;

    public Graph(int rows, int columns) {
        this.rows = rows;
        this.columns = columns;
        this.adjacency_list = new LinkedList<>();
    }

    public void addVertex(Vertex index) {
        adjacency_list.add(index);
    }

    public void addEdge(Vertex src, Vertex dest, double weight) {
        src.getAdjacentEdges().add(new Edge(dest, weight));
    }

    public int getRows(){
        return rows;
    }
    public int getColumns(){
        return columns;
    }
    public void printGraph() {
        System.out.println(rows + " " + columns);
        for (Vertex v : adjacency_list) {
            System.out.print("\t");
            for (Edge e : v.getAdjacentEdges()) {
                System.out.print(" " + e.getDestination().getIndex() + " :" + e.getWeight() + " ");
            }
            System.out.print("\n");
        }
    }


}


