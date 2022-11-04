package dev.jasonli0616.jasonticketscanner;

import javafx.scene.control.Alert;

import java.sql.*;
import java.util.ArrayList;

public class Database {

    /**
     * Connect to SQLite database.
     * @return the connection
     */
    public static Connection connect() {
        Connection conn = null;

        try {

            String connectionPath = String.format("jdbc:sqlite:%s/.jasonticketscanner.db", System.getProperty("user.home"));
            conn = DriverManager.getConnection(connectionPath);

        } catch (SQLException exception) {
            new Alert(Alert.AlertType.ERROR, exception.getMessage()).showAndWait();
            exception.printStackTrace();
        }

        return conn;
    }

    /**
     * Creates:
     * students table
     * tickets table
     */
    public static void createTables() {

        String studentTableQuery =
                "CREATE TABLE IF NOT EXISTS students (" +
                "   id              INTEGER PRIMARY KEY," +
                "   student_card    TEXT NOT NULL" +
                ")";

        String ticketTableQuery =
                "CREATE TABLE IF NOT EXISTS tickets (" +
                "   id          INTEGER PRIMARY KEY" +
                "   student_id  INTEGER NOT NULL" +
                "   datetime    TEXT NOT NULL" +
                "   FOREIGN KEY (student_id) REFERENCES students(id)" +
                ")";

        Connection conn = connect();

        try {
            Statement stmt = conn.createStatement();

            stmt.execute(studentTableQuery);
            stmt.execute(ticketTableQuery);

            conn.close();
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }

    /**
     * Inserts one student
     * @param studentCard student card number
     */
    public static void insertStudent(String studentCard) {

        String insertStudentQuery =
                "INSERT INTO students (student_card)" +
                "VALUES (?)";

        Connection conn = connect();

        try {
            PreparedStatement pstmt = conn.prepareStatement(insertStudentQuery);
            pstmt.setString(1, studentCard);
            pstmt.executeUpdate();

            conn.close();
        } catch (SQLException exception) {
            new Alert(Alert.AlertType.ERROR, exception.getMessage()).showAndWait();
            exception.printStackTrace();
        }
    }

    /**
     * Get primary key of student.
     * If student does not exist, return 0.
     * @param studentCard student card number
     */
    public static int getStudentPrimaryKey(String studentCard) {

        String selectStudentQuery =
                "SELECT * FROM students" +
                "WHERE student_card = ?";

        Connection conn = connect();

        try {
            PreparedStatement pstmt = conn.prepareStatement(selectStudentQuery);
            pstmt.setString(1, studentCard);

            ResultSet results = pstmt.executeQuery();
            while (results.next()) {
                int primaryKey = results.getInt("ID");
                return primaryKey;
            }

            conn.close();
        } catch (SQLException exception) {
            new Alert(Alert.AlertType.ERROR, exception.getMessage()).showAndWait();
            exception.printStackTrace();
        }

        return 0;
    }

    /**
     * Insert a ticket.
     * @param studentPrimaryKey student id
     * @param datetime datetime as string
     */
    public static void createTicket(int studentPrimaryKey, String datetime) {

        String insertTicketQuery =
                "INSERT INTO tickets (student_id, datetime)" +
                "VALUES (?, ?)";

        Connection conn = connect();

        try {
            PreparedStatement pstmt = conn.prepareStatement(insertTicketQuery);
            pstmt.setInt(1, studentPrimaryKey);
            pstmt.setString(2, datetime);

            pstmt.executeUpdate();

            conn.close();
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }

    /**
     * Get all student's tickets.
     * @param studentPrimaryKey student id
     * @return student's tickets as ArrayList of datetime
     */
    public static ArrayList<String> getStudentTickets(int studentPrimaryKey) {

        ArrayList<String> tickets = new ArrayList<String>();

        String selectStudentTicketsQuery =
                "SELECT * FROM tickets" +
                "WHERE student_id = ?";

        Connection conn = connect();

        try {
            PreparedStatement pstmt = conn.prepareStatement(selectStudentTicketsQuery);
            pstmt.setInt(1, studentPrimaryKey);

            ResultSet results = pstmt.executeQuery();
            while (results.next()) {
                String datetime = results.getString("datetime");
                tickets.add(datetime);
            }

            conn.close();
        } catch (SQLException exception) {
            new Alert(Alert.AlertType.ERROR, exception.getMessage()).showAndWait();
            exception.printStackTrace();
        }

        return tickets;
    }

    /**
     * Delete a ticket.
     * @param ticketPrimaryKey ticket id
     */
    public static void deleteTicket(int ticketPrimaryKey) {

        String deleteTicketQuery =
                "DELETE FROM tickets" +
                "WHERE id = ?";

        Connection conn = connect();

        try {
            PreparedStatement pstmt = conn.prepareStatement(deleteTicketQuery);
            pstmt.setInt(1, ticketPrimaryKey);
            pstmt.executeUpdate();

            conn.close();
        } catch (SQLException exception) {
            new Alert(Alert.AlertType.ERROR, exception.getMessage()).showAndWait();
            exception.printStackTrace();
        }
    }

}