package graphStructure;

import java.util.LinkedList;

public class Vertex {
    private int index;
    private LinkedList<Edge> adjacent_edges;

    public Vertex(){}
    public Vertex(int index) {
        this.index = index;
        this.adjacent_edges = new LinkedList<>();
    }

    public int getIndex(){
        return index;
    }
    public LinkedList<Edge> getAdjacentEdges(){
        return adjacent_edges;
    }
}
