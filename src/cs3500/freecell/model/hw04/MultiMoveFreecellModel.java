package cs3500.freecell.model.hw04;

import cs3500.freecell.model.PileType;
import cs3500.freecell.model.hw02.ICard;
import cs3500.freecell.model.hw04.piles.CascadePileMultiMove;
import cs3500.freecell.model.hw04.piles.FoundationPileMultiMove;
import cs3500.freecell.model.hw04.piles.IPileMultiMove;
import cs3500.freecell.model.hw04.piles.OpenPileMultiMove;
import java.util.ArrayList;

/**
 * Represents a code implementation of the Freecell game with the ability to move multiple cards at
 * once.
 */
public class MultiMoveFreecellModel extends AFreecellModel<IPileMultiMove> {

  /**
   * Creates a new MultiMoveFreecellModel.
   */
  public MultiMoveFreecellModel() {
    super(p -> {
      switch (p) {
        case FOUNDATION:
          return new FoundationPileMultiMove();
        case CASCADE:
          return new CascadePileMultiMove();
        case OPEN:
          return new OpenPileMultiMove();
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

    IPileMultiMove destPile = getPilesOfType(destination).get(destPileNumber);
    IPileMultiMove srcPile = getPilesOfType(source).get(pileNumber);

    if (srcPile.numCards() - cardIndex > maxCardsMoved()) {
      throw new IllegalArgumentException(
          "Trying to move " + (srcPile.numCards() - cardIndex) + " cards, when maximum is "
              + Math.round(maxCardsMoved()) + ".");
    }

    ArrayList<ICard> toBeMoved = new ArrayList<ICard>();
    toBeMoved.add(srcPile.getCardAt(cardIndex));
    srcPile.removeCardAt(cardIndex);
    while (cardIndex < srcPile.numCards()) {
      toBeMoved.add(srcPile.getCardAt(cardIndex));
      srcPile.removeCardAt(cardIndex);
    }
    try {
      destPile.addStackIfPossible(toBeMoved);
    } catch (IllegalArgumentException e) {
      for (ICard c : toBeMoved) {
        srcPile.addCard(c);
      }
      throw e;
    }
  }

  /**
   * Calculates the maximum number of cards that can be moved based on the number of empty piles.
   *
   * @return the maximum number of cards moved
   */
  protected double maxCardsMoved() {
    int countOpens = 0;
    int countCascades = 0;

    for (int i = 0; i < opens.size(); i++) {
      if (getNumCardsInOpenPile(i) == 0) {
        countOpens++;
      }
    }

    for (int i = 0; i < cascades.size(); i++) {
      if (getNumCardsInCascadePile(i) == 0) {
        countCascades++;
      }
    }

    return (countOpens + 1) * Math.pow(2, countCascades);
  }
}
