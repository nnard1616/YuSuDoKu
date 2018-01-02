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
import yusudoku.gui.listeners.MenuOperation;
import yusudoku.gui.listeners.UserInputMenuButtonListener;

/**
 *
 * @author nathan
 */
public class UserInputPuzzleWindow extends ApplicationWindow{
    
    public void createPuzzlePanel() {
        
        JPanel panel = new JPanel(new GridLayout(9, 9));
        JTextField cell;
        for (int i = 0; i < 81; i++){
            
            cell = new JTextField();
            cell.setFont(new Font("SansSerif", Font.PLAIN, 20));
            
            
            cell.setHorizontalAlignment(JTextField.CENTER);
            Border border = BorderFactory.createMatteBorder(CellBorder.Top(i), 
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
        JLabel statusMessage = new JLabel("Enter your puzzle!");
        
        menuPanel.add(statusMessage);
        
        //Buttons
        JPanel menuButtons = new JPanel(new GridLayout(1,2));
        
        //Done
        JButton done = new JButton("Done");
        
        //Clear
        JButton clear = new JButton("Clear");
        
        
        //add buttons to menuButtons
        menuButtons.add(done);
        menuButtons.add(clear);
        
        menuPanel.add(menuButtons);
        super.menuPanel = menuPanel;
        
        
        //add button listeners
        done.addActionListener(new UserInputMenuButtonListener(MenuOperation.DONE, this));
        clear.addActionListener(new UserInputMenuButtonListener(MenuOperation.CLEAR, this));
    }
    
}
