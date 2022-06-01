package graphStructure;

import java.util.Comparator;

public class Edge implements Comparator<Edge> {
    private double weight;
    private Vertex destination;

    public Edge(){}
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

    @Override public int compare(Edge e1, Edge e2){
        if(e1.getWeight() < e2.getWeight()){
            return -1;
        }
        else if(e1.getWeight() > e2.getWeight()){
            return 1;
        }

        return 0;
    }
}