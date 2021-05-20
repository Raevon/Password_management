package sample;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import models.User;
import utilities.Mixer;
import utilities.FileManager;
import utilities.UserStringConverter;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.security.InvalidKeyException;
import java.util.ResourceBundle;

public class MainController implements Initializable{

    private User user = null;

    public void Register(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("Register.fxml"));
        Scene secondScene = new Scene(root);
        Stage newWindow = new Stage();
        newWindow.setTitle("Registration");
        newWindow.setScene(secondScene);
        newWindow.show();
    }

    public void Login(ActionEvent actionEvent) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Login.fxml"));
        Parent root = loader.load();

        LoginController loginController = loader.getController();
        user = new User("asd", "1", "1");
        loginController.setUser(user);

        Scene secondScene = new Scene(root);
        Stage newWindow = new Stage();
        newWindow.setTitle("Login");
        newWindow.setScene(secondScene);
        newWindow.show();
    }

    public void AddPassword(ActionEvent actionEvent) throws IOException {
        System.out.println(user);
        if (user == null || user.getHash().equals("1")){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("Must log in first");
            alert.show();
            return;
        }

        FXMLLoader loader = new FXMLLoader(getClass().getResource("AddPassword.fxml"));
        Parent root = loader.load();

        AddPasswordController addPasswordController = loader.getController();
        addPasswordController.setUser(user);

        Scene secondScene = new Scene(root);
        Stage newWindow = new Stage();
        newWindow.setTitle("Add password");
        newWindow.setScene(secondScene);
        newWindow.show();
    }

    public void ViewPassword(ActionEvent actionEvent) throws IOException {
        if (user == null || user.getHash().equals("1")){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("Must log in first");
            alert.show();
            return;
        }

        FXMLLoader loader = new FXMLLoader(getClass().getResource("ViewPassword.fxml"));
        Parent root = loader.load();

        ViewPasswordController viewPasswordController = loader.getController();
        viewPasswordController.setUser(user);

        Scene secondScene = new Scene(root);
        Stage newWindow = new Stage();
        newWindow.setTitle("View password");
        newWindow.setScene(secondScene);
        newWindow.show();
    }

    public void setOnCloseRequest(Stage primaryStage)
    {
        primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent event) {
                try {
                    Mixer.FileEncryptCombo("users", FileManager.getLinesByName("users"));
                    Mixer.FileEncryptCombo(user.getNickname(),
                            UserStringConverter.getPasswordCvsData(user));
                } catch (InvalidKeyException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            //user = new User("asd", "", "");
        } catch (Exception e) {
            e.printStackTrace();
        }

        File file = new File(FileManager.getAesPath("users"));
        if(file.exists()){
            try {
                Mixer.FileDecryptionCombo("users");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
