package uk.ac.soton.comp2211.team_43_project.event;

/**
 * The Parameter Update Listener is used to handle the event when the parameters are changed. It passes
 * the new parameters in the message.
 */
public interface ParameterUpdateListener {

  /**
   * Handle new parameters input.
   *
   * @param TORA TORA
   * @param TODA TODA
   * @param ASDA ASDA
   * @param LDA LDA
   */
  void parameterUpdate(int TORA, int TODA, int ASDA, int LDA);
}
