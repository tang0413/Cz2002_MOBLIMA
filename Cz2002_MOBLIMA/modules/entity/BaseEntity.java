package modules.entity;

public abstract class BaseEntity {
    protected int id;
    public BaseEntity(int id){
        this.id = id;
    }
    public abstract String StringlizeEntity(); //WARNING: include the ones in db only!!

    public int getId() {
        return id;
    }
}
