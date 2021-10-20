package cs3500.freecell.controller;

import cs3500.freecell.model.FreecellModel;
import cs3500.freecell.model.PileType;
import cs3500.freecell.model.hw02.ICard;
import cs3500.freecell.view.FreecellTextView;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.function.Predicate;

/**
 * Represents a simple controller for the Freecell game.
 */
public class SimpleFreecellController implements FreecellController<ICard> {

  private final FreecellModel<ICard> model;
  private final FreecellTextView view;
  private final Readable in;

  /**
   * Constructs a SimpleFreecellController.
   *
   * @param model model to be controlled
   * @param rd    Readable to be read from
   * @param ap    Appendable to be written to
   * @throws IllegalArgumentException if any parameters are null
   */
  public SimpleFreecellController(FreecellModel<ICard> model, Readable rd, Appendable ap)
      throws IllegalArgumentException {
    this.model = ensureNonNull(model, "Model cannot be null");
    this.in = ensureNonNull(rd, "Readable cannot be null");
    this.view = new FreecellTextView(model, ensureNonNull(ap, "Appendable cannot be null"));
  }

  @Override
  public void playGame(List<ICard> deck, int numCascades, int numOpens, boolean shuffle)
      throws IllegalStateException, IllegalArgumentException {
    ensureNonNull(deck, "Deck cannot be null");

    try {
      model.startGame(deck, numCascades, numOpens, shuffle);
    } catch (IllegalArgumentException e) {
      this.renderMessage("Could not start game.");
      return;
    }

    Predicate<String> validInt = (String in) -> {
      try {
        Integer.parseInt(in);
        return true;
      } catch (NumberFormatException e) {
        return false;
      }
    };

    Predicate<String> validPileAddress = (String in) -> {
      return "CFO".contains(in.substring(0, 1)) && validInt.test(in.substring(1));
    };

    Scanner sc = new Scanner(in);

    this.renderBoard();

    while (!model.isGameOver()) {
      String[] inputs;
      try {
        inputs = getInputs(sc,
            new ArrayList<Predicate<String>>(
                Arrays.asList(validPileAddress, validInt, validPileAddress)));
      } catch (IllegalStateException e) {
        if (e.getMessage().equals("quit")) {
          renderMessage("Game quit prematurely.");
          return;
        } else {
          throw e;
        }
      }

      try {
        model.move(getPileType(inputs[0].substring(0, 1)),
            Integer.parseInt(inputs[0].substring(1)) - 1, Integer.parseInt(inputs[1]) - 1,
            getPileType(inputs[2].substring(0, 1)), Integer.parseInt(inputs[2].substring(1)) - 1);
        renderBoard();
      } catch (IllegalArgumentException e) {
        renderMessage("Invalid move. Please try again.\n");
      }
    }
    renderMessage("Game over.");
  }

  /**
   * Gets set of inputs based on list of rules corresponding to each input.
   *
   * @param sc      Scanner to get user inputs from
   * @param ruleSet set of rules corresponding to each input
   * @return set of inputs conforming to list of rules provided
   * @throws IllegalStateException if game is quit prematurely
   */
  private String[] getInputs(Scanner sc, List<Predicate<String>> ruleSet)
      throws IllegalStateException {
    String[] inputs = new String[ruleSet.size()];
    for (int i = 0; i < ruleSet.size(); i++) {
      inputs[i] = getNextSatisfies(sc, ruleSet.get(i));
    }
    return inputs;
  }

  /**
   * Gets the next valid input conforming to a given rule.
   *
   * @param sc   Scanner to retrieve inputs from
   * @param pred rule the input must follow
   * @return the next valid input from sc conforming to pred
   * @throws IllegalStateException if game is quit prematurely
   */
  private String getNextSatisfies(Scanner sc, Predicate<String> pred)
      throws IllegalStateException {
    String next = getNext(sc);
    while (!pred.test(next)) {
      renderMessage("Invalid input. Please try again.\n");
      next = getNext(sc);
    }
    return next;
  }

  /**
   * Gets the PileType corresponding to a string input.
   *
   * @param input string representing a PileType
   * @return the PileType represented by input
   * @throws IllegalArgumentException if the given string doesn't correspond to a pile type
   */
  private PileType getPileType(String input) throws IllegalArgumentException {
    switch (input) {
      case "C":
        return PileType.CASCADE;
      case "F":
        return PileType.FOUNDATION;
      case "O":
        return PileType.OPEN;
      default:
        throw new IllegalArgumentException("String does not correspond to a pile type");
    }
  }

  /**
   * Renders a message to the view.
   *
   * @param message message to be rendered by the view
   * @throws IllegalStateException if message could not be rendered by the view
   */
  private void renderMessage(String message) throws IllegalStateException {
    try {
      view.renderMessage(message);
    } catch (IOException e) {
      throw new IllegalStateException("Appendable has failed");
    }
  }

  /**
   * Renders the board to the view.
   *
   * @throws IllegalStateException if the board could not be rendered by the view
   */
  private void renderBoard() throws IllegalStateException {
    try {
      view.renderBoard();
    } catch (IOException e) {
      throw new IllegalStateException("Appendable has failed");
    }
  }

  /**
   * Gets the next value from the given scanner.
   *
   * @param sc Scanner to retrieve input from
   * @return the next value from the given scanner
   * @throws IllegalStateException if the next value contains a "q" or "Q", or if the Readable could
   *                               not be read from
   */
  private String getNext(Scanner sc) throws IllegalStateException {
    try {
      String next = sc.next();
      if (next.toLowerCase().contains("q")) {
        throw new IllegalStateException("quit");
      } else {
        return next;
      }
    } catch (NoSuchElementException e) {
      throw new IllegalStateException("Failed to read from Readable");
    }
  }

  /**
   * Ensures the given object is not null.
   *
   * @param in           item to be verified
   * @param errorMessage message to be incldued with the error if in is null
   * @param <T>          The type of the object being passed to the function
   * @return in if in is non-null
   * @throws IllegalArgumentException if in is null
   */
  private <T> T ensureNonNull(T in, String errorMessage) throws IllegalArgumentException {
    if (in != null) {
      return in;
    } else {
      throw new IllegalArgumentException(errorMessage);
    }
  }
}
