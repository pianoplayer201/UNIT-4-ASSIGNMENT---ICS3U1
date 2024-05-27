/*
djfjsjgsfjkj
*/
import jdk.jshell.spi.ExecutionControl;

import java.io.*;
import java.util.InputMismatchException;
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

    public static void main(String[] args) throws ExecutionControl.NotImplementedException {

        //CONSTANTS
        final String ACCOUNT_FILE = "account.txt";
        final String RESERVATION_FILE = "reservation.txt";
        final int ADMIN_ID = 0;

        //Variables
        int userID;
        boolean loggedIn = false;
        boolean isAdmin = false;
        String[][] roomData, accountData;

        //MAIN WHILE LOOP
        while (true) {
            // read data
            accountData = readAccounts(ACCOUNT_FILE);
            roomData = readRoomInfo(RESERVATION_FILE);

            // login
            userID = userLogin(accountData);
            loggedIn = true;
            if (userID == ADMIN_ID) {
                isAdmin = true;
            }

            // post-login prompt
            while(loggedIn){
                int selection = reservationMenu(accountData, roomData, userID, isAdmin);
                switch(selection){
                    case 0:
                        // case 0: log out
                        loggedIn = false;
                        break;
                    case 1:
                        // case 1: search available reservations by date
                        searchAvailableByDate(roomData);
                        break;
                    case 2:
                        //case 2: search booked rooms by date
                        searchReservationByDate(roomData);
                        break;
                    case 3:
                        //case 3: search booked rooms by name
                        searchByName(roomData);
                        break;
                    case 4:
                        // case 4: make reservation
                        roomData = makeReservation(roomData, userID);
                        break;
                    case 5:
                        // case 5: cancel reservation
                        roomData = deleteReservation(roomData, userID);
                        break;
                    case 6:
                        // case 6: change reservation
                        roomData = changeReservation(roomData, userID);
                        break;
                    case 7:
                        // case 7: change pin
                        changePin(accountData, userID);
                        break;
                    case 8:
                        // case 8: add employee
                        if(isAdmin){
                            accountData = addEmployee(accountData);
                        }
                        break;
                    case 9:
                        // case 9: delete employee
                        if(isAdmin){
                            accountData = deleteEmployee(accountData);
                        }
                        break;
                    case 10:
                        // case 10: create rooms
                        if(isAdmin){
                            roomData = makeRoom(roomData);
                        }
                        break;
                    case 11:
                        // case 11: delete rooms
                        if(isAdmin){
                            throw new ExecutionControl.NotImplementedException("NOT IMPLEMENTED");
//                            //NOT YET IMPLEMENTED
//                            System.out.println("NOT YET IMPLEMENTED");
                        }
                        break;
                }
            }

            // log out and write to file
            writeFile(RESERVATION_FILE, ACCOUNT_FILE, roomData, accountData);

        }

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

            // count number of lines
            for (file = new BufferedReader(new FileReader(filepath)); file.readLine() != null; ++numOfLines) {
                // TODO: empty for loops are bad, consider alternatives
            }

            // reset file and initialize data[][]
            data = new String[numOfLines][ACCOUNT_ELEMENT_COUNT];
            file.close();
            file = new BufferedReader(new FileReader(filepath));

            // read file and load data
            for (int i = 0; i < data.length; ++i) {
                data[i] = file.readLine().split(",");
            }
            file.close();

        } catch (IOException e) {
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

            // count number of lines
            for (file = new BufferedReader(new FileReader(filepath)); file.readLine() != null; ++numOfLines) {
                // TODO: empty for loops are bad, consider alternatives
            }

            // initialize data and reset file
            data = new String[numOfLines][RESERVATION_ELEMENT_COUNT];
            file.close();
            file = new BufferedReader(new FileReader(filepath));

            // read file and load data
            for (int i = 0; i < data.length; ++i) {
                data[i] = file.readLine().split(",");
            }
            file.close();

        } catch (IOException e) {
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
        // initialize objects
        BufferedWriter file;

        // update the user
        System.out.println("SAVING DATA...");

        // TODO: consider turning these three try/except blocks into a reusable method

        // try to write to reservations
        try {
            file = new BufferedWriter(new FileWriter(reservationPath));

            for (String[] reservationDatum : reservationData) {
                for (int j = 0; j < RESERVATION_ELEMENT_COUNT; j++) {
                    file.write(reservationDatum[j] + ',');
                }
                file.write("\n");
            }
            file.close();
        } catch (IOException e) {
            System.out.println(e);
        }

        // write account data
        try {
            file = new BufferedWriter(new FileWriter(reservationPath));

            for (String[] reservationDatum : reservationData) {
                for (int j = 0; j < RESERVATION_ELEMENT_COUNT; j++) {
                    file.write(reservationDatum[j] + ',');
                }
                file.write("\n");
            }
            file.close();
        } catch (IOException e) {
            System.out.println(e);
        }
        // write reservations
        try {
            file = new BufferedWriter(new FileWriter(accountPath));

            for (String[] accountDatum : accountData) {
                for (int j = 0; j < ACCOUNT_ELEMENT_COUNT; j++) {
                    file.write(accountDatum[j] + ',');
                }
                file.write("\n");
            }
            file.close();
        } catch (IOException e) {
            System.out.println(e);
        }
    }

    /*
    Programmer: Noah (edits by Ryan)
    Method: userLogin
    -----
    Parameters:
    String[][] accountData - 2D Array with all employee account info.
    -----
    Returns:
    int userID - The ID of the user logged in.
    -----
    Description: This method prompts the user for their ID and pin, and then verifies that the ID and pin match.
     */
    public static int userLogin(String[][] accountData) {
        // initialize objects
        Scanner sc = new Scanner(System.in);
        String userID;
        boolean userVerify;
        String userPin = "";
        int accountIndex = 0;

        // loop until valid uid and pin are entered
        do {
            userVerify = false;
            System.out.print("Enter ID\n > ");
            userID = sc.nextLine();
            // verify existence of the inputted id
            for (int i = 0; i <= accountData.length - 1; i++) {
                if (accountData[i][0].equals(userID)) {
                    userVerify = true;
                    accountIndex += i;
                }
            }
            // if the id does not exist, prompt user to re-enter
            while (!userVerify) {
                System.out.print("ID INVALID, please re-enter your ID\n > ");
                userID = sc.nextLine();

                for (int i = 0; i <= accountData.length - 1; i++) {
                    if (accountData[i][0].equals(userID)) {
                        userVerify = true;
                        accountIndex += i;
                    }
                }
            }

            // ask for pin
            System.out.print("Enter pin: ");
            userPin = sc.nextLine();

            // check if the entered pin matches the pin for the entered ID
            // if the pin does not match, prompt the user to re-enter their pin or enter a different ID
            while (!accountData[accountIndex][3].equals(userPin) && !userPin.equals("0")){
                    System.out.print("Incorrect pin entered, enter your pin, or enter '0' to enter a different ID\n > ");
                    userPin = sc.nextLine();
            }
        } while (userPin.equals("0"));

        // return integer uid
        return Integer.parseInt(userID);
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
        // initialize objects
        final int VALID_ID_LENGTH = 6;
        String input = null;
        boolean inputValid = false;
        boolean quit = false;

        Scanner sc = new Scanner(System.in);
        //`accountData[][]` is later copied into `temp[][]` via the for loop during input.
        String[][] updatedData = new String[accountData.length + 1][ACCOUNT_ELEMENT_COUNT];


        // uid prompt and validation
        while (!inputValid) {
            System.out.print("Enter the new Employee's ID (6 digits), or enter 0 to go back to menu options.\n > ");
            try {
                input = sc.nextLine();
                // user quits
                if (Integer.parseInt(input) == 0) {
                    inputValid = true;
                    quit = true;
                }
                // id is valid
                else if (input.length() == VALID_ID_LENGTH) {

                    inputValid = true;
                    // check for duplicate accounts
                    for (String[] accountDatum : accountData) {
                        if (accountDatum[ACCOUNT_ID_INDEX].equals(input)) {
                            inputValid = false;
                            System.out.println("DUPLICATE USERID ENTERED");
                        }
                    }
                } else {
                    System.out.println("ID MUST BE 6 DIGITS");
                }

            } catch (NumberFormatException e) {
                System.out.println("INVALID INPUT");
            }
        }

        // copy accountData to temp
        if (!quit) {
            for (int i = 0; i < accountData.length; i++) {
                System.arraycopy(accountData[i], 0, updatedData[i], 0, ACCOUNT_ELEMENT_COUNT);
            }
        } else {
            updatedData = accountData;
        }

        // fill Out All fields for new employee using further prompts
        if (!quit) {

            updatedData[accountData.length][ACCOUNT_ID_INDEX] = input;
            System.out.print("Please enter Employee First Name\n > ");
            updatedData[accountData.length][ACCOUNT_FIRSTNAME_INDEX] = sc.nextLine();
            System.out.print("Please enter Employee Last Name\n > ");
            updatedData[accountData.length][ACCOUNT_LASTNAME_INDEX] = sc.nextLine();
            System.out.print("Please set a 4 digit account pin.\n > ");

            // input Validation for Pin
            inputValid = false;
            while (!inputValid) {
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

        // assign to accountData and output confirmation
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
    public static String[][] deleteEmployee(String[][] accountData) {
        // initialize objects
        final String DELETE_FLAG = "DELETE";
        final int VALID_ID_LENGTH = 6;
        String input = null;
        boolean inputValid = false;
        boolean quit = false;
        boolean deletePass = false;

        Scanner sc = new Scanner(System.in);
        //accountData[][] is later copied into temp[][] via the for loop during input.
        String[][] updatedData = new String[accountData.length - 1][ACCOUNT_ELEMENT_COUNT];


        // uid prompt and validation
        while (!inputValid) {
            System.out.print("Enter the 6 DIGIT Employee ID to delete, or enter 0 to go back to menu options.\n > ");
            try {
                input = sc.nextLine();
                // the user id is valid
                if (input.length() == VALID_ID_LENGTH) {
                    Integer.parseInt(input);
                    // check for valid account
                    for (int i = 0; i < accountData.length; i++) {
                        if (accountData[i][ACCOUNT_ID_INDEX].equals(input)) {
                            accountData[i][ACCOUNT_ID_INDEX] = DELETE_FLAG;
                            inputValid = true;
                        }
                    }
                }
                // user quits
                else if (Integer.parseInt(input) == 0) {
                    inputValid = true;
                    quit = true;
                } else {
                    System.out.println("ID MUST BE 6 DIGITS");
                }
            } catch (NumberFormatException e) {
                System.out.println("INVALID INPUT");
            }
        }

        if (!quit) {
            // copy accountData to temp
            for (int i = 0; i < accountData.length; i++) {
                // skip deleted lines
                if (accountData[i][ACCOUNT_ID_INDEX].equals(DELETE_FLAG)) {
                    deletePass = true;
                } else if (deletePass) {
                    System.arraycopy(accountData[i], 0, updatedData[i - 1], 0, ACCOUNT_ELEMENT_COUNT);
                } else {
                    System.arraycopy(accountData[i], 0, updatedData[i], 0, ACCOUNT_ELEMENT_COUNT);
                }
            }
        } else {
            updatedData = accountData;
        }

        // assign to accountData and output confirmation
        System.out.println("CHANGES SAVED");
        accountData = updatedData;
        return updatedData;
    }

    /*
    Programmer: Noah Hur
    Method: makeRoom
    -----
    Parameters:
    String[][] roomData
    -----
    Returns:
    String[][] makeRoom
    -----
    Description: Only available to the admin. Will search through roomData and will ask to re-enter room number if already available. 
    If not available, creates a new temp array that is one index larger than roomData[], fills it with the new reservation, and then assigns that temp array to roomData[]. 
    The method will also check for invalid input and loop until valid input is entered.
    */

    public static String[][] makeRoom(String[][] roomData)
    {
        // create a scanner object for user input
        Scanner sc = new Scanner(System.in);
        String newRoom;
        boolean roomVerify = true;
        // create a temporary 2D array to hold room data
        String[][] temp_roomData;

        // prompt the user to enter a new room number
        System.out.print("Enter new room number: ");
        newRoom = sc.nextLine();

        // check if the entered room number already exists in the room data
        for (int i = 0; i <= roomData.length - 1; i++)
        {
            if (newRoom.equals(roomData[i][0])) {
                roomVerify = false;
                break;
            }
        }
        // if the room number already exists, prompt the user to enter a new room number
        while (!roomVerify)
        {
            roomVerify = true;
            System.out.print("room number already exists, enter new room number: ");
            newRoom = sc.nextLine();
            for (int i = 0; i <= roomData.length - 1; i++)
            {
                if (newRoom.equals(roomData[i][0])) {
                    roomVerify = false;
                    break;
                }
            }
        }

        // create a new 2D array with an additional row for the new room
        temp_roomData = new String [roomData.length + 1][5];

        // copy the existing room data into the new 2D array
        for (int i = 0; i <= roomData.length - 1; i++)
        {
            System.arraycopy(roomData[i], 0, temp_roomData[i], 0, 5);
        }

        // add the new room number to the last row of the new 2D array
        temp_roomData[temp_roomData.length - 1][0] = newRoom;

        // initialize the remaining elements of the last row to "-1"
        for (int i = 1; i <= 4; i++)
        {
            temp_roomData[temp_roomData.length - 1][i] = "-1";
        }

        // print a confirmation message
        System.out.println("new room: " + newRoom + ", created");

        // return the new 2D array with the added room
        return temp_roomData;
    }

    /*
    Programmer: Ryan Mehrian
    Method: reservationMenu
    ----
    Parameters:
    String[][] accountData - 2D Array with all employee account info.
    String[][] roomData - 2D Array with all room & reservation info.
    int userID - The ID of the user logged in.
    boolean isAdmin - If the user logged in is Admin.
    ----
    Returns:
    int selectionOption
    -----
    This program prompts the user with a menu of options for the reservation system, and then tells main
    to call the relevant submenu based on the user's choice (returns the choice).
    */
    public static int reservationMenu(String[][] accountData, String[][] roomData, int userID, boolean isAdmin){
        // initialize objects
        int selectionOption = 0;
        String userName = "";
        boolean inputValid = false;
        Scanner sc = new Scanner(System.in);

        // look for username
        for (String[] accountDatum : accountData) {
            if (Integer.parseInt(accountDatum[ACCOUNT_ID_INDEX]) == userID) {
                userName = accountDatum[ACCOUNT_FIRSTNAME_INDEX];
            }
        }

        // list options, input and validation
        while(!inputValid){
            System.out.printf("WELCOME %S, what would you like to do today?\n", userName);

            // default options
            System.out.print("""
                    1. Search for Available Rooms by Date
                    2. Search for Reservations by Date
                    3. Search for Reservations by Name
                    4. Make a Reservation
                    5. Cancel a Reservation
                    6. Change a Reservation
                    7. Change your Pin Number
                    """);
            // admin options
            if(isAdmin){
                System.out.print("""
                        8. Add an Employee
                        9. Delete an Employee
                        10. Add a Room
                        11. Delete a Room
                        """);
            }
            // logout option
            System.out.print("0. Logout\n > ");

            // input and validation
            try {
                // Attempt to read the user's selection as an integer
                selectionOption = sc.nextInt();
                // Consume the newline character left in the input stream
                sc.nextLine();
                // If the user is an admin
                if(isAdmin){
                    // Check if the selection is within the valid range for admins
                    if(selectionOption >= 0 && selectionOption <= 11){
                        // If it is, mark the input as valid
                        inputValid = true;
                    }
                }
                // If the user is not an admin
                else {
                    // Check if the selection is within the valid range for non-admins
                    if(selectionOption >= 0 && selectionOption <= 7){
                        // If it is, mark the input as valid
                        inputValid = true;
                    }
                    // If the selection is not within the valid range
                    else{
                        // Mark the input as invalid and print an error message
                        // inputValid = false;
                        System.out.println("invalid option");
                    }
                }
            // If the input could not be read as an integer
            } catch (InputMismatchException e){
                // Print an error message
                System.out.println("input must be a number");
            }


        }
        // return selected option
        return selectionOption;
    }
    
    /*
    Programmer: Mansour Abdelsalam
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
        // create a Scanner object for user input
        Scanner scan = new Scanner(System.in);

        // initialize a boolean to track if a name is found
        boolean name_found = false;
        // initialize strings to hold the first and last name
        String first_name = "";
        String last_name = "";

        // prompt the user to enter the client's information
        System.out.println("Enter the client's information.");
        System.out.print("First name: ");
        first_name = scan.nextLine();
        System.out.print("Last name: ");
        last_name = scan.nextLine();

        // print a blank line for readability
        System.out.println();
        // print the search query
        System.out.println("Rooms reserved by "+first_name+" "+last_name+":");

        // loop through the room data
        for (String[] roomDatum : roomData) {
            // if the first and last name in the room data match the input and the first name is not "-1"
            if (((roomDatum[RES_FNAME]).equalsIgnoreCase(first_name)) && ((roomDatum[RES_LNAME]).equalsIgnoreCase(last_name)) && (!first_name.equals("-1"))) {
                // set name_found to true
                name_found = true;
                // print the room number and reservation date
                System.out.println("Room #: " + roomDatum[RES_ROOM]);
                System.out.println("Date reserved: " + roomDatum[RES_DATE]);
                // print a blank line for readability
                System.out.println();
            }
        }
        // if no reservations were found for the client
        if(!name_found){
            // print a message indicating no reservations were found
            System.out.println("No reservations found for this client.");
            // print a blank line for readability
            System.out.println();
        }
    }
    
    /*
    Programmer: Mansour Abdelsalam
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
        // create a scanner object for user input
        Scanner scan = new Scanner(System.in);

        // initialize variables for date validation and room found flag
        boolean valid_date = false;
        boolean room_found = false;
        int month = 0;
        int day = 0;
        int year = 0;
        StringBuilder date = new StringBuilder();

        // prompt the user to enter the date
        System.out.println("Enter the date to check.");
        do{
            try{
                // ask for month, day, and year
                System.out.print("Month: ");
                month = scan.nextInt();
                System.out.print("Day: ");
                day = scan.nextInt();
                System.out.print("Year: ");
                year = scan.nextInt();

                // validate the entered date
                if (((month <= 12) && (month >= 1)) && ((day <= 31) && (day >= 1))){
                    // if valid, construct the date string
                    date.append(month).append("/").append(day).append("/").append(year);
                    valid_date = true;
                } else {
                    // if not valid, ask the user to reenter the date
                    System.out.println("Invalid date entered.");
                    System.out.println(); //blank line
                    System.out.println("Reenter date:");
                }
            } catch(InputMismatchException e){
                // handle non-integer input
                System.out.println("Invalid input. Please enter numerical values.");
                scan.next(); //clear invalid input
                System.out.println(); //blank line
                System.out.println("Reenter date:");
            }
        } while(!valid_date);

        // the date is valid, proceed to search for available rooms
        System.out.println();
        System.out.println("Available rooms on "+date+":");

        // loop through the room data
        for (String[] roomDatum : roomData) {
            // if the room is not reserved on the entered date, print the room number
            if (!roomDatum[RES_DATE].contentEquals(date)) {
                room_found = true;
                System.out.println("Room #: " + roomDatum[RES_ROOM]);
                System.out.println(); //blank line
            }
        }
        // if no rooms are found, inform the user
        if(!room_found){
            System.out.println("No rooms available on this date.");
            System.out.println(); // blank line
        }
    }
    
    /*
    Programmer: Mansour Abdelsalam
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
        // create a scanner object for user input
        Scanner scan = new Scanner(System.in);

        // initialize variables for date validation and room found flag
        boolean valid_date = false;
        boolean room_found = false;
        int month = 0;
        int day = 0;
        int year = 0;
        StringBuilder date = new StringBuilder();

        // prompt the user to enter the date
        System.out.println("Enter the date to check.");
        do {
            try{
                // ask for month, day, and year
                System.out.print("Month: ");
                month = scan.nextInt();
                System.out.print("Day: ");
                day = scan.nextInt();
                System.out.print("Year: ");
                year = scan.nextInt();

                // validate the entered date
                if (((month <= 12) && (month >= 1)) && ((day <= 31) && (day >= 1))){
                    // if valid, construct the date string
                    date.append(month).append("/").append(day).append("/").append(year);
                    valid_date = true;
                } else {
                    // if not valid, ask the user to reenter the date
                    System.out.println("Invalid date entered.");
                    System.out.println(); //blank line
                    System.out.println("Reenter date:");
                }
            } catch(InputMismatchException e){
                // handle non-integer input
                System.out.println("Invalid input. Please enter numerical values.");
                scan.next(); //clear invalid input
                System.out.println(); //blank line
                System.out.println("Reenter date:");
            }
        } while(!valid_date);

        // the date is valid, proceed to search for available rooms
        System.out.println();
        System.out.println("Rooms reserved on "+date+":");

        // loop through the room data
        for (String[] roomDatum : roomData) {
            // if the room is reserved on the entered date, print the room number and customer name
            if (roomDatum[RES_DATE].contentEquals(date)) {
                room_found = true;
                System.out.println("Room #: " + roomDatum[RES_ROOM]);
                System.out.println("Customer name: " + roomDatum[RES_FNAME] + " " + roomDatum[RES_LNAME]);
                System.out.println(); //blank line
            }
        }
        // if no reservations were found for the date
        if(!room_found){
            // print a message indicating no reservations were found
            System.out.println("No rooms are reserved on this date.");
            System.out.println(); // blank line
        }
    }
    
    /*
    Programmer: Mansour Abdelsalam
    Method: makeReservation
    -----
    Parameters:
    String[][] roomData - 2d array containing room information
    int userID - the user id of the employee making the reservation
    -----
    Returns:
    String[][] temp_array - the temporary array made used to make edits roomData
    -----
    Description: Prompts the user for a room number and a date. If the room is available at the date, prompt the user for a clientele first and last name. 
    The userID of the employee who made the reservation is saved. Will check if inputted date is between 1 to 31, and month is between 1 to 12. 
    If a reservation is made for a room that already has a reservation on that date, it informs the user that the reservation cannot be made.
    */
    public static String[][] makeReservation(String[][] roomData, int userID){
        // create a scanner for user input
        Scanner scan = new Scanner(System.in);

        // initialize variables for room existence, room number validity, date validity, and room availability
        boolean room_exists = false;
        boolean valid_room_num = false;
        boolean valid_date = false;
        boolean room_taken = false;
        String[][] temp_array;
        int room_num = 0;
        int month = 0;
        int day = 0;
        int year = 0;
        StringBuilder date = new StringBuilder();
        String first_name = "";
        String last_name = "";
        String employee_id = "";

        // prompt user to enter room number to be reserved
        System.out.println("Enter room number to be reserved.");
        do {
            try{
                System.out.print("Room #: ");
                room_num = scan.nextInt();
                System.out.println(); //blank line

                // if room number is valid, set valid_room_num to true
                valid_room_num = true;
            } catch(InputMismatchException e){
                // if input is invalid, prompt user to enter a valid room number
                System.out.println("Invalid input. Please enter numerical values.");
                scan.next(); //clear invalid input
                System.out.println(); //blank line
                System.out.println("Reenter room number:");
            }
        } while(!valid_room_num);

        // prompt user to enter the date to be reserved
        System.out.println("Enter the date to be reserved.");
        do {
            try{
                System.out.print("Month: ");
                month = scan.nextInt();
                System.out.print("Day: ");
                day = scan.nextInt();
                System.out.print("Year: ");
                year = scan.nextInt();

                // if date is valid, set valid_date to true
                if (((month <= 12) && (month >= 1)) && ((day <= 31) && (day >= 1))){
                    date.append(month).append("/").append(day).append("/").append(year);
                    valid_date = true;
                } else {
                    // if date is invalid, prompt user to reenter the date
                    System.out.println("Invalid date entered.");
                    System.out.println(); //blank line
                    System.out.println("Reenter date:");
                }
            } catch(InputMismatchException e){
                // if input is invalid, prompt user to enter a valid date
                System.out.println("Invalid input. Please enter numerical values.");
                scan.next(); //clear invalid input
                System.out.println(); //blank line
                System.out.println("Reenter date:");
            }
        } while(!valid_date);
        System.out.println(); //blank line
        scan.nextLine(); //get rid of new line in input stream

        // check if room is available on the entered date
        for (String[] roomDatum : roomData) {
            if (room_num == Integer.parseInt(roomDatum[RES_ROOM])) {
                room_exists = true;
                if (date.toString().equals(roomDatum[RES_DATE])) {
                    room_taken = true;
                    System.out.println("This room cannot be booked on this date.");
                    System.out.printf("Customer %s %s has already booked Room No. %s for the date %s.\n", roomDatum[RES_FNAME], roomDatum[RES_LNAME], roomDatum[RES_ROOM], roomDatum[RES_DATE]);
                    System.out.println(); //blank line
                }
            }
        }

        // if room is available and exists, proceed with reservation
        if ((!room_taken) && (room_exists)) {
            System.out.println("Enter the client's information.");
            System.out.print("First name: ");
            first_name = scan.nextLine();
            System.out.print("Last name: ");
            last_name = scan.nextLine();
            System.out.println(); //blank line

            // format and print user id
            employee_id = String.format("%06d", userID);
            System.out.println("Your user ID is: "+employee_id);
            System.out.println(); //blank line

            // create a new array to hold the updated room data
            temp_array = new String[roomData.length+1][RESERVATION_ELEMENT_COUNT];
            for(int i = 0; i<roomData.length; i++){
                System.arraycopy(roomData[i], 0, temp_array[i], 0, RESERVATION_ELEMENT_COUNT);
            }
            // add the new reservation to the array
            temp_array[temp_array.length-1][RES_ROOM] = String.valueOf(room_num);
            temp_array[temp_array.length-1][RES_DATE] = date.toString();
            temp_array[temp_array.length-1][RES_FNAME] = first_name;
            temp_array[temp_array.length-1][RES_LNAME] = last_name;
            temp_array[temp_array.length-1][RES_EMPL_ID] = employee_id;

            // print confirmation of reservation
            System.out.println("Reservation made:");
            for(int i = 0; i<RESERVATION_ELEMENT_COUNT; i++){
                System.out.println(temp_array[roomData.length][i]);
            }
            System.out.println(); //blank line

            // return the updated room data
            return temp_array;
        } else {
            // if room does not exist, inform the user
            if(!room_exists){
                System.out.println("Room No. "+room_num+" does not exist.");
                System.out.println(); //blank line
            }
            // return the original room data
            return roomData;
        }
    }
    
    /*
    Programmer: Mansour Abdelsalam
    Method: deleteReservation
    -----
    Parameters:
    String[][] roomData - 2d array containing room information
    int userID - the user id of the employee making the reservation
    -----
    Returns:
    String[][] temp_array - the temporary array made used to make edits roomData
    -----
    Description: Asks the user for a room number and edits all elements for that room's roomData other than room number to be -1 (null value). 
    */
    public static String[][] deleteReservation(String[][] roomData, int userID){
        // create a scanner for user input
        Scanner scan = new Scanner(System.in);

        // initialize variables for room number validation, date validation, reservation validation
        boolean valid_room_num = false;
        boolean valid_date = false;
        boolean valid_reservation = false;
        String[][] temp_array;
        int room_num = 0;
        int month = 0;
        int day = 0;
        int year = 0;
        StringBuilder date = new StringBuilder();
        int room_position = 0;

        // prompt user to enter room number to be deleted
        System.out.println("Enter the room number of the reservation to be deleted.");
        do{
            try{
                System.out.print("Room #: ");
                room_num = scan.nextInt();
                System.out.println(); //blank line
                valid_room_num = true;
            } catch(InputMismatchException e){
                System.out.println("Invalid input. Please enter numerical values.");
                scan.next(); //clear invalid input
                System.out.println(); //blank line
                System.out.println("Reenter room number:");
            }
        } while(!valid_room_num);

        // prompt user to enter the date of the reservation to be deleted
        System.out.println("Enter the date of the reservation to be deleted.");
        do{
            try{
                System.out.print("Month: ");
                month = scan.nextInt();
                System.out.print("Day: ");
                day = scan.nextInt();
                System.out.print("Year: ");
                year = scan.nextInt();

                // validate the entered date
                if (((month <= 12) && (month >= 1)) && ((day <= 31) && (day >= 1))){
                    date.append(month).append("/").append(day).append("/").append(year);
                    valid_date = true;
                } else {
                    System.out.println("Invalid date entered.");
                    System.out.println(); //blank line
                    System.out.println("Reenter date:");
                }
            } catch(InputMismatchException e){
                System.out.println("Invalid input. Please enter numerical values.");
                scan.next(); //clear invalid input
                System.out.println(); //blank line
                System.out.println("Reenter date:");
            }
        } while(!valid_date);
        System.out.println(); //blank line
        scan.nextLine(); //get rid of new line in input stream

        // check if the reservation exists
        for(int i = 0; i<roomData.length; i++){
            if(room_num == Integer.parseInt(roomData[i][RES_ROOM])){
                if(date.toString().equals(roomData[i][RES_DATE])){
                    valid_reservation = true;
                    room_position = i;
                    System.out.printf("Customer %s %s has booked Room No. %s for the date %s.\n",roomData[i][RES_FNAME],roomData[i][RES_LNAME],roomData[i][RES_ROOM],roomData[i][RES_DATE]);
                    System.out.println(); //blank line
                }
            }
        }

        // if the reservation is valid, delete it
        if (valid_reservation) {
            temp_array = new String[roomData.length][RESERVATION_ELEMENT_COUNT];

            // copy room data to temporary array
            for(int i = 0; i<roomData.length; i++){
                System.arraycopy(roomData[i], 0, temp_array[i], 0, RESERVATION_ELEMENT_COUNT);
            }

            // set reservation details to -1 (null value)
            for(int i = 1; i<RESERVATION_ELEMENT_COUNT; i++){
                temp_array[room_position][i] = String.valueOf(-1);
            }

            System.out.println("Reservation deleted.");
            System.out.println(); //blank line

            // return the updated room data
            return temp_array;
        } else {
            System.out.println("No reservation found for this room number and date.");
            System.out.println(); //blank line
            // return the original room data
            return roomData;
        }
    }
    
    /*
    Programmer: Mansour Abdelsalam
    Method: changeReservation
    -----
    Parameters:
    String[][] roomData - 2d array containing room information
    int userID - the user id of the employee making the reservation
    -----
    Returns:
    String[][] temp_array - the temporary array made used to make edits roomData
    -----
    Description: Indexes to find the indicated reservation by room number and date, and then prompts the user to choose to change either room number, date reserved, or customer name. 
    The method will check if the new room number or date is available and will let the user know if it is not available.
    */
    public static String[][] changeReservation(String[][] roomData, int userID){
        // create a scanner for user input
        Scanner scan = new Scanner(System.in);

        // initialize variables for various validation checks
        boolean valid_room_num = false;
        boolean valid_date = false;
        boolean valid_reservation_found = false;
        boolean valid_reservation_made = false;
        boolean invalid_reservation = false;
        String[][] temp_array;
        int room_num = 0;
        int month = 0;
        int day = 0;
        int year = 0;
        StringBuilder date = new StringBuilder();
        String first_name = "";
        String last_name = "";
        String employee_id = "";
        int room_position = 0;

        // create a temporary array to hold room data
        temp_array = new String[roomData.length][RESERVATION_ELEMENT_COUNT];

        // prompt user to enter room number to be changed
        System.out.println("Enter the room number of the reservation to be changed.");
        do{
            try{
                // attempt to read room number
                System.out.print("Room #: ");
                room_num = scan.nextInt();
                System.out.println(); //blank line
                valid_room_num = true;
            } catch(InputMismatchException e){
                // handle invalid input
                System.out.println("Invalid input. Please enter numerical values.");
                scan.next(); //clear invalid input
                System.out.println(); //blank line
                System.out.println("Reenter room number:");
            }
        } while(!valid_room_num);

        // prompt user to enter the date of the reservation to be changed
        System.out.println("Enter the date of the reservation to be changed.");
        do{
            try{
                // attempt to read date
                System.out.print("Month: ");
                month = scan.nextInt();
                System.out.print("Day: ");
                day = scan.nextInt();
                System.out.print("Year: ");
                year = scan.nextInt();

                // validate date
                if (((month <= 12) && (month >= 1)) && ((day <= 31) && (day >= 1))){
                    date.append(month).append("/").append(day).append("/").append(year);
                    valid_date = true;
                } else {
                    // handle invalid date
                    System.out.println("Invalid date entered.");
                    System.out.println(); //blank line
                    System.out.println("Reenter date:");
                }
            } catch(InputMismatchException e){
                // handle invalid input
                System.out.println("Invalid input. Please enter numerical values.");
                scan.next(); //clear invalid input
                System.out.println(); //blank line
                System.out.println("Reenter date:");
            }
        } while(!valid_date);
        System.out.println(); //blank line
        scan.nextLine(); //get rid of new line in input stream

        // check if the reservation exists
        for(int i = 0; i<roomData.length; i++){
            if(room_num == Integer.parseInt(roomData[i][RES_ROOM])){
                if(date.toString().equals(roomData[i][RES_DATE])){
                    valid_reservation_found = true;
                    room_position = i;
                    // print existing reservation details
                    System.out.println("Room #: "+roomData[i][RES_ROOM]);
                    System.out.println("Date reserved: "+roomData[i][RES_DATE]);
                    System.out.println("First name: "+roomData[i][RES_FNAME]);
                    System.out.println("Last name: "+roomData[i][RES_LNAME]);
                    System.out.println(); //blank line
                }
            }
        }

        // if the reservation is found, proceed with changes
        if (valid_reservation_found) {

            // reset variables for new reservation details
            room_num = 0;
            month = 0;
            day = 0;
            year = 0;
            date = new StringBuilder();

            // prompt user to enter new reservation details
            System.out.println("Enter the new information for this reservation.");

            // read and validate new room number
            valid_room_num = false;
            System.out.println("New Room #: ");
            do{
                try{
                    System.out.print("Room #: ");
                    room_num = scan.nextInt();
                    valid_room_num = true;
                } catch(InputMismatchException e){
                    System.out.println("Invalid input. Please enter numerical values.");
                    scan.next(); //clear invalid input
                    System.out.println(); //blank line
                    System.out.println("Reenter room number:");
                }
            } while(!valid_room_num);
            System.out.println();

            // read and validate new date
            valid_date = false;
            System.out.println("New date reserved: ");
            do{
                try{
                    System.out.print("Month: ");
                    month = scan.nextInt();
                    System.out.print("Day: ");
                    day = scan.nextInt();
                    System.out.print("Year: ");
                    year = scan.nextInt();

                    // validate date
                    if (((month <= 12) && (month >= 1)) && ((day <= 31) && (day >= 1))){
                        date.append(month).append("/").append(day).append("/").append(year);
                        valid_date = true;
                    } else {
                        System.out.println("Invalid date entered.");
                        System.out.println(); //blank line
                        System.out.println("Reenter date:");
                    }
                } catch(InputMismatchException e){
                    System.out.println("Invalid input. Please enter numerical values.");
                    scan.next(); //clear invalid input
                    System.out.println(); //blank line
                    System.out.println("Reenter date:");
                }
            } while(!valid_date);
            System.out.println(); //blank line
            scan.nextLine(); //get rid of new line in input stream

            // read new client name
            System.out.println("New first name: ");
            first_name = scan.nextLine();
            System.out.println(); //blank line

            System.out.println("New last name: ");
            last_name = scan.nextLine();
            System.out.println(); //blank line

            // format and print user id
            employee_id = String.format("%06d", userID);
            System.out.println("Your user ID is: "+employee_id);
            System.out.println();

            // check if new reservation details are valid
            // valid_date = true;
            valid_room_num = false;

            for(int i = 0; i<roomData.length; i++){
                if(room_num == Integer.parseInt(roomData[i][RES_ROOM])){
                    valid_room_num = true;
                    if (i != room_position){
                        if(date.toString().equals(roomData[i][RES_DATE])){
                            valid_date = false;
                        }
                    }
                }
            }

            // if new reservation details are valid, proceed with changes
            if(valid_date && valid_room_num){
                valid_reservation_made = true;
            }

            // if new reservation is valid, update room data
            if(valid_reservation_made){
                for(int i = 0; i<roomData.length; i++){
                    System.arraycopy(roomData[i], 0, temp_array[i], 0, roomData.length);
                }

                // update reservation details
                temp_array[room_position][RES_ROOM] = String.valueOf(room_num);
                temp_array[room_position][RES_DATE] = date.toString();
                temp_array[room_position][RES_FNAME] = first_name;
                temp_array[room_position][RES_LNAME] = last_name;
                temp_array[room_position][RES_EMPL_ID] = employee_id;

                // print confirmation of reservation change
                System.out.println("Reservation changed:");
                for(int i = 0; i<RESERVATION_ELEMENT_COUNT; i++){
                    System.out.println(temp_array[room_position][i]);
                }
                System.out.println(); //blank line
            } else {
                // handle invalid reservation details
                if(!valid_room_num){
                    System.out.println("Room No. "+room_num+" does not exist.");
                } else {
                    System.out.println("Room No."+room_num+" is already taken on "+date+".");
                }
                System.out.println();
                invalid_reservation = true;
            }

        } else {
            // handle reservation not found
            invalid_reservation = true;
            System.out.println("No reservation found for this room number and date.");
            System.out.println();
        }

        // return updated room data if reservation change was successful, otherwise return original room data
        if(!invalid_reservation){
            return temp_array;
        } else {
            return roomData;
        }
    }


    /*
    Programmer: Mansour Abdelsalam
    Method: changePin
    -----
    Parameters:
    String[][] accountData - 2d array containing account information
    -----
    Returns:
    void
    -----
    Description: Prompts the user for a new pin to replace their old one.
    */
    public static void changePin(String[][] accountData, int userID){
        // create a scanner for user input
        Scanner scan = new Scanner(System.in);

        // initialize variables for pin validation, employee id, new pin, and account position
        boolean valid_pin = false;
        String employee_id = "";
        String new_pin = "";
        int account_position = 0;

        // format the user id to a 6-digit string
        employee_id = String.format("%06d", userID);

        // loop through the account data to find the account position of the user
        for(int i = 0; i<accountData.length; i++){
            if(accountData[i][ACCOUNT_ID_INDEX].equals(employee_id)){
                account_position = i;
            }
        }

        // print the user id and current pin
        System.out.println("Your user ID is: "+employee_id);
        System.out.println("Your current PIN is: "+accountData[account_position][ACCOUNT_PIN_INDEX]);
        System.out.println(); //blank line

        // prompt the user to enter a new pin
        System.out.println("Enter a new 4-digit PIN:");
        do{
            System.out.print("Pin: ");
            new_pin = scan.nextLine();

            // validate the new pin
            if((new_pin.length() == 4) && (new_pin.matches("\\d{4}"))){
                valid_pin = true;
            } else {
                // handle invalid pin
                System.out.println("Invalid PIN. Please enter a 4-digit number.");
                System.out.println(); //blank line
                System.out.println("Reenter PIN:");
            }
        } while(!valid_pin);
        System.out.println();

        // update the pin in the account data
        accountData[account_position][ACCOUNT_PIN_INDEX] = new_pin;

        // print confirmation of pin change
        System.out.println("PIN successfully changed.");
        System.out.println("Your user ID is: "+employee_id);
        System.out.println("Your new PIN is: "+accountData[account_position][ACCOUNT_PIN_INDEX]);
        System.out.println(); //blank line
    }
}



