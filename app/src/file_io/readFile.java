package file_io;

import graphStructure.Graph;
import graphStructure.Vertex;

import java.io.IOException;
import java.util.Scanner;
import java.io.File;

public class readFile {

    public Graph readGraphFromFile(String file_path) throws IOException {

        Scanner scan = new Scanner(new File(file_path));

        int rows = scan.nextInt();
        int columns = scan.nextInt();
        Graph graf = new Graph(rows, columns);
        for(int i = 0; i < rows * columns; i++) {
            graf.adjacency_list.add(new Vertex(i));
        }

        String line = scan.nextLine();
        int src_vertex = -1;
        int dest_vertex = -1;
        double edge_weight = -1;

        while (scan.hasNextLine()) {
            src_vertex++;
            line = scan.nextLine();

            line = line.replace("\t", "");
            line = line.replace(":", " ");
            while(!('0' <= line.charAt(0) && line.charAt(0) <= '9')) {
                line = line.substring(1);
            }
            String[] arr = line.split("  ");

            for (int i = 0; i < arr.length; i++) {
                if (i % 2 == 0) {
                    dest_vertex = Integer.parseInt(arr[i]);
                } else {
                    edge_weight = Double.parseDouble(arr[i]);
                    graf.addEdge(graf.adjacency_list.get(src_vertex), graf.adjacency_list.get(dest_vertex), edge_weight);
                }
            }

        }
        return graf;
    }

}