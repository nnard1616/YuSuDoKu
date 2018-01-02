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
package yusudoku.logical;

import yusudoku.puzzle.Cell;
import yusudoku.puzzle.Puzzle;

/**
 *
 * @author nathan
 */
public class Solver {
    private Puzzle originalPuzzle;
    private Puzzle solvedPuzzle;
    private boolean hasAnySolution;
    
    public Solver(int[][] givenCells){ //for user supplied puzzle
        //TODO  -- check if given puzzle is solvable
        this.originalPuzzle = new Puzzle(givenCells);
        this.solvedPuzzle = this.originalPuzzle.clone();
        this.hasAnySolution = solveSudoku();
    }
    
    public Solver(Puzzle puzzle){
        this.originalPuzzle = puzzle.clone();
        this.solvedPuzzle = puzzle.clone();
        this.hasAnySolution = solveSudoku();
    }

    public boolean hasAnySolution() {
        return hasAnySolution;
    }
    
    public Puzzle getPuzzle(){
        return originalPuzzle;
    }
    
    public Puzzle getSolution(){
        return solvedPuzzle;
    }
    
    private boolean doesRowHave(int row, int value){
        for (int j = 0; j < 9; j++)
            if ( solvedPuzzle.getCellValue(row, j) == value)
                return true;
        return false;
    }
    
    private boolean doesColHave(int col, int value){
        for (int i = 0; i < 9; i++)
            if ( solvedPuzzle.getCellValue(i, col) == value)
                return true;
        return false;
    }
    
    public static int modFloor(int n, int d){
    // Helper function for doesCellsGroupHave:
    // Helps map a cell's coordinates to its corresponding group's first cell's
    // coordinates.  Here is this method's mapping:
    //      [0,2] -> 0; [3,5] -> 3; [6,8] -> 6
        while (n % d != 0)
            n--;
        return n;
    }
    
    private boolean doesCellsGroupHave(Cell cell, int value){
    // Cell is in one of 9 different 3x3 groups.  We check all cells in cell's 
    // corresponding group to see if the query value is in one of them.
    
        // first we map cell's coordinates to the starting coordinates of our
        // group's top left member:
        int groupRowStart = modFloor(cell.getX(), 3);
        int groupColStart = modFloor(cell.getY(), 3);

        for (int i = groupRowStart; i != groupRowStart + 3; i++)
            for (int j = groupColStart; j != groupColStart + 3; j++)
                if (solvedPuzzle.getCellValue(i, j) == value)
                    return true;
        return false;
    }
    
    private boolean followsRules(int i, int j, int value){
        Cell currentCell = solvedPuzzle.getCell(i, j);
        return !(doesRowHave(currentCell.getX(), value) ||
                 doesColHave(currentCell.getY(), value) ||
                 doesCellsGroupHave(currentCell, value)   );  
    }
    
    //based on:  codepumpkin.com/sudoku-solver-using-backtracking
    private boolean solveSudoku(){
        for (int i = 0; i < 9; i++)
            for (int j = 0; j < 9; j++)
                //value 0 means it's not been filled in yet
                if (solvedPuzzle.getCellValue(i, j) == 0){
                    //cycle through possible values
                    for (int n = 1; n < 10; n++){
                        if (followsRules(i,j,n)){
                            solvedPuzzle.setCellValue(i, j, n);
                            if(solveSudoku()){
                                return true;
                            }else
                                solvedPuzzle.setCellValue(i, j, 0);
                        }
                    }
                    return false;
                }
        return true;
    }
}
