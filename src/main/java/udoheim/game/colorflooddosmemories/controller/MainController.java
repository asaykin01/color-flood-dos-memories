package udoheim.game.colorflooddosmemories.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import udoheim.game.colorflooddosmemories.enumeration.ColorName;
import udoheim.game.colorflooddosmemories.model.Cell;
import udoheim.game.colorflooddosmemories.model.User;
import udoheim.game.colorflooddosmemories.model.Grid;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

@Controller
@SessionAttributes({ "theGrid", "theGridLength", "theGridHeight",
    "colorNames"})
public class MainController {
  
  private HashSet<ColorName> colorNames;
  private Grid theGrid;
  private int theGridLength = 10;
  private int theGridHeight = 10;
  private int colorRowPercent;
  
  @GetMapping({"/"})
  public String main(@RequestParam(required = false, defaultValue = "World")
                             String name, Model model) {
    model.addAttribute("name", name);
    if (theGrid == null) {
      theGrid = new Grid(this.theGridLength,
          this.theGridLength);
    }
    model.addAttribute("theGird", theGrid.getGrid());
    if (colorNames == null) {
      colorNames = ColorName.getColorNames();
    }
    model.addAttribute("colorNames", colorNames);
    
    colorRowPercent = 100 / colorNames.size();
    
    return "main";
  }
  
  @GetMapping("/submitPlayerColor")
  public String submitPlayerColor (Model model,
                                   @RequestParam(name = "nameOfColor")
                                   String nameOfColor) {
    System.out.println("color " + nameOfColor);
    ColorName colorName = ColorName.getColorName(nameOfColor);
    this.theGrid.playerTurn(colorName);
    model.addAttribute("theGird", theGrid.getGrid());
    return "main";
  }
  
  public Cell[][] getTheGrid() {
    return theGrid.getGrid();
  }
  
  public int getTheGridLength() {
    return theGridLength;
  }
  
  public void setTheGridLength(int theGridLength) {
    this.theGridLength = theGridLength;
  }
  
  public int getTheGridHeight() {
    return theGridHeight;
  }
  
  public void setTheGridHeight(int theGridHeight) {
    this.theGridHeight = theGridHeight;
  }
  
  public HashSet<ColorName> getColorNames() {
    return colorNames;
  }
  
  public int getColorRowPercent() {
    return colorRowPercent;
  }
  
  public void setColorRowPercent(int colorRowPercent) {
    this.colorRowPercent = colorRowPercent;
  }
  
  public User getPlayer() {
    return theGrid.getPlayer();
  }
  
  public User getComputer() {
    return theGrid.getComputer();
  }
}
