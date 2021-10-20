package modeltests;

import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

import cs3500.freecell.model.FreecellModelCreator;
import cs3500.freecell.model.FreecellModelCreator.GameType;
import cs3500.freecell.model.hw02.SimpleFreecellModel;
import cs3500.freecell.model.hw04.MultiMoveFreecellModel;
import org.junit.Test;

/**
 * Tests the FreecellModelCreator class.
 */
public class FreecellModelCreatorTest {

  @Test
  public void testCreateSimpleFreecell() {
    assertTrue(FreecellModelCreator.create(GameType.SINGLEMOVE) instanceof SimpleFreecellModel);
  }

  @Test
  public void testCreateMultimoveFreecell() {
    assertTrue(FreecellModelCreator.create(GameType.MULTIMOVE) instanceof MultiMoveFreecellModel);
  }

  @Test(expected = NullPointerException.class)
  public void testCreateNull() {
    FreecellModelCreator.create(null);
  }

  @Test
  public void testCreatesDifferentObjectsSingle() {
    assertNotEquals(FreecellModelCreator.create(GameType.SINGLEMOVE),
        FreecellModelCreator.create(GameType.SINGLEMOVE));
  }

  @Test
  public void testCreatesDifferentObjectsMulti() {
    assertNotEquals(FreecellModelCreator.create(GameType.MULTIMOVE),
        FreecellModelCreator.create(GameType.MULTIMOVE));
  }
}