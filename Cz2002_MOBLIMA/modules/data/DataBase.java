package modules.data;

import modules.entity.Admin;
import modules.entity.BaseEntity;
import modules.entity.movie.Actor;
import modules.entity.movie.Movie;

import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.util.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.lang.reflect.Constructor;

public class DataBase {
    private static final String SEPARATOR = "|";
    private static final String VALUESEPARATOR = "=";
    private static final String DIR = "/Users/zhangxinyi/DMAL/Cz2002_MOBLIMA/Cz2002_MOBLIMA/dataFiles/";
    //TODO: Changed the absolute path to relative
    public static ArrayList readAdminList(String filename) throws FileNotFoundException {
        //TODO: make it more general so that we don't have to write readAdminList, readCinemaList, readMovieList...
        ArrayList stringArray = (ArrayList)readFile(DIR + filename);
        ArrayList alr = new ArrayList<Admin>();
        for (int i = 0 ; i < stringArray.size() ; i++) {
            String st = (String)stringArray.get(i);
            StringTokenizer star = new StringTokenizer(st , SEPARATOR);	// pass in the string to the string tokenizer using delimiter ","
            int  id = Integer.parseInt(star.nextToken().trim().split(VALUESEPARATOR)[1]);	// first token value
            String  username = star.nextToken().trim().split(VALUESEPARATOR)[1];	// second token value
            String  password = star.nextToken().trim().split(VALUESEPARATOR)[1];	// third token value
            Admin admin = new Admin(id, username, password);
            // add to Professors list
            alr.add(admin) ;
        }
        return alr ;
    }
    public static ArrayList readMovieList(String filename) throws FileNotFoundException {
        //TODO: make it more general so that we don't have to write readAdminList, readCinemaList, readMovieList...
        ArrayList stringArray = (ArrayList)readFile(DIR + filename);
        ArrayList alr = new ArrayList<Movie>();
        for (int i = 0 ; i < stringArray.size() ; i++) {
            String st = (String)stringArray.get(i);
            StringTokenizer star = new StringTokenizer(st , SEPARATOR);	// pass in the string to the string tokenizer using delimiter ","
            int  id = Integer.parseInt(star.nextToken().trim().split(VALUESEPARATOR)[1]);
            String  name = star.nextToken().trim().split(VALUESEPARATOR)[1];
            String  descprition = star.nextToken().trim().split(VALUESEPARATOR)[1];
            Double  totalScore = Double.parseDouble(star.nextToken().trim().split(VALUESEPARATOR)[1]);
            int numOfPersonRating = Integer.parseInt(star.nextToken().trim().split(VALUESEPARATOR)[1]);
            Movie movie = new Movie(id, name, descprition,totalScore, numOfPersonRating);
            alr.add(movie) ;
        }
        return alr ;
    }

    public static ArrayList readActorList(String filename) throws FileNotFoundException {
        //TODO: make it more general so that we don't have to write readAdminList, readCinemaList, readMovieList...
        ArrayList stringArray = (ArrayList)readFile(DIR + filename);
        ArrayList alr = new ArrayList<Actor>();
        for (int i = 0 ; i < stringArray.size() ; i++) {
            String st = (String)stringArray.get(i);
            StringTokenizer star = new StringTokenizer(st , SEPARATOR);	// pass in the string to the string tokenizer using delimiter ","
            int  id = Integer.parseInt(star.nextToken().trim().split(VALUESEPARATOR)[1]);
            String  name = star.nextToken().trim().split(VALUESEPARATOR)[1];
            String rawInMovie = star.nextToken().trim().split(VALUESEPARATOR)[1];
            String splittedInMovie[] = rawInMovie.split(",");
            ArrayList<String> inMovie = new ArrayList<String>( Arrays.asList(splittedInMovie) );
            Actor actor = new Actor(id, name, inMovie);
            alr.add(actor) ;
        }
        return alr ;
    }

    public static List readFile(String fileName) throws FileNotFoundException {
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
