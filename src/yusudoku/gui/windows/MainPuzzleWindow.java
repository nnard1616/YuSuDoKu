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
package yusudoku.gui.windows;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.BorderFactory;
import yusudoku.gui.listeners.MainMenuButtonListener;
import yusudoku.gui.listeners.MenuOperation;
import yusudoku.logical.GeneratePuzzle;
import yusudoku.logical.Solver;
import yusudoku.puzzle.Puzzle;

/**
 *
 * @author nathan
 */
public class MainPuzzleWindow extends ApplicationWindow{
    private Puzzle puzzle;
    
    public MainPuzzleWindow(Puzzle puzzle){
        this.puzzle = puzzle;
    }
    
    public MainPuzzleWindow(){
        GeneratePuzzle generator = new GeneratePuzzle();
        Puzzle generated = generator.makeIt();

        this.puzzle = generated;
    }
    
    public void createPuzzlePanel() {
        Solver puzzleSolver = new Solver(puzzle);
        super.puzzleSolver = puzzleSolver;
        
        JPanel panel = new JPanel(new GridLayout(9, 9));
        JTextField cell;
        int cellValue;
        Border border;
        
        for (int i = 0; i < 81; i++){
            cellValue = puzzleSolver.getPuzzle().getCellValue(i);
            
            if (cellValue != 0){//something to put in the cell
                cell = new JTextField(cellValue + "");
                cell.setFont(new Font("SansSerif", Font.BOLD, 20));
                cell.setEnabled(false);
                cell.setDisabledTextColor(Color.RED);
            }
            else{//nothing to put in the cell
                cell = new JTextField();
                cell.setFont(new Font("SansSerif", Font.PLAIN, 20));
            }
            
            cell.setHorizontalAlignment(JTextField.CENTER);
            border = BorderFactory.createMatteBorder(CellBorder.Top(i), 
                                                     CellBorder.Left(i),
                                                     CellBorder.Bottom(i),
                                                     CellBorder.Right(i), 
                                                     Color.BLACK);
            cell.setBorder(border);
            panel.add(cell);
        }
        super.puzzlePanel = panel;
    }
    

    public void createMenuPanel() {
        //Status Message
        JPanel menuPanel = new JPanel(new GridLayout(2, 1));
        JLabel statusMessage = new JLabel("See if you can solve the puzzle!");
        
        menuPanel.add(statusMessage);
        
        //Buttons
        JPanel menuButtons = new JPanel(new GridLayout(1,4));
        
        //Check
        JButton check = new JButton("Check");
        
        //Hint
        JButton hint = new JButton("Hint");
        
        //Solve Button
        JButton solve = new JButton("Solve");
        
        //New Puzzle
        JButton newpuzzle = new JButton("New");
        
        //add buttons to menuButtons
        menuButtons.add(check);
        menuButtons.add(hint);
        menuButtons.add(solve);
        menuButtons.add(newpuzzle);
        
        menuPanel.add(menuButtons);
        super.menuPanel = menuPanel;
        
        
        solve.addActionListener(new MainMenuButtonListener(MenuOperation.SOLVE, this));
        check.addActionListener(new MainMenuButtonListener(MenuOperation.CHECK, this));
        hint.addActionListener(new MainMenuButtonListener(MenuOperation.HINT, this));
        newpuzzle.addActionListener(new MainMenuButtonListener(MenuOperation.NEW, this));
    }
}
