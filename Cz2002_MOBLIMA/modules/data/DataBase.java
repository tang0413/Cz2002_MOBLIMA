package modules.data;

import modules.entity.BaseEntity;
import modules.entity.Cineplex;
import modules.entity.movie.Movie;
import modules.utils.Sorting;

import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.util.*;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * This class is used to read and write data from the txt files under dataFiles folder.
 * All methods are static so easy to use whenever needed
 */
public class DataBase {
    /**
     * A HashMap to store the read and unchanged data to save efficiency
     */
    private static HashMap<String, ArrayList> bufferList= new HashMap<>();
    /**
     * A HashMap to store the current maximum id of the entity classes e.g. Movie
     */
    private static HashMap<Class, Integer> bufferMaxIdList = new HashMap<>();
    /**
     * A separator used to tokenize the records read from txt file
     */
    private static final String SEPARATOR = "|";
    /**
     * A separator used to tokenize every equations inside the tokenized records e.g. id=1
     */
    private static final String VALUESEPARATOR = "=";
    /**
     * The relative directory where all txt files are stored
     */
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

    /**
     * This is used to read data from the txt file
     * Note if no file is changed since last read, it will check the bufferlist and return directly
     * @param filename The filename to read data from e.g. MovieList.txt
     * @param classObject The class of the objects to be created e.g. Movie.class
     * @return an ArrayList of instantiated objects of specified class from a certain file
     * @throws FileNotFoundException
     * @throws NoSuchMethodException
     * @throws IllegalAccessException
     * @throws InvocationTargetException
     * @throws InstantiationException
     */
    public static ArrayList readList(String filename, Class<? extends BaseEntity> classObject) throws FileNotFoundException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        String listName= filename.substring(0 , filename.indexOf("."));
        if (bufferList.containsKey(listName)){
            return bufferList.get(listName);
        }
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
        bufferList.put(listName, alr);
        bufferMaxIdList.put(classObject, alr.size());
        return alr ;
    }

    /**
     * This is to clear the bufferList when files are updated
     */
    public static void clearBuffer(){
        bufferList = new HashMap<>();
    }

    /**
     * This is used to read data line by line from a file
     * @param fileName The filename to read data from e.g. MovieList.txt
     * @return An ArrayList of string
     * @throws FileNotFoundException
     */
    private static List readFile(String fileName) throws FileNotFoundException {
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

    /**
     * This is used to save updated data back into files
     * @param fileName The filename to change e.g. MovieList.txt
     * @param entityToUpdate A object of BaseEntity or its subclasses, which is newly created or modified
     * @throws IOException
     */
    public static void setData(String fileName, BaseEntity entityToUpdate) throws IOException {
        String listName= fileName.substring(0 , fileName.indexOf("."));
        try {
            String idString = "id=" + entityToUpdate.getId();
            ArrayList originalFile = (ArrayList)readFile(DIR + fileName);
            Boolean found = false;
            for (int i = 0 ; i < originalFile.size() ; i++) {
                String st = (String)originalFile.get(i);
                if (st.matches("^" + idString + "\\|.*")){
                    found = true;
                    originalFile.set(i, entityToUpdate.StringlizeEntity());
                }
            }
            if (!found){
                originalFile.add(entityToUpdate.StringlizeEntity());
            }
            writeFile(fileName, originalFile);
            clearBuffer(); //TODO no need to every entity
        } catch (Exception e){
        }
    }

    /**
     * This is used to delete one record from a txt file
     * Not in use now
     * @param fileName The filename to change e.g. MovieList.txt
     * @param entityToDelete A object of BaseEntity or its subclasses, which is to be deleted from database
     * @throws FileNotFoundException
     */
    public static void deleteData(String fileName, BaseEntity entityToDelete) throws FileNotFoundException {
        //TODO test this function
        String listName= fileName.substring(0 , fileName.indexOf("."));
        try {
            String idString = "id=" + entityToDelete.getId();
            ArrayList originalFile = (ArrayList)readFile(DIR + fileName);
            for (int i = 0 ; i < originalFile.size() ; i++) {
                String st = (String)originalFile.get(i);
                if (st.matches("^" + idString + "\\|.*")){
                    originalFile.remove(i);
                }
            }
            writeFile(fileName, originalFile);
            clearBuffer(); //TODO no need to every entity
        } catch (Exception e){
        }
    }

    /**
     * This is used to get a new id to help instantiate a new object of a specific class
     * @param classObj The class of the objects to be created e.g. Movie.class
     * @return a new Id as integer
     */
    public static int getNewId(Class<? extends BaseEntity> classObj){
        bufferMaxIdList.put(classObj, bufferMaxIdList.get(classObj) + 1);
        return bufferMaxIdList.get(classObj);
    }

    /**
     * This is used to get a movie object given its unique movieId
     * @param movieId the id of the movie to be found
     * @return the movie with id equals to movieId; if no such movie, exception will be thrown.
     */
    public static Movie getMovieById(int movieId) throws Exception {
        ArrayList<Movie> movieList = readList("MovieList.txt", Movie.class);
        for (Movie m: movieList){
            if (m.getId() == movieId){
                return m;
            }
        }
        throw new Exception("No such movie!");
    }

    /**
     * This is used by setData and deleteData to write to files
     * @param fileName The filename to change e.g. MovieList.txt
     * @param content A ArrayList of string that will be written into the file
     * @throws FileNotFoundException
     */
    private static void writeFile(String fileName, ArrayList content) throws FileNotFoundException {
        try {
            PrintWriter out = new PrintWriter(new FileWriter(DIR + fileName));
            for (int i =0; i < content.size() ; i++) {
                out.println((String)content.get(i));
            }
            out.close();
        } catch (Exception e){
        }
    }
}
