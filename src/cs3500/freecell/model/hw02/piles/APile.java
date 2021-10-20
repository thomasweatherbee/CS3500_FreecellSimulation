package cs3500.freecell.model.hw02.piles;

import cs3500.freecell.model.hw02.ICard;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Abstract base class for implementations of IPile.
 */
public abstract class APile implements IPile {

  protected final List<ICard> cards;

  protected APile() {
    this.cards = new ArrayList<ICard>();
  }

  @Override
  public ICard getCardAt(int cardIndex) throws IllegalArgumentException {
    if (cardIndex < 0 || cardIndex >= cards.size()) {
      throw new IllegalArgumentException(
          "Index " + cardIndex + " out of bounds for pile of size " + cards.size());
    }
    return cards.get(cardIndex);
  }

  @Override
  public void addCard(ICard card) {
    Objects.requireNonNull(card);
    cards.add(card);
  }

  @Override
  public int numCards() {
    return cards.size();
  }

  @Override
  public void removeCardAt(int cardIndex) throws IllegalArgumentException {
    if (cardIndex < 0 || cardIndex >= cards.size()) {
      throw new IllegalArgumentException(
          "Index " + cardIndex + " out of bounds for pile of size " + cards.size());
    }
    cards.remove(cardIndex);
  }
}
