
package client;

import wsclient.User;
import gui.MainMenu;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import util.Messages;
import wsclient.ILogin;
import wsclient.LoginService;
import wsclient.Request;
import wsclient.Response;

/**
 *
 * @author anape
 */
public class Kontroler {

    FXMLDocumentController document;

    public Kontroler(FXMLDocumentController document) {
        this.document = document;
        this.document.login.setOnAction(new LoginHandler(this));
    }

    public void login() {
        String username = this.document.usernameField.getText().trim();
        String password = this.document.passwordField.getText().trim();

        User user = new User();
        user.setUsername(username);
        user.setPassword(password);

        LoginService service = new LoginService();
        ILogin iservice = service.getLoginPort();

        Request request = new Request();
        request.setUser(user);

        Response response = iservice.login(request);
        if (response != null) {
            if (response.getUser() != null) {
                Messages.show("Uspesno prijavljivanje", Alert.AlertType.INFORMATION, "Uspesno ste se prijavili!");
            } else {
                Messages.show("Neuspesno prijavljivanje", Alert.AlertType.ERROR, "Neuspesno prijavljivanje, pogresni kredencijali!");
                return;
            }

            try {
                this.document.stage.close();
                MainMenu mainMenu = new MainMenu();
                Stage s = new Stage();
                mainMenu.setUser(username, password);
                mainMenu.start(s);

            } catch (IOException ex) {
                // Error creating stage
                Logger.getLogger(Kontroler.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
             Messages.show("Neuspesno prijavljivanje", Alert.AlertType.ERROR, "Neuspesno prijavljivanje");
        }
    }

}
