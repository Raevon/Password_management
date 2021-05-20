package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import models.Password;
import models.User;
import securities.AESEncryption;
import utilities.FileManager;
import utilities.Mixer;
import utilities.UserStringConverter;

public class AddPasswordController {
    private User user;

    @FXML
    protected TextField title;
    @FXML
    protected TextField password;
    @FXML
    protected TextField url;
    @FXML
    protected TextField comment;

    public void AddPasword(ActionEvent actionEvent) {
        try {
            String encrypted = AESEncryption.encrypt(password.getText());
            Password password1 = new Password(title.getText(), encrypted, url.getText(), comment.getText());
            user.addPassword(password1);
            FileManager.appendFile(user.getNickname(), UserStringConverter.getPasswordCvsData(password1));
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("Login successful");
            alert.show();

        }catch (Exception exc){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText(exc.getMessage());
            alert.show();
        }

    }

    public void setUser(User user){
        this.user = user;
    }

    public void GeneratePasword(ActionEvent actionEvent) {
        password.setText(Mixer.generateRandomSpecialCharacters(20));
    }
}
