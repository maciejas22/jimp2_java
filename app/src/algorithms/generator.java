package algorithms;

import graphStructure.Graph;
import file_io.writeFile;
import graphStructure.Vertex;

import java.io.IOException;
import java.util.Random;

public class generator {

    private double getRandomDouble(double min, double max){
        Random random = new Random();
        return random.nextDouble(max - min) + min;
    }

    public Graph generateGraph(int rows, int columns, double min_weight, double max_weight){
        Graph graf = new Graph(rows, columns);
        for(int i = 0; i < rows*columns; i++){
            graf.addVertex(new Vertex(i));
        }

        if(columns > 1){
            for(int level = 1; level <= rows; level++){
                for(int i = level*columns-columns; i < level*columns-1; i++){
                    graf.addEdge(graf.adjacency_list.get(i), graf.adjacency_list.get(i+1), getRandomDouble(min_weight, max_weight));
                    graf.addEdge(graf.adjacency_list.get(i+1), graf.adjacency_list.get(i), getRandomDouble(min_weight, max_weight));
                }
            }
        }

        if(rows > 1){
            for(int level = 0; level < columns; level++){
                for(int i = 0; i < rows-1; i++){
                    graf.addEdge(graf.adjacency_list.get(level + columns*i), graf.adjacency_list.get(level +columns*(i+1)), getRandomDouble(min_weight, max_weight));
                    graf.addEdge(graf.adjacency_list.get(level +columns*(i+1)), graf.adjacency_list.get(level + columns*i), getRandomDouble(min_weight, max_weight));
                }
            }
        }

        try{
            new writeFile().writeGraphToFile(graf, "wynik.txt");
        }
        catch(IOException e){
            e.printStackTrace();
        }

        return graf;
    }
}
