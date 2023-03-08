package uk.ac.soton.comp2211.team_43_project.ui;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Popup;
import javafx.stage.PopupWindow;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import uk.ac.soton.comp2211.team_43_project.App;
import uk.ac.soton.comp2211.team_43_project.Data;
import uk.ac.soton.comp2211.team_43_project.model.Runway;

/**
 * MainWindow displays the tool.
 */
public class MainWindow implements Initializable {

  private static final Logger logger = LogManager.getLogger(MainWindow.class);

  Scene scene;
  Parent root = null;

  @FXML private AnchorPane topDownViewPane;
  @FXML private AnchorPane sideViewPane;
  @FXML private BorderPane topDownViewContainer;
  @FXML private BorderPane sideViewContainer;
  @FXML private ChoiceBox<String> runwaySelect;

  private RunwayTopPane runwayTop;
  private RunwaySidePane runwaySide;
  private Popup popup;
  private Data data = Data.getInstance();

  private double offsetY = 0;
  private double offsetX = 0;

  /**
   * Create the main window.
   */
  public MainWindow() {
    logger.info("Building " + this.getClass().getName());

    try {
      var loader = new FXMLLoader(getClass().getResource("/layout.fxml"));
      loader.setController(this);
      root = loader.load();
    } catch (Exception e) {
      logger.error("Unable to read file: " + e.getMessage());
      e.printStackTrace();
      System.exit(1);
    }

    scene = new Scene(root);

    build();
  }

  /**
   * Build the window.
   */
  private void build() {

    Data.getInstance().setRunwaysUpdateListener(this::updateChoices);

    //Example Runways-
    Runway runway1 = new Runway("09R", true, 3660, 3660, 3660, 3353, 307);
    Runway runway2 = new Runway("09L", false, 3902, 3902, 3902, 3595, 306);
    Runway runway3 = new Runway("08R", true, 3159, 3311, 3233, 2765, 0);
    Runway runway4 = new Runway("08L", false, 2561, 3040,  2561,  2241,  0);

    data.addRunway(runway1);
    data.addRunway(runway2);
    data.addRunway(runway3);
    data.addRunway(runway4);
    //-

    setRunwayPane(runway1);
    runwaySelect.getSelectionModel().select(0);
    runwaySelect.setOnAction(this::changeRunway);



    zoomIn(topDownViewContainer, topDownViewPane);
    pan(topDownViewContainer, topDownViewPane);
    topDownViewContainer.setOnMouseMoved(this::getOffset);

    zoomIn(sideViewContainer, sideViewPane);
    pan(sideViewContainer, sideViewPane);
    sideViewContainer.setOnMouseMoved(this::getOffset);
  }

  /**
   * Update the runway choice box.
   */
  public void updateChoices() {
    runwaySelect.setOnAction(null);
    ObservableList<String> runwayChoices =
        FXCollections.observableArrayList(data.getRunwayNames());
    runwaySelect.setItems(runwayChoices);
    runwaySelect.getSelectionModel().select(0);
    setRunwayPane(Data.getInstance().getRunway(runwayChoices.get(0)));
    runwaySelect.setOnAction(this::changeRunway);


  }

  /**
   * Change the runway being displayed.
   *
   * @param runway runway to display
   */
  public void setRunwayPane(Runway runway) {
    Boolean isRotated = false;
    Boolean isStacked = false;
    if (runwayTop != null) {
      isRotated = runwayTop.getRotated();
      isStacked = runwayTop.getIsStacked();
    }

    runwayTop = new RunwayTopPane(runway);
    topDownViewPane.getChildren().clear();
    topDownViewPane.getChildren().add(runwayTop);

    runwaySide = new RunwaySidePane(runway);
    sideViewPane.getChildren().clear();
    sideViewPane.getChildren().add(runwaySide);

    if (isRotated) {
      runwayTop.setRotated(false);
      runwayTop.toggleRotateRunway();
    }

    if (isStacked) {
      runwayTop.setIsStacked(false);
      runwayTop.toggleStackIndicators();

      runwaySide.setIsStacked(false);
      runwaySide.toggleStackIndicators();
    }
  }


  /**
   * Get mouse offset on screen.
   *
   * @param e mouse event
   */
  private void getOffset(MouseEvent e) {
    offsetX = e.getScreenX() - App.getInstance().getStage().getX();
    offsetY = e.getScreenY() - App.getInstance().getStage().getY();
  }

  /**
   * Pan the runway view.
   *
   * @param control main container
   * @param node runway view to pan
   */
  private void pan(Node control, Node node) {
    control.setOnMouseDragged(e -> {
      control.setManaged(false);
      node.setTranslateX(e.getSceneX() + control.getTranslateX() - offsetX);
      node.setTranslateY(e.getSceneY() + control.getTranslateY() - offsetY);
    });
  }

  /**
   * Scales the runway view.
   *
   * @param control main container
   * @param node runway view to scale
   */
  private void zoomIn(Node control, Node node) {
    control.setOnScroll((ScrollEvent e) -> { //get mouse scroll input
      double zoom = Data.getZoomSensitivity();
      double deltaY = e.getDeltaY();
      if (deltaY < 0) {
        zoom = 2.0 - zoom;
      }
      node.setScaleX(node.getScaleX() * zoom);
      node.setScaleY(node.getScaleY() * zoom);
    });
  }

  /**
   * Change the runway display to show the currently selected runway.
   *
   * @param e action event
   */
  private void changeRunway(ActionEvent e) {
    String choice = runwaySelect.getSelectionModel().getSelectedItem();
    Runway runway = data.getRunway(choice);
    setRunwayPane(runway);
  }



  @Override
  public void initialize(URL url, ResourceBundle bundle) {
    logger.info("initialising");
  }


  /**
   * Close the popup window.
   */
  public void closePopup() {
    popup.getContent().clear();
    popup.hide();
  }

  /**
   * Add a runway to the data runway list.
   *
   * @param des desginator
   * @param away is taking off away
   * @param tora TORA
   * @param toda TODA
   * @param asda ASDA
   * @param lda LDA
   * @param dt displaced threshold
   */
  public void addRunway(String des, Boolean away, int tora, int toda, int asda, int lda, int dt) {
    Runway runway = new Runway(des, away, tora, toda, asda, lda, dt);
    data.addRunway(runway);
  }

  /**
   * Toggle runway rotation.
   */
  @FXML
  public void toggleCompassDirection() {
    runwayTop.toggleRotateRunway();
  }

  /**
   * Toggle indicator stacking.
   */
  @FXML
  public void toggleStackIndicators() {
    runwayTop.toggleStackIndicators();
  }

  /**
   * Open a popup window to allow a user to create a runway.
   */
  @FXML
  public void declareNewRunway() {
    popup = new Popup();
    CreateRunwayPane createRunwayPane = new CreateRunwayPane(this);
    popup.setAnchorLocation(PopupWindow.AnchorLocation.WINDOW_TOP_LEFT);
    popup.getContent().add(createRunwayPane);
    popup.show(App.getInstance().getStage());

  }

  public ChoiceBox<String> getRunwaySelect() {
    return runwaySelect;
  }

  public Scene getScene() {
    return scene;
  }
}
