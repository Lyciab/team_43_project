package uk.ac.soton.comp2211.team_43_project.model;

/**
 * A Runway object.
 */
public class Runway {

  private final String designator;
  private final Boolean away;
  private int toda;
  private int tora;
  private int asda;
  private int lda;
  private int displacedThreshold;
  private int clearway;
  private int stopway;

  /**
   * Create a new runway.
   *
   * @param designator designator of the runway
   * @param away is taking off away
   * @param tora the TORA
   * @param toda the TODA
   * @param asda the ASDA
   * @param lda the LDA
   * @param displacedThreshold the displaced threshold
   */
  public Runway(String designator, Boolean away,
                int tora, int toda, int asda, int lda, int displacedThreshold) {
    this.designator = designator;
    this.tora = tora;
    this.toda = toda;
    this.asda = asda;
    this.lda = lda;
    this.displacedThreshold = displacedThreshold;
    this.away = away;

    declareParameters();
  }

  /**
   * Declares the calculated parameters.
   */
  private void declareParameters() {
    clearway = toda - tora;
    stopway = asda - tora;
    displacedThreshold = tora - lda;

  }

  public int getTora() {
    return tora;
  }

  public String getDesignator() {
    return designator;
  }

  public int getAsda() {
    return asda;
  }

  public int getClearway() {
    return clearway;
  }

  public int getDisplacedThreshold() {
    return displacedThreshold;
  }

  public int getLda() {
    return lda;
  }

  public int getStopway() {
    return stopway;
  }

  public int getToda() {
    return toda;
  }

  public Boolean getAway() {
    return away;
  }
}
