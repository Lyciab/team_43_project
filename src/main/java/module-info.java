module uk.ac.soton.comp2211.team_43_project {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.apache.logging.log4j;


    opens uk.ac.soton.comp2211.team_43_project to javafx.fxml;
    exports uk.ac.soton.comp2211.team_43_project;
}
