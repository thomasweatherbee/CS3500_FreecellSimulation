package cs3500.freecell.model.hw04;

import cs3500.freecell.model.FreecellModel;
import cs3500.freecell.model.PileType;
import cs3500.freecell.model.hw02.CardImpl;
import cs3500.freecell.model.hw02.ICard;
import cs3500.freecell.model.hw02.Suite;
import cs3500.freecell.model.hw02.Value;
import cs3500.freecell.model.hw02.piles.IPile;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;

/**
 * Abstract class representing a model of a Freecell game.
 * @param <K> the type of pile being used in the implementation (IPile, IPileMultiMove, etc.)
 */
public abstract class AFreecellModel<K extends IPile> implements FreecellModel<ICard> {

  protected final List<K> cascades;
  protected final List<K> opens;
  protected final List<K> foundations;
  protected final IPileFactory<K> pileFactory;
  protected boolean started;

  /**
   * Constructs a new AFreecellModel.
   *
   * @param pileFactory returns new piles of type K
   */
  protected AFreecellModel(IPileFactory<K> pileFactory) {
    this.cascades = new ArrayList<K>();
    this.opens = new ArrayList<K>();
    this.foundations = new ArrayList<K>();
    this.pileFactory = pileFactory;
    this.started = false;
  }

  @Override
  public List<ICard> getDeck() {
    ArrayList<ICard> deck = new ArrayList<ICard>();
    Suite[] suites = Suite.values();
    Value[] values = Value.values();

    for (int i = 0; i < suites.length; i++) {
      for (int j = 0; j < values.length; j++) {
        deck.add(new CardImpl(suites[i], values[j]));
      }
    }

    return deck;
  }

  /**
   * Returns a shuffled shallow copy (cards are immutable, so deep copy is unnecessary) of the given
   * deck of unshuffled cards.
   *
   * @param unshuffled deck of cards to be shuffled.
   * @return shuffled deck of cards.
   */
  private List<ICard> shuffleDeck(List<ICard> unshuffled) {
    ArrayList<ICard> shuffled = new ArrayList<ICard>();
    Random r = new Random();

    while (unshuffled.size() > 0) {
      shuffled.add(unshuffled.remove(r.nextInt(unshuffled.size())));
    }

    return shuffled;
  }

  /**
   * Ensures that a given deck is a valid. An invalid deck is defined as a deck that has one or more
   * of these flaws: <ul>
   * <li>It does not have 52 cards</li> <li>It has duplicate cards</li> <li>It
   * has at least one invalid card (invalid suit or invalid number) </li> </ul>
   *
   * @param deck the deck to be validated.
   * @return is the given deck valid?
   */
  private void validateDeck(List<ICard> deck) throws IllegalArgumentException {
    Objects.requireNonNull(deck);

    List<ICard> validDeck = getDeck();
    if (validDeck.size() != deck.size()) {
      throw new IllegalArgumentException(
          "Provided deck contains " + deck.size() + " cards, must contain 52 cards");
    }

    for (int i = 0; i < deck.size(); i++) {
      if (!validDeck.contains(deck.get(i))) {
        throw new IllegalArgumentException(deck.get(i).toString() + " is not a valid card");
      } else {
        validDeck.remove(deck.get(i));
      }
    }
  }

  @Override
  public void startGame(List<ICard> deck, int numCascadePiles, int numOpenPiles, boolean shuffle)
      throws IllegalArgumentException {
    if (numCascadePiles < 4 || numOpenPiles < 1) {
      throw new IllegalArgumentException("Must have at least 4 cascade piles and 1 open pile");
    }

    List<ICard> deckCopy = new ArrayList<ICard>();
    for (int i = 0; i < deck.size(); i++) {
      deckCopy.add(deck.get(i));
    }

    this.validateDeck(deckCopy);

    cascades.clear();
    opens.clear();
    foundations.clear();

    for (int i = 0; i < numCascadePiles; i++) {
      cascades.add(pileFactory.makePileOfType(PileType.CASCADE));
    }

    for (int i = 0; i < numOpenPiles; i++) {
      opens.add(pileFactory.makePileOfType(PileType.OPEN));
    }

    for (int i = 0; i < 4; i++) {
      foundations.add(pileFactory.makePileOfType(PileType.FOUNDATION));
    }

    if (shuffle) {
      deckCopy = this.shuffleDeck(deckCopy);
    }

    for (int i = 0; i < deckCopy.size(); i++) {
      cascades.get(i % cascades.size()).addCard(deckCopy.get(i));
    }

    started = true;
  }

  public abstract void move(PileType source, int pileNumber, int cardIndex, PileType destination,
      int destPileNumber) throws IllegalArgumentException, IllegalStateException;

  /**
   * Ensures the game is started.
   *
   * @throws IllegalStateException if the game is not yet started.
   */
  protected void assertStarted() throws IllegalStateException {
    if (!started) {
      throw new IllegalStateException("Game is not yet started");
    }
  }

  /**
   * Returns the list of piles referenced by a given PileType.
   *
   * @param pt type of pile.
   * @return the list of piles referenced by pt.
   * @throws IllegalArgumentException if pt is not a valid PileType.
   */
  protected List<K> getPilesOfType(PileType pt) throws IllegalArgumentException {
    switch (pt) {
      case OPEN:
        return opens;
      case FOUNDATION:
        return foundations;
      case CASCADE:
        return cascades;
      default:
        throw new IllegalArgumentException("Invalid pile type");
    }
  }

  /**
   * Determines if the requested pile exists.
   *
   * @param pile  type of pile to check.
   * @param index the index of the pile to check.
   * @return does index of type pile exist?
   */
  protected boolean indexOutOfBounds(PileType pile, int index) {
    if (index < 0) {
      return true;
    }
    return index >= getPilesOfType(pile).size();
  }

  @Override
  public boolean isGameOver() {
    for (K pile : foundations) {
      if (!pile.isFull()) {
        return false;
      }
    }
    return true;
  }

  @Override
  public int getNumCardsInFoundationPile(int index)
      throws IllegalArgumentException, IllegalStateException {
    return this.numCardsInPile(index, this.foundations);
  }

  @Override
  public int getNumCascadePiles() {
    if (!started) {
      return -1;
    }

    return this.cascades.size();
  }

  @Override
  public int getNumCardsInCascadePile(int index)
      throws IllegalArgumentException, IllegalStateException {
    return this.numCardsInPile(index, this.cascades);
  }

  @Override
  public int getNumCardsInOpenPile(int index)
      throws IllegalArgumentException, IllegalStateException {
    return this.numCardsInPile(index, this.opens);
  }

  @Override
  public int getNumOpenPiles() {
    if (!started) {
      return -1;
    }

    return this.opens.size();
  }

  /**
   * Returns the number of cards at the specified pile number in a set of IPiles.
   *
   * @param index pile number in piles.
   * @param piles set of piles to consider.
   * @return the number of cards at index in piles.
   * @throws IllegalArgumentException if the requested index is out of bounds.
   * @throws IllegalStateException    if the game has not started yet.
   */
  private int numCardsInPile(int index, List<K> piles)
      throws IllegalArgumentException, IllegalStateException {
    assertStarted();
    if (index < 0 || index >= piles.size()) {
      throw new IllegalArgumentException("Index out of bounds");
    }
    return piles.get(index).numCards();
  }

  @Override
  public ICard getFoundationCardAt(int pileIndex, int cardIndex)
      throws IllegalArgumentException, IllegalStateException {
    return this.getCardAt(pileIndex, cardIndex, this.foundations);
  }

  @Override
  public ICard getCascadeCardAt(int pileIndex, int cardIndex)
      throws IllegalArgumentException, IllegalStateException {
    return this.getCardAt(pileIndex, cardIndex, this.cascades);
  }

  @Override
  public ICard getOpenCardAt(int pileIndex)
      throws IllegalArgumentException, IllegalStateException {
    return this.getCardAt(pileIndex, 0, this.opens);
  }

  /**
   * Returns the card in a given pile at a given index.
   *
   * @param pileIndex index of IPile in list of piles.
   * @param cardIndex index of card in IPile.
   * @param piles     set of IPiles of a given type.
   * @return the card at cardIndex in the IPile at pileIndex in piles.
   * @throws IllegalArgumentException if the requested index is out of bounds.
   * @throws IllegalStateException    if the game has not started yet.
   */
  private ICard getCardAt(int pileIndex, int cardIndex, List<K> piles)
      throws IllegalArgumentException, IllegalStateException {
    assertStarted();
    if (pileIndex < 0 || pileIndex >= piles.size()) {
      throw new IllegalArgumentException("Pile index out of bounds");
    }

    return piles.get(pileIndex).getCardAt(cardIndex);
  }
}
