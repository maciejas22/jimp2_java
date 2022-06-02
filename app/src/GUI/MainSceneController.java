package GUI;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
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
    private Boolean isConnected = null;

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
    private StackPane windowForGraph;

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
        isConnected = true;

        double diameterX, diameterY;
        int height = 590, width = 590;

        Pane nodes = new Pane(); Circle c;
        Pane edges = new Pane(); Line l;
        diameterX = width / ((2 * columns) - 1);
        diameterY = height / ((2 * rows) - 1);
        for(double i = 5+diameterX/2; i < width; i+=diameterX*2){
            for(double j = 5+diameterY/2; j < height; j+=diameterY*2){
                c = new Circle(i, j, Math.min(diameterX,diameterY)/2);
                c.setFill(Color.WHITE);
                c.setStroke(Color.BLACK);
                nodes.getChildren().add(c);

                
                l = new Line(5+diameterX/2, j, width-diameterX/2, j);
                edges.getChildren().add(l);
            }
            l = new Line(i, 5+diameterY/2, i, height-diameterY/2 );
            edges.getChildren().add(l);
        }
        
        windowForGraph.getChildren().clear();
        windowForGraph.getChildren().addAll(edges, nodes);

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
        }
        else{
            Info.appendText("Wprowadz nazwe pliku.\n");
            return;
        }
        ReadFile=PlikTxt.getText();
        isConnected = null;
    }

    @FXML
    void BFSClicked(ActionEvent event) {
        if(graf != null){
            Info.appendText("Wykonujemy algorytm BFS\n");
            isConnected = new BFS().isConnected(graf);
            if(isConnected){
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
        
        
        if(isConnected == null){
            Info.appendText("Najpierw wykonaj algorytm BFS! \n");
            return;
        }
        else if(!isConnected){
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

        int diameterX, diameterY;
        int height = 590, width = 590;
        double x0,x1,y0,y1;

        diameterX = width / ((2 * columns) - 1);
        diameterY = height / ((2 * rows) - 1);

        int wiersze0, kolumny0, wiersze1, kolumny1;

        for(int i=0;i<trasa.size()-1;i++) {
            wiersze0=trasa.get(i)/columns;
            kolumny0=trasa.get(i)%columns;
            wiersze1=trasa.get(i+1)/columns;
            kolumny1=trasa.get(i+1)%columns;

            x0=5+kolumny0*diameterX*2 + diameterX/2;
            y0=5+wiersze0*diameterY*2 + diameterY/2;

            x1=5+kolumny1*diameterX*2 + diameterX/2;
            y1=5+wiersze1*diameterY*2 + diameterY/2;

            Circle q= new Circle(x0,y0,Math.min(diameterX,diameterY)/2);
            q.setFill(Color.RED);
            Circle q1= new Circle(x1,y1,Math.min(diameterX,diameterY)/2);
            q1.setFill(Color.RED);
            Pane dot = new Pane();

            Line l1=new Line(x0,y0,x1,y1);
            l1.setStroke(Color.RED);
            Pane arm = new Pane();

            dot.getChildren().add(q);
            dot.getChildren().add(q1);
            arm.getChildren().add(l1);
            windowForGraph.getChildren().addAll(arm, dot);
        }

    }

    @FXML
    void HelpClicked(ActionEvent event) {
        Info.appendText("POMOC\n");
    }   
    
}

