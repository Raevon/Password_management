package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import models.Password;
import models.User;
import securities.AESEncryption;
import utilities.FileManager;
import utilities.Mixer;
import utilities.UserStringConverter;

public class ViewPasswordController {
    private User user;
    private Password passwordas;

    @FXML
    protected TextField title;
    @FXML
    protected TextField password;
    @FXML
    protected TextField decyphered;
    @FXML
    protected TextField url;
    @FXML
    protected TextField comment;

    public void viewPassword(ActionEvent actionEvent) {
        try {
            if (title.getText().equals("")){
                return;
            }
            passwordas = user.getPassword(title.getText());
            if(passwordas == null){
                throw new Exception("This password does not exist");
            }

            password.setText(passwordas.getPassword());
            url.setText(passwordas.getUrl());
            comment.setText(passwordas.getUrl());

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

    public void copyPassword(ActionEvent actionEvent) {
        final Clipboard clipboard = Clipboard.getSystemClipboard();
        // ClipboardContent provides flexibility to store data in different formats
        final ClipboardContent content = new ClipboardContent();
        content.putString(AESEncryption.decrypt(passwordas.getPassword()));
        clipboard.setContent(content);
    }

    public void showPassword(ActionEvent actionEvent) {
        decyphered.setText(AESEncryption.decrypt(passwordas.getPassword()));
    }

    public void updatePassword(ActionEvent actionEvent) {
        try {
            String newPassword = password.getText();
            if(newPassword.equals(passwordas.getPassword())){
                newPassword = AESEncryption.decrypt(passwordas.getPassword());
            }

            Password evenMoreNewPassword = new Password(title.getText(), AESEncryption.encrypt(newPassword), url.getText(), comment.getText());
            user.updatePassword(evenMoreNewPassword, passwordas.getTitle());

            System.out.println( UserStringConverter.getPasswordCvsData(user));
            FileManager.writeFile(user.getNickname(), String.join("", UserStringConverter.getPasswordCvsData(user)));

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("Password updated successfully");
            alert.show();
        }catch (Exception exc){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText(exc.getMessage());
            alert.show();
        }
    }

    public void removePassword(ActionEvent actionEvent) {
        try {
            if (title.getText().equals("")){
                return;
            }
            passwordas = user.getPassword(title.getText());
            if(passwordas == null){
                throw new Exception("This password does not exist");
            }

            user.removePassword(title.getText());

            System.out.println( UserStringConverter.getPasswordCvsData(user));
            FileManager.writeFile(user.getNickname(), String.join("", UserStringConverter.getPasswordCvsData(user)));

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("Password removed successfully");
            alert.show();
        }catch (Exception exc){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText(exc.getMessage());
            alert.show();
        }
    }

    public void generatePassword(ActionEvent actionEvent) {
        password.setText(Mixer.generateRandomSpecialCharacters(20));
    }
}
