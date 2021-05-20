package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import models.User;
import utilities.Mixer;

public class LoginController {
    private User user;

    @FXML
    protected TextField nickname;
    @FXML
    protected TextField password;
    @FXML
    protected Button button;

    public void Login(ActionEvent actionEvent) {
        try {
            User temp = Mixer.login(nickname.getText(), password.getText());
            user.setNickname(temp.getNickname());
            user.setHash(temp.getHash());
            user.setSalt(temp.getSalt());
            user.setPasswords(temp.getPasswords());

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("Login successful");
            alert.show();

            Stage stage = (Stage) button.getScene().getWindow();
            stage.close();
        }catch (Exception exc){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText(exc.getMessage());
            alert.show();
        }

    }

    public void setUser(User user){
        this.user = user;
    }
}
