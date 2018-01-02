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

/**
 *
 * @author nathan
 */
public class CellBorder {
    private static final int B = 3; //group borderwidth
    
    private static final int[] HAVERBORDER = new int[] {1,1,B,1,1,B,1,1,1,
                                                        1,1,B,1,1,B,1,1,1,
                                                        1,1,B,1,1,B,1,1,1,
                                                        1,1,B,1,1,B,1,1,1,
                                                        1,1,B,1,1,B,1,1,1,
                                                        1,1,B,1,1,B,1,1,1,
                                                        1,1,B,1,1,B,1,1,1,
                                                        1,1,B,1,1,B,1,1,1,
                                                        1,1,B,1,1,B,1,1,1 };
    
    private static final int[] HAVELBORDER = new int[] {1,1,1,B,1,1,B,1,1,
                                                        1,1,1,B,1,1,B,1,1,
                                                        1,1,1,B,1,1,B,1,1,
                                                        1,1,1,B,1,1,B,1,1,
                                                        1,1,1,B,1,1,B,1,1,
                                                        1,1,1,B,1,1,B,1,1,
                                                        1,1,1,B,1,1,B,1,1,
                                                        1,1,1,B,1,1,B,1,1,
                                                        1,1,1,B,1,1,B,1,1 };
    
    private static final int[] HAVETBORDER = new int[] {1,1,1,1,1,1,1,1,1,
                                                        1,1,1,1,1,1,1,1,1, 
                                                        1,1,1,1,1,1,1,1,1, 
                                                        B,B,B,B,B,B,B,B,B,
                                                        1,1,1,1,1,1,1,1,1, 
                                                        1,1,1,1,1,1,1,1,1, 
                                                        B,B,B,B,B,B,B,B,B,
                                                        1,1,1,1,1,1,1,1,1, 
                                                        1,1,1,1,1,1,1,1,1 };
    
    private static final int[] HAVEBBORDER = new int[] {1,1,1,1,1,1,1,1,1,
                                                        1,1,1,1,1,1,1,1,1,
                                                        B,B,B,B,B,B,B,B,B,
                                                        1,1,1,1,1,1,1,1,1,
                                                        1,1,1,1,1,1,1,1,1,
                                                        B,B,B,B,B,B,B,B,B,
                                                        1,1,1,1,1,1,1,1,1, 
                                                        1,1,1,1,1,1,1,1,1,
                                                        1,1,1,1,1,1,1,1,1 };
    
    public static int Right(int i){
        return HAVERBORDER[i];
    }
    
    public static  int Left(int i){
        return HAVELBORDER[i];
    }
    
    public static int Top(int i){
        return HAVETBORDER[i];
    }
    
    public static int Bottom(int i){
        return HAVEBBORDER[i];
    }
}
