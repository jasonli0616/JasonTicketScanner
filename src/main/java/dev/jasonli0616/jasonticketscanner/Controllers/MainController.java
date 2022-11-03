package dev.jasonli0616.jasonticketscanner.Controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class MainController {

    @FXML
    private TextField studentIDInput;

    @FXML
    protected void handleSubmitButton() {
        // Handle submit ID
        System.out.println(studentIDInput.getText());
    }

}