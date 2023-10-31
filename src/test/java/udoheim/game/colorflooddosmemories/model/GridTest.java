package udoheim.game.colorflooddosmemories.model;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import udoheim.game.colorflooddosmemories.enumeration.ColorName;

import static org.junit.jupiter.api.Assertions.*;

class GridTest {
  
  static Grid grid;
  
  @BeforeAll
  static void beforeAll() {
    Cell[][] cells = new Cell[4][4];
    createCell(cells, 0,0,ColorName.BLUE);
    createCell(cells, 0,1,ColorName.YELLOW);
    createCell(cells, 0,2,ColorName.YELLOW);
    createCell(cells, 0,3,ColorName.BLUE);
    createCell(cells, 1,0,ColorName.RED);
    createCell(cells, 1,1,ColorName.RED);
    createCell(cells, 1,2,ColorName.GREEN);
    createCell(cells, 1,3,ColorName.GREEN);
    createCell(cells, 2,0,ColorName.RED);
    createCell(cells, 2,1,ColorName.RED);
    createCell(cells, 2,2,ColorName.BLUE);
    createCell(cells, 2,3,ColorName.GREEN);
    createCell(cells, 3,0,ColorName.GREEN);
    createCell(cells, 3,1,ColorName.BLUE);
    createCell(cells, 3,2,ColorName.YELLOW);
    createCell(cells, 3,3,ColorName.BLUE);
    grid = new Grid(cells);
  }
  
  @Test
  void playerTurn() {
    ColorName colorName = ColorName.BLUE;
    assertEquals(1,grid.getPlayerCells().size());
    grid.playerTurn(colorName);
    assertEquals(2,grid.getPlayerCells().size());
    for (Cell cell : grid.getPlayerCells()) {
      assertEquals(colorName, cell.getColorName());
    }
  }
  
  @Test
  void computerTurn() {
    assertEquals(1,grid.getComputerCells().size());
    grid.computerTurn();
    assertEquals(4,grid.getComputerCells().size());
    ColorName colorName = ColorName.GREEN;
    for (Cell cell : grid.getComputerCells()) {
      assertEquals(colorName, cell.getColorName());
    }
  }
  
  
  
  static void createCell(Cell[][] cells, int i, int j, ColorName colorName) {
    cells[i][j] = new Cell(new Coordinates(i, j), colorName);
  }
}