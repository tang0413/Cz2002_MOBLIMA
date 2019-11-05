package modules.entity;

/**
 * This is the superclass / baseclass of all the other classes under modules.entity package
 */
public abstract class BaseEntity {
    /**
     * The unique id for each object by class
     */
    protected int id;

    /**
     * Instantiate the obj using id
     * @param id The id passed in to assign to the obj
     */
    public BaseEntity(int id){
        this.id = id;
    }

    /**
     * Used by DataBase setData/deleteData
     * @return A stringlized obj to store back to one's own txt file as a String
     */
    public abstract String StringlizeEntity(); //WARNING: include the ones in db only!!

    /**
     * @return the object's id
     */
    public int getId() {
        return id;
    }
}
