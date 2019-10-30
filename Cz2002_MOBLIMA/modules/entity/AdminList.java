package modules.entity;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import modules.data.DataBase;

public class AdminList extends BaseEntity {
    private static ArrayList<Admin>  adminList;

    public AdminList(int id) {
        super(id);
    }

    public static List<Admin> getAllAdmin() throws FileNotFoundException {
        adminList = DataBase.readAdminList("AdminList.txt");
        return adminList;
    }
    public static Boolean auth(String username, String password){
        try{
            getAllAdmin();
        }catch (FileNotFoundException e) {
            System.out.println("exception");
        }
        for(Admin a: adminList){
            if (a.auth(username, password)){
                return true;
            }
        }
        return false;
    }
}
