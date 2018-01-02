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

import java.util.List;
import java.util.Random;
import static yusudoku.logical.Solver.modFloor;
import yusudoku.logical.checkers.CheckUniqueSolution;
import yusudoku.logical.checkers.CheckerException;
import yusudoku.puzzle.Cell;
import yusudoku.puzzle.Puzzle;

/**
 *
 * @author nathan
 */
public class GeneratePuzzle {
    private Random randomizer;
    private Cell[][] possCells;
    
    public GeneratePuzzle(){
        randomizer = new Random();
    }
    
    public Puzzle makeIt(){
        initializeCells();
        randomlyFill();
        Puzzle potential = new Puzzle(this.possCells);
        
        try{
            CheckUniqueSolution hasUniqueSolution = new CheckUniqueSolution(potential);
            hasUniqueSolution.check();
        } catch (CheckerException e1){
            potential = makeIt();
        }
        return potential;
    }
    
    public void initializeCells(){
        this.possCells = new Cell[9][9];
        for (int i = 0; i< 9; i++)
            for (int j=0; j < 9; j++)
                this.possCells[i][j] = new Cell(i, j, 0);
    }
    
    public void randomlyFill(){
        int fillCount = 0;
        int n; // for picking number from 1-9
        int i = randomizer.nextInt(9);
        int j = randomizer.nextInt(9);
        int value = possCells[i][j].getValue();
        
        while (fillCount < 31){
            
            //we don't want already filled-in cells
            while (value != 0){
                i = randomizer.nextInt(9);
                j = randomizer.nextInt(9);
                value = possCells[i][j].getValue();
            }
            
            //We now have a cell that needs to be filled

            //current cell's list of possibilities
            List<Integer> possibilities = possCells[i][j].getPossible();
            
            //if current cell has no possibilities, we need to start over.
            //puzzle is not solvable at this point.
            if (possibilities.isEmpty()){
                makeIt();
                return;
            }
            
            //Pick a number to put in the cell from list of possibilities
            n = randomizer.nextInt(possibilities.size());
            value = possibilities.get(n);
            
            
            //now we have a number that follows the rules!
            //put the number into the cell and update surrounding cells'
            //lists of possibilities.
            possCells[i][j].setValue(value);
            rowNowHas(i, value);
            colNowHas(j, value);
            groupNowHas(possCells[i][j], value);
            fillCount++;
        }
               
    }
    
    public void rowNowHas(int row, int value){
        for (int j = 0; j < 9; j++)
            possCells[row][j].removePossibility(value);
    }
    
    public void colNowHas(int col, int value){
        for (int i = 0; i < 9; i++)
            possCells[i][col].removePossibility(value);
    }
    
    public void groupNowHas(Cell cell, int value){
    // Cell is in one of 9 different 3x3 groups.  We check all cells in cell's 
    // corresponding group to see if the query value is in one of them.
    
        // first we map cell's coordinates to the starting coordinates of our
        // group's top left member:
        int groupRowStart = modFloor(cell.getX(), 3);
        int groupColStart = modFloor(cell.getY(), 3);

        for (int i = groupRowStart; i != groupRowStart + 3; i++)
            for (int j = groupColStart; j != groupColStart + 3; j++)
                possCells[i][j].removePossibility(value);
    }
}
