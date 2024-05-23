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
    static final int ACCOUNT_ELEMENT_COUNT = 4;
    static final int RES_ROOM = 0;
    static final int RES_DATE = 1;
    static final int RES_FNAME = 2;
    static final int RES_LNAME = 3;
    static final int RES_EMPL_ID = 4;
    static final int RESERVATION_ELEMENT_COUNT = 5;

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
        roomData = readRoomInfo(RESERVATION_FILE);

        for(int i = 0; i < accountData.length; i++){
            for(int j = 0; j < accountData[i].length; j++){
                System.out.print(accountData[i][j] + " ");
            }
            System.out.println();
        }
        for(int i = 0; i < roomData.length; i++){
         for(int j = 0; j < roomData[i].length; j++){
                System.out.print(roomData[i][j] + " ");
            }
            System.out.println();
        }
        //END TESTING

    }

    /*
    Programmer: Ryan Mehrian
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
            data = new String[numOfLines][ACCOUNT_ELEMENT_COUNT];
            file.close();
            file = new BufferedReader(new FileReader(filepath));

            //Read File, Fill out Data
            for(int i = 0; i < data.length; ++i) {
                data[i] = file.readLine().split(",");
            }
            file.close();

        }
        catch (IOException e) {
            System.out.println(e + "\nUH OH! YOU CAN'T READ THE ACCOUNT ID FILE!");
            data = null;
        }

        return data;
    }

    /*
    Programmer:Ryan Mehrian
    Method: readRoomInfo
    -----
    Parameters:
    String filepath - The filepath of the reservations file.
    -----
    Returns:
    String[][] data - 2D array that holds all reservation and room info.
    -----
    Description: this method reads in all reservation and room info from the file, and
    then returns it as a 2D array. Each row of the array is a separate reservation, and each column
    is a piece of info about that reservation (Room Number, Check-In Date, Check-Out Date, Guest Name)
    */
    public static String[][] readRoomInfo(String filepath) {

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
            data = new String[numOfLines][RESERVATION_ELEMENT_COUNT];
            file.close();
            file = new BufferedReader(new FileReader(filepath));

            //Read File, Fill out Data
            for(int i = 0; i < data.length; ++i) {
                data[i] = file.readLine().split(",");
            }
            file.close();

        }
        catch (IOException e) {
            System.out.println(e + "\nUH OH! YOU CAN'T READ THE RESERVATIONS FILE!");
            data = null;
        }

        return data;
    }

    /*
    Programmer: Ryan Mehrian
    Method: writeFile
    -----
    Parameters:
    String reservationPath - The filepath of the reservations file.
    String accountPath - The filepath of the account file.
    String[][] reservationData - 2D array that holds all reservation and room info, prepared to be written.
    String[][] accountData - 2D array that holds all account info, prepared to be written.
    -----
    Returns:
    void
    -----
    Description: This method writes all data back to the files.
    */
    public static void writeFile(String reservationPath, String accountPath, String[][] reservationData, String[][] accountData) {
        //Declarations
        BufferedWriter file;

        //Logging out Message
        System.out.println("SAVING DATA...");

        //Write Reservations
        try {
            file = new BufferedWriter(new FileWriter(reservationPath));

            for(int i = 0; i < reservationData.length; i++){
                for(int j = 0; j < RESERVATION_ELEMENT_COUNT; j++){
                    file.write(reservationData[i][j] + ',');
                }
                file.write("\n");
            }
            file.close();
        }
        catch(IOException e){
            System.out.println(e);
        }

        //Write Account Data
        try {
            file = new BufferedWriter(new FileWriter(reservationPath));

            for(int i = 0; i < reservationData.length; i++){
                for(int j = 0; j < RESERVATION_ELEMENT_COUNT; j++){
                    file.write(reservationData[i][j] + ',');
                }
                file.write("\n");
            }
            file.close();
        }
        catch(IOException e){
            System.out.println(e);
        }
        //Write Reservations
        try {
            file = new BufferedWriter(new FileWriter(accountPath));

            for(int i = 0; i < accountData.length; i++){
                for(int j = 0; j < ACCOUNT_ELEMENT_COUNT; j++){
                    file.write(accountData[i][j] + ',');
                }
                file.write("\n");
            }
            file.close();
        }
        catch(IOException e){
            System.out.println(e);
        }
    }
        /*
    Mansour Abdelsalam
    Method: searchByName
    -----
    Parameters:
    String[][] roomData - 2d array containing room information
    -----
    Returns:
    void
    -----
    Description: Asks user for a first name and last name and then prints out each reservation made under that client name, with room number and date information.
    */
    public static void searchByName(String[][] roomData){
        Scanner scan = new Scanner(System.in);

        String first_name = "";
        String last_name = "";

        System.out.println("Enter the client's information.");
        System.out.print("First name: ");
        first_name = scan.nextLine();
        System.out.print("Last name: ");
        last_name = scan.nextLine();

        for (int i = 0; i<roomData.length; i++){
            if(((roomData[i][ACCOUNT_FIRSTNAME_INDEX].toLowerCase()).equals(first_name.toLowerCase())) && ((roomData[i][RES_LNAME].toLowerCase()).equals(last_name.toLowerCase()))){
              System.out.println("Room #: "+roomData[i][RES_ROOM]);
              System.out.println("Date reserved: "+roomData[i][RES_DATE]);
              System.out.println(); //blank line
            }
        }
    }

    /*
    Mansour Abdelsalam
    Method: searchAvailableByDate
    -----
    Parameters:
    String[][] roomData - 2d array containing room information
    -----
    Returns:
    void
    -----
    Description: Asks user for a date in MM/DD/YYYY format (will ask user to reenter if invalid format given) and then print out each available room on that day.
    Will check if inputted date is between 1 to 31, and month is between 1 to 12.
    */
    public static void searchAvailableByDate(String[][] roomData){
        Scanner scan = new Scanner(System.in);

        boolean valid_date = false;
        boolean room_found = false;
        int month = 0;
        int day = 0;
        int year = 0;
        String date = "";

        System.out.println("Enter the date to check.");
        do{
            System.out.print("Month: ");
            month = scan.nextInt();
            System.out.print("Day: ");
            day = scan.nextInt();
            System.out.print("Year: ");
            year = scan.nextInt();

            if (((month <= 12) && (month >= 1)) && ((day <= 31) && (day >= 1))){
                date += ""+month+"/"+day+"/"+year;
                valid_date = true;
            } else {
                System.out.println("Invalid date entered.");
                System.out.println(); //blank line
                System.out.println("Reenter date:");
            }
        } while(!valid_date);

        if(valid_date){
            System.out.println(); //blank line
            System.out.println("Available rooms on "+date+":");

            for(int i = 0; i<roomData.length; i++){
                if(!roomData[i][RES_DATE].equals(date)){
                    room_found = true;
                    System.out.println("Room #: "+roomData[i][RES_ROOM]);
                }
            }
            if(!room_found){
                System.out.println("No rooms available on this date.");
                System.out.println(); //blank line
            }
        }
    }

    /*
    Mansour Abdelsalam
    Method: searchReservationByDate
    -----
    Parameters:
    String[][] roomData - 2d array containing room information
    -----
    Returns:
    void
    -----
    Description: Asks user for a date in MM/DD/YYYY format (will ask user to reenter if invalid format given) and then print out each reservation on that day, with room number and client information.
    */
    public static void searchReservationByDate(String[][] roomData){
        Scanner scan = new Scanner(System.in);

        boolean valid_date = false;
        boolean room_found = false;
        int month = 0;
        int day = 0;
        int year = 0;
        String date = "";

        System.out.println("Enter the date to check.");
        do{
            System.out.print("Month: ");
            month = scan.nextInt();
            System.out.print("Day: ");
            day = scan.nextInt();
            System.out.print("Year: ");
            year = scan.nextInt();

            if (((month <= 12) && (month >= 1)) && ((day <= 31) && (day >= 1))){
                date += ""+month+"/"+day+"/"+year;
                valid_date = true;
            } else {
                System.out.println("Invalid date entered.");
                System.out.println(); //blank line
                System.out.println("Reenter date:");
            }
        } while(!valid_date);

        if(valid_date){
            System.out.println(); //blank line
            System.out.println("Rooms reserved on "+date+":");

            for(int i = 0; i<roomData.length; i++){
                if(roomData[i][RES_DATE].equals(date)){
                    room_found = true;
                    System.out.println("Room #: "+roomData[i][RES_ROOM]);
                    System.out.println("Customer name: "+roomData[i][RES_FNAME]+" "+roomData[i][RES_LNAME]);
                    System.out.println(); //blank line
                }
            }
            if(!room_found){
                System.out.println("No rooms are reserved on this date.");
                System.out.println(); //blank line
            }
        }
        
    }

    public static int userLogin(String[][] accountData){
        Scanner sc = new Scanner(System.in);
        int userID;
        boolean userVerify;
        int userPin;
        int accountIndex = 0;

        do
        {
            userVerify = false;
            System.out.print("Enter ID: ");
            userID = sc.nextInt();
            for (int i = 0; i <= accountData.length - 1; i++)
            {
                  if (Integer.parseInt(accountData[i][0]) == (userID))
                  {
                    userVerify = true;
                    accountIndex += i;
                  }
            }
            while (userVerify == false)
            {
                  System.out.print("Invalid ID entered, enter new ID: ");
                  userID = sc.nextInt();

                  for (int i = 0; i <= accountData.length - 1; i++)
                  {
                    if (Integer.parseInt(accountData[i][0]) == (userID))
                    {
                      userVerify = true;
                      accountIndex += i;
                    }
                  }
            }

              System.out.print("enter pin: ");
              userPin = sc.nextInt();

              while (Integer.parseInt(accountData[accountIndex][3]) != (userPin) && userPin != 0)
              {
                System.out.print("Incorrect pin entered, enter new pin: ");
                userPin = sc.nextInt();
              }
        } while (userPin == 0);

        return userID;
    }

        /*
    Programmer: Ryan Mehrian
    Method: addEmployee
    ----
    Parameters:
    String[][] accountData - 2D Array with all employee account info.
    ----
    Returns:
    updatedData[][] - 2D Array that holds accountData but updated.
    ----
    Description:
    Only available to the admin. Creates a new temp array that is one index larger than accountData[], fills the new
    array with every room besides the removed one, and then returns the array.
     */
    public static String[][] addEmployee(String[][] accountData){
        //Declarations
        final int VALID_ID_LENGTH = 6;
        String input = null;
        boolean inputValid = false;
        boolean quit = false;

        Scanner sc = new Scanner(System.in);
        //accountData[][] is later copied into temp[][] via the for loop during input.
        String[][] updatedData = new String[accountData.length+1][ACCOUNT_ELEMENT_COUNT];


        //USERID Prompt and Validation Loop:
        while(!inputValid) {
            System.out.print("Enter the new Employee's ID (6 digits), or enter 0 to go back to menu options.\n > ");
            try {
                input = sc.nextLine();
                //User quit
                if (Integer.parseInt(input) == 0){
                    inputValid = true;
                    quit = true;
                }
                //Valid ID
                else if (input.length() == VALID_ID_LENGTH) {

                    inputValid = true;
                    //Check for Duplicate Account
                    for (int i = 0; i < accountData.length; i++) {
                        if(accountData[i][ACCOUNT_ID_INDEX].equals(input)){
                            inputValid = false;
                            System.out.println("DUPLICATE USERID ENTERED");
                        }
                    }
                }
                else{
                    System.out.println("ID MUST BE 6 DIGITS");
                }

            } catch (NumberFormatException e) {
                System.out.println("INVALID INPUT");
            }
        }

        //Copy accountData to temp
        if(!quit){
            for(int i = 0; i < accountData.length; i++){
                for(int j = 0; j < ACCOUNT_ELEMENT_COUNT; j++){
                    updatedData[i][j] = accountData[i][j];
                }
            }
        }
        else{
            updatedData = accountData;
        }

        //Fill Out All fields for new employee using further prompts.
        if(!quit){

            updatedData[accountData.length][ACCOUNT_ID_INDEX] = input;
            System.out.print("Please enter Employee First Name\n > ");
            updatedData[accountData.length][ACCOUNT_FIRSTNAME_INDEX] = sc.nextLine();
            System.out.print("Please enter Employee Last Name\n > ");
            updatedData[accountData.length][ACCOUNT_LASTNAME_INDEX] = sc.nextLine();
            System.out.print("Please set a 4 digit account pin.\n > ");

            //Input Validation for Pin
            inputValid = false;
            while(!inputValid) {
                try {
                    input = sc.nextLine();
                    if (input.length() == 4) {
                        Integer.parseInt(input);
                        inputValid = true;
                        updatedData[accountData.length][ACCOUNT_PIN_INDEX] = input;

                    } else {
                        System.out.print("PIN MUST BE A 4 DIGIT NUMBER\n > ");
                    }
                } catch (NumberFormatException e) {
                    System.out.print("PIN MUST BE A 4 DIGIT NUMBER\n > ");
                }
            }
        }

        //Assign to accountData and output confirmation
        System.out.println("CHANGES SAVED");
        accountData = updatedData;
        return updatedData;
    }

    /*
    Programmer: Ryan Mehrian
    Method: deleteEmployee
    ----
    Parameters:
    String[][] accountData - 2D Array with all employee account info.
    ----
    Returns:
    updatedData[][] - 2D Array that holds accountData but updated.
    ----
    Description:
    Only available to the admin. Creates a new temp array that is one index smaller than accountData[], fills the new
    array with every room besides the removed one, and then returns the array.
     */
    public static String[][] deleteEmployee(String[][] accountData){
        //Declarations
        final String DELETE_FLAG = "DELETE";
        final int VALID_ID_LENGTH = 6;
        String input = null;
        boolean inputValid = false;
        boolean quit = false;
        boolean deletePass = false;

        Scanner sc = new Scanner(System.in);
        //accountData[][] is later copied into temp[][] via the for loop during input.
        String[][] updatedData = new String[accountData.length-1][ACCOUNT_ELEMENT_COUNT];


        //USERID Prompt and Validation Loop:
        while(!inputValid) {
            System.out.print("Enter the 6 DIGIT Employee ID to delete, or enter 0 to go back to menu options.\n > ");
            try {
                input = sc.nextLine();
                //Enters Valid UserID
                if (input.length() == VALID_ID_LENGTH) {
                    Integer.parseInt(input);
                    //Check for Valid Account
                    for (int i = 0; i < accountData.length; i++) {
                        if(accountData[i][ACCOUNT_ID_INDEX].equals(input)){
                            accountData[i][ACCOUNT_ID_INDEX] = DELETE_FLAG;
                            inputValid = true;
                        }
                    }
                }
                //Enters quit number of 0
                else if (Integer.parseInt(input) == 0){
                    inputValid = true;
                    quit = true;
                }
                else{
                    System.out.println("ID MUST BE 6 DIGITS");
                }
            } catch (NumberFormatException e) {
                System.out.println("INVALID INPUT");
            }
        }

        if(!quit){
            //Copy accountData to temp
             for(int i = 0; i < accountData.length; i++) {
                 //Skips deleted line.
                 if (accountData[i][ACCOUNT_ID_INDEX].equals(DELETE_FLAG)) {
                     deletePass = true;
                 } else if (deletePass) {
                     for (int j = 0; j < ACCOUNT_ELEMENT_COUNT; j++) {
                         updatedData[i - 1][j] = accountData[i][j];
                     }
                 } else {
                     for (int j = 0; j < ACCOUNT_ELEMENT_COUNT; j++) {
                         updatedData[i][j] = accountData[i][j];
                     }
                 }
             }
        }
        else{
            updatedData = accountData;
        }

        //Assign to accountData and output confirmation
        System.out.println("CHANGES SAVED");
        accountData = updatedData;
        return updatedData;
    }
}


