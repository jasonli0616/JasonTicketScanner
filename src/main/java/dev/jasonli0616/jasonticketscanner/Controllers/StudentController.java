package dev.jasonli0616.jasonticketscanner.Controllers;

import dev.jasonli0616.jasonticketscanner.Database;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;

import java.util.ArrayList;

public class StudentController {
    String studentCard;
    int studentPrimaryKey;
    ArrayList<String> previousTickets;

    @FXML
    protected Label displayStudentCard;

    @FXML protected ListView previousTicketsList;

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

    public void draw() {
        // Title
        displayStudentCard.setText("Student: " + studentCard);

        // Previous tickets list
        previousTicketsAmountLabel.setText("Total: " + previousTickets.size());
        previousTicketsList.setItems(FXCollections.observableArrayList(previousTickets));
    }

}