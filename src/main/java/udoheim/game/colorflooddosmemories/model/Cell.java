package udoheim.game.colorflooddosmemories.model;

import udoheim.game.colorflooddosmemories.enumeration.ColorName;
import udoheim.game.colorflooddosmemories.utilities.ColorManagement;

import java.awt.Color;
import java.util.ArrayList;
import java.util.HashSet;

import static udoheim.game.colorflooddosmemories.model.Coordinates.NEIGHBOR_COORDS;

public class Cell {
  
  private final String id;
  private final Coordinates coordinates;
  
  private User user;
  private ColorName colorName;
  private String displayText;
  
  public Cell () {
    this(new Coordinates(0,0));
  }
  
  public Cell(Coordinates coordinates) {
    this(coordinates, ColorManagement.getRandomColor());
  }
  
  public Cell(Coordinates coordinates, ColorName colorName) {
    this.coordinates = coordinates;
    this.id = this.coordinates.getX() + "_" + this.coordinates.getY();
    this.colorName = colorName;
    this.user = null;
    this.displayText = " ";
  }
  
  
  public HashSet<Cell> getNeighbors(Cell[][] cellGrid) {
    HashSet<Cell> neighbors = new HashSet<>();
    for (Coordinates coord : NEIGHBOR_COORDS) {
      Cell neighbor = this.getNeighbor(cellGrid, coord);
      if (neighbor != null) {
        neighbors.add(neighbor);
      }
    }
    return neighbors;
  }
  
  private Cell getNeighbor (Cell[][] cellGrid, Coordinates mod) {
    Cell neighbor = null;
    
    try {
      Coordinates n = this.coordinates.getRelative(mod);
      
      if (n.getX() >= 0 && n.getX() < cellGrid.length &&
          n.getY() >= 0 && n.getY() < cellGrid[0].length) {
        neighbor = cellGrid[n.getX()][n.getY()];
      }
    } catch (Exception e) {
      //
    }
    
    return neighbor;
  }
  
  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder()
        .append(this.coordinates)
        .append(", ").append(this.colorName);
    return sb.toString();
  }
  
  public String getId() {
    return id;
  }
  
  public Coordinates getCoordinates () {
    return this.coordinates;
  }
  
  public User getUser() {
    return this.user;
  }
  
  public void setUser(User user) {
    this.user = user;
  }
  
  public ColorName getColorName() {
    return this.colorName;
  }
  
  public void setColorName(ColorName colorName) {
    this.colorName = colorName;
  }
  
  public String getDisplayText() {
    return displayText;
  }
  
  public void setDisplayText(String displayText) {
    this.displayText = displayText;
  }
}
