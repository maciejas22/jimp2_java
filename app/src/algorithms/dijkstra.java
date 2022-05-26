package algorithms;

import graphStructure.Vertex;
import graphStructure.Edge;
import graphStructure.Graph;

import java.util.PriorityQueue;

public class dijkstra {

    private double distance[];
    private Vertex previous[];
    private PriorityQueue<Edge> prioqueue;
    private boolean is_visited[];

    public Vertex[] findPath(Graph graf, Vertex src){
        previous = new Vertex[graf.getColumns() * graf.getRows()];
        prioqueue = new PriorityQueue<Edge>(6, new Edge());
        is_visited = new boolean[graf.getColumns() * graf.getRows()];

        distance = new double[graf.getColumns() * graf.getRows()];
        for(int i = 0; i < graf.getColumns() * graf.getRows(); i++){
            distance[i] = Double.MAX_VALUE;
            previous[i] = new Vertex();
            for(int j = 0; j < graf.adjacency_list.get(i).getAdjacentEdges().size();j++)
                prioqueue.add(graf.adjacency_list.get(i).getAdjacentEdges().get(j));
        }
        distance[src.getIndex()] = 0;

        Edge current_edge;
        Vertex current_vertex = src;
        double tmp_distance = -1;

        while(!prioqueue.isEmpty()){
            current_edge = prioqueue.remove();
            for(Edge e: current_edge.getDestination().getAdjacentEdges()){
                tmp_distance = distance[current_edge.getDestination().getIndex()] + e.getWeight();
                if(tmp_distance < distance[e.getDestination().getIndex()]){
                    distance[e.getDestination().getIndex()] = tmp_distance;
                    previous[e.getDestination().getIndex()] = current_edge.getDestination();
                }
            }
        }
        return previous;
    }
}
