package modules.data;

import modules.entity.BaseEntity;
import modules.entity.Cineplex;

import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.util.*;
import java.nio.file.Path;
import java.nio.file.Paths;

public class DataBase {
    private static HashMap<String, ArrayList> bufferList= new HashMap<>();
    private static HashMap<Class, Integer> bufferMaxIdList = new HashMap<>();
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

    public static void clearBuffer(){
        bufferList = new HashMap<>();
    }

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

    public static int getNewId(Class<? extends BaseEntity> classObj){
        bufferMaxIdList.put(classObj, bufferMaxIdList.get(classObj) + 1);
        return bufferMaxIdList.get(classObj);
    }

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
