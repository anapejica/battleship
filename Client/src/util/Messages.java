
package util;

import java.util.Optional;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;

/**
 *
 * @author anape
 */
public class Messages {

    public static void showError(String message) {
        Alert infoAlert;
        infoAlert = new Alert(Alert.AlertType.ERROR);
        infoAlert.setTitle("Greska");
        infoAlert.setHeaderText(null);
        infoAlert.setContentText(message);
        infoAlert.showAndWait();
    }

    public static void showSuccess(String message) {
        Alert infoAlert;
        infoAlert = new Alert(Alert.AlertType.INFORMATION);
        infoAlert.setTitle("Uspelo");
        infoAlert.setHeaderText(null);
        infoAlert.setContentText(message);
        infoAlert.showAndWait();
    }

    public static void showWarning(String message) {
        Alert infoAlert;
        infoAlert = new Alert(Alert.AlertType.WARNING);
        infoAlert.setTitle("Upozorenje");
        infoAlert.setHeaderText(null);
        infoAlert.setContentText(message);
        infoAlert.showAndWait();
    }

    public static void dialog(String message, boolean successOperation) {
        Alert alert;
        if (successOperation) {
            alert = new Alert(Alert.AlertType.INFORMATION);
        } else {
            alert = new Alert(Alert.AlertType.ERROR);
        }
        ButtonType buttonTypeCancel = new ButtonType("Izlaz", ButtonBar.ButtonData.CANCEL_CLOSE);
        alert.getButtonTypes().setAll(buttonTypeCancel);
        Optional<ButtonType> result = alert.showAndWait();
    }
    
    public static void showInformation(String message) {
        Alert infoAlert;
        infoAlert = new Alert(Alert.AlertType.NONE);
        infoAlert.setTitle("Obavestenje");
        infoAlert.setHeaderText(null);
        infoAlert.setContentText(message);
        infoAlert.getDialogPane().getButtonTypes().add(ButtonType.OK);
        infoAlert.showAndWait();
    }
    
     public static void show(String title, Alert.AlertType type, String message) {
        Alert infoAlert;
        infoAlert = new Alert(type);
        infoAlert.setTitle(title);
        infoAlert.setHeaderText(null);
        infoAlert.setContentText(message);
        if(type.equals(Alert.AlertType.NONE)){
            infoAlert.getDialogPane().getButtonTypes().add(ButtonType.OK);
        }
        infoAlert.showAndWait();
    }
    
    
}
