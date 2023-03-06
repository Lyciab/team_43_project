package uk.ac.soton.comp2211.team_43_project.ui;

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
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import uk.ac.soton.comp2211.team_43_project.App;
import uk.ac.soton.comp2211.team_43_project.Data;
import uk.ac.soton.comp2211.team_43_project.model.Runway;

import java.net.URL;
import java.util.ResourceBundle;

public class MainWindow implements Initializable {

  private static final Logger logger = LogManager.getLogger(MainWindow.class);
  private final App app;

  Scene scene = null;
  Parent root = null;
  Data data = null;

  @FXML private AnchorPane topDownViewPane;
  @FXML private AnchorPane sideViewPane;
  @FXML private BorderPane topDownViewContainer;
  @FXML private BorderPane sideViewContainer;
  @FXML private TextField textTORA;
  @FXML private ChoiceBox<String> runwaySelect;

  private RunwayTopPane runwayTop;
  private RunwaySidePane runwaySide;
  private ObservableList<String> runwayChoices;

  private double offsetY = 0, offsetX = 0;




  public MainWindow (App app) {
    logger.info("Building " + this.getClass().getName());

    this.app = app;

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

  private void build() {

    //TODO: THIS \/\/\/
    data = new Data();

    Runway runway1 = new Runway("09R",true, 3660,3660,3660,3353,307);
    Runway runway2 = new Runway("09L", false,3902, 3902, 3902, 3595, 306);
    Runway runway3 = new Runway("08R",true, 3159,3311,3233,2765,0);
    Runway runway4 = new Runway("08L", false,2561, 3040, 2561, 2241, 0);

    data.addRunway(runway1);
    data.addRunway(runway2);
    data.addRunway(runway3);
    data.addRunway(runway4);

    runwayChoices = FXCollections.observableArrayList(data.getRunwayNames());
    runwaySelect.setItems(runwayChoices);

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

  public void setRunwayPane(Runway runway) {
    Boolean isRotated = false;
    if (runwayTop != null) {
      isRotated = runwayTop.getRotated();
    }

    runwayTop = new RunwayTopPane(runway);
    topDownViewPane.getChildren().clear();
    topDownViewPane.getChildren().add(runwayTop);

    runwaySide = new RunwaySidePane(data);
    sideViewPane.getChildren().clear();
    sideViewPane.getChildren().add(runwaySide);

    if (isRotated) {
      runwayTop.setRotated(false);
      runwayTop.toggleRotateRunway();
    }
  }


  public void getOffset(MouseEvent e) {
    offsetX = e.getScreenX() - app.getStage().getX();
    offsetY = e.getScreenY() - app.getStage().getY();
  }

  public void pan(Node control, Node node) {
    control.setOnMouseDragged(e -> {
      control.setManaged(false);
      node.setTranslateX(e.getSceneX() + control.getTranslateX() - offsetX);
      node.setTranslateY(e.getSceneY() + control.getTranslateY() - offsetY);
    });
  }

  public void zoomIn(Node control, Node node) {
    control.setOnScroll((ScrollEvent e) ->{
      double zoom = Data.getZoomSensitivity();
      double dY = e.getDeltaY();
      if (dY < 0) {
        zoom = 2.0 - zoom;
      }
      node.setScaleX(node.getScaleX() * zoom);
      node.setScaleY(node.getScaleY() * zoom);
    });
  }

  public void changeRunway(ActionEvent e) {
    String choice = runwaySelect.getSelectionModel().getSelectedItem();
    Runway runway = data.getRunway(choice);
    setRunwayPane(runway);
  }

  public Scene getScene() {
    return scene;
  }

  /**
   * Initialise the Login Window
   *
   * @param url
   * @param bundle
   */
  @Override
  public void initialize(URL url, ResourceBundle bundle) {
    logger.info("initialising");
    // TODO: Any setting up of the window when it is initialised
  }


  @FXML
  public void toggleCompassDirection() {
    runwayTop.toggleRotateRunway();
  }

  @FXML
  public void inputTORA() {
    runwayTop.setRunwayWidth(Integer.parseInt(textTORA.getText()));
  }


}
