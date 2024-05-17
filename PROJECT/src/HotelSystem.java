/*
djfjsjgsfjkj
*/

import java.io.*;
import java.util.Scanner;

public class HotelSystem {
  public static void main(String[] args){

  }

  /*
    Method: readAccounts
    -----
    Parameters:
    String filepath - The filepath of the account file.
    -----
    Returns:
    String[][] data - 2D array that holds all account info.
    -----
    Description: This method reads in all employee account data from the file, and
    then returns it as a 2D array. Each row of the array is a separate employee, and each
    column contains that employee's info (ID, First Name, Last name, Pin)
     */
    public static String[][] readAccounts(String filepath){

        //Make new String Array
        String[][] data;
        //Open BufferedReader
        try{
            BufferedReader file = new BufferedReader(new FileReader(filepath));

            //Fill Array with all the information.
            data = new String[][]{file.readLine().split(",")};
        }
        catch(IOException e){
            System.out.println(e + "\n ERROR: CANNOT READ FILE");
            data = null;
        }

        return data;
    }
}
