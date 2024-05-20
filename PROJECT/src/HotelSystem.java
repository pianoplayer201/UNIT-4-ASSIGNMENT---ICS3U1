/*
djfjsjgsfjkj
*/

import java.io.*;
import java.util.Scanner;

public class HotelSystem {

    //GLOBAL CONSTANTS
    static final int ACCOUNT_ID_INDEX = 0;
    static final int ACCOUNT_FIRSTNAME_INDEX = 1;
    static final int ACCOUNT_LASTNAME_INDEX = 2;
    static final int ACCOUNT_PIN_INDEX = 3;
    static final int ACCOUNT_ENTRY_LENGTH = 4;

    public static void main(String[] args){

        //CONSTANTS
        final String ACCOUNT_FILE = "account.txt";
        final String RESERVATION_FILE = "reservation.txt";

        //Variables
        int userID;
        boolean isAdmin = false;
        String [][] roomData, accountData;

        //TESTING (delete later)
        accountData = readAccounts(ACCOUNT_FILE);
        for(int i = 0; i < accountData.length; i++){
            for(int j = 0; j < accountData[i].length; j++){
                System.out.print(accountData[i][j] + " ");
            }
            System.out.println();
        }

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
    public static String[][] readAccounts(String filepath) {

        //Declarations
        int numOfLines = 0;
        String[][] data;
        BufferedReader file;

        try {

            //COUNT NUM OF LINES
            for(file = new BufferedReader(new FileReader(filepath)); file.readLine() != null; ++numOfLines) {
                //ALL THE COUNTING IS DONE IN THE HEADER, NO BODY REQUIRED!
            }

            //INIT DATA[][] and reset BufferedReader to top of file.
            data = new String[numOfLines][ACCOUNT_ENTRY_LENGTH];
            file.close();
            file = new BufferedReader(new FileReader(filepath));

            //Read File, Fill out Data
            for(int i = 0; i < data.length; ++i) {
                data[i] = file.readLine().split(",");
            }
            file.close();

        }
        catch (IOException e) {
            System.out.println(e + "\nUH OH! YOU CAN'T READ THE FILE!");
            data = null;
        }

        return data;
    }
}
