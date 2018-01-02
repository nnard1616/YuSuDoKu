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
package yusudoku.gui.listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import yusudoku.gui.windows.ApplicationWindow;
import yusudoku.gui.windows.MainPuzzleWindow;
import yusudoku.gui.windows.UserInputPuzzleWindow;

/**
 *
 * @author nathan
 */
public class WelcomeMenuButtonListener implements ActionListener{
    private MenuOperation operation;
    private ApplicationWindow window;
    
    public WelcomeMenuButtonListener(MenuOperation operation, ApplicationWindow window){
        this.operation = operation;
        this.window = window;
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        int choice; //for yes/no warning messages
        switch (operation){
            case NEW:
                choice = userOrComputer();
                window.getFrame().dispose();
                if (choice == 0)//user selected
                    SwingUtilities.invokeLater(new UserInputPuzzleWindow());
                else
                    SwingUtilities.invokeLater(new MainPuzzleWindow());
        } 
    }
    
    public int userOrComputer(){
        Object[] options = { "User", "Computer" };
        String message = "Will the user provide the puzzle, or should the computer do it?";
        int choice = JOptionPane.showOptionDialog(null, message, "Question",JOptionPane.DEFAULT_OPTION, 
                                                  JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
        return choice;
    }
}
