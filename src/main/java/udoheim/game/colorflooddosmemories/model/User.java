package udoheim.game.colorflooddosmemories.model;

/**
 * Class to keep track of who owns what cells
 */
public class User {
  public String getName() {
    return name;
  }
  
  private final String name;
  
  public User (String name) {
    this.name = name;
  }
  
  @Override
  public String toString() {
    return name;
  }
  
}
