package udoheim.game.colorflooddosmemories.model;

import udoheim.game.colorflooddosmemories.enumeration.ColorName;

import java.util.HashMap;
import java.util.HashSet;

public class CellsByColor {
  
  private HashMap<ColorName, HashSet<Cell>> map;
  
  public CellsByColor() {
    map = new HashMap<>();
  }
  
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
  
  public HashSet<Cell> getSetByColor(ColorName colorName) {
    HashSet<Cell> set = new HashSet<>();
    if (colorName != null && this.map.containsKey(colorName)) {
      set = this.map.get(colorName);
    }
    return set;
  }
  
}
