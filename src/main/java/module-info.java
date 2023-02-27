module uk.ac.soton.comp2211.team_43_project {
    requires javafx.controls;
    requires javafx.fxml;


    opens uk.ac.soton.comp2211.team_43_project to javafx.fxml;
    exports uk.ac.soton.comp2211.team_43_project;
}