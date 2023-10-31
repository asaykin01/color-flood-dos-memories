package udoheim.game.colorflooddosmemories.model;


import java.util.ArrayList;
import java.util.Arrays;

public class Coordinates {
  
  
  public static final Coordinates LEFT_NEIGHBOR = new Coordinates(-1, 0);
  public static final Coordinates TOP_NEIGHBOR = new Coordinates(0, 1);
  public static final Coordinates RIGHT_NEIGHBOR = new Coordinates(1, 0);
  public static final Coordinates BOTTOM_NEIGHBOR = new Coordinates(0, -1);
  
  public static final ArrayList<Coordinates> NEIGHBOR_COORDS =
      new ArrayList<>(Arrays.asList(LEFT_NEIGHBOR, TOP_NEIGHBOR,
          RIGHT_NEIGHBOR, BOTTOM_NEIGHBOR));
  
  private final int X;
  private final int Y;
  
  
  public Coordinates(int x, int y) {
    this.X = x;
    this.Y = y;
  }
  
  public Coordinates getRelative(Coordinates mod) {
    return this.getRelative(mod.X, mod.Y);
  }
  public Coordinates getRelative(int xMod, int yMod) {
    return new Coordinates(this.X + xMod, this.Y + yMod);
  }
  
  public int getX() {
    return X;
  }
  
  public int getY() {
    return Y;
  }
  
  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder()
        .append("(").append(this.X).append(",")
        .append(this.Y).append(")");
    return sb.toString();
  }
}
