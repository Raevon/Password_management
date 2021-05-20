package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import utilities.Mixer;

public class RegisterController {
    @FXML
    protected TextField nickname;
    @FXML
    protected TextField password;
    @FXML
    protected Button button;
    
    public void Register(ActionEvent actionEvent) {
        try{
            Mixer.register(nickname.getText(), password.getText());

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("Registration successful");
            alert.show();

            Stage stage = (Stage) button.getScene().getWindow();
            stage.close();
        }catch (Exception exc){
            System.out.println(exc);
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText(exc.getMessage());
            alert.show();
        }

    }
}
