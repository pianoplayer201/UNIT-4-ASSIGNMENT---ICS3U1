/*
djfjsjgsfjkj
*/
import java.io.*;
public class HotelSystem {

    //GLOBAL CONSTANTS
    static final int ACCOUNT_ID_INDEX = 0;
    static final int ACCOUNT_FIRSTNAME_INDEX = 1;
    static final int ACCOUNT_LASTNAME_INDEX = 2;
    static final int ACCOUNT_PIN_INDEX = 3;
    static final int ACCOUNT_ELEMENT_COUNT = 4;
    static final int ROOM_NUMBER_INDEX = 0;
    static final int RESERVATION_DATE_INDEX = 1;
    static final int CUSTOMER_FIRSTNAME_INDEX = 2;
    static final int CUSTOMER_LASTNAME_INDEX = 3;
    static final int REGISTRAR_ID_INDEX = 4;
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
    Method: userLogout
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
    Description: This method logs the user out of the system by writing all reservation and account data to their
    respective files. The user should return to the login screen after via the main method.
    */
    /*
    Method: userLogout
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
    Description: This method logs the user out of the system by writing all reservation and account data to their
    respective files. The user should return to the login screen after via the main method.
    */
    public static void userLogout(String reservationPath, String accountPath, String[][] reservationData, String[][] accountData) {
        //Declarations
        BufferedWriter file;

        //Logging out Message
        System.out.println("LOGGING OUT...");

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
}
