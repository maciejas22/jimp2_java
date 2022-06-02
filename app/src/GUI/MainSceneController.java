package GUI;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;

import graphStructure.Graph;
import graphStructure.Vertex;
import algorithms.BFS;
import algorithms.generator;
import file_io.readFile;
import file_io.writeFile;

public class MainSceneController {

    private int rows,columns,DixtraStart,DixtraEnd;
    private double WeightMini,WeightMaxi;
    private String SavedFile, ReadFile;
    private Graph graf = null;
    private int isConnected = -1;

    @FXML
    private TextField DoWDix;

    @FXML
    private TextField Kolumny;

    @FXML
    private TextField PlikTxt;

    @FXML
    private TextField PlikZapisz;

    @FXML
    private TextField WMax;

    @FXML
    private TextField WMini;

    @FXML
    private TextField Wiersze;

    @FXML
    private TextField ZWDix;

    @FXML
    private TextArea Info;

    @FXML
    private AnchorPane root;

    @FXML 
    private ScrollPane windowForGraph;

    private StackPane graph_image = new StackPane();
    boolean is_graph_drawn = false;
    private StackPane dijkstra_image = new StackPane();
    boolean is_dijkstra_drawn = false;
    private final int circleRadius = 10;

    void drawGraph(Graph graf){
        double x, y;
        Pane edges = new Pane();    Line l;
        Pane nodes = new Pane();    Circle c;
        
        x = -2*circleRadius;
        for(int i = 0; i < graf.getColumns(); i++){
            x += 4*circleRadius;
            y = 2*circleRadius;
            for(int j = 0; j < graf.getRows(); j++){
                c = new Circle(x, y, circleRadius);
                c.setFill(Color.WHITE);
                c.setStroke(Color.BLACK);
                nodes.getChildren().add(c);

                l = new Line(2*circleRadius, y, (2*graf.getColumns()-1)*circleRadius*2, y);
                edges.getChildren().add(l);

                y += 4*circleRadius;
            }
            l = new Line(x ,2*circleRadius, x, (2*graf.getRows()-1)*circleRadius*2);
            edges.getChildren().add(l);
        }

        graph_image.getChildren().clear();
        graph_image.getChildren().addAll(edges, nodes);
        windowForGraph.setContent(graph_image);
        is_graph_drawn = true;
    }

    void drawDijktra(Graph graf, ArrayList<Integer> trasa){
        int row0, row1, column0, column1;
        int x0, y0, x1, y1;
        Pane dots = new Pane(); dots.getChildren().clear();
        Pane arm = new Pane();
        if(is_graph_drawn){
            for(int i = 0; i < trasa.size()-1; i++){
                row0 = trasa.get(i)/columns;
                column0 = trasa.get(i)%columns;
                row1 = trasa.get(i+1)/columns;
                column1 = trasa.get(i+1)%columns;
                
                x0 = 2*circleRadius + 4*circleRadius*column0;
                y0 = 2*circleRadius + 4*circleRadius*row0;
                x1 = 2*circleRadius + 4*circleRadius*column1;
                y1 = 2*circleRadius + 4*circleRadius*row1;

                Circle cir1 = new Circle(x0, y0, circleRadius);
                cir1.setFill(Color.RED);
                Circle cir2 = new Circle(x1, y1, circleRadius);
                cir2.setFill(Color.RED);
                dots.getChildren().addAll(cir1, cir2);

                Line line = new Line(x0, y0, x1, y1);
                line.setStroke(Color.RED);
                arm.getChildren().add(line);
            }

            dijkstra_image.getChildren().clear();
            dijkstra_image.getChildren().addAll(arm, dots);

            StackPane stack = new StackPane();
            if(vertex1 != null && vertex2 != null){
                stack.getChildren().addAll(graph_image, dijkstra_image, vertex1, vertex2);
                windowForGraph.setContent(stack);
            }
            else {
                stack.getChildren().addAll(graph_image, dijkstra_image);
                windowForGraph.setContent(stack);
            }
            is_dijkstra_drawn = true;
        }
    }

    @FXML
    void GenerujClicked(ActionEvent event) throws IOException {
        if(!Kolumny.getText().isEmpty() && !Wiersze.getText().isEmpty() && !WMax.getText().isEmpty() && !WMini.getText().isEmpty()){
            try {
            columns = Integer.parseInt(Kolumny.getText());
            rows = Integer.parseInt(Wiersze.getText());
            WeightMaxi = Double.parseDouble(WMax.getText());
            WeightMini = Double.parseDouble(WMini.getText());
            }
            catch(NumberFormatException e){
                Info.setText("Wprowadzono niepoprawne dane");
                return;
            }
        }
        else{
            Info.setText("Wprowadz wszystkie dane.\n");
            return;
        }
        
        if(columns < 1 || rows < 1 || WeightMaxi < 0 || WeightMini < 0 || WeightMaxi < WeightMini){
            Info.setText("Wprowadzone dane są niepoprawne.\n");
            return;
        }

        Info.appendText("Generujemy graf o wielkość "+rows+"x"+columns+"\n");
        Info.appendText("Minimalna waga wynasi: "+WeightMini+" zaś maxymalna: "+WeightMaxi+"\n");
        graf = new generator().generateGraph(rows, columns, WeightMini, WeightMaxi);
        isConnected = 1;
        dijkstra_image.getChildren().clear();
        drawGraph(graf);
        vertex1 = null; vertex2 = null;
        vertex_list[0] = -1; vertex_list[1] = -1;
        is_dijkstra_drawn = false;
    }

    @FXML
    void ZapiszClicked(ActionEvent event) throws IOException {
        if(!PlikZapisz.getText().isEmpty()){
            SavedFile = PlikZapisz.getText();
        }
        else{
            Info.appendText("Wprowadz nazwe pliku.\n");
            return;
        }

        if(!SavedFile.contains(".")){
            SavedFile += ".txt";
        }

        if(graf != null){
           new writeFile().writeGraphToFile(graf, SavedFile);
           Info.appendText("Graf został zapisany do pliku o nazwie: "+ SavedFile+ "\n");
           new file_io.writeFile().writeGraphToFile(graf, SavedFile);
        }
        else {
            Info.appendText("Najpierw wygeneruj graf! \n");
        }
    }

    @FXML
    void WczytajClicked(ActionEvent event) throws IOException {
        if(!PlikTxt.getText().isEmpty()){
            ReadFile = PlikTxt.getText();
            Info.appendText("Wczytuje graf z pliku o nazwie: "+ReadFile+"\n");
            graf = new readFile().readGraphFromFile(ReadFile);
            Info.appendText("Wczytano graf o wielkości "+graf.getRows()+"x"+graf.getColumns()+"\n");
        }
        else{
            Info.appendText("Wprowadz nazwe pliku.\n");
            return;
        }
        ReadFile=PlikTxt.getText();
        isConnected = -1;
        is_graph_drawn = false;
        rows = graf.getRows(); columns = graf.getColumns();

        graph_image.getChildren().clear();
        windowForGraph.setContent(graph_image);
        is_dijkstra_drawn = false;
    }

    @FXML
    void BFSClicked(ActionEvent event) {
        if(isConnected == 1){
            Info.appendText("Graf jest spójny\n");
            return;
        }
        else if(isConnected == 0){
            Info.appendText("Graf jest niespójny\n");
            return;
        }
        
        if(graf != null){
            Info.appendText("Wykonujemy algorytm BFS\n");
            isConnected = new BFS().isConnected(graf) ? 1 : 0;
            if(isConnected == 1){
                drawGraph(graf);
                Info.appendText("Graf jest spójny\n");
            }
            else {
                Info.appendText("Graf nie jest spójny\n");
            }
        }
        else {
            Info.appendText("Najpierw wygeneruj graf! \n");
        }
    }

    @FXML
    void DijkstraClicked(ActionEvent event) {
        if(vertex_list[0] != -1 && vertex_list[1] != -1 && is_dijkstra_drawn == false) {
            DixtraStart = vertex_list[0];
            DixtraEnd = vertex_list[1];
            dijkstra_image.getChildren().clear();
        }
        else{
            vertex1 = null;
            vertex2 = null;
            if(ZWDix.getText().isEmpty() || DoWDix.getText().isEmpty()){
                Info.appendText("Wprowadz wszystkie dane.\n");
                return;
            }
            try {
                DixtraStart = Integer.parseInt(ZWDix.getText());
                DixtraEnd = Integer.parseInt(DoWDix.getText());
            }
            catch(NumberFormatException e){
                Info.appendText("Wprowadzono niepoprawne dane.\n");
                return;
            }
        }

        if(isConnected == -1){
            Info.appendText("Najpierw wykonaj algorytm BFS! \n");
            return;
        }
        else if(isConnected == 0){
            Info.appendText("Graf nie jest spójny! \n");
            return;
        }
        
        if(graf != null){
            Info.appendText("Wykonujemy algorytm Dijkstry\n");
        }
        else {
            Info.appendText("Najpierw wygeneruj graf! \n");
            return;
        }

        if(DixtraStart < 0 || DixtraStart >= graf.adjacency_list.size() || DixtraEnd < 0 || DixtraEnd >= graf.adjacency_list.size()){
            Info.appendText("Wprowadzono niepoprawne dane.\n");
            return;
        }

        ArrayList<Integer> trasa = new ArrayList<Integer>();
        Vertex[] path = new algorithms.dijkstra().findPath(graf, graf.adjacency_list.get(DixtraStart));
        Vertex current = graf.adjacency_list.get(DixtraEnd);
        while(current != graf.adjacency_list.get(DixtraStart)){
            trasa.add(current.getIndex());
            current = path[current.getIndex()];
        }
        trasa.add(DixtraStart);
        Collections.reverse(trasa);

        Info.appendText("Droga z wieszchołka nr "+DixtraStart+" do wieszchołka nr "+DixtraEnd+" \n");
        for(int i = 0; i < trasa.size(); i++){
            Info.appendText(trasa.get(i)+" ");
        }
        Info.appendText("\n");

        drawDijktra(graf, trasa);
    }

    private int[] vertex_list = {-1, -1};
    @FXML
    void click(MouseEvent e1){
        double last_clicked_x = e1.getX();
        double last_clicked_y = e1.getY();
        
        if(is_graph_drawn && !is_dijkstra_drawn) {
            int[] res = color_clicked_vertex(last_clicked_x, last_clicked_y);
            if(vertex_list[0] == -1){
                vertex_list[0] = res[0] + res[1] * columns;
            }
            else if(vertex_list[1] == -1){
                vertex_list[1] = res[0] + res[1] * columns;
            }
        }
    }

    Pane vertex1 = null;
    Pane vertex2 = null;
    boolean drawn1 = false;
    boolean drawn2 = false;
    Pane tmp = new Pane();
    int[] color_clicked_vertex(double x, double y){
        x -= 5; y -= 5;
        int c = (int) x / (4*circleRadius);
        int r = (int) y / (4*circleRadius);   

        double circle_center_x = c * 4*circleRadius + 10 + circleRadius;
        if(circle_center_x > (2*columns-1)*2*circleRadius) {
            circle_center_x = (2*columns-1)*2*circleRadius;
        }
        
        double circle_center_y = r * 4*circleRadius + 10 + circleRadius;
        if(circle_center_y > (2*rows-1)*2*circleRadius) {
            circle_center_y = (2*rows-1)*2*circleRadius;
        }

        Circle selected = new Circle(circle_center_x, circle_center_y, circleRadius);
        selected.setFill(Color.RED);

        if(vertex1 == null){
            vertex1 = new Pane();
            vertex1.getChildren().clear();
            vertex1.getChildren().add(selected);
            tmp.getChildren().addAll(graph_image, dijkstra_image, vertex1);
            drawn1 = true;
        }
        else if(vertex2 == null){
            vertex2 = new Pane();
            vertex2.getChildren().clear();
            vertex2.getChildren().add(selected);
            tmp.getChildren().clear();
            tmp.getChildren().addAll(graph_image, dijkstra_image, vertex1, vertex2);
            drawn2 = true;
        }
        windowForGraph.setContent(tmp);

        return new int[]{(int)(circle_center_x-circleRadius-10)/(4*circleRadius-1), (int)(circle_center_y-circleRadius-10)/(4*circleRadius-1)};
    }

    @FXML
    void HelpClicked(ActionEvent event) {
        Info.appendText("--------POMOC--------\n");
        Info.appendText("Zakladka \"Generator\" pozwoli ci wygenerować graf dowolnej wielkości! \n");
        Info.appendText("Generowanie grafów mających więcej niż 20k wierzhołków może zająć dużo czasu. \n");
        Info.appendText("Program pozwala także wczytać oraz zapisać graf do pliku tekstowego.\n");
        Info.appendText("W przypadku gdy nie ustawisz rozszerzenia pliku przy zapisywaniu grafu zostanie wybrane domyślnie rozszerzenie txt.\n");
        Info.appendText("Przycisk \"BFS\" sprawdza czy graf jest spójny - czyli czy każdą parę wierzhołków łączy jakaś ścieżka. \n");
        Info.appendText("Za pomocą przycisku \"Dijkstra\" najdziesz najkótszą ścieżkę między dowolną parą wierzchołków.\n");
        Info.appendText("Parę wierzhołków można wskazać kursorem!\n");
    }

}
