package uk.ac.soton.comp2211.team_43_project;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import uk.ac.soton.comp2211.team_43_project.event.ParameterUpdateListener;
import uk.ac.soton.comp2211.team_43_project.model.Runway;

import java.util.ArrayList;
import java.util.List;

public class Data {

  private static final Logger logger = LogManager.getLogger(Data.class);

  private static final int stripEnd = 60;
  private static final int visualStripWidth = 150;
  private static final int thresholdLength = 25;
  private static final float scaling = .1f;
  private static final double zoomSensitivity = 1.05f;

  private ArrayList<Runway> runways;
  protected ParameterUpdateListener parameterUpdateListener;

  public Data() {
    runways = new ArrayList<>();
  }

  public static float getScaling() {
    return scaling;
  }

  public static int getVisualStripWidth() {
    return visualStripWidth;
  }

  public static int getStripEnd() {
    return stripEnd;
  }

  public static double getZoomSensitivity() {
    return zoomSensitivity;
  }

  public static double getThresholdLength() {
    return thresholdLength;
  }

  public ArrayList<Runway> getRunways() {
    return runways;
  }

  public void addRunway(Runway runway) {
    runways.add(runway);
  }
  
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

  public Runway getRunway(String designator) {
    for (Runway runway : runways) {
      String name = "";
      if (runway.getAway()) {
        name = designator.replace(" Take Off Away / Landing Over", "");
      } else {
        name = designator.replace(" Take Off Towards / Landing Towards", "");
      }
      if (runway.getDesignator().equals(name)) return runway;
    }
    return null;
  }

  public void setParameterUpdateListener(ParameterUpdateListener parameterUpdateListener) {
    this.parameterUpdateListener = parameterUpdateListener;
  }
}
