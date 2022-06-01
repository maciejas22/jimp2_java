package file_io;

import graphStructure.Edge;
import graphStructure.Graph;
import graphStructure.Vertex;
import javafx.scene.shape.Path;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;

public class writeFile {
    public void writeGraphToFile(Graph graf, String file_name) throws IOException {
        FileWriter fileWriter = new FileWriter(file_name);
        PrintWriter printWriter = new PrintWriter(fileWriter);
        printWriter.print(graf.getRows() + " " + graf.getColumns() + "\n");

        for (Vertex v : graf.adjacency_list) {
            printWriter.print("\t");
            for (Edge e : v.getAdjacentEdges()) {
                printWriter.print(" " + e.getDestination().getIndex() + " :" + e.getWeight() + " ");
            }
            printWriter.print("\n");
        }
        printWriter.close();
    }
}
