package modeltests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import cs3500.freecell.model.FreecellModel;
import cs3500.freecell.model.PileType;
import cs3500.freecell.model.hw02.SimpleFreecellModel;
import cs3500.freecell.model.hw04.MultiMoveFreecellModel;
import cs3500.freecell.view.FreecellTextView;
import cs3500.freecell.view.FreecellView;
import org.junit.Before;
import org.junit.Test;

/**
 * Represents general tests for all FreecellModel implementations.
 */
public abstract class AbstractModelTest {
  FreecellModel sfm;
  FreecellView ftv;

  @Before
  public void setUp() throws Exception {
    sfm = getModel();
    ftv = new FreecellTextView(sfm);
  }

  public abstract FreecellModel getModel();

  /**
   * Tests all the AbstractModelTests with a SimpleFreecellModel.
   */
  public static class SimpleFreecellModelTestGeneral extends AbstractModelTest {

    @Override
    public FreecellModel getModel() {
      return new SimpleFreecellModel();
    }
  }

  /**
   * Tests all the AbstractModelTests with a MultiMoveFreecellModel.
   */
  public static class MultiMoveFreecellModelTestGeneral extends AbstractModelTest {

    @Override
    public FreecellModel getModel() {
      return new MultiMoveFreecellModel();
    }
  }

  @Test
  public void startGameShuffled() {
    sfm.startGame(sfm.getDeck(), 4, 1, false);
    String unShuffled = ftv.toString();
    sfm.startGame(sfm.getDeck(), 4, 1, true);
    String shuffled = ftv.toString();
    assertNotEquals(unShuffled, shuffled);
  }

  @Test(expected = IllegalArgumentException.class)
  public void startGameNotEnoughCascades() {
    sfm.startGame(sfm.getDeck(), 3, 2, false);
  }

  @Test(expected = IllegalArgumentException.class)
  public void startGameNotEnoughOpens() {
    sfm.startGame(sfm.getDeck(), 5, 0, false);
  }

  @Test
  public void moveCtoO() {
    sfm.startGame(sfm.getDeck(), 4, 1, false);
    sfm.move(PileType.CASCADE, 0, 12, PileType.OPEN, 0);
    assertEquals(sfm.getOpenCardAt(0).toString(), "10♠");
    assertEquals(sfm.getCascadeCardAt(0, sfm.getNumCardsInCascadePile(0) - 1).toString(), "6♠");
  }

  @Test
  public void moveOtoC() {
    sfm.startGame(sfm.getDeck(), 8, 5, false);
    sfm.move(PileType.CASCADE, 7, 5, PileType.OPEN, 0);
    sfm.move(PileType.CASCADE, 7, 4, PileType.OPEN, 1);
    sfm.move(PileType.CASCADE, 7, 3, PileType.OPEN, 2);
    sfm.move(PileType.OPEN, 2, 0, PileType.CASCADE, 5);
    assertEquals(sfm.getCascadeCardAt(5, 6), "6♥");
  }

  @Test
  public void moveCtoC() {
    sfm.startGame(sfm.getDeck(), 8, 5, false);
    sfm.move(PileType.CASCADE, 7, 5, PileType.OPEN, 0);
    sfm.move(PileType.CASCADE, 7, 4, PileType.OPEN, 1);
    sfm.move(PileType.CASCADE, 7, 3, PileType.CASCADE, 5);
    assertEquals(sfm.getCascadeCardAt(5, 6), "6♥");
  }

  @Test
  public void moveCtoSameCValid() {
    sfm.startGame(sfm.getDeck(), 8, 5, false);
    sfm.move(PileType.CASCADE, 7, 5, PileType.OPEN, 0);
    sfm.move(PileType.CASCADE, 7, 4, PileType.OPEN, 1);
    sfm.move(PileType.CASCADE, 7, 3, PileType.CASCADE, 5);
    sfm.move(PileType.CASCADE, 5, 6, PileType.CASCADE, 5);
    assertEquals(sfm.getCascadeCardAt(5, 6), "6♥");
  }

  @Test
  public void moveOtoO() {
    sfm.startGame(sfm.getDeck(), 8, 5, false);
    sfm.move(PileType.CASCADE, 7, 5, PileType.OPEN, 0);
    sfm.move(PileType.CASCADE, 7, 4, PileType.OPEN, 1);
    sfm.move(PileType.OPEN, 1, 0, PileType.OPEN, 2);
    assertEquals(sfm.getOpenCardAt(2), "A♠");
    assertEquals(sfm.getNumCardsInOpenPile(1), 0);
  }

  @Test
  public void moveCtoFAce() {
    sfm.startGame(sfm.getDeck(), 8, 5, false);
    sfm.move(PileType.CASCADE, 7, 5, PileType.OPEN, 0);
    sfm.move(PileType.CASCADE, 7, 4, PileType.FOUNDATION, 0);
    assertEquals(sfm.getFoundationCardAt(0, 0), "A♠");
  }

  @Test
  public void moveCtoFStack() {
    sfm.startGame(sfm.getDeck(), 8, 5, false);
    sfm.move(PileType.CASCADE, 7, 5, PileType.OPEN, 0);
    sfm.move(PileType.CASCADE, 7, 4, PileType.FOUNDATION, 0);
    sfm.move(PileType.CASCADE, 0, 6, PileType.OPEN, 1);
    sfm.move(PileType.CASCADE, 0, 5, PileType.FOUNDATION, 0);
    assertEquals(sfm.getFoundationCardAt(0, 1), "2♠");
  }

  @Test
  public void moveOtoF() {
    sfm.startGame(sfm.getDeck(), 8, 5, false);
    sfm.move(PileType.CASCADE, 7, 5, PileType.OPEN, 0);
    sfm.move(PileType.CASCADE, 7, 4, PileType.OPEN, 1);
    sfm.move(PileType.OPEN, 1, 0, PileType.FOUNDATION, 0);
    assertEquals(sfm.getFoundationCardAt(0, 0), "A♠");
  }

  @Test
  public void moveFtoO() {
    sfm.startGame(sfm.getDeck(), 8, 5, false);
    sfm.move(PileType.CASCADE, 7, 5, PileType.OPEN, 0);
    sfm.move(PileType.CASCADE, 7, 4, PileType.OPEN, 1);
    sfm.move(PileType.OPEN, 1, 0, PileType.FOUNDATION, 0);
    sfm.move(PileType.FOUNDATION, 0, 0, PileType.OPEN, 1);
    assertEquals(sfm.getOpenCardAt(1), "A♠");
  }

  @Test
  public void moveFtoF() {
    sfm.startGame(sfm.getDeck(), 8, 5, false);
    sfm.move(PileType.CASCADE, 7, 5, PileType.OPEN, 0);
    sfm.move(PileType.CASCADE, 7, 4, PileType.FOUNDATION, 0);
    sfm.move(PileType.FOUNDATION, 0, 0, PileType.FOUNDATION, 1);
    assertEquals(sfm.getFoundationCardAt(1, 0), "A♠");
    assertEquals(sfm.getNumCardsInFoundationPile(0), 0);
  }

  @Test(expected = IllegalArgumentException.class)
  public void moveCtoSameCInvalid() {
    sfm.startGame(sfm.getDeck(), 8, 5, false);
    sfm.move(PileType.CASCADE, 7, 5, PileType.CASCADE, 7);
  }

  @Test(expected = IllegalArgumentException.class)
  public void moveCtoCSameColor() {
    sfm.startGame(sfm.getDeck(), 4, 1, false);
    sfm.move(PileType.CASCADE, 0, 12, PileType.OPEN, 0);
    sfm.move(PileType.OPEN, 0, 0, PileType.CASCADE, 0);
  }

  @Test(expected = IllegalArgumentException.class)
  public void moveCtoFullO() {
    sfm.startGame(sfm.getDeck(), 4, 1, false);
    sfm.move(PileType.CASCADE, 0, 12, PileType.OPEN, 0);
    sfm.move(PileType.CASCADE, 0, 11, PileType.OPEN, 0);
  }

  @Test(expected = IllegalArgumentException.class)
  public void moveCtoF_2toEmpty_Invalid() {
    sfm.startGame(sfm.getDeck(), 8, 5, false);
    sfm.move(PileType.CASCADE, 0, 6, PileType.OPEN, 1);
    sfm.move(PileType.CASCADE, 0, 5, PileType.FOUNDATION, 0);
  }

  @Test(expected = IllegalArgumentException.class)
  public void moveCtoF_non2toAce_Invalid() {
    sfm.startGame(sfm.getDeck(), 8, 5, false);
    sfm.move(PileType.CASCADE, 7, 5, PileType.OPEN, 0);
    sfm.move(PileType.CASCADE, 7, 4, PileType.FOUNDATION, 0);
    sfm.move(PileType.CASCADE, 0, 6, PileType.FOUNDATION, 0);
  }

  @Test(expected = IllegalArgumentException.class)
  public void moveCtoF_wrongColor2toAce_Invalid() {
    sfm.startGame(sfm.getDeck(), 8, 5, false);
    sfm.move(PileType.CASCADE, 7, 5, PileType.OPEN, 0);
    sfm.move(PileType.CASCADE, 7, 4, PileType.FOUNDATION, 0);
    sfm.move(PileType.CASCADE, 3, 6, PileType.OPEN, 1);
    sfm.move(PileType.CASCADE, 3, 5, PileType.OPEN, 2);
    sfm.move(PileType.CASCADE, 3, 4, PileType.OPEN, 3);
    sfm.move(PileType.CASCADE, 3, 3, PileType.FOUNDATION, 0);
  }

  @Test(expected = IllegalStateException.class)
  public void moveNotStarted() {
    sfm.move(PileType.CASCADE, 7, 5, PileType.OPEN, 0);
  }

  @Test
  public void isGameOverFalse() {
    sfm.startGame(sfm.getDeck(), 8, 5, false);
    assertEquals(sfm.isGameOver(), false);
  }

  @Test
  public void isGameOverTrue() {
    sfm.startGame(sfm.getDeck(), 52, 1, false);
    for (int i = 0; i < 52; i++) {
      sfm.move(PileType.CASCADE, i, 0, PileType.FOUNDATION, i / 13);
    }
    assertEquals(sfm.isGameOver(), true);
  }

  @Test
  public void testGetNumCardsInFoundationPileEmpty() {
    sfm.startGame(sfm.getDeck(), 52, 1, false);
    assertEquals(sfm.getNumCardsInFoundationPile(0), 0);
  }

  @Test
  public void testGetNumCardsInFoundationPileNonEmpty() {
    sfm.startGame(sfm.getDeck(), 52, 1, false);
    sfm.move(PileType.CASCADE, 0, 0, PileType.FOUNDATION, 0);
    assertEquals(sfm.getNumCardsInFoundationPile(0), 1);
  }

  @Test
  public void testGetNumCascadePiles() {
    sfm.startGame(sfm.getDeck(), 52, 1, false);
    assertEquals(sfm.getNumCascadePiles(), 52);
  }

  @Test
  public void testGetNumCascadePilesNotStarted() {
    assertEquals(sfm.getNumCascadePiles(), -1);
  }

  @Test
  public void testGetNumCardsInCascadePileEmpty() {
    sfm.startGame(sfm.getDeck(), 52, 1, false);
    sfm.move(PileType.CASCADE, 0, 0, PileType.FOUNDATION, 0);
    assertEquals(sfm.getNumCardsInCascadePile(0), 0);
  }

  @Test
  public void testRestart() {
    sfm.startGame(sfm.getDeck(), 8, 5, false);
    sfm.move(PileType.CASCADE, 7, 5, PileType.OPEN, 0);
    sfm.move(PileType.CASCADE, 7, 4, PileType.OPEN, 1);
    sfm.move(PileType.OPEN, 1, 0, PileType.FOUNDATION, 0);
    sfm.move(PileType.FOUNDATION, 0, 0, PileType.OPEN, 1);
    sfm.startGame(sfm.getDeck(), 8, 5, false);
    FreecellModel unchanged = new SimpleFreecellModel();
    unchanged.startGame(unchanged.getDeck(), 8, 5, false);
    assertEquals(ftv.toString(), new FreecellTextView(unchanged).toString());
  }

  @Test(expected = IllegalArgumentException.class)
  public void testGetNumCardsInCascadePileIndexTooHigh() {
    sfm.startGame(sfm.getDeck(), 52, 1, false);
    int a = sfm.getNumCardsInCascadePile(3789);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testGetNumCardsInCascadePileNegativeIndex() {
    sfm.startGame(sfm.getDeck(), 52, 1, false);
    int a = sfm.getNumCardsInCascadePile(-1);
  }

  @Test(expected = IllegalStateException.class)
  public void testGetNumCardsInCascadePileNotStarted() {
    int a = sfm.getNumCardsInCascadePile(2);
  }

  @Test
  public void testGetNumCardsInOpenPileEmpty() {
    sfm.startGame(sfm.getDeck(), 52, 1, false);
    assertEquals(sfm.getNumCardsInOpenPile(0), 0);
  }

  @Test
  public void testGetNumCardsInOpenPileNonEmpty() {
    sfm.startGame(sfm.getDeck(), 52, 1, false);
    sfm.move(PileType.CASCADE, 2, 0, PileType.OPEN, 0);
    assertEquals(sfm.getNumCardsInOpenPile(0), 1);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testGetNumCardsInOpenPileOutOfBounds() {
    sfm.startGame(sfm.getDeck(), 52, 1, false);
    int a = sfm.getNumCardsInOpenPile(2);
  }

  @Test(expected = IllegalStateException.class)
  public void testGetNumCardsInOpenPileNotStarted() {
    int a = sfm.getNumCardsInOpenPile(0);
  }

  @Test
  public void testGetNumOpenPiles() {
    sfm.startGame(sfm.getDeck(), 52, 1, false);
    assertEquals(sfm.getNumOpenPiles(), 1);
  }

  @Test
  public void testGetNumOpenPilesNotStarted() {
    assertEquals(sfm.getNumOpenPiles(), -1);
  }

  @Test
  public void testGetFoundationCardAt() {
    sfm.startGame(sfm.getDeck(), 52, 1, false);
    sfm.move(PileType.CASCADE, 0, 0, PileType.FOUNDATION, 0);
    assertEquals(sfm.getFoundationCardAt(0, 0), "A♣");
  }

  @Test(expected = IllegalArgumentException.class)
  public void testGetFoundationCardAtInvalidIndex() {
    sfm.startGame(sfm.getDeck(), 52, 1, false);
    sfm.move(PileType.CASCADE, 0, 0, PileType.FOUNDATION, 0);
    String c = sfm.getFoundationCardAt(0, 1).toString();
  }

  @Test(expected = IllegalStateException.class)
  public void testGetFoundationCardNotStarted() {
    String c = sfm.getFoundationCardAt(0, 0).toString();
  }

  @Test
  public void testGetCascadeCardAt() {
    sfm.startGame(sfm.getDeck(), 52, 1, false);
    assertEquals(sfm.getCascadeCardAt(0, 0), "A♣");
  }

  @Test(expected = IllegalArgumentException.class)
  public void testGetCascadeCardAtInvalidIndex() {
    sfm.startGame(sfm.getDeck(), 52, 1, false);
    String c = sfm.getCascadeCardAt(0, -1).toString();
  }

  @Test(expected = IllegalStateException.class)
  public void testGetCascadeCardAtNotStarted() {
    String c = sfm.getCascadeCardAt(0, 0).toString();
  }

  @Test
  public void testGetOpenCardAt() {
    sfm.startGame(sfm.getDeck(), 52, 1, false);
    sfm.move(PileType.CASCADE, 0, 0, PileType.OPEN, 0);
    assertEquals(sfm.getOpenCardAt(0), "A♣");
  }

  @Test(expected = IllegalArgumentException.class)
  public void testGetOpenCardAtInvalidIndex() {
    sfm.startGame(sfm.getDeck(), 52, 1, false);
    String c = sfm.getOpenCardAt(-1).toString();
  }

  @Test(expected = IllegalStateException.class)
  public void testGetOpenCardAtNotStarted() {
    String c = sfm.getOpenCardAt(0).toString();
  }
}
