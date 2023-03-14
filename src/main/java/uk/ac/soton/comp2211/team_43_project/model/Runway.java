package uk.ac.soton.comp2211.team_43_project.model;

/**
 * A Runway object.
 */
public class Runway {

  private final String designator;
  private final Boolean away;
  private float
      toda;
  private float
      tora;
  private float
      asda;
  private float
      lda;
  private float
      displacedThreshold;
  private float
      clearway;
  private float
      stopway;

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
                float
                    tora, float
                    toda, float
                    asda, float
                    lda, float
                    displacedThreshold) {
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

  public float
  getTora() {
    return tora;
  }

  public String getDesignator() {
    return designator;
  }

  public float
  getAsda() {
    return asda;
  }

  public float
  getClearway() {
    return clearway;
  }

  public float
  getDisplacedThreshold() {
    return displacedThreshold;
  }

  public float
  getLda() {
    return lda;
  }

  public float
  getStopway() {
    return stopway;
  }

  public float
  getToda() {
    return toda;
  }

  public Boolean getAway() {
    return away;
  }
}
