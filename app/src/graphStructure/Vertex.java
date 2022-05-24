package graphStructure;

import java.util.LinkedList;
import graphStructure.Edge;

public class Vertex {
    private int index;
    private LinkedList<Edge> adjacent_edges;

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
