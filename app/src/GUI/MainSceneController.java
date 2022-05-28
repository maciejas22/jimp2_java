import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
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
    void GenerujClicked(ActionEvent event) {
        //Stage mainWindow = (Stage) Wiersze.getScene().getWindow();
        //String tekst = Wiersze.getText();
        rows=Integer.parseInt(Wiersze.getText());
        columns=Integer.parseInt(Kolumny.getText());
        WeightMini=Double.parseDouble(WMini.getText());
        WeightMaxi=Double.parseDouble(WMax.getText());
        Info.appendText("Generujemy graf o wielkość "+rows+"x"+columns+"\n");
        Info.appendText("Minimalna waga wynasi: "+WeightMini+" zaś maxymalna: "+WeightMaxi+"\n");
        //System.out.println(tekst);
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
