package cs3500.freecell.model.hw02;

/**
 * Type for the four types of Suites for a given card: clubs, diamonds, hearts, and spades.
 */
public enum Suite {
  CLUBS("♣"), DIAMONDS("♦"), HEARTS("♥"), SPADES("♠");

  private final String representation;

  Suite(String representation) {
    this.representation = representation;
  }

  public String toString() {
    return representation;
  }
}
