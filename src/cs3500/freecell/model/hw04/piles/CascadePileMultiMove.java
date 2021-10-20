package cs3500.freecell.model.hw04.piles;

import cs3500.freecell.model.hw02.ICard;
import cs3500.freecell.model.hw02.piles.CascadePile;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Represents a Freecell cascade pile with the ability to move multiple cards at once.
 */
public class CascadePileMultiMove extends CascadePile implements IPileMultiMove {

  @Override
  public void addCardIfPossible(ICard card) throws IllegalArgumentException {
    Objects.requireNonNull(card);
    if (cards.size() == 0) {
      cards.add(card);
    } else {
      if (card.getColor() != cards.get(cards.size() - 1).getColor()
          && card.getValue() == cards.get(cards.size() - 1).getValue() - 1) {
        cards.add(card);
      } else {
        throw new IllegalArgumentException(card.toString() + " cannot be placed on top of " +
            cards.get(cards.size() - 1).toString());
      }
    }
  }

  @Override
  public void addStackIfPossible(List<ICard> stack) throws IllegalArgumentException {
    List<ICard> saveState = new ArrayList<ICard>(cards);
    try {
      for (ICard card : stack) {
        addCardIfPossible(card);
      }
    } catch (IllegalArgumentException e) {
      cards.clear();
      cards.addAll(saveState);
      throw new IllegalArgumentException("Unable to move stack, recieved error: " + e.getMessage());
    }
  }
}
