/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fop_2048;

import static com.sun.java.accessibility.util.AWTEventMonitor.addKeyListener;
import java.util.Random;
import java.util.Scanner;
import java.awt.event.*;
import java.io.EOFException;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import static java.lang.System.exit;



/**
 *
 * @author Hp
 */
public class Layout {
    private int row;
    private int col;
    private String name;  //user name
    private int score;
    private int checkScore;
    private int undoScore;
    private char[][] arr;
    private char[][] undo;
    private char[][] check;
    private int[] topScore;
    private String[] nameList;
    boolean end = false;
    String file; //get file name
    
   
    public Layout(int row, int col, String file, String name){
        this.row=row;
        this.col=col;
        this.file = file;
        this.name = name;
        score = 0;
        topScore = new int[10];
        nameList = new String[10];
        System.out.println("[w - up ; s - down ; a - left ; d - right]\n[u - undo ; 0 - exit]]");
        readFile();
        arr = new char[row][col];
        undo = new char[row][col];
        check = new char[row][col];
        arr = sizeOfScale(arr);
        

    }
 
    public void readFile(){  //read to display top ten score
        try{  //read from
            ObjectInputStream in = new ObjectInputStream(new FileInputStream(file));
            try{
                while(true){
                    for(int i=1; i<=10; i++){
                        String x = in.readUTF();
                        int y = in.readInt();
                        System.out.print(i+". "+x+"\t"+y+"\n");
                        topScore[i-1] = y;
                    }
                }
            }catch(EOFException e){}
            
            System.out.println();
            in.close();
        }catch(FileNotFoundException e){
            System.out.println("File was not found");
        }catch(IOException e){
            System.out.println("Problem with file input");
        }
    }
    
    public  char[][] sizeOfScale(char[][]arr){
        Random r = new Random();
        int place1 = r.nextInt(row);
        int place2 = r.nextInt(col);
        int place4 = r.nextInt(row);
        int place5 = r.nextInt(col);
        //String [][] arr = new String[row][col];
        for(int i=0; i<(row); i++){  //random generate first two 
            for(int j=0; j<(col); j++){
                arr[i][j] = '-';
                if(place1 !=place4){
                    if(place1%2==0 || place5%2==0){
                        arr[place1][place2]='A';
                        arr[place4][place5]='B';
                    }else{
                        arr[place1][place2]='A';
                        arr[place4][place5]='A';
                    }
                    System.out.print(arr[i][j]);
                }
            }
            System.out.println();
        }
        return arr;
    }
    
    public  void moveTile(){
        Scanner sc = new Scanner(System.in);
        String key = sc.next();
        switch(key){
            case "a":  //move to left
                if(!end){
                    moveLeft();
                }
                break;
            case "d":  //move to right
                if(!end){
                    moveRight();
                }
                break;
            case "w":  //move up
                if(!end){
                    moveUp();
                }
                break;
            case "s":  //move down
                if(!end){
                    moveDown();
                }
                break;
            case "u":  //undo
                if(!end){
                undo();
                }
                break;
            case "0":
                if(end){
                    exit(0);
                }
                break;
            default:
        }        
    }
        
     public  void moveLeft(){
         saveCheck();
         //if got '-', exchange to move left
         for(int i=0; i<row; i++){
            for(int j=0; j<col-1; j++){
                for(int k = 0; k <col-1; k++)
                if(arr[i][k]=='-'){
                    char temp = arr[i][k+1];
                    arr[i][k+1] = arr[i][k];
                    arr[i][k] = temp;
                }
            }
        }
        //if same but not '-', combine
        for(int i=0; i<row; i++){
            for(int j=0; j<col-1; j++){
                if(arr[i][j]==arr[i][j+1] && arr[i][j] != '-'){
                    arr[i][j]++;
                    score += ((int)arr[i][j] -64);  //sum up the score
                    arr[i][j+1] = '-';
                }
                
            }
        }
        //move all to left
        for(int i=0; i<row; i++){
            for(int j=0; j<col-1; j++){
                for(int k = 0; k <col-1; k++){  //bubble sort
                    if(arr[i][k]=='-'){
                        char temp = arr[i][k+1];
                        arr[i][k+1] = arr[i][k];
                        arr[i][k] = temp;
                    }
                }
            }
        }
        newNumber();  
                
     }
     
     public void moveRight(){
         saveCheck();
         for(int i=row-1; i>=0; i--){
            for(int j=col-1; j>0; j--){
                for(int k =col-1; k>0; k--){
                    if(arr[i][k]=='-'){
                        char temp = arr[i][k-1];
                        arr[i][k-1] = arr[i][k];
                        arr[i][k] = temp;
                    }
                }
            }
        }
        
        for(int i=row-1; i>=0; i--){
            for(int j=col-1; j>0; j--){
                if(arr[i][j]==arr[i][j-1] && arr[i][j] != '-'){
                    arr[i][j]++;
                    score += ((int)arr[i][j] -64);
                    arr[i][j-1] = '-';
                }
                
            }
        }
        
        for(int i=row-1; i>=0; i--){
            for(int j=col-1; j>0; j--){
                for(int k =col-1; k>0; k--){
                    if(arr[i][k]=='-'){
                        char temp = arr[i][k-1];
                        arr[i][k-1] = arr[i][k];
                        arr[i][k] = temp;
                    }
                }
            }
        }
        newNumber();
                
     }
     
     public void moveUp(){
         saveCheck();
         for(int i=0; i<col; i++){
            for(int j=0; j<row; j++){
                for(int k = 0; k <row-1; k++)
                if(arr[k][i]=='-'){
                    char temp = arr[k+1][i];
                    arr[k+1][i] = arr[k][i];
                    arr[k][i] = temp;
                }
            }
        }
        
        for(int i=0; i<col; i++){
            for(int j=0; j<row-1; j++){
                if(arr[j][i]==arr[j+1][i] && arr[j][i] != '-'){
                    arr[j][i]++;
                    score += ((int)arr[j][i] -64);
                    arr[j+1][i] = '-';
                }
            }
        }
        
        for(int i=0; i<col; i++){
            for(int j=0; j<row; j++){
                for(int k = 0; k <row-1; k++){
                    if(arr[k][i]=='-'){
                        char temp = arr[k+1][i];
                        arr[k+1][i] = arr[k][i];
                        arr[k][i] = temp;
                    }
                }
            }
        }
        newNumber();
     }
     
     public void moveDown(){
         saveCheck();
         for(int i=col-1; i>=0; i--){
            for(int j=row-1; j>0; j--){
                for(int k =row-1; k>0; k--){
                    if(arr[k][i]=='-'){
                        char temp = arr[k-1][i];
                        arr[k-1][i] = arr[k][i];
                        arr[k][i] = temp;
                    }
                }
            }
        }
        
        for(int i=col-1; i>=0; i--){
            for(int j=row-1; j>0; j--){
                if(arr[j][i]==arr[j-1][i] && arr[j][i] != '-'){
                    arr[j][i]++;
                    score += ((int)arr[j][i] -64);
                    arr[j-1][i] = '-';
                }
            }
        }
        
        for(int i=col-1; i>=0; i--){
            for(int j=row-1; j>0; j--){
                for(int k =row-1; k>0; k--){
                    if(arr[k][i]=='-'){
                        char temp = arr[k-1][i];
                        arr[k-1][i] = arr[k][i];
                        arr[k][i] = temp;
                    }
                }
            }
        }
        newNumber();
     }
     
     public void newNumber(){  //generate new num(char) only when still got space
         boolean move = false;  
         for(int i=0; i<row; i++){
             for(int j=0; j<col; j++){
                 if(check[i][j] != arr[i][j]){
                     move = true;
                     for(int m=0; m<row; m++){
                         for(int n=0; n<col; n++){
                             undo[m][n] = check[m][n];  //for undo
                         }
                     }
                     undoScore = checkScore;  
                     break;
                 }
             }
             if(move)
                 break;
         }
         
            boolean hasSpace = false;
            for(int i=0; i<row; i++){
                for(int j=0; j<col; j++){
                    if(arr[i][j]=='-'){                       
                        hasSpace = true;
                        break;
                    }
                 }
                if(hasSpace){
                    break;
                }
            }
            
            if(move && hasSpace)
                rand();
            else if(!hasSpace){
                boolean hasMove = hasMove();
                if(!hasMove)
                {
                    end = true;
                }
            }
         
         for(int i=0; i<row; i++){
             for(int j=0; j<col; j++){
                 System.out.print(arr[i][j]);
             }
             System.out.println();
         }
         System.out.println("Score : "+score);
         if(end){
             System.out.println("Game over");
             
             try{  //write score to binary file
                ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(file));
               int position = 10,temp1=0, temp2;
               //determine position
                for(int i=topScore.length-1; i>=0; i--){
                   if(score>topScore[i]){
                        position = i;
                    }else{
                       break;
                   }
                }
              //rearrange
              temp1 = score;
              for(int i=position; i<10; i++){
                  temp2 = topScore[i];
                  topScore[i] = temp1;
                  temp1 = temp2;
              }  
              //write to
              for(int i=0; i<topScore.length; i++){
                   out.writeUTF(nameList[i]);
                   out.writeInt(topScore[i]);
               }
                    
                    out.close();
                    System.out.println("Score recorded");
                    //System.out.println("Successfully write score to the binary file");

            }catch(IOException e){
                System.out.println("Problem with file output");
            }
        
         }
         
     }
     
     public void rand(){
         Random r = new Random();
         int place3 = r.nextInt(row);
         int place4 = r.nextInt(col);
         if(arr[place3][place4]!='-'){
             rand();
         }else if(place3%2==0 && score>=20){
             arr[place3][place4] = 'B';
         }else{
             arr[place3][place4] = 'A';
         }
     }
     
     public void saveCheck(){
         for(int i=0; i<row; i++){
             for(int j=0; j<col; j++){
                 check[i][j] = arr[i][j];
             }
         }
         checkScore = score;
     }
     
     public void undo(){
         for(int m=0; m<row; m++){
            for(int n=0; n<col; n++){
                arr[m][n] = undo[m][n];
                System.out.print(arr[m][n]);
            }
             System.out.println();
         }
         score = undoScore;
         System.out.println("Score : "+score);
     }
     
     public boolean hasMove(){  //still can add(combine)
         boolean hasMove = false;
         for(int i=0; i<row; i++){
             for(int j=0; j<col; j++){
                 if(i==0 && j==0){
                    if(arr[i][j]==arr[i+1][j] || arr[i][j]==arr[i][j+1]){  //down & right
                            hasMove = true;
                    }
                }else if(i==0 && j==col-1){
                    if(arr[i][j]==arr[i+1][j] || arr[i][j]==arr[i][j-1]){  //down & left
                            hasMove = true;
                    }
                }else if(i==row-1 && j==0){
                    if(arr[i][j]==arr[i-1][j] || arr[i][j]==arr[i][j+1]){  
                            hasMove = true;
                    }
                }else if(i==row-1 && j==col-1){
                    if(arr[i][j]==arr[i-1][j] || arr[i][j]==arr[i][j-1]){  
                            hasMove = true;
                    }
                }else if(j==0){  //left edge
                    if(arr[i][j]==arr[i-1][j] || arr[i][j]==arr[i][j+1] || arr[i][j]==arr[i+1][j]){  //up & right & down
                            hasMove = true;
                    }
                }else if(i==0){  //up
                    if(arr[i][j]==arr[i][j+1] || arr[i][j]==arr[i][j-1] ||arr[i][j]==arr[i+1][j]){  //right & left & down
                            hasMove = true;
                    }
                }else if(j==col-1){  //right
                    if(arr[i][j]==arr[i][j-1] ||arr[i][j]==arr[i+1][j]  || arr[i][j]==arr[i-1][j]){  
                            hasMove = true;
                    }
                }else if(i==row-1){  //down
                    if(arr[i][j]==arr[i][j-1] || arr[i][j]==arr[i-1][j] || arr[i][j]==arr[i][j+1]){  
                            hasMove = true;
                    }
                }else{  //middle
                    if(arr[i][j]==arr[i][j-1] || arr[i][j]==arr[i-1][j] || arr[i][j]==arr[i][j+1] || arr[i][j]==arr[i+1][j]){  
                            hasMove = true;
                    }
                }
                 if(hasMove)
                     break;
            }
             if(hasMove)
                 break;
         }
         return hasMove;
     }
     
    
}


//        addKeyListener(new KeyAdapter() {
//            @Override
//            public void keyPressed(KeyEvent e) {
//                switch (e.getKeyCode()) {
//                    case KeyEvent.VK_ALT:
//                        exit(0);
//                        break;
//                    case KeyEvent.VK_S:
//                        moveDown();
//                        break;
//                    case KeyEvent.VK_LEFT:
//                        moveLeft();
//                        break;
//                    case KeyEvent.VK_D:
//                        moveRight();
//                        break;
//                }
//                repaint();
//            }
//
//            private void repaint() {
//                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
//            }
//        });