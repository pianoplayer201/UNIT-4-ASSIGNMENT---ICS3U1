/*
djfjsjgsfjkj
*/
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

    public static void main(String[] args) {

        //CONSTANTS
        final String ACCOUNT_FILE = "account.txt";
        final String RESERVATION_FILE = "reservation.txt";
        final int ADMIN_ID = 000000;

        //Variables
        int userID;
        boolean loggedIn = false;
        boolean isAdmin = false;
        String[][] roomData, accountData;

        //MAIN WHILE LOOP
        while(true){
            //Read in Data
            accountData = readAccounts(ACCOUNT_FILE);
            roomData = readRoomInfo(RESERVATION_FILE);

            //Login
            userID = userLogin(accountData);
            loggedIn = true;
            if(userID == ADMIN_ID);{
                isAdmin = true;
            }

            //Logged in Menus
            while(loggedIn){
                int selection = reservationMenu(accountData, roomData, userID, isAdmin);
                switch(selection){
                    case 0:
                        //LOGOUT
                        loggedIn = false;
                        break;
                    case 1:
                        //SEARCH AVAILABLE RESERVATIONS BY DATE
                        searchAvailableByDate(roomData);
                        break;
                    case 2:
                        //SEARCH BOOKED ROOMS BY NAME
                        searchReservationByDate(roomData);
                        break;
                    case 3:
                        //SEARCH BOOKED ROOMS BY NAME
                        searchByName(roomData);
                        break;
                    case 4:
                        //MAKE RESERVATION
                        roomData = makeReservation(roomData, userID);
                        break;
                    case 5:
                        //CANCEL A RESERVATION
                        //NOT IMPLEMENETED YET!!!
                        System.out.println("NOT IMPLEMENTED YET");
                        break;
                    case 6:
                        //Change the Reservation
                        System.out.println("NOT YET IMPLEMENTED");
                        break;
                    case 7:
                        //CHANGE THE PIN
                        //changePin(accountData, userID);
                        break;
                    case 8:
                        //Add Employee
                        if(isAdmin){
                            accountData = addEmployee(accountData);
                        }
                        break;
                    case 9:
                        //Delete Employee
                        if(isAdmin){
                            accountData = deleteEmployee(accountData);
                        }
                        break;
                    case 10:
                        //Make Rooms
                        if(isAdmin){
                            roomData = makeRoom(roomData);
                        }
                        break;
                    case 11:
                        //Delete Rooms
                        if(isAdmin){
                            //NOT YET IMPLEMENTED
                            System.out.println("NOT YET IMPLEMENTED");
                        }
                        break;
                }
            }

            //Logout & WriteFile
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

            //COUNT NUM OF LINES
            for (file = new BufferedReader(new FileReader(filepath)); file.readLine() != null; ++numOfLines) {
                //ALL THE COUNTING IS DONE IN THE HEADER, NO BODY REQUIRED!
            }

            //INIT DATA[][] and reset BufferedReader to top of file.
            data = new String[numOfLines][ACCOUNT_ELEMENT_COUNT];
            file.close();
            file = new BufferedReader(new FileReader(filepath));

            //Read File, Fill out Data
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

            //COUNT NUM OF LINES
            for (file = new BufferedReader(new FileReader(filepath)); file.readLine() != null; ++numOfLines) {
                //ALL THE COUNTING IS DONE IN THE HEADER, NO BODY REQUIRED!
            }

            //INIT DATA[][] and reset BufferedReader to top of file.
            data = new String[numOfLines][RESERVATION_ELEMENT_COUNT];
            file.close();
            file = new BufferedReader(new FileReader(filepath));

            //Read File, Fill out Data
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
        //Declarations
        BufferedWriter file;

        //Logging out Message
        System.out.println("SAVING DATA...");

        //Write Reservations
        try {
            file = new BufferedWriter(new FileWriter(reservationPath));

            for (int i = 0; i < reservationData.length; i++) {
                for (int j = 0; j < RESERVATION_ELEMENT_COUNT; j++) {
                    file.write(reservationData[i][j] + ',');
                }
                file.write("\n");
            }
            file.close();
        } catch (IOException e) {
            System.out.println(e);
        }

        //Write Account Data
        try {
            file = new BufferedWriter(new FileWriter(reservationPath));

            for (int i = 0; i < reservationData.length; i++) {
                for (int j = 0; j < RESERVATION_ELEMENT_COUNT; j++) {
                    file.write(reservationData[i][j] + ',');
                }
                file.write("\n");
            }
            file.close();
        } catch (IOException e) {
            System.out.println(e);
        }
        //Write Reservations
        try {
            file = new BufferedWriter(new FileWriter(accountPath));

            for (int i = 0; i < accountData.length; i++) {
                for (int j = 0; j < ACCOUNT_ELEMENT_COUNT; j++) {
                    file.write(accountData[i][j] + ',');
                }
                file.write("\n");
            }
            file.close();
        } catch (IOException e) {
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
        //Declarations
        Scanner scan = new Scanner(System.in);

        String first_name = "";
        String last_name = "";

        System.out.println("Enter the client's information.");
        System.out.print("First name: ");
        first_name = scan.nextLine();
        System.out.print("Last name: ");
        last_name = scan.nextLine();

        for (int i = 0; i < roomData.length; i++) {
            if (((roomData[i][ACCOUNT_FIRSTNAME_INDEX]).equalsIgnoreCase(first_name)) && ((roomData[i][RES_LNAME]).equalsIgnoreCase(last_name))) {
                System.out.println("Room #: " + roomData[i][RES_ROOM]);
                System.out.println("Date reserved: " + roomData[i][RES_DATE]);
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
    public static void searchAvailableByDate(String[][] roomData) {
        Scanner scan = new Scanner(System.in);

        boolean valid_date = false;
        boolean room_found = false;
        int month = 0;
        int day = 0;
        int year = 0;
        String date = "";

        System.out.println("Enter the date to check.");
        do {
            System.out.print("Month: ");
            month = scan.nextInt();
            System.out.print("Day: ");
            day = scan.nextInt();
            System.out.print("Year: ");
            year = scan.nextInt();

            if (((month <= 12) && (month >= 1)) && ((day <= 31) && (day >= 1))) {
                date += month + "/" + day + "/" + year;
                valid_date = true;
            } else {
                System.out.println("Invalid date entered.");
                System.out.println(); //blank line
                System.out.println("Reenter date:");
            }
        } while (!valid_date);

        if (valid_date) {
            System.out.println(); //blank line
            System.out.println("Available rooms on " + date + ":");

            for (int i = 0; i < roomData.length; i++) {
                if (!roomData[i][RES_DATE].equals(date)) {
                    room_found = true;
                    System.out.println("Room #: " + roomData[i][RES_ROOM]);
                }
            }
            if (!room_found) {
                System.out.println("No rooms available on this date.");
                System.out.println(); //blank line
            }
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
    public static void searchReservationByDate(String[][] roomData) {
        Scanner scan = new Scanner(System.in);

        boolean valid_date = false;
        boolean room_found = false;
        int month = 0;
        int day = 0;
        int year = 0;
        String date = "";

        System.out.println("Enter the date to check.");
        do {
            System.out.print("Month: ");
            month = scan.nextInt();
            System.out.print("Day: ");
            day = scan.nextInt();
            System.out.print("Year: ");
            year = scan.nextInt();

            if (((month <= 12) && (month >= 1)) && ((day <= 31) && (day >= 1))) {
                date += month + "/" + day + "/" + year;
                valid_date = true;
            } else {
                System.out.println("Invalid date entered.");
                System.out.println(); //blank line
                System.out.println("Reenter date:");
            }
        } while (!valid_date);

        if (valid_date) {
            System.out.println(); //blank line
            System.out.println("Rooms reserved on " + date + ":");

            for (int i = 0; i < roomData.length; i++) {
                if (roomData[i][RES_DATE].equals(date)) {
                    room_found = true;
                    System.out.println("Room #: " + roomData[i][RES_ROOM]);
                    System.out.println("Customer name: " + roomData[i][RES_FNAME] + " " + roomData[i][RES_LNAME]);
                    System.out.println(); //blank line
                }
            }
            if (!room_found) {
                System.out.println("No rooms are reserved on this date.");
                System.out.println(); //blank line
            }
        }

    }

    public static int userLogin(String[][] accountData) {
        Scanner sc = new Scanner(System.in);
        int userID;
        boolean userVerify;
        int userPin;
        int accountIndex = 0;

        do {
            userVerify = false;
            System.out.print("Enter ID: ");
            userID = sc.nextInt();
            for (int i = 0; i <= accountData.length - 1; i++) {
                if (Integer.parseInt(accountData[i][0]) == (userID)) {
                    userVerify = true;
                    accountIndex += i;
                }
            }
            while (!userVerify) {
                System.out.print("Invalid ID entered, enter new ID: ");
                userID = sc.nextInt();

                for (int i = 0; i <= accountData.length - 1; i++) {
                    if (Integer.parseInt(accountData[i][0]) == (userID)) {
                        userVerify = true;
                        accountIndex += i;
                    }
                }
            }

            System.out.print("enter pin: ");
            userPin = sc.nextInt();

            while (Integer.parseInt(accountData[accountIndex][3]) != (userPin) && userPin != 0) {
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
        String[][] updatedData = new String[accountData.length + 1][ACCOUNT_ELEMENT_COUNT];


        //USERID Prompt and Validation Loop:
        while (!inputValid) {
            System.out.print("Enter the new Employee's ID (6 digits), or enter 0 to go back to menu options.\n > ");
            try {
                input = sc.nextLine();
                //User quit
                if (Integer.parseInt(input) == 0) {
                    inputValid = true;
                    quit = true;
                }
                //Valid ID
                else if (input.length() == VALID_ID_LENGTH) {

                    inputValid = true;
                    //Check for Duplicate Account
                    for (int i = 0; i < accountData.length; i++) {
                        if (accountData[i][ACCOUNT_ID_INDEX].equals(input)) {
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

        //Copy accountData to temp
        if (!quit) {
            for (int i = 0; i < accountData.length; i++) {
                System.arraycopy(accountData[i], 0, updatedData[i], 0, ACCOUNT_ELEMENT_COUNT);
            }
        } else {
            updatedData = accountData;
        }

        //Fill Out All fields for new employee using further prompts.
        if (!quit) {

            updatedData[accountData.length][ACCOUNT_ID_INDEX] = input;
            System.out.print("Please enter Employee First Name\n > ");
            updatedData[accountData.length][ACCOUNT_FIRSTNAME_INDEX] = sc.nextLine();
            System.out.print("Please enter Employee Last Name\n > ");
            updatedData[accountData.length][ACCOUNT_LASTNAME_INDEX] = sc.nextLine();
            System.out.print("Please set a 4 digit account pin.\n > ");

            //Input Validation for Pin
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
    public static String[][] deleteEmployee(String[][] accountData) {
        //Declarations
        final String DELETE_FLAG = "DELETE";
        final int VALID_ID_LENGTH = 6;
        String input = null;
        boolean inputValid = false;
        boolean quit = false;
        boolean deletePass = false;

        Scanner sc = new Scanner(System.in);
        //accountData[][] is later copied into temp[][] via the for loop during input.
        String[][] updatedData = new String[accountData.length - 1][ACCOUNT_ELEMENT_COUNT];


        //USERID Prompt and Validation Loop:
        while (!inputValid) {
            System.out.print("Enter the 6 DIGIT Employee ID to delete, or enter 0 to go back to menu options.\n > ");
            try {
                input = sc.nextLine();
                //Enters Valid UserID
                if (input.length() == VALID_ID_LENGTH) {
                    Integer.parseInt(input);
                    //Check for Valid Account
                    for (int i = 0; i < accountData.length; i++) {
                        if (accountData[i][ACCOUNT_ID_INDEX].equals(input)) {
                            accountData[i][ACCOUNT_ID_INDEX] = DELETE_FLAG;
                            inputValid = true;
                        }
                    }
                }
                //Enters quit number of 0
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
            //Copy accountData to temp
            for (int i = 0; i < accountData.length; i++) {
                //Skips deleted line.
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

        //Assign to accountData and output confirmation
        System.out.println("CHANGES SAVED");
        accountData = updatedData;
        return updatedData;
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
    public static String[][] makeReservation(String[][] roomData, int userID) {
        Scanner scan = new Scanner(System.in);

        boolean valid_date = false;
        boolean room_taken = false;
        String[][] temp_array;
        int room_num = 0;
        int month = 0;
        int day = 0;
        int year = 0;
        String date = "";
        String first_name = "";
        String last_name = "";
        String employee_id = "";

        System.out.println("Enter room number to be reserved.");
        System.out.print("Room #: ");
        room_num = scan.nextInt();
        System.out.println(); //blank line

        System.out.println("Enter the date to reserve.");
        do {
            System.out.print("Month: ");
            month = scan.nextInt();
            System.out.print("Day: ");
            day = scan.nextInt();
            System.out.print("Year: ");
            year = scan.nextInt();

            if (((month <= 12) && (month >= 1)) && ((day <= 31) && (day >= 1))) {
                date += month + "/" + day + "/" + year;
                valid_date = true;
            } else {
                System.out.println("Invalid date entered.");
                System.out.println(); //blank line
                System.out.println("Reenter date:");
            }
        } while (!valid_date);
        System.out.println(); //blank line
        scan.nextLine(); //get rid of new line in input stream

        for (int i = 0; i < roomData.length; i++) {
            if (room_num == Integer.parseInt(roomData[i][RES_ROOM])) {
                if (date.equals(roomData[i][RES_DATE])) {
                    room_taken = true;
                    System.out.println("This room cannot be booked on this date.");
                    System.out.printf("Customer %s %s has booked already Room No. %s for the date %s.\n", roomData[i][RES_FNAME], roomData[i][RES_LNAME], roomData[i][RES_ROOM], roomData[i][RES_DATE]);
                    System.out.println(); //blank line
                }
            }
        }

        if (!room_taken) {
            System.out.println("Enter the client's information.");
            System.out.print("First name: ");
            first_name = scan.nextLine();
            System.out.print("Last name: ");
            last_name = scan.nextLine();
            System.out.println(); //blank line

            employee_id = String.format("%06d", userID);
            System.out.println("Your user ID is: " + employee_id);
            System.out.println(); //blank line

            temp_array = new String[roomData.length + 1][RESERVATION_ELEMENT_COUNT];
            for (int i = 0; i < roomData.length; i++) {
                System.arraycopy(roomData[i], 0, temp_array[i], 0, RESERVATION_ELEMENT_COUNT);
            }
            temp_array[temp_array.length - 1][RES_ROOM] = String.valueOf(room_num);
            temp_array[temp_array.length - 1][RES_DATE] = date;
            temp_array[temp_array.length - 1][RES_FNAME] = first_name;
            temp_array[temp_array.length - 1][RES_LNAME] = last_name;
            temp_array[temp_array.length - 1][RES_EMPL_ID] = employee_id;

            System.out.println("Reservation made:");
            for (int i = 0; i < roomData.length; i++) {
                System.out.println(roomData[roomData.length - 1][i]);
            }
            System.out.println(); //blank line

            return temp_array;

        } else {
            return roomData;
        }
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
    Scanner sc = new Scanner(System.in);
    String newRoom;
    boolean roomVerify = true;
    String[][] temp_roomData;

    System.out.print("Enter new room number: ");
    newRoom = sc.nextLine();

    for (int i = 0; i <= roomData.length - 1; i++)
    {
      if (newRoom.equals(roomData[i][0]))
      {
        roomVerify = false;
      }
    }
    while (roomVerify == false)
    {
      roomVerify = true;
      System.out.print("room number already exists, enter new room number: ");
      newRoom = sc.nextLine();
      for (int i = 0; i <= roomData.length - 1; i++)
      {
        if (newRoom.equals(roomData[i][0]))
        {
          roomVerify = false;
        }
      }
    }

    temp_roomData = new String [roomData.length + 1][5];

    for (int i = 0; i <= roomData.length - 1; i++)
    {
      for (int ii = 0;ii <= 4; ii++)
      {
        temp_roomData[i][ii] = roomData[i][ii];
      }
    }

    temp_roomData[temp_roomData.length - 1][0] = newRoom;

    for (int i = 1; i <= 4; i++)
    {
      temp_roomData[temp_roomData.length - 1][i] = "-1";
    }

    System.out.println("new room: " + newRoom + ", created");

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
        //Declarations
        int selectionOption = 0;
        String userName = "";
        boolean inputValid = false;
        Scanner sc = new Scanner(System.in);

        //Find Username
        for(int i = 0; i < accountData.length; i++) {
            if (Integer.parseInt(accountData[i][ACCOUNT_ID_INDEX]) == userID) {
                userName = accountData[i][ACCOUNT_FIRSTNAME_INDEX];
            }
        }

        //List Options, Input and Validation
        while(!inputValid){
            System.out.printf("WELCOME %s, what would you like to do today?\n", userName);

            //DEFAULT OPTIONS
            System.out.print("1. Search for Available Rooms by Date\n" +
                    "2. Search for Reservations by Date\n" +
                    "3. Search for Reservations by Name\n" +
                    "4. Make a Reservation\n" +
                    "5. Cancel a Reservation\n" +
                    "6. Change a Reservation\n" +
                    "7. Change your Pin Number\n");
            //ADMIN OPTIONS
            if(isAdmin){
                System.out.print("8. Add an Employee\n" +
                        "9. Delete an Employee\n" +
                        "10. Add a Room\n" +
                        "11. Delete a Room\n");
            }
            //Logout Option
            System.out.print("0. Logout\n > ");

            //INPUT and Validation
            try {
                selectionOption = sc.nextInt();
                sc.nextLine();
                if(isAdmin){
                    if(selectionOption >= 0 && selectionOption <= 11){
                        inputValid = true;
                    }
                }
                else {
                    if(selectionOption >= 0 && selectionOption <= 7){
                        inputValid = true;
                    }
                    else{
                        inputValid = false;
                        System.out.println("INVALID OPTION");
                    }
                }
            } catch (InputMismatchException e){
                System.out.println("INPUT MUST BE A NUMBER");
            }


        }
        //Return the selected option as an int.
        return selectionOption;
    }
}



