/*
 * Copyright (C) 2018 nathan
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package yusudoku.puzzle;

/**
 *
 * @author nathan
 */
public class Puzzle {
    private Cell[][] cells;
    
    public Puzzle(Cell[][] cells){
        this.cells = cells;
    }
    
    public Puzzle(int[][] numbers){
        this.cells =  new Cell[9][9];
        for (int i = 0; i < 9; i++)
            for(int j = 0; j < 9; j++)
                this.cells[i][j] = new Cell(i, j, numbers[i][j]);
    }

    public Puzzle() {
        this.cells =  new Cell[9][9];
        for (int i = 0; i < 9; i++)
            for(int j = 0; j < 9; j++)
                this.cells[i][j] = new Cell(i, j);
    }
    
    //used for when rotating the puzzle
    public void updateCellCoordinates(){
        for (int i = 0; i < 9; i++)
            for(int j = 0; j < 9; j++)
                this.cells[i][j] = new Cell(i, j, cells[i][j].getValue());
    }
    
    public void setCellValue(int x, int y, int value){
        this.cells[x][y].setValue(value);
    }
    
    public void setCellValue(int sudokuIndex, int value){
        getCell(sudokuIndex).setValue(value);
    }
    
    public void clearCell(int x, int y){
        this.cells[x][y].setValue(0);
    }
    
    public void clearCell(int sudokuIndex){
        getCell(sudokuIndex).setValue(0);
    }
    
    public int getCellValue(int x, int y){
        return this.cells[x][y].getValue();
    }
    
    public int getCellValue(int sudokuIndex){
        return getCell(sudokuIndex).getValue();
    }
    
    public Cell getCell(int x, int y){
        return this.cells[x][y];
    }
    
    public Cell getCell(int sudokuIndex){
    //mathematical mapping from sudokuIndex to (x,y) coordinate pair.
    //See note in yusudoku.puzzle.Cell
        int x = sudokuIndex / 9;
        int y = sudokuIndex % 9;
        return this.cells[x][y];
    }
    
    public void rotatePuzzleCCW90(){
        Cell[][] result = new Cell[9][9];
        for (int i = 0; i < 9; i++){
            for (int j = 0; j < 9; j++){
                result[i][j] = cells[j][8-i];
            }
        }
        cells = result;
        updateCellCoordinates();
    }
    
    public void rotatePuzzleCCW180(){
        rotatePuzzleCCW90();
        rotatePuzzleCCW90();
    }
    
    public void rotatePuzzleCCW270(){
        rotatePuzzleCCW180();
        rotatePuzzleCCW90();
    }
    
    @Override
    public Puzzle clone(){
        Cell[][] clonedCells = new Cell[9][9];
        for (int i = 0; i < 9; i++)
            for(int j = 0; j < 9; j++)
                clonedCells[i][j] = cells[i][j].clone();
        return new Puzzle(clonedCells);
    }
    
    @Override
    public String toString() {
        String result = "";
        for (int i = 0; i < 9; i++){
            for(int j = 0; j < 9; j++){
                result += cells[i][j] + " ";
                if (j%3 == 2)
                    result += " ";
            }
            result += "\n";
            if (i%3 == 2)
                result += "\n";
        }
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj.getClass() != this.getClass())
            return false;
        Puzzle otherPuzzle = (Puzzle)obj;
        for (int i = 0; i < 9; i++){
            for(int j = 0; j < 9; j++){
                if (this.getCellValue(i, j) != otherPuzzle.getCellValue(i, j))
                    return false;
            }
        }
        return true;
    }
    
    
}
