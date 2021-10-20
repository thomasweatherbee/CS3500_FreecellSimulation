package modeltests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import cs3500.freecell.model.hw02.CardImpl;
import cs3500.freecell.model.hw02.ICard;
import cs3500.freecell.model.hw02.Suite;
import cs3500.freecell.model.hw02.Value;
import java.awt.Color;
import org.junit.Before;
import org.junit.Test;

/**
 * Tests the CardImple class.
 */
public class CardImplTest {

  ICard card;

  @Before
  public void setUp() throws Exception {
    card = new CardImpl(Suite.CLUBS, Value.EIGHT);
  }

  @Test
  public void testToString() {
    assertEquals(card.toString(), "8♣");
  }

  @Test
  public void testEqualsEqual() {
    ICard a = new CardImpl(Suite.CLUBS, Value.EIGHT);
    assertEquals(card, a);
  }

  @Test
  public void testEqualsNotEqual() {
    ICard a = new CardImpl(Suite.HEARTS, Value.EIGHT);
    assertNotEquals(card, a);
  }

  @Test
  public void testHashCode() {
    ICard a = new CardImpl(Suite.CLUBS, Value.EIGHT);
    assertEquals(card.hashCode(), a.hashCode());
  }

  @Test
  public void testGetSuite() {
    assertEquals(card.getSuite(), "♣");
  }

  @Test
  public void testGetColor() {
    assertEquals(card.getColor(), Color.BLACK);
  }

  @Test
  public void testGetValue() {
    assertEquals(card.getValue(), 8);
  }
}