package modules.control;

import modules.boundary.Console;
import modules.data.DataBase;
import modules.entity.Admin;
import modules.entity.Cineplex;

import javax.xml.crypto.Data;
import java.util.ArrayList;
import java.util.Arrays;

public class UpdateSystemController extends BaseController{
    private ArrayList<Admin> adminList;
    private Admin admin;
    /**
     * This is to instantiate a Controller object with I/O access
     *
     * @param inheritedConsole the Console instance passed down from the previous controller
     */
    public UpdateSystemController(Console inheritedConsole, Admin thisAdmin) {
        super(inheritedConsole);
        this.admin = thisAdmin;
        logText = "Here are the editable system configuration";
        logMenu = new ArrayList<>();
        logMenu.add("Add New Cineplex");
        logMenu.add("Add New Cinema");
        logMenu.add("Edit Ticket Pricing");
        logMenu.add("Edit Holiday");
        logMenu.add("Edit My Password");
        logMenu.add("Back");
    }

    /**
     * This is the basic method to enter a serious of process
     */
    @Override
    public void enter() {
        while (true){
            console.logText(logText);
            console.logMenu(logMenu);
            int choice = console.getInt("Enter index to proceed", 1, logMenu.size());
            switch (choice){
                case 1:
                    addCineplex();
                    break;
                case 2:
                    addCinema();
                    break;
                case 5:
                    updateAdmin();
                    break;
                case 6:
                    return;
            }
        }
    }

    private void addCineplex(){
        while (true) {
            String cineplexName = console.getStr("Please enter a name for the new cineplex");
            try {
                ArrayList<Cineplex> currentCineplexList = DataBase.readList(Cineplex.class);
                Boolean unique = true;
                for (Cineplex c: currentCineplexList){
                    if (c.getCineplexName().toLowerCase().equals(cineplexName.toLowerCase())){
                        unique = false;
                        break;
                    }
                }
                if (!unique){
                    console.logWarning("Name clashes with current existing cineplex! Try again?");
                    if (!console.getStr("Type 'y' to continue").equals("y")){
                        return;
                    }
                } else {
                    String cinemaNames = console.getCinemaNames();
                    String newId = Integer.toString(DataBase.getNewId(Cineplex.class));
                    ArrayList<String> newCineplexNames = new ArrayList<>();
                    newCineplexNames.add(newId);
                    newCineplexNames.add(cineplexName);
                    newCineplexNames.add(cinemaNames);
                    Cineplex newCineplex = new Cineplex(newCineplexNames);
                    DataBase.setData(newCineplex);
                    console.logSuccess();
                    return;
                }
            } catch (Exception e){
                console.logWarning("Failed to add Cineplex!");
                return;
            }
        }
    }

    private void addCinema(){
        while (true) {
            try {
                ListCineplexController cineplexController = new ListCineplexController(console);
                Cineplex chosenCineplex = cineplexController.enter(true);
                console.logReminder("Cinemas with your entered names will be added to: " + chosenCineplex.getCineplexName());
                console.logReminder("Current existing cinemas: " + chosenCineplex.getCinemaList());
                console.logWarning("This process is NOT invertible");
                String cinemaNames = console.getCinemaNames();
                String splittedNames[] = cinemaNames.split(",");
                ArrayList<String> cinemaNamesList = new ArrayList<>( Arrays.asList(splittedNames));
                for(String s: cinemaNamesList){
                    if(!chosenCineplex.getCinemaList().contains(s)){
                        chosenCineplex.addCinema(s);
                    }
                }
                DataBase.setData(chosenCineplex);
                console.logSuccess();
                return;
            } catch (Exception e){
                console.logWarning("Failed to add Cineplex!");
                return;
            }
        }
    }

    private void updateAdmin(){
        while (true){
            String oldPassword = console.getStr("Please enter your old password");
            if (admin.checkPassword(oldPassword)){
                setNewPassword(admin);
                return;
            } else {
                console.logWarning("Incorrect Old Password! Try again?");
                if (!console.getStr("Type 'y' to continue").equals("y")){
                    return;
                }
            }
        }
    }

    private void setNewPassword(Admin admin){
        while (true){
            String newPassword = console.getStr("Please enter your new password");
            if (newPassword.equals(console.getStr("Please enter your new password again to confirm"))){
                try {
                    admin.setPassword(newPassword);
                    DataBase.setData(admin);
                    console.logSuccess();
                    return;
                } catch (Exception e){
                    console.logWarning("Failed to update password!");
                    e.printStackTrace();
                    return;
                }
            } else {
                console.logWarning("Passwords doesn't match! Try again?");
                if (!console.getStr("Type 'y' to continue").equals("y")){
                    return;
                }
            }
        }
    }
}
