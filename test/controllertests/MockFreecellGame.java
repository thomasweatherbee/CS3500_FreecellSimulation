package controllertests;

import cs3500.freecell.model.FreecellModel;
import cs3500.freecell.model.PileType;
import cs3500.freecell.model.hw02.ICard;
import java.io.IOException;
import java.util.List;

/**
 * Represents a Mock Freecell Game to test inputs.
 */
public class MockFreecellGame implements FreecellModel<ICard> {

  private Appendable log;

  MockFreecellGame(Appendable a) {
    this.log = a;
  }

  @Override
  public List<ICard> getDeck() {
    return null;
  }

  @Override
  public void startGame(List<ICard> deck, int numCascadePiles, int numOpenPiles, boolean shuffle)
      throws IllegalArgumentException {
    try {
      log.append(
          deck.toString() + " " + numCascadePiles + " " + numOpenPiles + " " + shuffle + "\n");
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  @Override
  public void move(PileType source, int pileNumber, int cardIndex, PileType destination,
      int destPileNumber) throws IllegalArgumentException, IllegalStateException {
    try {
      log.append(
          source.toString() + " " + pileNumber + " " + cardIndex + " " + destination.toString()
              + " " + destPileNumber);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  @Override
  public boolean isGameOver() {
    return false;
  }

  @Override
  public int getNumCardsInFoundationPile(int index)
      throws IllegalArgumentException, IllegalStateException {
    return 0;
  }

  @Override
  public int getNumCascadePiles() {
    return 0;
  }

  @Override
  public int getNumCardsInCascadePile(int index)
      throws IllegalArgumentException, IllegalStateException {
    return 0;
  }

  @Override
  public int getNumCardsInOpenPile(int index)
      throws IllegalArgumentException, IllegalStateException {
    return 0;
  }

  @Override
  public int getNumOpenPiles() {
    return 0;
  }

  @Override
  public ICard getFoundationCardAt(int pileIndex, int cardIndex)
      throws IllegalArgumentException, IllegalStateException {
    return null;
  }

  @Override
  public ICard getCascadeCardAt(int pileIndex, int cardIndex)
      throws IllegalArgumentException, IllegalStateException {
    return null;
  }

  @Override
  public ICard getOpenCardAt(int pileIndex) throws IllegalArgumentException, IllegalStateException {
    return null;
  }
}
