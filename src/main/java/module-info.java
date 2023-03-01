module uk.ac.soton.comp2211.team_43_project {
    requires java.scripting;
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.media;
    requires org.apache.logging.log4j;

    opens uk.ac.soton.comp2211.team_43_project to javafx.fxml;
    exports uk.ac.soton.comp2211.team_43_project;
    exports uk.ac.soton.comp2211.team_43_project.scene;
    exports uk.ac.soton.comp2211.team_43_project.ui;
}