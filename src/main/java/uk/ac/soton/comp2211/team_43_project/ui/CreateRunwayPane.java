package uk.ac.soton.comp2211.team_43_project.ui;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.TilePane;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import uk.ac.soton.comp2211.team_43_project.event.Error;

/**
 * CreateRunwayPane creates a new pane to allow a user to input parameters for
 * a new runway.
 */
public class CreateRunwayPane extends TilePane implements Initializable {

  private final Logger logger = LogManager.getLogger(CreateRunwayPane.class);

  Parent root;

  @FXML private TextField designatorField;
  @FXML private TextField toraField;
  @FXML private TextField todaField;
  @FXML private TextField asdaField;
  @FXML private TextField ldaField;
  @FXML private TextField displacedField;
  @FXML private ChoiceBox<String> dirField;

  private final MainWindow window;

  /**
   * Create a new 'create runway' pane to allow a user to define a runway to add.
   *
   * @param window window to popup on
   */
  public CreateRunwayPane(MainWindow window) {
    logger.info("Starting " + this.getClass().getName());


    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/create-runway.fxml"));
    fxmlLoader.setController(this);

    try {
      root = fxmlLoader.load();
    } catch (IOException exception) {
      throw new RuntimeException(exception);
    }

    this.getChildren().add(root);
    this.window = window;

    build();


  }

  /**
   * Build the components of the pane.
   */
  private void build() {
    logger.info("Building " + this.getClass().getName());

    ObservableList<String> choices = FXCollections.observableArrayList("away", "towards");
    dirField.setItems(choices);

    dirField.getSelectionModel().select(0);
  }


  /**
   * Close the popup window.
   */
  @FXML
  public void close() {
    logger.info("closing CreateRunway window");
    window.closePopup();
  }

  /**
   * Confirm the creation of a runway with the inputted parameters.
   */
  @FXML
  public void confirm() {
    logger.info("creating new Runway");
    float tora;
    float toda;
    float asda;
    float lda;
    float dt = 0;

    //Error handling:

    //RUNWAY DESIGNATOR
    String des = designatorField.getText();
    if (des.equals("")) {
      Error.error("Runway Designator must be declared.");
      return;
    }

    if (des.length() == 3 || des.length() == 2) {
      try {
        int num = Integer.parseInt(des.substring(0, 2));
        if (num <= 36 && num >= 1) {
          if (des.length() == 3) {
            char side = des.charAt(2);
            if (!(side == 'L' || side == 'C' || side == 'R')) {
              Error.error("Invalid runway designator - position must be one of: L/C/R.");
              return;
            }
          }
        } else {
          Error.error("Invalid runway designator - number must be in range 1-36.");
          return;
        }
      } catch (Exception e) {
        Error.error("Invalid runway designator - must be a valid number.");
        return;
      }
    } else {
      Error.error("Invalid runway designator - must be of format XX[C/L/R].");
      return;
    }

    //TORA
    String toraText = toraField.getText();
    if (toraText.equals("")) {
      Error.error("TORA must be declared.");
      return;
    }

    try {
      tora = Float.parseFloat(toraText);
      if (tora < 0) {
        Error.error("Invalid TORA input - must be a positive number.");
        return;
      }
    } catch (Exception e) {
      Error.error("Invalid TORA input.");
      return;
    }

    //TODA
    String todaText = todaField.getText();
    if (todaText.equals("")) {
      Error.error("TODA must be declared");
      return;
    }

    try {
      toda = Float.parseFloat(todaText);
      if (toda < 0) {
        Error.error("Invalid TODA input - must be a positive number.");
        return;
      }
    } catch (Exception e) {
      Error.error("Invalid TODA input.");
      return;
    }

    //ASDA
    String asdaText = asdaField.getText();
    if (asdaText.equals("")) {
      Error.error("ASDA must be declared");
      return;
    }

    try {
      asda = Float.parseFloat(asdaText);
      if (asda < 0) {
        Error.error("Invalid ASDA input - must be a positive number.");
        return;
      }
    } catch (Exception e) {
      Error.error("Invalid ASDA input.");
      return;
    }

    //LDA
    String ldaText  = ldaField.getText();
    if (ldaText.equals("")) {
      Error.error("LDA must be declared");
      return;
    }

    try {
      lda = Float.parseFloat(ldaText);
      if (lda < 0) {
        Error.error("Invalid LDA input - must be a positive number.");
        return;
      }
    } catch (Exception e) {
      Error.error("Invalid LDA input.");
      return;
    }

    //DISPLACED THRESHOLD
    String dtText   = displacedField.getText();
    if (!dtText.equals("")) {
      try {
        dt = Float.parseFloat(dtText);
        if (dt < 0) {
          Error.error("Invalid Displaced Threshold input - must be a positive number.");
          return;
        }
      } catch (Exception e) {
        Error.error("Invalid Displaced Threshold input.");
        return;
      }
    }

    Boolean away = dirField.getSelectionModel().getSelectedItem().equals("away");
    window.addRunway(des, away, tora, toda, asda, lda, dt);
    close();
  }

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    logger.info("initialising");
  }
}
