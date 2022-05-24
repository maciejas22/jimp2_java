package menu;

import graphStructure.*;
import file_io.readFile;
import file_io.writeFile;
import algorithms.generator;
import algorithms.BFS;

import java.io.IOException;
import java.util.Scanner;
import java.io.File;

class HelloWorld {
    public static void main(String[] args) throws IOException{
        Graph graf = new readFile().readGraphFromFile("E:/jimp_java/app/src/menu/mojgraf.txt");
        graf.printGraph();
        boolean x = new BFS().isConnected(graf);
        System.out.print(x);
//        new writeFile().writeGraphToFile(graf, "wynik.txt");
//        Graph graf = new generator().generateGraph(2, 3, 1, 3);
//        graf.printGraph();
    }
}