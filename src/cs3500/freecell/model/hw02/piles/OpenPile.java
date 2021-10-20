package cs3500.freecell.model.hw02.piles;

import cs3500.freecell.model.hw02.ICard;
import java.util.Objects;

/**
 * Represents a freecell open pile.
 */
public class OpenPile extends APile {

  @Override
  public ICard getCardAt(int cardIndex) throws IllegalArgumentException {
    if (cards.size() == 0) {
      return null;
    } else {
      return cards.get(0);
    }
  }

  @Override
  public boolean isFull() {
    return super.cards.size() >= 1;
  }

  @Override
  public void addCardIfPossible(ICard card) throws IllegalArgumentException {
    Objects.requireNonNull(card);
    if (isFull()) {
      throw new IllegalArgumentException("Open pile is already occupied");
    } else {
      cards.add(card);
    }
  }
}
