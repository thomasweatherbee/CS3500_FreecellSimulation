package controllertests;

import static org.junit.Assert.assertEquals;

import cs3500.freecell.controller.FreecellController;
import cs3500.freecell.controller.SimpleFreecellController;
import cs3500.freecell.model.FreecellModel;
import cs3500.freecell.model.hw02.CardImpl;
import cs3500.freecell.model.hw02.ICard;
import cs3500.freecell.model.hw02.SimpleFreecellModel;
import cs3500.freecell.model.hw02.Suite;
import cs3500.freecell.model.hw02.Value;
import cs3500.freecell.view.FreecellTextView;
import cs3500.freecell.view.FreecellView;
import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Arrays;
import org.junit.Before;
import org.junit.Test;

/**
 * Tests the SimpleFreecellController class.
 */
public class SimpleFreecellControllerTest {

  FreecellModel<ICard> model;
  FreecellView view;
  FreecellController<ICard> controller;
  Appendable ap;
  Readable rd;

  @Before
  public void setUp() throws Exception {
    model = new SimpleFreecellModel();
    ap = new StringBuffer();
    view = new FreecellTextView(model, ap);
  }

  private void initController(String input) {
    rd = new StringReader(input);
    controller = new SimpleFreecellController(model, rd, ap);
  }

  @Test
  public void workingMove() {
    initController("C8 6 O1 q");
    controller.playGame(model.getDeck(), 8, 4, false);
    assertEquals(model.getOpenCardAt(0).toString(), "9♠");
  }

  @Test
  public void workingMoveMultiple() {
    initController("C8 6 O1 C8 5 F1 C1 7 O2 C1 6 F1 q");
    controller.playGame(model.getDeck(), 8, 4, false);
    assertEquals(model.getFoundationCardAt(0, 0), "A♠");
    assertEquals(model.getFoundationCardAt(0, 1), "2♠");
  }

  @Test
  public void testQuit() {
    initController("q");
    controller.playGame(model.getDeck(), 8, 4, false);
    assertEquals(ap.toString(), view.toString() + "Game quit prematurely.");
  }

  @Test
  public void testQuitInMiddle() {
    initController("C8 6 q O1");
    controller.playGame(model.getDeck(), 8, 4, false);
    assertEquals(ap.toString(), view.toString() + "Game quit prematurely.");
  }

  @Test
  public void testQuitInMiddleInCardIndex() {
    initController("C8 6q O1");
    controller.playGame(model.getDeck(), 8, 4, false);
    assertEquals(ap.toString(), view.toString() + "Game quit prematurely.");
  }

  @Test
  public void testQuitInMiddleInPileAddress() {
    initController("C8 6 Oq1");
    controller.playGame(model.getDeck(), 8, 4, false);
    assertEquals(ap.toString(), view.toString() + "Game quit prematurely.");
  }

  @Test
  public void testQuitInMiddleEndOfPileAddress() {
    initController("C8q 6 O1");
    controller.playGame(model.getDeck(), 8, 4, false);
    assertEquals(ap.toString(), view.toString() + "Game quit prematurely.");
  }

  @Test
  public void testQuitInMiddleAfterOneMove() {
    initController("C8 6 O1 O1 q");
    controller.playGame(model.getDeck(), 8, 4, false);
    FreecellModel<ICard> unchangedModel = new SimpleFreecellModel();
    unchangedModel.startGame(model.getDeck(), 8, 4, false);
    assertEquals(ap.toString(),
        new FreecellTextView(unchangedModel, new StringBuffer("")).toString() +
            view.toString() + "Game quit prematurely.");
  }

  @Test
  public void testGameEnded() {
    StringBuilder setOfMoves = new StringBuilder("");
    for (int i = 0; i < 52; i++) {
      int from = i + 1;
      int to = i / 13 + 1;
      setOfMoves.append("C" + from + " 1 " + "F" + to + " ");
    }
    initController(setOfMoves.toString());
    controller.playGame(model.getDeck(), 52, 4, false);
    assertEquals(
        ap.toString().substring(ap.toString().length() - 10, ap.toString().length()),
        "Game over.");
  }

  @Test
  public void testQAfterGameEnded() {
    StringBuilder setOfMoves = new StringBuilder("");
    for (int i = 0; i < 52; i++) {
      int from = i + 1;
      int to = i / 13 + 1;
      setOfMoves.append("C" + from + " 1 " + "F" + to + " ");
    }
    setOfMoves.append("q");
    initController(setOfMoves.toString());
    controller.playGame(model.getDeck(), 52, 4, false);
    assertEquals(
        ap.toString().substring(ap.toString().length() - 10, ap.toString().length()),
        "Game over.");
  }

  @Test
  public void testInvalidSrcPileType() {
    initController("A1 q");
    controller.playGame(model.getDeck(), 8, 4, false);
    assertEquals(ap.toString(),
        view.toString() + "Invalid input. Please try again.\nGame quit prematurely.");
  }

  @Test
  public void testInvalidSrcPileIndex() {
    initController("Ca q");
    controller.playGame(model.getDeck(), 8, 4, false);
    assertEquals(ap.toString(),
        view.toString() + "Invalid input. Please try again.\nGame quit prematurely.");
  }

  @Test
  public void testInvalidCardIndex() {
    initController("C1 g q");
    controller.playGame(model.getDeck(), 8, 4, false);
    assertEquals(ap.toString(),
        view.toString() + "Invalid input. Please try again.\nGame quit prematurely.");
  }

  @Test
  public void testInvalidDestPileType() {
    initController("C1 0 P1 q");
    controller.playGame(model.getDeck(), 8, 4, false);
    assertEquals(ap.toString(),
        view.toString() + "Invalid input. Please try again.\nGame quit prematurely.");
  }

  @Test
  public void testInvalidDestPileIndex() {
    initController("C1 0 F3p q");
    controller.playGame(model.getDeck(), 8, 4, false);
    assertEquals(ap.toString(),
        view.toString() + "Invalid input. Please try again.\nGame quit prematurely.");
  }

  @Test
  public void testInvalidSrcPileThenValid() {
    initController("Cp C1 7 O1 q");
    controller.playGame(model.getDeck(), 8, 4, false);
    assertEquals(model.getOpenCardAt(0).toString(), "10♠");
  }

  @Test
  public void testInvalidCardIndexThenValid() {
    initController("C1 a 7 O1 q");
    controller.playGame(model.getDeck(), 8, 4, false);
    assertEquals(model.getOpenCardAt(0).toString(), "10♠");
  }

  @Test
  public void testInvalidMoveThenValid() {
    initController("C1 7 F1 C1 7 O1 q");
    controller.playGame(model.getDeck(), 8, 4, false);
    assertEquals(model.getOpenCardAt(0).toString(), "10♠");
  }

  @Test(expected = IllegalArgumentException.class)
  public void testNullModel() {
    controller = new SimpleFreecellController(null, new StringReader(""), new StringWriter());
  }

  @Test(expected = IllegalArgumentException.class)
  public void testNullAppendable() {
    controller = new SimpleFreecellController(model, new StringReader(""), null);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testNullReadable() {
    controller = new SimpleFreecellController(model, null, new StringWriter());
  }

  @Test(expected = IllegalArgumentException.class)
  public void testNullDeck() {
    initController("Cp C1 7 O1 q");
    controller.playGame(null, 8, 4, false);
  }

  @Test
  public void testInvalidStartGameParams() {
    initController("Cp C1 7 O1 q");
    controller.playGame(model.getDeck(), 2, 4, false);
    assertEquals(ap.toString(), "Could not start game.");
  }

  @Test(expected = IllegalStateException.class)
  public void testEmptyReadable() {
    initController("");
    controller.playGame(model.getDeck(), 5, 4, false);
  }

  @Test(expected = IllegalStateException.class)
  public void testBrokenAppendable() {
    controller = new SimpleFreecellController(model, new StringReader("a"), new Appendable() {
      @Override
      public Appendable append(CharSequence csq) throws IOException {
        throw new IOException();
      }

      @Override
      public Appendable append(CharSequence csq, int start, int end) throws IOException {
        throw new IOException();
      }

      @Override
      public Appendable append(char c) throws IOException {
        throw new IOException();
      }
    });
    controller.playGame(model.getDeck(), 2, 4, false);
  }

  @Test
  public void testStartGameInputs() {
    FreecellModel<ICard> mockModel = new MockFreecellGame(ap);
    controller = new SimpleFreecellController(mockModel, new StringReader("q"),
        new StringWriter());
    controller
        .playGame(new ArrayList<ICard>(Arrays.asList(new CardImpl(Suite.CLUBS, Value.ACE),
            new CardImpl(Suite.DIAMONDS, Value.FIVE))), 4, 8, false);
    assertEquals(ap.toString(), "[A♣, 5♦] 4 8 false\n");
  }

  @Test
  public void testMoveInputs() {
    FreecellModel<ICard> mockModel = new MockFreecellGame(ap);
    controller = new SimpleFreecellController(mockModel, new StringReader("C7 3 F3 q"),
        new StringWriter());
    controller
        .playGame(new ArrayList<ICard>(Arrays.asList(new CardImpl(Suite.CLUBS, Value.ACE))), 4, 8,
            false);
    assertEquals(ap.toString(), "[A♣] 4 8 false\n" + "CASCADE 6 2 FOUNDATION 2");
  }
}