package cs3500.freecell.model.hw04.piles;

import cs3500.freecell.model.hw02.ICard;
import cs3500.freecell.model.hw02.piles.OpenPile;
import java.util.List;

/**
 * Represents a Freecell open pile with the ability to move multiple cards at once.
 */
public class OpenPileMultiMove extends OpenPile implements IPileMultiMove {

  @Override
  public void addStackIfPossible(List<ICard> stack) throws IllegalArgumentException {
    if (stack.size() > 1) {
      throw new IllegalArgumentException("Cannot move multiple cards onto an open pile.");
    } else {
      addCardIfPossible(stack.get(0));
    }
  }
}
