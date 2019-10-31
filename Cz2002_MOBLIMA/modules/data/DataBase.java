package modules.data;
//package data;

import modules.entity.BaseEntity;
import modules.entity.Cineplex;
import modules.entity.movie.*;

import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.util.*;
import java.nio.file.Path;
import java.nio.file.Paths;

public class DataBase {
    private static final String SEPARATOR = "|";
    private static final String VALUESEPARATOR = "=";
    private static final String DIR = "Cz2002_MOBLIMA/dataFiles/";

    public static ArrayList readCineList(String filename) throws FileNotFoundException {
        //TODO: Remove this method, use readList instead
        ArrayList stringArray = (ArrayList)readFile(DIR + filename);
        ArrayList alr = new ArrayList<Cineplex>();
        for (int i = 0 ; i < stringArray.size() ; i++) {
            String st = (String)stringArray.get(i);
            StringTokenizer star = new StringTokenizer(st , SEPARATOR);	// pass in the string to the string tokenizer using delimiter ","
            int  id = Integer.parseInt(star.nextToken().trim().split(VALUESEPARATOR)[1]);
            String  cineplexname = star.nextToken().trim().split(VALUESEPARATOR)[1];
            Cineplex cineplex = new Cineplex(id, cineplexname);
            alr.add(cineplex) ;
        }
        return alr ;
    }

    public static ArrayList readDirectorList(String filename) throws FileNotFoundException {
        //TODO: Remove this method, use readList instead
        ArrayList stringArray = (ArrayList)readFile(DIR + filename);
        ArrayList alr = new ArrayList<Director>();
        for (int i = 0 ; i < stringArray.size() ; i++) {
            String st = (String)stringArray.get(i);
            StringTokenizer star = new StringTokenizer(st , SEPARATOR);	// pass in the string to the string tokenizer using delimiter ","
            int  id = Integer.parseInt(star.nextToken().trim().split(VALUESEPARATOR)[1]);
            String  name = star.nextToken().trim().split(VALUESEPARATOR)[1];
            String rawInMovie = star.nextToken().trim().split(VALUESEPARATOR)[1];
            String splittedInMovie[] = rawInMovie.split(",");
            ArrayList<String> inMovie = new ArrayList<String>( Arrays.asList(splittedInMovie) );
            Director director = new Director(id, name, inMovie);
            alr.add(director) ;
        }
        return alr ;
    }

    public static ArrayList readStatusList(String filename) throws FileNotFoundException {
        //TODO: Remove this method, use readList instead
        ArrayList stringArray = (ArrayList) readFile(DIR + filename);
        ArrayList alr = new ArrayList<ShowStatus>();
        for (int i = 0; i < stringArray.size(); i++) {
            String st = (String) stringArray.get(i);
            StringTokenizer star = new StringTokenizer(st, SEPARATOR);    // pass in the string to the string tokenizer using delimiter ","
            int id = Integer.parseInt(star.nextToken().trim().split(VALUESEPARATOR)[1]);
            String name = star.nextToken().trim().split(VALUESEPARATOR)[1];
            ShowStatus showStatus = new ShowStatus(id, name);
            alr.add(showStatus);
        }
        return alr;
    }

    public static ArrayList readList(String filename, Class<? extends BaseEntity> classObject) throws FileNotFoundException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        ArrayList stringArray = (ArrayList)readFile(DIR + filename);
        ArrayList alr = new ArrayList<>();
        for (int i = 0 ; i < stringArray.size() ; i++) {
            String st = (String)stringArray.get(i);
            StringTokenizer star = new StringTokenizer(st , SEPARATOR);
            ArrayList<String> paramList = new ArrayList<>();
            while (star.hasMoreElements()){
                paramList.add(star.nextToken().trim().split(VALUESEPARATOR)[1]);
            }
            alr.add(classObject.getConstructor(ArrayList.class).newInstance(paramList)) ;
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

    public static void setData(String fileName, BaseEntity entityToUpdate) throws FileNotFoundException {
        //TODO: update the entry accordingly if id exist. if no then insert new
    }

    public static void deleteData(String fileName, BaseEntity entityToDelete) throws FileNotFoundException {
        //TODO: delete the entry accordingly if id exist
    }
}
