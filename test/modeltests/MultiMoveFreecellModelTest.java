package modeltests;

import static org.junit.Assert.assertEquals;

import cs3500.freecell.model.FreecellModel;
import cs3500.freecell.model.PileType;
import cs3500.freecell.model.hw04.MultiMoveFreecellModel;
import cs3500.freecell.view.FreecellTextView;
import org.junit.Before;
import org.junit.Test;

/**
 * Tests the MultiMoveFreecellModel class.
 */
public class MultiMoveFreecellModelTest {
  FreecellModel model;
  FreecellTextView view;
  Appendable out;

  @Before
  public void setUp() throws Exception {
    model = new MultiMoveFreecellModel();
    out = new StringBuffer();
    view = new FreecellTextView(model, out);
  }

  @Test
  public void testMoveValid2Cards() {
    model.startGame(model.getDeck(), 16, 4, false);
    model.move(PileType.CASCADE, 0, 3, PileType.OPEN, 0);
    model.move(PileType.CASCADE, 2, 3, PileType.OPEN, 1);
    model.move(PileType.CASCADE, 0, 2, PileType.CASCADE, 14);
    model.move(PileType.CASCADE, 14, 2, PileType.CASCADE, 2);
    assertEquals(model.getCascadeCardAt(2,  2).toString(), "9♥");
    assertEquals(model.getCascadeCardAt(2, 3).toString(), "8♠");
    assertEquals(model.getCascadeCardAt(2, 4).toString(), "7♥");
  }

  @Test
  public void testMoveValid3Cards() {
    model.startGame(model.getDeck(), 16, 6, false);
    model.move(PileType.CASCADE, 0, 3, PileType.OPEN, 0);
    model.move(PileType.CASCADE, 2, 3, PileType.OPEN, 1);
    model.move(PileType.CASCADE, 0, 2, PileType.CASCADE, 14);
    model.move(PileType.CASCADE, 14, 2, PileType.CASCADE, 2);
    model.move(PileType.CASCADE, 9, 2, PileType.OPEN, 2);
    model.move(PileType.CASCADE, 9, 1, PileType.OPEN, 3);
    model.move(PileType.CASCADE, 2, 2,PileType.CASCADE, 9);
    assertEquals(model.getCascadeCardAt(9,  0).toString(), "10♣");
    assertEquals(model.getCascadeCardAt(9,  1).toString(), "9♥");
    assertEquals(model.getCascadeCardAt(9, 2).toString(), "8♠");
    assertEquals(model.getCascadeCardAt(9, 3).toString(), "7♥");
  }

  @Test
  public void testMoveStackToEmptyCascade() {
    model.startGame(model.getDeck(), 16, 6, false);
    model.move(PileType.CASCADE, 0, 3, PileType.OPEN, 0);
    model.move(PileType.CASCADE, 2, 3, PileType.OPEN, 1);
    model.move(PileType.CASCADE, 0, 2, PileType.CASCADE, 14);
    model.move(PileType.CASCADE, 14, 2, PileType.CASCADE, 2);
    model.move(PileType.CASCADE, 9, 2, PileType.OPEN, 2);
    model.move(PileType.CASCADE, 9, 1, PileType.OPEN, 3);
    model.move(PileType.CASCADE, 2, 2,PileType.CASCADE, 9);
    model.move(PileType.CASCADE, 0, 1, PileType.OPEN, 4);
    model.move(PileType.CASCADE, 0, 0, PileType.FOUNDATION, 0);
    model.move(PileType.CASCADE, 9, 0, PileType.CASCADE, 0);
    assertEquals(model.getCascadeCardAt(0,  0).toString(), "10♣");
    assertEquals(model.getCascadeCardAt(0,  1).toString(), "9♥");
    assertEquals(model.getCascadeCardAt(0, 2).toString(), "8♠");
    assertEquals(model.getCascadeCardAt(0, 3).toString(), "7♥");
    assertEquals(model.getNumCardsInCascadePile(9), 0);
  }

  @Test (expected = IllegalArgumentException.class)
  public void testMoveInvalidStack() {
    model.startGame(model.getDeck(), 16, 2, false);
    model.move(PileType.CASCADE, 0, 3, PileType.OPEN, 0);
    model.move(PileType.CASCADE, 2, 3, PileType.OPEN, 1);
    model.move(PileType.CASCADE, 0, 2, PileType.CASCADE, 14);
    model.move(PileType.CASCADE, 14, 2, PileType.CASCADE, 2);
  }

  @Test (expected = IllegalArgumentException.class)
  public void testMoveInvalidStack_containsValidStack_() {
    model.startGame(model.getDeck(), 11, 4, false);
    model.move(PileType.CASCADE, 11, 1, PileType.CASCADE, 2);
  }

  @Test (expected = IllegalArgumentException.class)
  public void testMoveInvalidStack_rightOrderButSameColor_() {
    model.startGame(model.getDeck(), 12, 4, false);
    model.move(PileType.CASCADE, 9, 3, PileType.OPEN, 0);
    model.move(PileType.CASCADE, 9, 1, PileType.CASCADE, 0);
  }

  @Test (expected = IllegalArgumentException.class)
  public void testMoveTooManyCards_move4whenMaxIs3_() {
    model.startGame(model.getDeck(), 12, 2, false);
    model.move(PileType.CASCADE, 8, 2, PileType.CASCADE, 10);
    model.move(PileType.CASCADE, 10, 2, PileType.CASCADE, 0);
  }

  @Test (expected = IllegalArgumentException.class)
  public void testMoveStackToFoundation() {
    model.startGame(model.getDeck(), 12, 4, false);
    model.move(PileType.CASCADE, 3, 4, PileType.OPEN, 0);
    model.move(PileType.CASCADE, 3, 2, PileType.FOUNDATION, 0);
  }

  @Test (expected = IllegalArgumentException.class)
  public void testMoveStackToOpen() {
    model.startGame(model.getDeck(), 12, 4, false);
    model.move(PileType.CASCADE, 3, 4, PileType.OPEN, 0);
    model.move(PileType.CASCADE, 3, 2, PileType.OPEN, 0);
  }

  @Test (expected = IllegalArgumentException.class)
  public void testMoveStackFoundationToCascade() {
    model.startGame(model.getDeck(), 12, 4, false);
    model.move(PileType.CASCADE, 3, 4, PileType.OPEN, 0);
    model.move(PileType.CASCADE, 3, 3, PileType.FOUNDATION, 0);
    model.move(PileType.CASCADE, 4, 3, PileType.FOUNDATION, 0);
    model.move(PileType.CASCADE, 3, 2, PileType.OPEN, 1);
    model.move(PileType.CASCADE, 3, 1, PileType.OPEN, 2);
    model.move(PileType.CASCADE, 3, 0, PileType.OPEN, 3);
    model.move(PileType.FOUNDATION, 0, 0, PileType.CASCADE, 3);
  }
}