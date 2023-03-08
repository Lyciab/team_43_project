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
    Boolean away = dirField.getSelectionModel().getSelectedItem().equals("away");
    String des = designatorField.getText();
    int tora = Integer.parseInt(toraField.getText());
    int toda = Integer.parseInt(todaField.getText());
    int asda = Integer.parseInt(asdaField.getText());
    int lda = Integer.parseInt(ldaField.getText());
    int dt = Integer.parseInt(displacedField.getText());


    window.addRunway(des, away, tora, toda, asda, lda, dt);
    close();
  }

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    logger.info("initialising");
  }
}
