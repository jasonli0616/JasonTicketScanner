module dev.jasonli0616.jasonticketscanner {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens dev.jasonli0616.jasonticketscanner to javafx.fxml;
    exports dev.jasonli0616.jasonticketscanner;
    exports dev.jasonli0616.jasonticketscanner.Controllers;
    opens dev.jasonli0616.jasonticketscanner.Controllers to javafx.fxml;
}