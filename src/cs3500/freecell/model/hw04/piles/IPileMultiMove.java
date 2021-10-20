package cs3500.freecell.model.hw04.piles;

import cs3500.freecell.model.hw02.ICard;
import cs3500.freecell.model.hw02.piles.IPile;
import java.util.List;

/**
 * Represents a Freecell pile with the ability to move multiple cards at once.
 */
public interface IPileMultiMove extends IPile {

  /**
   * Adds a stack of cards to this pile if it is a valid move (ie. the cards in the stack are
   * alternating colors and descending numbers, and this is a type of pile that accepts stacks).
   *
   * @param stack stack of cards to add
   * @throws IllegalArgumentException if stack of cards could not be added
   */
  public void addStackIfPossible(List<ICard> stack) throws IllegalArgumentException;
}
