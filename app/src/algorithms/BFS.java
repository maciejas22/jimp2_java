package algorithms;

import graphStructure.Graph;
import graphStructure.Vertex;
import graphStructure.Edge;

import java.util.LinkedList;
import java.util.Queue;

public class BFS {
    public boolean BFS(Graph graf, int start_index){
        Queue<Vertex> queue = new LinkedList<>();
        LinkedList<Integer> is_visited = new LinkedList<>();
        for(int i = 0; i < graf.getRows()*graf.getColumns(); i++){
            is_visited.add(0);
        }

        is_visited.set(start_index, 1);
        queue.add(graf.adjacency_list.get(start_index));

        while(queue.size() != 0) {
            Vertex v = queue.remove();
            for(Edge e: v.getAdjacentEdges()){
                if(is_visited.get(e.getDestination().getIndex()) == 0){
                    is_visited.set(e.getDestination().getIndex(), 1);
                    queue.add(e.getDestination());
                }
            }
        }

        boolean all_visited = true;
        for(int n = graf.getRows() * graf.getColumns() - 1; n>=0; n--){
            if (is_visited.get(n) == 0) {
                all_visited = false;
                break;
            }
        }
        return all_visited;
    }

    public boolean isConnected(Graph graf){
        boolean is_visited = true;

        for(int i = 0; i < graf.getColumns()* graf.getRows(); i++){
            if(!BFS(graf, i)){
                is_visited = false;
            }
        }

        return is_visited;
    }
}
