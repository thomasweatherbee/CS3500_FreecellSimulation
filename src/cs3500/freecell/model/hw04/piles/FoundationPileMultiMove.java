package cs3500.freecell.model.hw04.piles;

import cs3500.freecell.model.hw02.ICard;
import cs3500.freecell.model.hw02.piles.FoundationPile;
import java.util.List;

/**
 * Represents a Freecell foundation pile with the ability to move multiple cards at once.
 */
public class FoundationPileMultiMove extends FoundationPile implements IPileMultiMove {

  @Override
  public void addStackIfPossible(List<ICard> stack) throws IllegalArgumentException {
    if (stack.size() > 1) {
      throw new IllegalArgumentException("Cannot move multiple cards onto a foundation pile.");
    } else {
      addCardIfPossible(stack.get(0));
    }
  }
}
