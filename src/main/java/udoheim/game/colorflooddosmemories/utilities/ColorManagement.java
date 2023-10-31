package udoheim.game.colorflooddosmemories.utilities;

import udoheim.game.colorflooddosmemories.enumeration.ColorName;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.ThreadLocalRandom;

public class ColorManagement {
  
  /**
   * Gets random color out of those defined in ColorName
   * @return chosen ColorName object
   */
  public static ColorName getRandomColor() {
    
    int randomIndex = ThreadLocalRandom.current().nextInt(0,
        ColorName.values().length);
    ColorName randomColor = ColorName.values()[randomIndex];
    return randomColor;
  }
  
}
