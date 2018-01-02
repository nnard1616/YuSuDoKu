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

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.WindowConstants;
import yusudoku.logical.Solver;
import yusudoku.puzzle.Puzzle;

/**
 *
 * @author nathan
 */
public abstract class ApplicationWindow implements Runnable{
    protected JFrame frame;
    protected JPanel puzzlePanel;
    protected JPanel menuPanel;
    protected Solver puzzleSolver;
    
    @Override
    public void run() {
        frame = new JFrame("YuSuDoKu");
        frame.setPreferredSize(new Dimension(400, 480));
        
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        createComponents(frame.getContentPane());
        
        frame.pack();
        frame.setLocation(500, 200);
        frame.setResizable(false);
        frame.setVisible(true);
    }
    
    public void createComponents(Container container) {
        createPuzzlePanel();
        container.add(puzzlePanel);
        
        createMenuPanel();
        container.add(menuPanel, BorderLayout.SOUTH);
    }

    public abstract void createPuzzlePanel();
    
    public abstract void createMenuPanel();
    
    public void clearPuzzlePanel(){
        JTextField textFieldCatcher;
        for (Component c: puzzlePanel.getComponents()){
            textFieldCatcher = (JTextField)c;
            //Only red text are given values that should not be cleared
            if (!textFieldCatcher.getDisabledTextColor().equals(Color.RED)){
                textFieldCatcher.setText("");
                textFieldCatcher.setEnabled(true);
                textFieldCatcher.setDisabledTextColor(Color.BLACK);
            }
        }
    }
    
    public Puzzle readPuzzlePanel(){
        Puzzle result = new Puzzle();
        JTextField textFieldCatcher;
        for (int i = 0; i < 81; i++){
            textFieldCatcher = (JTextField)puzzlePanel.getComponent(i);
            if (textFieldCatcher.getText().matches("[1-9]")){//a value is given
                result.setCellValue(i, Integer.parseInt(textFieldCatcher.getText()));
            } else //no value was given
                result.setCellValue(i, 0);
        }
        return result;
    }
    
    public int countFilled(){
        int count = 0;
        JTextField textFieldCatcher;
        for (Component c: puzzlePanel.getComponents()){
            textFieldCatcher = (JTextField)c;
            if (textFieldCatcher.getText().matches("[1-9]"))
                count++;
        }
        return count;
    }

    public JPanel getPuzzlePanel() {
        return puzzlePanel;
    }

    public JPanel getMenuPanel() {
        return menuPanel;
    }

    public JFrame getFrame() {
        return frame;
    }

    public Solver getPuzzleSolver() {
        return puzzleSolver;
    }
}
