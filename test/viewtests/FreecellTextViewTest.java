package viewtests;

import static org.junit.Assert.assertEquals;

import cs3500.freecell.model.FreecellModel;
import cs3500.freecell.model.PileType;
import cs3500.freecell.model.hw02.SimpleFreecellModel;
import cs3500.freecell.view.FreecellTextView;
import cs3500.freecell.view.FreecellView;
import java.io.IOException;
import java.io.StringWriter;
import org.junit.Before;
import org.junit.Test;

/**
 * Tests the FreecellTextView class.
 */
public class FreecellTextViewTest {
  FreecellModel s;
  FreecellView ftv;
  Appendable ap;

  @Before
  public void setUp() {
    s = new SimpleFreecellModel();
    ap = new StringWriter();
    ftv = new FreecellTextView(s, ap);
  }

  @Test
  public void testToString() {
    s.startGame(s.getDeck(), 4, 1, false);
    assertEquals(ftv.toString(), "F1: \n"
        + "F2: \n"
        + "F3: \n"
        + "F4: \n"
        + "O1: \n"
        + "C1: A♣, 5♣, 9♣, K♣, 4♦, 8♦, Q♦, 3♥, 7♥, J♥, 2♠, 6♠, 10♠\n"
        + "C2: 2♣, 6♣, 10♣, A♦, 5♦, 9♦, K♦, 4♥, 8♥, Q♥, 3♠, 7♠, J♠\n"
        + "C3: 3♣, 7♣, J♣, 2♦, 6♦, 10♦, A♥, 5♥, 9♥, K♥, 4♠, 8♠, Q♠\n"
        + "C4: 4♣, 8♣, Q♣, 3♦, 7♦, J♦, 2♥, 6♥, 10♥, A♠, 5♠, 9♠, K♠\n");
  }

  @Test
  public void testToStringMovedCards() {
    s.startGame(s.getDeck(), 4, 4, false);
    s.move(PileType.CASCADE, 3, 12, PileType.OPEN, 0);
    s.move(PileType.CASCADE, 3, 11, PileType.OPEN, 1);
    s.move(PileType.CASCADE, 3, 10, PileType.OPEN, 2);
    s.move(PileType.CASCADE, 3, 9, PileType.FOUNDATION, 2);
    assertEquals(ftv.toString(), "F1: \n"
        + "F2: \n"
        + "F3: A♠\n"
        + "F4: \n"
        + "O1: K♠\n"
        + "O2: 9♠\n"
        + "O3: 5♠\n"
        + "O4: \n"
        + "C1: A♣, 5♣, 9♣, K♣, 4♦, 8♦, Q♦, 3♥, 7♥, J♥, 2♠, 6♠, 10♠\n"
        + "C2: 2♣, 6♣, 10♣, A♦, 5♦, 9♦, K♦, 4♥, 8♥, Q♥, 3♠, 7♠, J♠\n"
        + "C3: 3♣, 7♣, J♣, 2♦, 6♦, 10♦, A♥, 5♥, 9♥, K♥, 4♠, 8♠, Q♠\n"
        + "C4: 4♣, 8♣, Q♣, 3♦, 7♦, J♦, 2♥, 6♥, 10♥\n");
  }

  @Test
  public void testToStringBeforeGameStarted() {
    assertEquals(ftv.toString(), "");
  }

  @Test
  public void testRenderMessage() {
    try {
      ftv.renderMessage("hello world");
    } catch (IOException e) {
      e.printStackTrace();
    }
    assertEquals(ap.toString(), "hello world");
  }

  @Test (expected = IOException.class)
  public void testRenderMessageFail() throws IOException {
    ftv = new FreecellTextView(s, new Appendable() {
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

    ftv.renderMessage("hello world");
  }

  @Test
  public void testRenderBoard() {
    s.startGame(s.getDeck(), 4, 1, false);
    try {
      ftv.renderBoard();
    } catch (IOException e) {
      e.printStackTrace();
    }

    assertEquals(ap.toString(), ftv.toString());
  }

  @Test (expected = IOException.class)
  public void testRenderBoardFail() throws IOException {
    s.startGame(s.getDeck(), 4, 1, false);
    ftv = new FreecellTextView(s, new Appendable() {
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

    ftv.renderBoard();
  }

  @Test
  public void testRenderBoardBeforeStartedGame() {
    try {
      ftv.renderBoard();
    } catch (IOException e) {
      e.printStackTrace();
    }
    assertEquals(ap.toString(), "");
  }
}