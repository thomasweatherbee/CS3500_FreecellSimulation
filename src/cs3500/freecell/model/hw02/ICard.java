package cs3500.freecell.model.hw02;

/**
 * Represents a playing card.
 */
public interface ICard {

  /**
   * Returns the string representation of a card.
   *
   * @return a string representation of the card.
   */
  String toString();

  /**
   * Overrides the equals method, checks if the string representations for two ICards are the same.
   *
   * @param other the other card.
   * @return is this card equal to other card?
   */
  boolean equals(Object other);

  /**
   * Calculates the hashcode for an ICard.
   *
   * @return the hashcode for an ICard.
   */
  int hashCode();

  /**
   * Returns the suite of the card.
   *
   * @return the suite of this ICard.
   */
  String getSuite();

  /**
   * Returns the color of the card.
   *
   * @return the color of this ICard.
   */
  Color getColor();

  /**
   * Returns the value of the card.
   *
   * @return the numerical value of this ICard.
   */
  int getValue();
}