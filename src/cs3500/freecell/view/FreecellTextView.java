package cs3500.freecell.view;

import cs3500.freecell.model.FreecellModel;
import java.io.IOException;
import java.util.Objects;

/**
 * Represents a text view of a Freecell model.
 */
public class FreecellTextView implements FreecellView {

  FreecellModel model;
  Appendable dest;

  public FreecellTextView(FreecellModel<?> model, Appendable destination) {
    this.model = Objects.requireNonNull(model);
    this.dest = Objects.requireNonNull(destination);
  }

  public FreecellTextView(FreecellModel<?> model) {
    this(model, System.out);
  }

  @Override
  public String toString() {
    StringBuilder out = new StringBuilder("");

    try {
      for (int i = 0; i < 4; i++) {
        out.append("F" + (i + 1) + ":");
        int cardsInPile = model.getNumCardsInFoundationPile(i);
        if (cardsInPile > 0) {
          out.append(" " + model.getFoundationCardAt(i, 0).toString());
          for (int j = 1; j < cardsInPile; j++) {
            out.append(", " + model.getFoundationCardAt(i, j).toString());
          }
        }
        out.append("\n");
      }

      int x = model.getNumOpenPiles();
      for (int i = 0; i < model.getNumOpenPiles(); i++) {
        out.append("O" + (i + 1) + ":");

        if (model.getNumCardsInOpenPile(i) > 0) {
          out.append(" " + model.getOpenCardAt(i).toString());
        }

        out.append("\n");
      }

      for (int i = 0; i < model.getNumCascadePiles(); i++) {
        out.append("C" + (i + 1) + ":");
        int cardsInPile = model.getNumCardsInCascadePile(i);
        if (cardsInPile > 0) {
          out.append(" " + model.getCascadeCardAt(i, 0).toString());
          for (int j = 1; j < cardsInPile; j++) {
            out.append(", " + model.getCascadeCardAt(i, j).toString());
          }
        }
        out.append("\n");
      }
      out.delete(out.length() - 1, out.length());
    } catch (IllegalStateException e) {
      return "";
    }
    return out.toString();
  }

  @Override
  public void renderBoard() throws IOException {
    dest.append(toString() + "\n");
  }

  @Override
  public void renderMessage(String message) throws IOException {
    dest.append(message);
  }
}
