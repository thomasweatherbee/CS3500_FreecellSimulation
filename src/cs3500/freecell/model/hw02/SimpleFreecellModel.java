package cs3500.freecell.model.hw02;

import cs3500.freecell.model.PileType;
import cs3500.freecell.model.hw02.piles.CascadePile;
import cs3500.freecell.model.hw02.piles.FoundationPile;
import cs3500.freecell.model.hw02.piles.IPile;
import cs3500.freecell.model.hw02.piles.OpenPile;
import cs3500.freecell.model.hw04.AFreecellModel;

/**
 * Represents a code implementation of a simplified Freecell game.
 */
public class SimpleFreecellModel extends AFreecellModel<IPile> {

  /**
   * Constructs a new SimpleFreecellModel.
   */
  public SimpleFreecellModel() {
    super((p) -> {
      switch (p) {
        case FOUNDATION:
          return new FoundationPile();
        case CASCADE:
          return new CascadePile();
        case OPEN:
          return new OpenPile();
        default:
          throw new IllegalArgumentException("Provided pile type is not a valid pile type");
      }
    });
  }

  @Override
  public void move(PileType source, int pileNumber, int cardIndex, PileType destination,
      int destPileNumber) throws IllegalArgumentException, IllegalStateException {
    assertStarted();

    if (indexOutOfBounds(source, pileNumber)) {
      throw new IllegalArgumentException("Source pile number out of bounds");
    }

    if (indexOutOfBounds(destination, destPileNumber)) {
      throw new IllegalArgumentException("Destination pile number out of bounds");
    }

    IPile destPile = getPilesOfType(destination).get(destPileNumber);
    IPile srcPile = getPilesOfType(source).get(pileNumber);

    if (cardIndex != srcPile.numCards() - 1) {
      throw new IllegalArgumentException("Player can only move the first card in the pile");
    }

    ICard toBeMoved = srcPile.getCardAt(cardIndex);
    srcPile.removeCardAt(cardIndex);
    try {
      destPile.addCardIfPossible(toBeMoved);
    } catch (IllegalArgumentException e) {
      srcPile.addCard(toBeMoved);
      throw e;
    }
  }
}