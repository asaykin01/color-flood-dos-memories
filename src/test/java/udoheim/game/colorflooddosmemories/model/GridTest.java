package udoheim.game.colorflooddosmemories.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import udoheim.game.colorflooddosmemories.enumeration.ColorName;

import static org.junit.jupiter.api.Assertions.*;

class GridTest {
  
  Grid grid;
  
  @BeforeEach
  void setup() {
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
    this.grid = new Grid(cells);
  }
  
  @Test
  void playerTurn() {
    assert(grid.getPlayerCells().size() == 1);
    grid.playerTurn(ColorName.BLUE);
    assert (grid.getPlayerCells().size() == 2);
  }
  
  
  
  void createCell(Cell[][] cells, int i, int j, ColorName colorName) {
    cells[i][j] = new Cell(new Coordinates(i, j), colorName);
  }
}