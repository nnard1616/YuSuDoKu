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

import java.awt.Color;
import java.awt.Component;
import java.util.List;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import yusudoku.gui.windows.ApplicationWindow;
import yusudoku.gui.windows.MainPuzzleWindow;
import yusudoku.gui.windows.UserInputPuzzleWindow;
import yusudoku.logical.checkers.CheckGivensAreValidNumbers;
import yusudoku.logical.checkers.Checker;
import yusudoku.logical.checkers.CheckerException;

/**
 *
 * @author nathan
 */
public class MainMenuButtonListener implements ActionListener{
    private MenuOperation operation;
    private ApplicationWindow window;
    
    public MainMenuButtonListener(MenuOperation operation, ApplicationWindow window){
        this.operation = operation;
        this.window = window;
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        // IMPROVEMENT TODO:
        // Put all of this code into another class somewhere in yusudoku.logical!!
        
        JLabel statusMessage = (JLabel)window.getMenuPanel().getComponent(0);
        
        JButton check     = (JButton)((JPanel)window.getMenuPanel().getComponent(1)).getComponent(0);
        JButton hint      = (JButton)((JPanel)window.getMenuPanel().getComponent(1)).getComponent(1);
        JButton solve     = (JButton)((JPanel)window.getMenuPanel().getComponent(1)).getComponent(2);
        JButton newpuzzle = (JButton)((JPanel)window.getMenuPanel().getComponent(1)).getComponent(3);
        
        JTextField textFieldCatcher;
        int choice; //for yes/no warning messages
        Random randomizer = new Random(); //for picking random hints
        switch (operation){
            case SOLVE:
                choice = confirm("Are you sure you want to see the solution?");
                if (choice == 0){ //0 means "yes"
                    for (int i = 0; i < 81; i++){
                        String cellSolution = window.getPuzzleSolver().getSolution().getCellValue(i)+"";
                        textFieldCatcher = (JTextField) window.getPuzzlePanel().getComponent(i);
                        String cellText = textFieldCatcher.getText();
                        
                        if (!cellText.matches("[1-9]")){
                            textFieldCatcher.setText(cellSolution);
                            textFieldCatcher.setEnabled(false);
                            textFieldCatcher.setDisabledTextColor(Color.BLACK);
                        } else{ //number was in the cell
                            if (textFieldCatcher.isEnabled()){ //then value is supplied by user
                                textFieldCatcher.setEnabled(false);
                                textFieldCatcher.setDisabledTextColor(Color.BLACK);
                                textFieldCatcher.setBackground(Color.getHSBColor((float)0.42, (float)0.93, (float)0.99));
                                if (!cellText.equals(cellSolution)){
                                    //user made an error, make bg light red with correct solution
                                    textFieldCatcher.setText(cellSolution);
                                    textFieldCatcher.setBackground(Color.pink);
                                    textFieldCatcher.setToolTipText(cellText + " was incorrect");
                                }
                            } 
                        }
                    }
                    check.setEnabled(false);
                    hint.setEnabled(false);
                    solve.setEnabled(false);
                    statusMessage.setText("Here is the solution!");
                }
                break;
                
            case CHECK:
                try{
                    Checker validValues = new CheckGivensAreValidNumbers(window.getPuzzlePanel());
                    validValues.check();
                } catch (CheckerException e1){
                    statusMessage.setText(e1.getMessage());
                    break;
                }
                
                int incorrect = 0;
                List<Integer> emptyIndices = getEmptyIndices();
                
                //following block is for checking a completely filled in puzzle
                
                // if puzzle is completely filled in 
                if(emptyIndices.isEmpty()){ 
                    solve.doClick(); // solve original puzzle and compare to user provided solution
                    if (getNumberWrong() == 0)  
                        statusMessage.setText("Congratulations! It was all correct!");
                    else
                        statusMessage.setText("Oops, you got a few wrong!");
                    break;
                }
                
                //rest is for when the puzzle is not completely filled in
                
                //for loop to count how many user provided entries are wrong
                for (int i = 0; i < 81; i++){
                    String cellSolution = window.getPuzzleSolver().getSolution().getCellValue(i)+"";
                    textFieldCatcher = (JTextField) window.getPuzzlePanel().getComponent(i);
                    String cellText = textFieldCatcher.getText();
                    
                    if (cellText.matches("[1-9]")){
                        if (textFieldCatcher.isEnabled()){//user supplied number
                            if(!cellText.equals(cellSolution))
                                incorrect++;
                        }
                    }
                }
                
                if (incorrect > 0 )
                    statusMessage.setText("One or more guesses are incorrect");
                else
                    statusMessage.setText("Looking good so far!");
                break;
            
            case HINT:
                choice = confirm("Are you sure you want a hint?");
                emptyIndices = getEmptyIndices();
                
                if (choice == 0){ //0 means "yes"
                    if (emptyIndices.isEmpty()){
                        statusMessage.setText("There's no room left to give hints!");
                        break;
                    }
                    int hintIndex = emptyIndices.get(randomizer.nextInt(emptyIndices.size()));
                    textFieldCatcher = (JTextField) window.getPuzzlePanel().getComponent(hintIndex);
                    String cellText = textFieldCatcher.getText();
                    
                    while(cellText.matches("[1-9]")){   //keep picking cells until
                                                        //empty cell is selected.
                        hintIndex = emptyIndices.get(randomizer.nextInt(emptyIndices.size()));
                        textFieldCatcher = (JTextField) window.getPuzzlePanel().getComponent(hintIndex);
                        cellText = textFieldCatcher.getText();
                    }
                    
                    String cellSolution = window.getPuzzleSolver().getSolution().getCellValue(hintIndex)+"";
                    textFieldCatcher.setText(cellSolution);
                    textFieldCatcher.setEnabled(false);
                    textFieldCatcher.setDisabledTextColor(Color.BLUE);
                }
                statusMessage.setText("Here's a hint in blue for ya!");
                break;
                
            case NEW:
                choice = confirm("Are you sure you want to close the\ncurrent puzzle and make a new one?");
                if (choice == 0){ //user picked yes
                    choice = userOrComputer();
                    window.getFrame().dispose();
                    if (choice == 0)//user selected
                        SwingUtilities.invokeLater(new UserInputPuzzleWindow());
                    else
                        SwingUtilities.invokeLater(new MainPuzzleWindow());
                }
        } 
    }
    
    public int confirm(String message){
        Object[] options = { "Yes", "No" };
        int choice = JOptionPane.showOptionDialog(null, message, "Warning",JOptionPane.DEFAULT_OPTION, 
                                                  JOptionPane.WARNING_MESSAGE, null, options, options[1]);
        return choice;
    }
    
    public int userOrComputer(){
        Object[] options = { "User", "Computer" };
        String message = "Will the user provide the puzzle, or should the computer do it?";
        int choice = JOptionPane.showOptionDialog(null, message, "Question",JOptionPane.DEFAULT_OPTION, 
                                                  JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
        return choice;
    }
    
    public List<Integer> getEmptyIndices(){
        List<Integer> emptyIndices = new ArrayList<>();
        int index = 0;
        for (Component c : window.getPuzzlePanel().getComponents()){
            if (!((JTextField)c).getText().matches("[1-9]"))
                emptyIndices.add(index);
            index++;
        }
        return emptyIndices;
    }
    
    public int getNumberWrong(){
        int wrong = 0;
        for (Component c : window.getPuzzlePanel().getComponents()){
            //solve button paints incorrect cells pink, so check if cell is pink
            if (!((JTextField)c).getBackground().equals(Color.pink))
                wrong++;
        }
        return wrong;
    }
}
