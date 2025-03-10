package udoheim.game.colorflooddosmemories.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import udoheim.game.colorflooddosmemories.enumeration.ColorName;
import udoheim.game.colorflooddosmemories.model.Cell;
import udoheim.game.colorflooddosmemories.model.User;
import udoheim.game.colorflooddosmemories.model.Grid;

import java.util.ArrayList;
import java.util.HashSet;

/**
 * Main controller of application
 */
@Controller
@SessionAttributes({ "theGrid", "theGridLength", "theGridHeight",
    "colorNames", "isGameOver"})
public class MainController {
  
  private ArrayList<ColorName> colorNames;
  private Grid theGrid;
  private int theGridLength = 19;
  private int theGridHeight = 15;
  private String colorRowWidthText;
  private boolean isGameOver;
  private String resultText;
  
  @GetMapping({"/"})
  public String main(Model model) {
    theGrid = new Grid(this.theGridLength, this.theGridLength);
    model.addAttribute("theGird", theGrid.getGrid());
    colorNames = ColorName.getColorNames();
    model.addAttribute("colorNames", colorNames);
    int colorRowPercent = 100 / colorNames.size();
    this.colorRowWidthText = "width : " + colorRowPercent + "%";
    model.addAttribute("colorRowWidthText", colorRowWidthText);
    this.isGameOver = false;
    model.addAttribute("isGameOver", this.isGameOver);
    this.resultText = "";
    model.addAttribute("resultText", this.resultText);
    
    return "main";
  }
  
  @GetMapping("/submitPlayerColor")
  public String submitPlayerColor (Model model,
                                   @RequestParam(name = "nameOfColor")
                                   String nameOfColor) {
    // if we load this for any reason and there is no grid, reset the game
    if (this.theGrid == null) {
      return this.main(model);
    }
    ColorName colorName = ColorName.getColorName(nameOfColor);
    this.theGrid.playerTurn(colorName);
    model.addAttribute("theGird", theGrid.getGrid());
    this.theGrid.computerTurn();
    
    this.isGameOver = this.theGrid.isGameOver();
    model.addAttribute("isGameOver", this.isGameOver);
    if (this.isGameOver) {
      User winner = this.theGrid.getCurrentWinner();
      if (winner != null) {
        this.resultText = winner.getName() + " has won!";
      } else {
        this.resultText = "It's a tie!";
      }
      this.resultText += " Please 'Reset Game' to play again.";
      model.addAttribute("resultText", this.resultText);
    } else {
      
      model.addAttribute("theGird", this.theGrid.getGrid());
      model.addAttribute("colorNames", this.colorNames);
      model.addAttribute("colorRowWidthText", this.colorRowWidthText);
    }
    return "main";
  }
  
  @GetMapping("/resetGame")
  public String resetGame(Model model) {
    return this.main(model);
  }
  
  public String getColorRowWidthText() {
    return colorRowWidthText;
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
  
  public ArrayList<ColorName> getColorNames() {
    return colorNames;
  }
  
  public User getPlayer() {
    return theGrid.getPlayer();
  }
  
  public User getComputer() {
    return theGrid.getComputer();
  }
  
  public boolean isGameOver() {
    return isGameOver;
  }
  
  public String getResultText() {
    return resultText;
  }
}
