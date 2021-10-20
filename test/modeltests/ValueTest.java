package modeltests;

import static org.junit.Assert.assertEquals;

import cs3500.freecell.model.hw02.Value;
import org.junit.Test;

/**
 * Tests the value enum.
 */
public class ValueTest {

  @Test
  public void testToString() {
    assertEquals(Value.ACE.toString(), "A");
    assertEquals(Value.EIGHT.toString(), "8");
  }
}