package uk.ac.soton.comp2211.team_43_project;

import java.util.ArrayList;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import uk.ac.soton.comp2211.team_43_project.event.RunwaysUpdateListener;
import uk.ac.soton.comp2211.team_43_project.model.Runway;

/**
 * The data controller for the tool. Contains standard declarations for parameters and the
 * list of runways.
 */
public class Data {

  private static final Logger logger = LogManager.getLogger(Data.class);

  private static final int stripEnd = 60;
  private static final int visualStripWidth = 150;
  private static final int thresholdLength = 25;
  private static final float scaling = .14f;
  private static final double zoomSensitivity = 1.05f;

  private ArrayList<Runway> runways;
  protected RunwaysUpdateListener runwaysUpdateListener;

  private static Data instance;

  public Data() {
    instance = this;
    runways = new ArrayList<>();
  }


  /**
   * Add a runway to the current list of runways.
   *
   * @param runway runway to add
   */
  public void addRunway(Runway runway) {
    runways.add(runway);
    runwaysUpdateListener.runwaysUpdate();
  }

  /**
   * Get the names of all runways for display.
   *
   * @return list of names
   */
  public ArrayList<String> getRunwayNames() {
    ArrayList<String> names = new ArrayList<>();
    for (Runway runway : runways) {
      String name;
      if (runway.getAway()) {
        name = runway.getDesignator() + " Take Off Away / Landing Over";
      } else {
        name = runway.getDesignator() + " Take Off Towards / Landing Towards";
      }
      names.add(name);
    }
    return names;
  }

  /**
   * Get the runway object with the designator given.
   *
   * @param designator the designator of the runway
   * @return the runway
   */
  public Runway getRunway(String designator) {
    for (Runway runway : runways) {
      String name = "";
      if (runway.getAway()) {
        name = designator.replace(" Take Off Away / Landing Over", "");
      } else {
        name = designator.replace(" Take Off Towards / Landing Towards", "");
      }
      if (runway.getDesignator().equals(name)) {
        return runway;
      }
    }
    return null;
  }

  public void setRunwaysUpdateListener(RunwaysUpdateListener runwaysUpdateListener) {
    this.runwaysUpdateListener = runwaysUpdateListener;
  }

  public static Data getInstance() {
    return instance;
  }

  public static float getScaling() {
    return scaling;
  }

  public static double getZoomSensitivity() {
    return zoomSensitivity;
  }

  public static int getThresholdLength() {
    return thresholdLength;
  }
}
