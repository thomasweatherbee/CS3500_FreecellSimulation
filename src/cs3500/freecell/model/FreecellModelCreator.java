package cs3500.freecell.model;

import cs3500.freecell.model.hw02.ICard;
import cs3500.freecell.model.hw02.SimpleFreecellModel;
import cs3500.freecell.model.hw04.MultiMoveFreecellModel;
import java.util.Objects;

/**
 * Factory class that creates new FreecellModels of a specified type.
 */
public class FreecellModelCreator {

  /**
   * Creates a new FreecellModel of the type specified (either single or multi-move).
   *
   * @param g type of model to create
   * @return new model specified by g
   */
  public static FreecellModel<ICard> create(GameType g) {
    Objects.requireNonNull(g);
    switch (g) {
      case MULTIMOVE:
        return new MultiMoveFreecellModel();
      case SINGLEMOVE:
        return new SimpleFreecellModel();
      default:
        throw new IllegalArgumentException("Provided game type is invalid");
    }
  }

  /**
   * Represents the two types of games FreecellModelCreator can make.
   */
  public enum GameType {
    SINGLEMOVE, MULTIMOVE
  }
}
