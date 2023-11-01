package udoheim.game.colorflooddosmemories.model;

/**
 * Class to keep track of who owns what cells
 */
public class User {
  
  private final String initial;
  private final String name;
  
  public User (String name) {
    if(name == null || name.isEmpty()) {
      name = "User";
    }
    this.name = name;
    this.initial = this.name.substring(0,1);
  }
  public String getName() {
    return name;
  }
  
  public String getInitial() {
    return initial;
  }
  
  @Override
  public String toString() {
    return name;
  }
  
}
