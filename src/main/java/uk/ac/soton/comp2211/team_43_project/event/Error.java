package uk.ac.soton.comp2211.team_43_project.event;

import javafx.scene.control.Alert;

public class Error {

//  private Error instance;

  public Error(String message) {
    Alert errorAlert = new Alert(Alert.AlertType.ERROR);
    errorAlert.setHeaderText("ERROR");
    errorAlert.setContentText(message);
    errorAlert.showAndWait();
//    instance = this;
  }

//  public Error getInstance() {
//    return instance;
//  }
}
