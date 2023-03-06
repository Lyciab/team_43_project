package uk.ac.soton.comp2211.team_43_project.model;

public class Runway {

  private String designator;
  private Boolean away;
  private int TODA, TORA, ASDA, LDA, displacedThreshold;
  private int clearway, stopway;

  public Runway(String designator, Boolean away, int TORA, int TODA, int ASDA, int LDA, int displacedThreshold) {
    this.designator = designator;
    this.TORA = TORA;
    this.TODA = TODA;
    this.ASDA = ASDA;
    this.LDA = LDA;
    this.displacedThreshold = displacedThreshold;
    this.away = away;

    declareParameters();
  }

  private void declareParameters() {
    clearway = TODA - TORA;
    stopway = ASDA - TORA;
    displacedThreshold = TORA - LDA;

  }

  public int getTORA() {
    return TORA;
  }

  public String getDesignator() {
    return designator;
  }

  public int getASDA() {
    return ASDA;
  }

  public int getClearway() {
    return clearway;
  }

  public int getDisplacedThreshold() {
    return displacedThreshold;
  }

  public int getLDA() {
    return LDA;
  }

  public int getStopway() {
    return stopway;
  }

  public int getTODA() {
    return TODA;
  }

  public Boolean getAway() {
    return away;
  }
}
