package modules.data;

import modules.entity.Admin;

import java.io.*;
import java.util.Scanner;
import java.util.List;
import java.util.ArrayList;
import java.util.StringTokenizer;
import java.nio.file.Path;
import java.nio.file.Paths;

public class DataBase {
    private static final String SEPARATOR = "|";
    private static final String VALUESEPARATOR = "=";
    private static final String DIR = "/Users/zhangxinyi/DMAL/Cz2002_MOBLIMA/Cz2002_MOBLIMA/dataFiles/";
    //TODO: Changed the absolute path to relative
    public static ArrayList readAdminList(String filename) throws FileNotFoundException {
        // read String from text file
        ArrayList stringArray = (ArrayList)read(DIR + filename);
        ArrayList alr = new ArrayList<Admin>();// to store Professors data

        for (int i = 0 ; i < stringArray.size() ; i++) {
            String st = (String)stringArray.get(i);
            // get individual 'fields' of the string separated by SEPARATOR
            StringTokenizer star = new StringTokenizer(st , SEPARATOR);	// pass in the string to the string tokenizer using delimiter ","

            int  id = Integer.parseInt(star.nextToken().trim().split(VALUESEPARATOR)[1]);	// first token
            String  username = star.nextToken().trim().split(VALUESEPARATOR)[1];	// second token
            String  password = star.nextToken().trim().split(VALUESEPARATOR)[1];	// third token
            Admin admin = new Admin(id, username, password);
            // add to Professors list
            alr.add(admin) ;
        }
        return alr ;
    }

    public static List read(String fileName) throws FileNotFoundException {
        List data = new ArrayList() ;
        Path currentDir = Paths.get(".");
        Scanner scanner = new Scanner(new FileInputStream(fileName));
        try {
            while (scanner.hasNextLine()){
                data.add(scanner.nextLine());
            }
        }
        finally{
            scanner.close();
        }
        return data;
    }
    // getAllMovie, getAllCinema, ....
    // setMovie, setBooking, ...
}
