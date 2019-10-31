package modules.entity;

public abstract class BaseEntity {
    protected static int maxId = 0;
    protected int id;
    public BaseEntity(int id){
        this.id = id;
        maxId ++;
    }

    public static int getNewId(){
        return maxId+1; //TODO to be tested， for adding new records to table
    }
    public abstract String StringlizeEntity(); //WARNING: include the ones in db only!!

    public int getId() {
        return id;
    }
}
