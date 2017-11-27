/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fop_assignment;

import java.util.Scanner;

/**
 *
 * @author Hp
 */
public class FOP_Assignment {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Scanner sc = new Scanner (System.in);
        System.out.println("Enter your name : ");
        String name = sc.nextLine();
        System.out.println("Enter the size of the layout[row, column] : ");
        int row = sc.nextInt();
        int col = sc.nextInt();
        //create file name with size
        StringBuilder f = new StringBuilder();
        f.append("score");
        if(col < row)
        {
            f.append(col);
            f.append('x');
            f.append(row);
        }
        else
        {
            f.append(row);
            f.append('x');
            f.append(col);
        }
        f.append(".dat");
        String file = f.toString();
        
        //call the game
        fop_2048.Layout layout = new fop_2048.Layout(row, col, file, name);
        while(true){
            layout.moveTile();
        }
       
    }
    
}
