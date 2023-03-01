package uk.ac.soton.comp2211.team_43_project.ui;

import javafx.scene.Scene;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class OptionsFrame extends TabPane {

  public static final Logger logger = LogManager.getLogger(OptionsFrame.class);
  protected Scene scene;


  public OptionsFrame(Scene scene) {
    this.scene = scene;
    build();
  }

  public void build() {
    logger.info("Building " + this.getClass().getName());

    Tab calculations = new Tab("Calculations");
    Tab obstacles = new Tab("Edit Obstacles");



  }



}
