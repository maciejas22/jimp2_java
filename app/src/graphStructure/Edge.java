package graphStructure;

import graphStructure.Vertex;

public class Edge {
    private double weight;
    private Vertex destination;

    public Edge(Vertex destination, double weight){
        this.destination = destination;
        this.weight = weight;
    }

    public double getWeight(){
        return weight;
    }
    public Vertex getDestination(){
        return destination;
    }
}