package modeltests;

import static org.junit.Assert.assertEquals;

import cs3500.freecell.model.hw02.Suite;
import org.junit.Test;

/**
 * Tests the Suite enum.
 */
public class SuiteTest {

  @Test
  public void testToString() {
    assertEquals(Suite.CLUBS.toString(), "♣");
    assertEquals(Suite.HEARTS.toString(), "♥");
    assertEquals(Suite.DIAMONDS.toString(), "♦");
    assertEquals(Suite.SPADES.toString(), "♠");
  }
}