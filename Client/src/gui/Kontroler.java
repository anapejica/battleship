
package gui;

import communication.ConnectionHandler;
import domain.Koordinate;
import domain.Map;
import domain.RankItem;
import domain.Brod;
import util.ResponseStatus;
import domain.User;
import gui.handlers.PodaciOAutoruHandler;
import gui.handlers.ExitHandler;
import gui.handlers.NovaIgraHandler;
import gui.handlers.PravilaHandler;
import gui.handlers.RangListaHandler;
import gui.handlers.BiranjeBrodaHandler;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.LinkedList;
import java.util.List;
import java.util.Properties;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import transfer.Request;
import transfer.Response;
import util.GameStatus;
import util.Messages;
import util.Operation;
import util.Brodovi;

/**
 *
 * @author anape
 */
public class Kontroler {

    User user;
    Map yourMap;
    GameStatus gameStatus;
    private LinkedList<Brod> ships;
    private Brod currentSelectedShip;
    private final ConnectionHandler connectionHandler;
    FXMLDocumentController document;
    private final OperationHandler operationHandler;

    public ConnectionHandler getConnectionHandler() {
        return connectionHandler;
    }

    public Kontroler(FXMLDocumentController document) {
        this.document = document;
        connectionHandler = new ConnectionHandler("localhost", 9999);
        operationHandler = new OperationHandler(this);
        operationHandler.start();

        Request request = new Request();
        request.setUser(MainMenu.user);
        request.setOperation(Operation.LOGIN);
        connectionHandler.getRequests().add(request);

        this.document.newGame.setOnAction(new NovaIgraHandler(this));
        this.document.authorInformation.setOnAction(new PodaciOAutoruHandler(this));
        this.document.shipsList.setOnAction(new BiranjeBrodaHandler(this));
        this.document.exitGame.setOnAction(new ExitHandler(this));
        this.document.scoreboard.setOnAction(new RangListaHandler(this));
        this.document.rules.setOnAction(new PravilaHandler(this));
    }

    public void exit() {
        this.document.stage.close();
        Platform.exit();
        System.exit(0);

    }

    public void novaIgra() {
        Request request = new Request();
        request.setUser(user);
        request.setOperation(Operation.KREIRAJ_IGRU);
        connectionHandler.getRequests().add(request);
    }

    public void prikaziPodatkeOAutoru() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setContentText("Ana Pejica");
        alert.setTitle("Informacije o autoru");
        alert.setHeaderText(null);
        alert.showAndWait();
    }

    public void close() {
        if (connectionHandler != null) {
            connectionHandler.closeConnection();
        }
    }

    void setInitialState() {
        this.document.boatImage.setVisible(true);
        this.document.titleLabel.setVisible(true);
        this.document.confirmButton.setVisible(false);
        this.document.userMap.setVisible(false);
        this.document.serverMap.setVisible(false);
        this.document.enemyBoardLbl.setVisible(false);
        this.document.statusTxtArea.setVisible(false);
        this.document.statusLbl.setVisible(false);
    }

    public void seTGridIsDisable(GridPane grid, boolean isDisable) {
        grid.getChildren().forEach((node) -> {
            node.setDisable(isDisable);
        });
    }

    void handleYourGridCellButtonFired(MouseEvent event) {
        switch (gameStatus) {
            case POSTAVLJANJE_BRODOVA:
                if (!ships.isEmpty() && currentSelectedShip != null) {
                    Node node = (Node) event.getSource();
                    // We get coordinates of field clicked
                    Koordinate coordinates = new Koordinate(this.document.userMap.getRowIndex(node),
                            this.document.userMap.getColumnIndex(node));
                    currentSelectedShip.setCoordinates(coordinates);
                    // If mouse 1 is clicked we place it horizontally
                    if (event.getButton() == MouseButton.PRIMARY) {
                        currentSelectedShip.setVertical(false);
                        if (placeShip(node, currentSelectedShip)) {
                            // Brod has been successfully placed
                            updateStatus("Brod '" + currentSelectedShip.getName() + "' duzine: "
                                    + currentSelectedShip.getLength() + " postavljen.", true);
                            removeFromCombobox(currentSelectedShip, ships);
                        }
                    }
                    // If mouse 2 is clicked we place it vertically
                    if (event.getButton() == MouseButton.SECONDARY) {
                        currentSelectedShip.setVertical(true);
                        if (placeShip(node, currentSelectedShip)) {
                            // Brod has been successfully placed
                            updateStatus("Brod '" + currentSelectedShip.getName() + "' duzine: "
                                    + currentSelectedShip.getLength() + " postavljen.", true);
                            removeFromCombobox(currentSelectedShip, ships);
                        }
                    }
                    if (ships.isEmpty()) {
                        updateStatus("Postavili ste sve brodove, sada mozete potvrditi formaciju!", true);
                        this.document.confirmButton.setDisable(false);
                        this.document.shipsList.setDisable(true);
                        seTGridIsDisable(this.document.userMap, true);
                        this.gameStatus = GameStatus.IGRA;
                    }
                    break;
                } else {
                    updateStatus("Izaberite prvo brod.");
                }
                break;
            case KRAJ:
                seTGridIsDisable(this.document.userMap, true);
                seTGridIsDisable(this.document.serverMap, true);
                break;
        }
    }

    void handleEnemyGridCellButtonFired(MouseEvent event) {
        Node node = (Node) event.getSource();
        // We get coordinates of field clicked
        // And if it is not disabled, we want to check for hit
        if (!node.isDisabled()) {
            Koordinate coordinates = new Koordinate(this.document.userMap.getRowIndex(node),
                    this.document.userMap.getColumnIndex(node));
            Request request = new Request();
            request.setCoordinates(coordinates);
            request.setOperation(Operation.USER_GADJA);
            connectionHandler.getRequests().add(request);
            node.setDisable(true);
        } else {
            updateStatus("Polje je prethodno bilo gadjano! Probajte drugo.");
        }

    }

    private boolean placeShip(Node node, Brod ship) {
        if (Brodovi.mozeLiBitiPostavljenBrod(ship, ships, yourMap)) {
            colorShip(ship);
            node.setDisable(true);
            return true;
        }
        updateStatus("Brod nije moguce postaviti na trazenu poziciju!");
        return false;
    }

    private void colorShip(Brod ship) {
        int y = ship.getCoordinates().getKolona();
        int x = ship.getCoordinates().getRed();
        System.out.println(x + "\t" + y);
        if (!ship.isVertical()) {
            for (int i = y; i < y + ship.getLength(); i++) {
                this.document.userMap.getChildren().get(x * 10 + i).setStyle("-fx-background-color: blue");
                yourMap.setBrodNaPoziciji(x, i, ship);
            }
        } else {
            for (int i = x; i < x + ship.getLength(); i++) {
                this.document.userMap.getChildren().get(i * 10 + y).setStyle("-fx-background-color: blue");
                yourMap.setBrodNaPoziciji(i, y, ship);
            }
        }
    }

    private void killShip(Brod ship, boolean userMap) {
        int y = ship.getCoordinates().getKolona();
        int x = ship.getCoordinates().getRed();
        Node node;
        if (!ship.isVertical()) {
            for (int i = y; i < y + ship.getLength(); i++) {
                if (userMap) {
                    node = this.document.userMap.getChildren().get(x * 10 + i);
                } else {
                    node = this.document.serverMap.getChildren().get(x * 10 + i);
                }
                node.setStyle("-fx-background-color: red");
            }
        } else {
            for (int i = x; i < x + ship.getLength(); i++) {
                if (userMap) {
                    node = this.document.userMap.getChildren().get(i * 10 + y);
                } else {
                    node = this.document.serverMap.getChildren().get(i * 10 + y);
                }
                node.setStyle("-fx-background-color: red");
            }
        }
    }

    private void updateStatus(String message) {
        String tmp = this.document.statusTxtArea.getText();
        tmp = message + "\n" + tmp;
        this.document.statusTxtArea.setText(tmp);
    }

    private void updateStatus(String message, boolean clear) {
        this.document.statusTxtArea.setText(message);
    }

    private <T> void putItemsIntoCombobox(List<T> elements) {
        ObservableList<T> options
                = FXCollections.observableArrayList();
        for (T t : elements) {
            options.add(t);
        }

        this.document.shipsList.setItems(options);
    }

    private <T> void removeFromCombobox(T item, List<T> items) {
        ObservableList<T> options
                = FXCollections.observableArrayList();
        if (item instanceof Brod
                && items.contains(item)) {
            items.remove(item);
        }
        for (T t : items) {
            options.add(t);
        }

        this.document.shipsList.setItems(options);
    }

    public void odaberiBrod() {
        currentSelectedShip = (Brod) this.document.shipsList.getSelectionModel().getSelectedItem();
    }

    void loadUser(Response response) {
        if (response.getResponseStatus() == ResponseStatus.OK) {
            User user = response.getUser();
            if (user != null) {
                this.user = user;
            }
        } else {
             Messages.show("Greska", Alert.AlertType.ERROR, "Problem sa ucitavanjem korisnika." + response.getResponseStatus() + response.getUser() + response.getOperation());
         }
    }

    public void kreirajIgru(Response finalResponse) {
        if (finalResponse.getResponseStatus() == ResponseStatus.OK) {
            this.document.boatImage.setVisible(false);
            this.document.titleLabel.setVisible(false);
            this.document.stage.setHeight(800);
            this.document.confirmButton.setVisible(true);
            this.document.userMap.setVisible(true);
            this.document.serverMap.setVisible(true);
            this.document.enemyBoardLbl.setVisible(true);
            this.document.confirmButton.setDisable(true);
            this.document.statusTxtArea.setVisible(true);
            this.document.statusTxtArea.setEditable(false);
            this.document.statusLbl.setVisible(true);
            this.document.statusTxtArea.setWrapText(true);
            this.document.shipsList.setDisable(false);
            updateStatus("Počnite sa postavljanjem brodova!", true);
            seTGridIsDisable(this.document.userMap, false);
            resetGrid(this.document.userMap);
            resetGrid(this.document.serverMap);

            gameStatus = GameStatus.POSTAVLJANJE_BRODOVA;
            yourMap = new Map();
            ships = Brodovi.getBrodovi();
            putItemsIntoCombobox(ships);
        } else {
             Messages.show("Greska", Alert.AlertType.ERROR, "Nije moguce kreirati igru");
        }
    }

    void confirmFormation(ActionEvent event) {
        // Initialize server map
        // get who is playing first
        Request request = new Request();
        request.setMap(yourMap);
        request.setOperation(Operation.START_IGRA);
        connectionHandler.getRequests().add(request);
    }

    void zapocniIgru(Response finalResponse) {
        if (finalResponse.getResponseStatus() == ResponseStatus.OK) {
            // See who is playing first
            // And start game
            updateStatus("Protivnik je izabrao brodove, igra moze da pocne!", true);
            seTGridIsDisable(this.document.serverMap, false);
            this.document.confirmButton.setDisable(true);
        } else {
            Messages.show("Greska", Alert.AlertType.ERROR, "Problem prilikom startovanja igre!");
           
        }
    }

    /**
     * Method is used to update single cell of grid
     *
     * @param text Text which will appear in status TextField
     * @param gridToUpdate Grid which will be updated with a shot result
     * @param gridToChangeIsDisable Grid which status will be changed to enable
     * or prevent shooting
     * @param newGridState A value to which gridToChangeIsDisable will be
     * changed
     * @param color Color which will appear in cell of updated grid
     * @param row Row of cell to update
     * @param col Column of cell to update
     * @param ship Brod that is on that cell
     */
    private void updateGUI(String text, GridPane gridToUpdate, String color, Integer row, Integer col, Brod ship) {
        Platform.runLater(() -> {
            updateStatus(text);

            if (ship != null && !ship.daLiJeOtkriven()) {
                // If ship is not alive we should all his fields color and write number
                killShip(ship, gridToUpdate.equals(this.document.userMap));
                return;
            }
            gridToUpdate.getChildren().get(row * 10 + col).setStyle("-fx-background-color: " + color);
        });
    }

    void korisnikGadja(Response finalResponse) {
        if (finalResponse.getResponseStatus() == ResponseStatus.OK) {
            if (finalResponse.getHit() != null && finalResponse.getUserPlaying() != null) {
                int row = finalResponse.getCoordinates().getRed();
                int col = finalResponse.getCoordinates().getKolona();
                updateGUI("Korisnik je gadjao polje " + (row + 1) + " , " + (col + 1),
                        this.document.serverMap,
                        getFieldColor(finalResponse.getHit()),
                        row,
                        col,
                        finalResponse.getShip());
                // See if is now turn for server to shoot
                // Then we create call to server and after that we update gui
                if (!finalResponse.getUserPlaying()) {
                    // Send request to wait for move
                    Request request = new Request();
                    request.setOperation(Operation.SERVER_GADJA);
                    connectionHandler.getRequests().add(request);
                }
            } else {
                updateStatus("Nije moguce gadjati prethodno izabrana polja!");
            }
        } else {
             Messages.show("Greska", Alert.AlertType.ERROR, "Greska prilikom gadjanja od strane korisnika!");
        }
    }

    private String getFieldColor(Boolean hit) {
        return hit ? "green" : "black";
    }

    void serverGadja(Response finalResponse) {
        if (finalResponse.getResponseStatus() == ResponseStatus.OK) {
            int row = finalResponse.getCoordinates().getRed();
            int col = finalResponse.getCoordinates().getKolona();
            updateGUI("Server je gadjao polje " + (row + 1) + " , " + (col + 1),
                    this.document.userMap,
                    getFieldColor(finalResponse.getHit()),
                    row,
                    col,
                    finalResponse.getShip());
            // See if is now turn for server to shoot
            // Then we create call to server and after that we update gui
            if (!finalResponse.getUserPlaying()) {
                // Send request to wait for move
                Request request = new Request();
                request.setOperation(Operation.SERVER_GADJA);
                connectionHandler.getRequests().add(request);
            }
        } else {
            Messages.show("Greska", Alert.AlertType.ERROR, "Greska prilikom gadjanja od strane servera!");
        }
    }

    void manageWin(Response finalResponse) {
        this.gameStatus = GameStatus.KRAJ;
        String message;
        GridPane map;
        if (finalResponse.getOperation() == Operation.USER_POBEDIO) {
            message = "Cestitamo na pobedi :)!";
            map = this.document.serverMap;
        } else {
            message = "Nazalost ste izgubili!";
            map = this.document.userMap;
        }
        updateGUI("",
                map,
                getFieldColor(finalResponse.getHit()),
                finalResponse.getCoordinates().getRed(),
                finalResponse.getCoordinates().getKolona(),
                finalResponse.getShip());
        seTGridIsDisable(this.document.serverMap, true);
        seTGridIsDisable(this.document.userMap, true);

        Request request = new Request();
        request.setOperation(Operation.KRAJ);
        request.setUser(this.user);
        connectionHandler.getRequests().add(request);

        Messages.show("Upozorenje", Alert.AlertType.WARNING, message);
    }

    private void resetGrid(GridPane serverMap) {
        serverMap.getChildren().forEach((node) -> {
            node.setStyle("-fx-background-color:none");
        });
    }

    public void prikaziRangListu() {
        Request request = new Request();
        request.setOperation(Operation.RANGLISTA);
        connectionHandler.getRequests().add(request);
    }

    void prikaziRangListu(Response finalResponse) {
        if (finalResponse.getResponseStatus() == ResponseStatus.OK) {
            String temp = "No.\tUsername\tScore\n";
            LinkedList<RankItem> rankList = (LinkedList<RankItem>) finalResponse.getRankList();
            int counter = 1;
            for (RankItem rankItem : rankList) {
                temp += counter++ + ".\t" + rankItem.getUser().getUsername() + "\t\t" + rankItem.getGame().getscore() + "\n";
            }
            Messages.show("Rang Lista", Alert.AlertType.NONE, temp);
        } else {
            Messages.show("Greska", Alert.AlertType.ERROR, "Problem prilikom dovlacenja podataka o rang listi.");
        }
    }

    public void prikaziPravila() {
        try {
            InputStream in = new FileInputStream("src/assets/rules.properties");
            Properties prop = new Properties();
            prop.load(in);
            Messages.show("Pravila igre", Alert.AlertType.NONE, prop.getProperty("text"));
        } catch (Exception ex) {
            System.out.println(ex);
        }
    }
}
