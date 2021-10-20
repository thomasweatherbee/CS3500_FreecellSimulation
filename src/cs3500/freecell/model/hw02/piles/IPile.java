package cs3500.freecell.model.hw02.piles;

import cs3500.freecell.model.hw02.ICard;

/**
 * Represents a pile of cards.
 */
public interface IPile {

  /**
   * Returns the card at a specified index in the pile.
   *
   * @param cardIndex index of requested card.
   * @return the card at cardIndex in this pile.
   * @throws IllegalArgumentException if cardIndex is out of bounds.
   */
  ICard getCardAt(int cardIndex) throws IllegalArgumentException;

  /**
   * Adds a card to the end of this pile.
   *
   * @param card card to be added.
   */
  void addCard(ICard card);

  /**
   * Returns the number of cards in the pile.
   *
   * @return the number of cards in this pile.
   */
  int numCards();

  /**
   * Determines if this pile is full--cascades cannot be filled, foundations have a maximum of 13
   * cards, and opens have a maximum of 1 card.
   *
   * @return is this pile at maximum capacity?
   */
  boolean isFull();

  /**
   * Adds a given card ot the end of a pile if possible, conforming to the rules of the pile.
   *
   * @param card card to be added.
   * @throws IllegalArgumentException if the specified card cannot be added.
   */
  void addCardIfPossible(ICard card) throws IllegalArgumentException;

  /**
   * Removes the card at the given index in the pile.
   *
   * @param cardIndex index of the card to be removed.
   * @throws IllegalArgumentException if cardIndex is out of bounds.
   */
  void removeCardAt(int cardIndex) throws IllegalArgumentException;
}
