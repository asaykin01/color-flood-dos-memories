package udoheim.game.colorflooddosmemories.model;

import udoheim.game.colorflooddosmemories.enumeration.ColorName;

import java.util.*;

/**
 * Class Grid stores the grid of cells and methods for updating it for gameplay
 */
public class Grid {
  
  private final User player = new User("Player");
  private final User computer = new User("Computer");
  
  private HashSet<Cell> playerCells;
  private HashSet<Cell> computerCells;
  
  private int gridLength;
  private int gridHeight;
  private Cell[][] grid;
  
  /**
   * Constructor that allows for a specific grid for testing purposes only.
   * @param grid - the grid. assuming the grid is proper
   */
  protected Grid (Cell[][] grid) {
    this.grid = grid;
    this.gridLength = grid.length;
    this.gridHeight = grid[0].length;
    this.initialUserSetup();
  }
  
  /**
   * Main constructor for this class
   * @param gridLength - length of grid
   * @param gridHeight - width of grid
   */
  public Grid (int gridLength, int gridHeight) {
    resetGrid(gridLength, gridHeight);
  }
  
  /**
   * Resets grid based on passed in length and height
   * @param gridLength - length of new grid
   * @param gridHeight - height of new grid
   */
  public synchronized void resetGrid(int gridLength, int gridHeight) {
    this.gridLength = gridLength;
    this.gridHeight = gridHeight;
    this.grid = new Cell[this.gridLength][this.gridHeight];
    
    for (int i = 0; i < this.grid.length; i++) {
      for (int j = 0; j < this.grid[i].length; j++) {
        this.grid[i][j] = new Cell(new Coordinates(i,j),
            ColorName.getRandomColor());
      }
    }
    
    this.initialUserSetup();
    
  }
  
  /**
   * Does initial user setup
   */
  private void initialUserSetup() {
    this.playerCells = new HashSet<>();
    this.computerCells = new HashSet<>();
    
    Cell playerCell = this.grid[this.gridLength - 1][0];
    this.addCellToUser(this.player, this.playerCells,
        playerCell);
    this.checkNeighborsInit(this.player, playerCell,
        playerCell.getColorName(), this.playerCells);
    
    Cell computerCell = this.grid[0][this.gridHeight - 1];
    this.addCellToUser(this.computer, computerCells,
        computerCell);
    this.checkNeighborsInit(this.computer, computerCell,
        computerCell.getColorName(), this.computerCells);
  }
  
  /**
   * Executes player turn.
   * @param colorName - the color the player selected
   */
  public synchronized void playerTurn(ColorName colorName) {
    HashSet<Cell> processedCells = new HashSet<>();
    ArrayList<HashSet<Cell>> newPlayerCellsList =
        new ArrayList<HashSet<Cell>>();
    
    // iterating over cells owned by player
    // the shallow cloning of playerCells avoids concurrent modification errors
    HashSet<Cell> playerCellsClone =
        new HashSet<>((Collection<Cell>)this.playerCells.clone());
    for (Cell playerCell : playerCellsClone) {
      // set player cell color to new color
      playerCell.setColorName(colorName);
      
      // mark cell as processed
      processedCells.add(playerCell);
      
      // get cell neighbors
      HashSet<Cell> playerCellNeighbors = playerCell.getNeighbors(this.grid);
      
      // iterate over neighbors to see if anything gets added
      HashSet<Cell> newPlayerCells =
          this.annexSameColorNeighbors(this.player, playerCellNeighbors,
              colorName, this.playerCells, processedCells);
      // add added set to list
      newPlayerCellsList.add(newPlayerCells);
    }
    
    // iterate over the list of sets and add all sets to player cells
    for (HashSet<Cell> pcSet : newPlayerCellsList) {
      this.playerCells.addAll(pcSet);
    }
    
  }
  
  /**
   * Method for computer to try to take turn. a turn is considered taken if
   * the computer was able to annex cells.
   * @return true if a turn was taken, false if not
   */
  public synchronized boolean computerTurn () {
    boolean turnTaken = false;
    // survey neighboring cells to computer cells
    CellsByColor tracker = this.surveyNeighbors(this.computerCells);
    // get most frequent color
    ColorName colorName = tracker.getMostFrequentColor();
    
    // if a color is found, proceed
    if (colorName != null) {
      // get the set of cells for that color
      HashSet<Cell> cellsToAnnex = tracker.getSetByColor(colorName);
      // change computer cells to new color
      for (Cell cell : this.computerCells) {
        cell.setColorName(colorName);
      }
      // add cells to annex to computer
      for (Cell cell : cellsToAnnex) {
        this.addCellToUser(this.computer, this.computerCells, cell);
      }
      turnTaken = true;
    }
    return turnTaken;
  }
  
  /**
   * Determines whether the game is over. The game is considered over if
   * there are no cells left not owned by either user, or the difference in
   * owned cells between users is more than not owned cells remaining.
   * @return true if game is over. false if game is not over.
   */
  public boolean isGameOver () {
    boolean isGameOver = false;
    int totalCells = this.gridLength * this.gridHeight;
    
    int notOwnedCells =
        totalCells - this.playerCells.size() + this.computerCells.size();
    int userCellDifference =
        Math.abs(this.playerCells.size() - this.computerCells.size());
    
    if (notOwnedCells <= 0 && userCellDifference > notOwnedCells) {
      isGameOver = true;
    }
    
    return isGameOver;
  }
  
  /**
   * Figures out which user is the current by seeing who owns more cells.
   * if the number of cells is the same between the users, then it's a tie.
   * @return user object or winner, or null if it is a tie.
   */
  public User getCurrentWinner () {
    User winner = null;
    if (this.playerCells.size() > this.computerCells.size()) {
      winner = this.player;
    } else if (this.computerCells.size() > this.playerCells.size()) {
      winner = this.computer;
    }
    return winner;
  }
  
  /**
   * Main method for iterating over neighbors of user cells to see which
   * color is most frequent among the neighbors that are not taken by any user
   * @param userCells - cells that belong to user in question
   * @return CellsByColor object containing information on neighbors by color
   * that can be annexed
   */
  protected synchronized CellsByColor surveyNeighbors(HashSet<Cell> userCells) {
    
    CellsByColor tracker = new CellsByColor();
    
    // iterate over user owned cells via shallow clone hashmap to avoid
    // concurrent modification exceptions.
    HashSet<Cell> userCellsClone = new HashSet<>((Collection) userCells.clone());
    for (Cell cell : userCellsClone) {
      
      // get the cells neighbors and iterate over them
      HashSet<Cell> userCellNeighbors = cell.getNeighbors(this.grid);
      for (Cell neighborCell : userCellNeighbors) {
        // only proceed if this is not a cell owned by any user
        if (neighborCell.getUser() == null) {
          // add cell to color tracking for whatever color this is
          tracker.addCell(neighborCell);
          // call method to see if any neighbors of this cell are of the same
          // color
          this.surveyNeighbors(neighborCell, tracker);
        }
      }
    }
    return tracker;
  }
  
  /**
   * Surveys neighbors of passed in cell to see if any of the neighbor cells
   * that are not taken and are of the same color as the passed in cell
   * @param cell - cell in question
   * @param tracker - structure for recording how many cells by color
   */
  private synchronized void surveyNeighbors(Cell cell, CellsByColor tracker) {
    HashSet<Cell> userCellNeighbors = cell.getNeighbors(this.grid);
    for (Cell neighborCell : userCellNeighbors) {
      // only proceed if this is not a cell owned by any user,
      // the cell is not already in tracker,
      // and the cell is of the same color as the calling cell
      if (neighborCell.getUser() == null
          && !tracker.getSetByColor(cell.getColorName()).contains(neighborCell)
          && neighborCell.getColorName().equals(cell.getColorName())) {
        // add cell to color tracking for whatever color this is
        tracker.addCell(neighborCell);
        // call this method recursively to see if more cels of same color can
        // be found
        surveyNeighbors(neighborCell, tracker);
      }
    }
  }
  
  /**
   * Does necessary work to connect a cell to a user
   * @param user - user in question
   * @param userCells - cells owned by user
   * @param cell - new cell to add
   */
  protected synchronized void addCellToUser(User user, HashSet<Cell> userCells,
                                          Cell cell) {
    cell.setUser(user);
    userCells.add(cell);
  }
  
  /**
   * When grid is first initialized, this is used to add any neighboring
   * cells to userCells if they are the same color as the cell the user started
   * with.
   * @param user - user in question
   * @param userCell - cell user has already
   * @param colorName - the color matching to
   * @param userCells - cells owned by user
   */
  protected synchronized void checkNeighborsInit(User user, Cell userCell,
                                       ColorName colorName,
                                  HashSet<Cell> userCells) {
    HashSet<Cell> processedCells = new HashSet<>();
    userCells.addAll(this.checkNeighborsInit(user,
        userCell.getNeighbors(this.grid),
        colorName, processedCells));
  }
  
  /**
   * When grid is first initialized,
   * Recursive method for adding cells to user cells if the color of them
   * matches the passed in color
   * @param user - user in question
   * @param neighborCells - cells user owns already
   * @param colorName - color we are matching
   * @param processedCells - cells already processed
   */
  protected synchronized HashSet<Cell> checkNeighborsInit(User user,
                                               HashSet<Cell> neighborCells,
                                                ColorName colorName,
                                                HashSet<Cell> processedCells) {
    HashSet<Cell> newUserCells = new HashSet<>();
    for (Cell cell : neighborCells) {
      if (!processedCells.contains(cell)) {
        processedCells.add(cell);
        if (cell.getColorName().equals(colorName)) {
          addCellToUser(user, newUserCells, cell);
          HashSet<Cell> newNeighbors = cell.getNeighbors(this.grid);
          newUserCells.addAll(this.checkNeighborsInit(user, newNeighbors,
              colorName, processedCells));
        }
      }
    }
    return newUserCells;
  }
  
  /**
   * When a color is chosen, iterates over neighbors recursively to annex
   * these cells into the user's cells
   * @param user - user doing the annexing
   * @param neighborCells - cells over which to iterate
   * @param colorName - color of cells looking for
   * @param userCells - user's cells
   * @param processedCells - cells already processed, so we don't repeatedly
   *                       examine them
   * @return Cells that were annexed as result of this method
   */
  protected synchronized HashSet<Cell> annexSameColorNeighbors (User user,
                                                     HashSet<Cell> neighborCells,
                                                      ColorName colorName,
                                                      HashSet<Cell> userCells,
                                                      HashSet<Cell> processedCells) {
    ArrayList<HashSet<Cell>> newUserCellsList = new ArrayList<>();
    for (Cell cell : neighborCells) {
      // only process cell if not processed yet
      if (!processedCells.contains(cell)) {
        // add to processed
        processedCells.add(cell);
        
        // if cell is not yet taken and is of the same color as passed in
        if (cell.getUser() == null && cell.getColorName().equals(colorName)) {
          // add cell to user
          this.addCellToUser(user, userCells, cell);
          
          // get cells neighbors - we need to see if any more of same color
          HashSet<Cell> newNeighbors = cell.getNeighbors(this.grid);
          
          // get cells to annex from the neighbors of this cell and add them
          // to the set of lists.
          HashSet<Cell> newUserCells =
              this.annexSameColorNeighbors(user, newNeighbors, colorName,
                  userCells, processedCells);
          newUserCellsList.add(newUserCells);
        }
        
      }
      
    }
    
    // flatten the list of sets of cells and put into single hash set
    HashSet<Cell> newUserCells = new HashSet<>();
    for (HashSet<Cell> set : newUserCellsList) {
      newUserCells.addAll(set);
    }
    
    return newUserCells;
  }
  
  
  public User getPlayer() {
    return player;
  }
  
  public User getComputer() {
    return computer;
  }
  
  public int getGridLength() {
    return gridLength;
  }
  
  public int getGridHeight() {
    return gridHeight;
  }
  
  public Cell[][] getGrid() {
    return grid;
  }
  
  public HashSet<Cell> getPlayerCells() {
    return playerCells;
  }
  
  public HashSet<Cell> getComputerCells() {
    return computerCells;
  }
}
