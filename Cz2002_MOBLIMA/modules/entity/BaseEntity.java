package modules.entity;

public abstract class BaseEntity {
    protected static int maxId = 0;
    protected int id;
    public BaseEntity(int id){
        this.id = id;
        maxId ++;
    }

    public static int getNewId(){
        return maxId+1; //TODO to be tested
    }
    public abstract String StringlizeEntity();

    public int getId() {
        return id;
    }
}
