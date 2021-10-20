package cs3500.freecell.model.hw04;

import cs3500.freecell.model.PileType;
import cs3500.freecell.model.hw02.piles.IPile;

/**
 * Creates new IPiles of type K.
 * @param <K> Type of pile to create, must be an extension of IPile
 */
public interface IPileFactory<K extends IPile> {

  /**
   * Makes a new pile implementing interface K of a specified type.
   * @param p the type of pile to create
   * @return a new pile of type specified by p
   * @throws IllegalArgumentException if p is not a valid pile type
   */
  public K makePileOfType(PileType p) throws IllegalArgumentException;
}
