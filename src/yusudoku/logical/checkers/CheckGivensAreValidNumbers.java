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
package yusudoku.logical.checkers;

import java.awt.Component;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 *
 * @author nathan
 */
public class CheckGivensAreValidNumbers implements Checker{
    private JPanel puzzlePanel;
    
    public CheckGivensAreValidNumbers(JPanel puzzlePanel){
        this.puzzlePanel = puzzlePanel;
    }
    
    @Override
    public void check() throws CheckerException{
        JTextField textFieldCatcher;
        for (Component c : puzzlePanel.getComponents()){
            textFieldCatcher = (JTextField)c;
            if (!textFieldCatcher.getText().equals(""))//not looking at blanks
                if (!textFieldCatcher.getText().matches("[1-9]")) //only want valid sudoku entries
                    throw new CheckerException("Only 1,2,3,4,5,6,7,8,9 are valid entries!");
        }
    }
}
