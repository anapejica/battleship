
package gui;

import communication.ConnectionHandler;
import domain.Brod;
import java.net.URL;
import java.util.HashMap;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextArea;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

/**
 * FXML Kontroler class
 *
 * @author anape
 */
public class FXMLDocumentController implements Initializable {

    Stage stage;
    Kontroler kontroler;

    @FXML
    public GridPane serverMap;
    @FXML
    public GridPane userMap;
    @FXML
    public Button confirmButton;
    @FXML
    public Label enemyBoardLbl;
    @FXML
    public TextArea statusTxtArea;
    @FXML
    public Label statusLbl;
    @FXML
    public ComboBox shipsList;

    public Stage getStage() {
        return stage;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    @FXML
    public MenuBar MainMenu;
    @FXML
    public Menu game;
    @FXML
    public MenuItem newGame;
    @FXML
    public Menu gameRules;
    @FXML
    public MenuItem rules;
    @FXML
    public MenuItem authorInformation;
    @FXML
    public MenuItem scoreboard;
    @FXML
    public Menu exitGame;
    @FXML
    public MenuItem exit;
    @FXML
    public ImageView boatImage;
    @FXML
    public Label titleLabel;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        kontroler = new Kontroler(this);
        kontroler.setInitialState();
    }

    @FXML
    public void newGame(ActionEvent event) {
    }

    @FXML
    public void showRules(ActionEvent event) {
    }

    @FXML
    public void showAuthorInformation(ActionEvent event) {
    }

    @FXML
    public void showScoreboard(ActionEvent event) {
    }

    @FXML
    public void exitGame(ActionEvent event) {
    }

    @FXML
    public void handleEnemyGridCellButtonFired(MouseEvent event) {
        this.kontroler.handleEnemyGridCellButtonFired(event);
    }

    @FXML
    public void handleYourGridCellButtonFired(MouseEvent event) {
        this.kontroler.handleYourGridCellButtonFired(event);
    }

    @FXML
    public void selectShip(ActionEvent event) {
    }

    @FXML
    public void confirmFormation(ActionEvent event) {
        this.kontroler.confirmFormation(event);
    }

}
