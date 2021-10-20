package cs3500.freecell.model.hw02.piles;

import cs3500.freecell.model.hw02.ICard;
import java.util.Objects;

/**
 * Represents a freecell foundation pile.
 */
public class FoundationPile extends APile {

  @Override
  public boolean isFull() {
    return cards.size() >= 13;
  }

  @Override
  public void addCardIfPossible(ICard card) throws IllegalArgumentException {
    Objects.requireNonNull(card);
    if (cards.size() == 0) {
      if (card.getValue() == 1) {
        cards.add(card);
      } else {
        throw new IllegalArgumentException(
            card.toString() + " cannot be moved to an empty foundation pile.");
      }
    } else if (card.getSuite().equals(cards.get(cards.size() - 1).getSuite())
        && card.getValue() == cards.get(cards.size() - 1).getValue() + 1) {
      cards.add(card);
    } else {
      throw new IllegalArgumentException(
          card.toString() + " cannot be moved to this foundation pile above " + cards
              .get(cards.size() - 1)
              .toString());
    }
  }
}
