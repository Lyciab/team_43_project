package uk.ac.soton.comp2211.team_43_project.event;

import javafx.scene.control.Alert;

/**
 * Error is used to display error messages.
 */
public class Error {

  private static final Alert errorAlert = new Alert(Alert.AlertType.ERROR);

  /**
   * Create default error message.
   */
  public Error() {
    errorAlert.setHeaderText("ERROR");
  }

  /**
   * Create an Error Alert popup.
   *
   * @param message error message
   */
  public static void error(String message) {
    errorAlert.setContentText(message);
    errorAlert.showAndWait();
  }
}
