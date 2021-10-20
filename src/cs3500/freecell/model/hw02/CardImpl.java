package cs3500.freecell.model.hw02;

import java.util.Objects;

/**
 * Represents a playing card with a suite and value.
 */
public class CardImpl implements ICard {

  private final Suite suite;
  private final Value value;

  public CardImpl(Suite suite, Value value) {
    this.suite = Objects.requireNonNull(suite);
    this.value = Objects.requireNonNull(value);
  }

  @Override
  public String toString() {
    return value.toString() + suite.toString();
  }

  @Override
  public boolean equals(Object other) {
    if (other == null) {
      return false;
    }

    if (other instanceof ICard) {
      other = (ICard) other;
    }

    return this.toString().equals(other.toString());
  }

  @Override
  public int hashCode() {
    return this.toString().hashCode();
  }

  @Override
  public String getSuite() {
    return this.suite.toString();
  }

  @Override
  public Color getColor() {
    if (getSuite().equals("♠") || getSuite().equals("♣")) {
      return Color.BLACK;
    } else {
      return Color.RED;
    }
  }

  @Override
  public int getValue() {
    return this.value.getValue();
  }
}
