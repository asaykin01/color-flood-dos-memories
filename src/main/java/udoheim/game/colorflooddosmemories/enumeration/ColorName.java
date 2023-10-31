package udoheim.game.colorflooddosmemories.enumeration;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

public enum ColorName {
  
  RED("red"),
  ORANGE("orange"),
  YELLOW("yellow"),
  GREEN("green"),
  SKY_BLUE("lightSkyBlue"),
  BLUE("blue"),
  PURPLE("purple");
  
  private static HashSet<ColorName> colorNames;
  private static Map<String, ColorName> colorNameMap;
  
  private final String NAME;
  private final String COLOR_STYLE;
  
  private ColorName (String name) {
    this.NAME  = name;
    this.COLOR_STYLE = new StringBuilder()
        .append("background : ")
        .append(this.NAME).toString();
    addToColorNames(this);
    addColorNameToMap(this);
  }
  
  private static void addToColorNames(ColorName colorName) {
    if (colorNames == null) {
      colorNames = new HashSet<>();
    }
    colorNames.add(colorName);
  }
  
  private static void addColorNameToMap(ColorName colorName) {
    if (colorNameMap == null) {
      colorNameMap = new HashMap<>();
    }
    colorNameMap.put(colorName.NAME, colorName);
  }
  
  public static ColorName getColorName(String name) {
    ColorName colorName = null;
    try {
      colorName = colorNameMap.get(name);
    } catch (Exception e) {
      System.out.println("Could not find object for color name.s");
      System.err.println(e);
    }
    return colorName;
  }
  
  
  @Override
  public String toString() {
    return this.COLOR_STYLE;
  }
  
  public String getNAME() {
    return this.NAME;
  }
  
  public String getCOLOR_STYLE() {
    return COLOR_STYLE;
  }
  
  public static HashSet<ColorName> getColorNames() {
    return colorNames;
  }
}
