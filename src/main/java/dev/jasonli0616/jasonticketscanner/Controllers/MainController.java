package dev.jasonli0616.jasonticketscanner.Controllers;

import dev.jasonli0616.jasonticketscanner.Database;
import dev.jasonli0616.jasonticketscanner.Main;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class MainController {

    @FXML
    private TextField studentCardInput;

    /**
     * Handle the student card input.
     * Process:
     * Checks database for student. Create new student if not exists.
     * Go to student screen, using student primary key.
     */
    @FXML
    protected void handleSubmitButton() throws IOException {
        String studentCardValue = studentCardInput.getText();
        if (! studentCardValue.isBlank()) {
            int studentID = Database.getStudentPrimaryKey(studentCardValue);

            // If student does not exist, create student
            if (studentID == 0)
                studentID = Database.insertStudent(studentCardValue);

            // Go to student screen
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("view/student-view.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 400, 400);
            Stage stage = (Stage) studentCardInput.getScene().getWindow();
            stage.setTitle("Student: " + studentCardValue);
            stage.setScene(scene);
            StudentController controller = fxmlLoader.getController();
            controller.setStudent(studentID);
        } else {
            new Alert(Alert.AlertType.ERROR, "No student card value.").showAndWait();
        }
    }

    @FXML
    protected void handleEditDatabaseButton() {
        Alert dialog = new Alert(Alert.AlertType.INFORMATION,
                "It is recommended to use https://sqlitebrowser.org/ to modify/view the database.\n" +
                    "To clear the database, just delete the file.\n" +
                    "The database is located at `" + Database.getDatabaseFilePath() + "`"
        );
        dialog.setWidth(600);
        dialog.showAndWait();
    }

}