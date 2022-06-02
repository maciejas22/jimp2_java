package GUI;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
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
    private StackPane dijkstra_image = new StackPane();
    boolean is_graph_drawn = false;
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
        drawGraph(graf);
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

        int row0, row1, column0, column1;
        int x0, y0, x1, y1;
        Pane dots = new Pane();
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
            stack.getChildren().addAll(graph_image, dijkstra_image);
            windowForGraph.setContent(stack);
        }
    }

    @FXML
    void HelpClicked(ActionEvent event) {
        Info.appendText("POMOC\n");
    }


}
