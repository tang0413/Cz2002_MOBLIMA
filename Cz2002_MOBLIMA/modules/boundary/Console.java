package modules.boundary;

import modules.data.DataBase;
import modules.entity.BaseEntity;
import modules.entity.Cineplex;
import modules.entity.Show;
import modules.entity.Ticket;
import modules.entity.movie.Movie;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.regex.Pattern;

/**
 * This class is used to manage input and output through the console.
 * It is instantiated when entering the app and passed down by controllers.
 */
public class Console {
    /**
     * Used to get input from user. It will not be closed until the end of program.
     */
    private Scanner sc;
    /**
     * Change output message to red
     */
    private final String ANSI_RED = "\u001B[31m";
    /**
     * Change output message to Cyan
     */
    private final String ANSI_CYAN = "\u001B[36m";
    /**
     * Reset the color of output message
     */
    private final String ANSI_RESET = "\u001B[0m";
    /**
     * Regular expression to check the syntax of email
     */
    private final Pattern EMAILPATTERN = Pattern.compile("^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-z" + "A-Z]{2,7}$");

    private final SimpleDateFormat DATEFORMAT = new SimpleDateFormat("dd/mm/yyyy");
    /**
     * All available movie status
     */
    private final List<String> movieStatusList = Arrays.asList("Coming Soon", "Preview", "Now Showing", "End Of Showing");
    /**
     * Characters that surround the text repeatedly
     */
    private String styleChar = "+";

    /**
     * Creates a new Console with a ueser input scanner
     */
    public Console() {
        sc = new Scanner(System.in);
    }

    /**
     * Used to print out the most basic, non-styled message
     * @param outputMsg The string message to print out as line in console
     */
    public void log(String outputMsg) {
        System.out.println(outputMsg);
    }

    /**
     * Used to print out message in red color as warning
     * @param outputMsg The string message to print out as line in console
     */
    public void logWarning(String outputMsg) {
        System.out.println(ANSI_RED + outputMsg + ANSI_RESET);
    }

    /**
     * Used to print out message in cyan as a reminder
     * @param outputMsg The string message to print out as line in console
     */
    public void logReminder(String outputMsg) {
        System.out.println(ANSI_CYAN + outputMsg + ANSI_RESET);
    }

    /**
     * Used to print out message with styleChar surrounded on top and bottom
     * @param outputMsg The string message to print out as line in console
     */
    public void logText(String outputMsg) {
        String repeated = new String(new char[outputMsg.length()]).replace("\0", styleChar);
        System.out.println(repeated);
        System.out.println(outputMsg);
        System.out.println(repeated);
    }

    /**
     * Used to print out a ArrayList with auto-indexes as a menu for users to select from
     * @param menuItems The ArrayList of String to be printed by sequence
     */
    public void logMenu(ArrayList<String> menuItems) {
        int i = 1;
        for (String str : menuItems) {
            System.out.println(i + ". " + str);
            i ++;
        }
        System.out.println(" ");
    }

    /**
     * Similar to logMenu, but print without or without index.
     * @param menuItems The ArrayList of String to be printed by sequence
     * @param withOutIndex if equals to false, will call logMenu instead
     */
    public void logMenu(ArrayList<String> menuItems, Boolean withOutIndex) {
        if (withOutIndex){
            for (String str : menuItems) {
                System.out.println(str);
            }
            System.out.println(" ");
        } else {
            logMenu(menuItems);
        }
    }

    /**
     * This is used to print an ArrayList of String in sequence, but each element broke into lines by separator
     * Mainly used to printout movie reviews
     * @param records The ArrayList of String to be printed by sequence
     * @param separator The separator used to tokenize each elements of records
     */
    public void logWithSeparator(ArrayList<String> records, String separator) {
        for (String str : records) {
            StringTokenizer star = new StringTokenizer(str , separator);
            System.out.println(" ");
            while(star.hasMoreElements()){
                System.out.println(star.nextToken().trim());
            }
        }
        System.out.println(" ");
    }

    public void logSeatPlan(ArrayList<Ticket> ticketList, Show show){
        int i,j, sc=0,c=0, increment;
        char ch;
        String[] data = new String[100];
        for(Ticket t : ticketList) {
            if (t.getShowId() == show.getId() && t.getCineplexId() == show.getCineplexId() && t.getMovieId() == show.getMovieId()) {
                data[sc] = t.getSeats();
                sc++;
            }
        }
        sc = 0;
        int size = 14; //default for c
        char temp = show.getCinemaname().charAt(0);
        switch (temp){
            case 'a':
                size =  16;
            case 'b':
                size = 18;
        }
        int ROWS = size/2;
        int SEATS = size/2;
        increment =0;
        while(increment!=(SEATS*3))
        {
            System.out.print("=");
            increment++;
        }
        System.out.print("\n");
        System.out.print("||");

        increment =0;
        while(increment!=(SEATS))
        {
            System.out.print(" ");
            increment++;
        }
        System.out.print("SCREEN");
        increment=1;
        while(increment!=(SEATS))
        {
            System.out.print(" ");
            increment++;
        }
        System.out.print("||");
        System.out.print("\n||    ");
        increment =0;
        while(increment!=SEATS)
        {
            System.out.print((increment+1) + " ");
            increment++;
        }
        System.out.print(" ||");
        System.out.println("");
        int showseats[][] = new int [ROWS][SEATS];
        for(i=1; i <= ROWS; i++) {
            System.out.print("|| " + (char) (i + 64) + " |");
            for (j = 1; j <= SEATS; j++) {
                while (data[sc] != null) {
                    int pos = data[sc].charAt(0) - 64;
                    if (i == pos && j == (Integer.parseInt(data[sc].substring(1)))) {
                        System.out.print("X|");
                        c = 1;
                    }
                    sc++;
                }
                if (c == 0)
                    System.out.print("_|");
                c = 0;
                sc = 0;
            }
            System.out.print(" || \n");
        }

        System.out.print("||");

        increment =0;
        while(increment!=(SEATS))
        {
            System.out.print(" ");
            increment++;
        }
        System.out.print("ENTRANCE");
        increment=3;
        while(increment!=(SEATS))
        {
            System.out.print(" ");
            increment++;
        }
        System.out.print("||");
        System.out.print("\n");
        increment =0;
        while(increment!=(SEATS*3))
        {
            System.out.print("=");
            increment++;
        }
        System.out.print("\n");
    }

    /**
     * This is to print out success message and auto return to the previous page
     * A sleep time of 2.5 seconds was set to allow the users to read
     */
    public void logSuccess() throws InterruptedException {
        logReminder("Updated successfully! Returning to the previous page...");
        TimeUnit.SECONDS.sleep(2);
        return;
    }

    /**
     * This is used to get an integer from users with a lowerBound and upperBound
     * @param label The text to print out first as a instruction
     * @param lowerBound The minimum accepted value
     * @param upperBound The maximum accepted value
     * @return the valid user input integer
     */
    public int getInt(String label, int lowerBound, int upperBound) {
        while (true) {
            try {
                this.log(">> " + label + " (number): ");
                int num = sc.nextInt();
                sc.nextLine();
                if (num >= lowerBound & num <= upperBound) {
                    return num;
                } else {
                    throw new InputMismatchException("OutOfRange");
                }
            } catch (InputMismatchException e) {
                if (e.getMessage() != "OutOfRange"){
                    sc.nextLine();
                }
                this.logWarning("Invalid input! Please try again.");
            }
        }
    }

    /**
     * This is used to get a non-checked string from the user
     * @param title The text to print out first as a instruction
     * @return the user input string
     */
    public String getStr(String title) {
        this.log(">> " + title + ": ");
        return sc.nextLine();
    }

    /**
     * This is used to get a checked string from the user, which is contained in a specified array list
     * @param title The text to print out first as a instruction
     * @param validValues An ArrayList of String which contains all the valid values
     * @return a valid user input string
     */
    public String getStr(String title, ArrayList<String> validValues) {
        while (true){
            this.log(">> " + title + ": ");
            String userInput = sc.nextLine();
            if (validValues.contains(userInput)){
                return userInput;
            } else {
                this.logWarning("Invalid input! Please try again.");
            }
        }
    }

    /**
     * This is used to get a movie status from the user
     * @param title The text to print out first as a instruction
     * @return A valid user input movie type as string
     */
    public String getMovieStatus(String title){
        this.logReminder("Status: " + Arrays.toString(movieStatusList.toArray()));
        while (true){
            this.log(">> " + title + ": ");
            String userEnteredValue = sc.nextLine();
            if(movieStatusList.contains(userEnteredValue)){
                if (userEnteredValue.equals("End Of Showing")){
                    logWarning("This will exclude this movie record from the list in the future. Continue?");
                    if(getStr("Type 'YES' to continue").equals("YES")){
                        return userEnteredValue;
                    } else {
                        continue;
                    }
                }
                return userEnteredValue;
            } else {
                this.logWarning("Invalid input! Please try again.");
            }
        }
    }

    /**
     * This is to get a valid id of a existing movie which is not in "End Of Showing" status
     * @param title The text to print out first as a instruction
     * @return a valid movie id, entered by the user
     */
    public int getMovieId(String title){
        while (true){
            int movieId = getInt(title, 1, DataBase.getMaxId(Movie.class));
            try {
                Movie chosenMovie = (Movie)DataBase.getObjById(movieId, Movie.class);
                if (chosenMovie.getStatus().equals("End Of Showing")){
                    logWarning("This movie is no longer available for showing!");
                } else {
                    return movieId;
                }
            } catch (Exception e){
                logWarning("No such movie!");
            }
        }
    }

    /**
     * This is to get a valid id of a existing cineplex
     * @param title The text to print out first as a instruction
     * @return a valid cineplex id, entered by the user
     */
    public int getCineplexId(String title){
        while (true){
            int cineplexId = getInt(title, 1, DataBase.getMaxId(Movie.class));
            try {
                DataBase.getObjById(cineplexId, Cineplex.class);
                return cineplexId;
            } catch (Exception e){
                logWarning("No such cineplex!");
            }
        }
    }

    /**
     * This is to get a valid String consisting of cinema names, with comma separated in between
     * @return a valid String consisting of cinema names, with comma separated in between
     */
    public String getCinemaNames(){
        this.log(">> " + "Please enter the cinema names e.g. 'a1,b1'" + ": ");
        while (true){
            String userInput = sc.nextLine();
            String splittedNames[] = userInput.split(",");
            ArrayList<String> chosenSeats = new ArrayList<>( Arrays.asList(splittedNames));
            Boolean valid = true;
            for (String s: chosenSeats){
                if (s.matches("[a-c][1-9]")){
                    continue;
                } else {
                    valid = false;
                }
            }
            if (valid){
                return userInput;
            } else {
                this.logWarning("Invalid input! Please try again.");
            }
        }
    }

    /**
     * This is used to get a valid email from the user
     * @return A valid user input email address as string
     */
    public String getEmail() {
        this.log(">> " + "Please enter your email" + ": ");
        while (true){
            String userInput = sc.nextLine();
            if (EMAILPATTERN.matcher(userInput).matches()){
                return userInput;
            } else {
                this.logWarning("Invalid input! Please try again.");
            }
        }
    }

    /**
     * This is used to get a time in "hh:mm" format in 24 hrs
     * @return a valid time in "hh:mm" format, entered by the user
     */
    public String getTime() {
        this.log(">> " + "Please indicate a time (e.g. 14:00)" + ": ");
        while (true){
            String userInput = sc.nextLine();
            if (userInput.matches("([01]?[0-9]|2[0-3]):[0-5][0-9]")){
                return userInput;
            } else {
                this.logWarning("Invalid input! Please try again.");
            }
        }
    }

    /**
     * This is used to get a date in "dd/mm/yyyy" format
     * @return a valid date in "dd/mm/yyyy" format, entered by the user
     */
    public String getDate() {
        this.log(">> " + "Please indicate a date (e.g. 11/12/2019)" + ": ");
        while (true){
            String userInput = sc.nextLine();
            try{
                DATEFORMAT.parse(userInput);
                return userInput;
            } catch (ParseException e){
                this.logWarning("Invalid input! Please try again.");
            }
        }
    }

    /**
     * This is used to get a valid price
     * @return a valid price e.g. with format 0.00
     */
    public double getPrice() {
        while (true) {
            try {
                this.log(">> " + "Please indicate a price e.g. 11.00" + ": ");
                double num = sc.nextDouble();
                return (double) Math.round(num * 100) / 100;
            } catch (InputMismatchException e) {
                this.logWarning("Invalid input! Please try again.");
            }
        }
    }

    /**
     * This is used to get valid seat numbers according to cinema type (the first character of cinema name)
     * @param cinemaName the name of a cinema e.g. a1
     * @return the user entered seat numbers, separated with comma
     */
    public ArrayList<String> getSeat(String cinemaName) {
        String seatRegex;
        switch (cinemaName.charAt(0)){
            case 'a':
                seatRegex= "[A-H][0][0-8]";
                break;
            case 'b':
                seatRegex= "[A-I][0][0-9]";
                break;
            case 'c':
            default:
                seatRegex= "[A-G][0][0-7]";
                break;
        }
        this.log(">> " + "Please Enter Your Seat(s) Choice e.g. 'E03,E04'" + ": ");
        while (true){
            String userInput = sc.nextLine();
            String splittedSeats[] = userInput.split(",");
            ArrayList<String> chosenSeats = new ArrayList<>( Arrays.asList(splittedSeats));
            Boolean valid = true;
            for (String s: chosenSeats){
                if (s.matches(seatRegex)){
                    continue;
                } else {
                    valid = false;
                }
            }
            if (valid){
                return chosenSeats;
            } else {
                this.logWarning("Invalid input! Please try again.");
            }
        }
    }

    /**
     * Called when the App ends to close the scanner
     */
    public void destoryScanner(){
        sc.close();
    }
}
