package GUI;
import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.stage.Stage;

public class MainSceneController {

    int rows,columns,DixtraStart,DixtraEnd;
    double WeightMini,WeightMaxi;
    String SavedFile, ReadFile;


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
        rows=Integer.parseInt(Wiersze.getText());
        columns=Integer.parseInt(Kolumny.getText());
        WeightMini=Double.parseDouble(WMini.getText());
        WeightMaxi=Double.parseDouble(WMax.getText());
        Info.appendText("Generujemy graf o wielkość "+rows+"x"+columns+"\n");
        Info.appendText("Minimalna waga wynasi: "+WeightMini+" zaś maxymalna: "+WeightMaxi+"\n");

        int diameterX, diameterY;
        int height = 590, width = 590;

        Pane nodes = new Pane(); Circle c;
        Pane edges = new Pane(); Line l;
        diameterX = width / ((2 * columns) - 1);
        diameterY = height / ((2 * rows) - 1);
        for(int i = 5+diameterX/2; i < width; i+=diameterX*2){
            for(int j = 5+diameterY/2; j < height; j+=diameterY*2){
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
    void ZapiszClicked(ActionEvent event) {
        SavedFile=PlikZapisz.getText();
        Info.appendText("Graf został zapisany do pliku o nazwie: "+SavedFile+".txt \n");
    }

    @FXML
    void WczytajClicked(ActionEvent event) {
        ReadFile=PlikTxt.getText();
        Info.appendText("Graf został wczytany z pliku o nazwie: "+ReadFile+"\n");
    }

    @FXML
    void BFSClicked(ActionEvent event) {

    }

    @FXML
    void DijkstraClicked(ActionEvent event) {
        DixtraStart=Integer.parseInt(ZWDix.getText());
        DixtraEnd=Integer.parseInt(DoWDix.getText());
        Info.appendText("Droga z wieszchołka nr "+DixtraStart+" do wieszchołka nr "+DixtraEnd+" ...\n");
    }

    @FXML
    void HelpClicked(ActionEvent event) {
        Info.appendText("POMOC\n");
    }


}
