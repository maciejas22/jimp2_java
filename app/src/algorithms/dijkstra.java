package algorithms;

import graphStructure.Vertex;
import graphStructure.Edge;
import graphStructure.Graph;

import java.util.PriorityQueue;

public class dijkstra {

    private double[] distance;
    private Vertex[] previous;
    private PriorityQueue<Edge> prioqueue;

    public double[] findPath(Graph graf, Vertex src){
        previous = new Vertex[graf.getColumns() * graf.getRows()];
        prioqueue = new PriorityQueue<Edge>(6, new Edge());

        distance = new double[graf.getColumns() * graf.getRows()];
        for(int i = 0; i < graf.getColumns() * graf.getRows(); i++){
            distance[i] = Double.MAX_VALUE;
            previous[i] = new Vertex();
        }
        distance[src.getIndex()] = 0;

        Edge current_edge;
        double tmp_distance = -1;
        prioqueue.add(new Edge(src, 0));

        while(!prioqueue.isEmpty()){
            current_edge = prioqueue.remove();
            for(Edge e: current_edge.getDestination().getAdjacentEdges()){
                tmp_distance = distance[current_edge.getDestination().getIndex()] + e.getWeight();
                if(tmp_distance < distance[e.getDestination().getIndex()]){
                    distance[e.getDestination().getIndex()] = tmp_distance;
                    previous[e.getDestination().getIndex()] = current_edge.getDestination();
                    prioqueue.add(e);
                }
            }
        }
        return distance;
    }
}
