package cs3500.freecell.model.hw02.piles;

import cs3500.freecell.model.hw02.ICard;
import java.util.Objects;

/**
 * Represents a freecell cascade pile.
 */
public class CascadePile extends APile {

  @Override
  public boolean isFull() {
    return false;
  }

  @Override
  public void addCardIfPossible(ICard card) throws IllegalArgumentException {
    Objects.requireNonNull(card);
    if (cards.size() == 0) {
      if (card.getValue() == 13) {
        cards.add(card);
      } else {
        throw new IllegalArgumentException(
            card.toString() + " must be a King to be placed on an empty cascade pile");
      }
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
}
