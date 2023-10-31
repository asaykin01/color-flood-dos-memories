package udoheim.game.colorflooddosmemories.model;

import udoheim.game.colorflooddosmemories.enumeration.ColorName;
import udoheim.game.colorflooddosmemories.utilities.ColorManagement;

import java.util.ArrayList;
import java.util.HashSet;

public class Grid {
  
  private final User player = new User("player");
  private final User computer = new User("computer");
  
  private HashSet<Cell> playerCells;
  private HashSet<Cell> computerCells;
  
  private int gridLength;
  private int gridHeight;
  private Cell[][] grid;
  
  public Grid (int gridLength, int gridHeight) {
    resetGrid(gridLength, gridHeight);
  }
  
  public synchronized void resetGrid(int gridLength, int gridHeight) {
    this.gridLength = gridLength;
    this.gridHeight = gridHeight;
    this.grid = new Cell[this.gridLength][this.gridHeight];
    
    for (int i = 0; i < this.grid.length; i++) {
      for (int j = 0; j < this.grid[i].length; j++) {
        this.grid[i][j] = new Cell(new Coordinates(i,j),
            ColorManagement.getRandomColor());
      }
    }
    
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
  
  public synchronized void playerTurn(ColorName colorName) {
    HashSet<Cell> processedCells = new HashSet<>();
    for (Cell playerCell : this.playerCells) {
      playerCell.setColorName(colorName);
      processedCells.add(playerCell);
      HashSet<Cell> playerCellNeighbors = playerCell.getNeighbors(this.grid);
      this.playerCells.addAll(this.annexSameColorNeighbors(this.player,
          playerCellNeighbors,
          colorName,
          processedCells));
    }
  }
  
  
  
  /**
   * Does necessary work to connect a cell to a user
   * @param user
   * @param userCells
   * @param cell
   */
  private synchronized void addCellToUser(User user, HashSet<Cell> userCells,
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
  private synchronized void checkNeighborsInit(User user, Cell userCell,
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
  private synchronized HashSet<Cell> checkNeighborsInit(User user,
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
  
  private synchronized HashSet<Cell> annexSameColorNeighbors (User user,
                                                     HashSet<Cell> neighborCells,
                                                      ColorName colorName,
                                                      HashSet<Cell> processedCells) {
    HashSet<Cell> newUserCells = new HashSet<>();
    for (Cell cell : neighborCells) {
      if (!processedCells.contains(cell)) {
        processedCells.add(cell);
        if (cell.getUser() == null && cell.getColorName().equals(colorName)) {
          this.addCellToUser(user, newUserCells, cell);
          HashSet<Cell> newNeighbors = cell.getNeighbors(this.grid);
          newUserCells.addAll(this.annexSameColorNeighbors(user, newNeighbors,
              colorName,
              processedCells));
        }
      }
    }
    return neighborCells;
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
