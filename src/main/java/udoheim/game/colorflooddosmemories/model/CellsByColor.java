package udoheim.game.colorflooddosmemories.model;

import udoheim.game.colorflooddosmemories.enumeration.ColorName;

import java.util.HashMap;
import java.util.HashSet;

/**
 * Cell for tracking cells by color
 */
public class CellsByColor {
  
  private HashMap<ColorName, HashSet<Cell>> map;
  
  public CellsByColor() {
    map = new HashMap<>();
  }
  
  /**
   * Adds cell to the map if the cell is not null
   * @param cell - the cell that is added
   */
  public void addCell (Cell cell) {
    if (cell != null) {
      if (this.map.containsKey(cell.getColorName())) {
        this.map.get(cell.getColorName()).add(cell);
      } else {
        HashSet<Cell> cells = new HashSet<>();
        cells.add(cell);
        this.map.put(cell.getColorName(), cells);
      }
    }
  }
  
  /**
   * Gets the color that has the most cells in its set
   * @return ColorName of most frequent color, or null if nothing found
   */
  public ColorName getMostFrequentColor() {
    int colorSize = 0;
    ColorName mostFrequent = null;
    for (ColorName colorName : this.map.keySet()) {
      if (mostFrequent == null) {
        mostFrequent = colorName;
        colorSize = this.map.get(mostFrequent).size();
      } else {
        if (this.map.get(colorName).size() > colorSize) {
          mostFrequent = colorName;
          colorSize = this.map.get(mostFrequent).size();
        }
      }
    }
    return mostFrequent;
  }
  
  /**
   * gets the set from map for the passed in color if color is not null
   * @param colorName - ColorName object for which to return set
   * @return HashSet of ColorName objects for the passed in color
   */
  public HashSet<Cell> getSetByColor(ColorName colorName) {
    HashSet<Cell> set = new HashSet<>();
    if (colorName != null && this.map.containsKey(colorName)) {
      set = this.map.get(colorName);
    }
    return set;
  }
  
}
