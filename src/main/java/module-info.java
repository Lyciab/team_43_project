module uk.ac.soton.comp2211.team_43_project {
    requires java.scripting;
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.media;
    requires org.apache.logging.log4j;

    opens uk.ac.soton.comp2211.team_43_project to javafx.fxml;
    opens uk.ac.soton.comp2211.team_43_project.ui to javafx.fxml;
    opens uk.ac.soton.comp2211.team_43_project.event to javafx.fxml;
    exports uk.ac.soton.comp2211.team_43_project;
    exports uk.ac.soton.comp2211.team_43_project.ui;
    exports uk.ac.soton.comp2211.team_43_project.event;
    exports uk.ac.soton.comp2211.team_43_project.model;
    opens uk.ac.soton.comp2211.team_43_project.model to javafx.fxml;
}