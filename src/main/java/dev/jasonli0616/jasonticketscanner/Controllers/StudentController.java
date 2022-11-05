package dev.jasonli0616.jasonticketscanner.Controllers;

import dev.jasonli0616.jasonticketscanner.Database;
import dev.jasonli0616.jasonticketscanner.Main;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class StudentController {
    String studentCard;
    int studentPrimaryKey;
    ArrayList<String> previousTickets;

    @FXML
    protected Label displayStudentCard;

    @FXML protected ListView<String> previousTicketsList;

    @FXML protected Label previousTicketsAmountLabel;

    /**
     * Set state of student card and id.
     * This must be called before anything else on this view,
     * because this sets the state.
     * @param studentPrimaryKeyValue student id
     */
    public void setStudent(int studentPrimaryKeyValue) {
        studentPrimaryKey = studentPrimaryKeyValue;
        studentCard = Database.getStudentCard(studentPrimaryKey);
        previousTickets = Database.getStudentTickets(studentPrimaryKey);

        draw();
    }

    private void draw() {
        // Title
        displayStudentCard.setText("Student: " + studentCard);

        // Previous tickets list
        previousTicketsAmountLabel.setText("Previous tickets: " + previousTickets.size());
        previousTicketsList.setItems(FXCollections.observableArrayList(previousTickets));
    }

    @FXML
    protected void createNewTicket() throws IOException {
        // Get current time
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        LocalDateTime nowDatetime = LocalDateTime.now();
        String formattedDatetime = dtf.format(nowDatetime);

        // Create ticket
        Database.createTicket(studentPrimaryKey, formattedDatetime);

        // Success dialog
        new Alert(Alert.AlertType.INFORMATION, "Created ticket for " + studentCard + " at " + formattedDatetime).showAndWait();
        returnToMainScreen();

    }

    @FXML
    protected void returnToMainScreen() throws IOException {
        // Return to main screen
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("view/main-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 320, 240);
        Stage stage = (Stage) previousTicketsList.getScene().getWindow();
        stage.setTitle("JasonTicketScanner");
        stage.setScene(scene);
        stage.show();
    }

}