package modules.control;

import modules.boundary.Console;
import modules.data.DataBase;
import modules.entity.Admin;
import modules.entity.Cineplex;
import modules.entity.Holiday;
import modules.entity.TicketType;

import javax.xml.crypto.Data;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Represents a type of controller that is able to do manipulation on system configuration. e.g. Update, create, or delete Holiday
 */
public class UpdateSystemController extends BaseController{
    /**
     * This is the amdin who asked for manipulation on system configuration
     */
    private Admin admin;
    /**
     * This is to instantiate a Controller specially for manipulating system configuration
     * @param inheritedConsole the Console instance passed down from the previous controller
     * @param thisAdmin a admin who asked for this action
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
     * This is to enter a series of process to change system configuration by firstly provide the admin with a menu
     *  including: Add New Cineplex, Add New Cinema, Edit Ticket Pricing, Edit Holiday, and Edit My Password
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
                case 3:
                    listPrice();
                    break;
                case 4:
                    updateHolidayOption();
                    break;
                case 5:
                    updateAdmin();
                    break;
                case 6:
                    return;
            }
        }
    }

    /**
     * This is to let the admin indicate whether he wants to Update, Delete, or add new Holiday
     */
    private void updateHolidayOption(){
        while (true){
            console.logText("Please choose from the following options");
            ArrayList<String> subMenu = new ArrayList<>();
            subMenu.add("Update Current Holidays");
            subMenu.add("Delete Current Holidays");
            subMenu.add("Add New Holidays");
            subMenu.add("Back");
            console.logMenu(subMenu);
            int choice = console.getInt("Enter index to proceed", 1, subMenu.size());
            if (choice == subMenu.size()){
                return;
            }
            switch (choice){
                case 1:
                case 2:
                    listHoliday(choice);
                    break;
                case 3:
                    addHoliday();
                    break;
            }
        }
    }

    /**
     * This is to list out current holidays for the admin to update of delete
     * @param choice 1 for update and 2 for delete, which the admin has indicated in updateHolidayOption
     */
    private void listHoliday(int choice){
        while (true){
            console.logText("Please choose from the following holidays");
            ArrayList<String> subMenu = new ArrayList<>();
            try {
                ArrayList<Holiday> holidayList = DataBase.readList(Holiday.class);
                for (Holiday h: holidayList){
                    subMenu.add(h.getDate());
                }
                subMenu.add("Back");
                console.logMenu(subMenu);
                int recordChoice = console.getInt("Enter index to proceed", 1, subMenu.size());
                if (recordChoice == subMenu.size()){
                    return;
                } else {
                    Holiday holi = holidayList.get(recordChoice-1);
                    if (choice == 1){
                        holi.setDate(console.getDate());
                        DataBase.setData(holi);
                    } else {
                        DataBase.deleteData(holi);
                    }
                    console.logSuccess();
                    return;
                }
            } catch (Exception e){
                e.printStackTrace();
                return;
            }
        }
    }

    /**
     * This is to create a new Holiday object from user input and then save it to the txt file (database)
     * Inside this method, the user will asked to enter all necessary information for a Holiday
     */
    private void addHoliday(){
        try{
            int newId = DataBase.getNewId(Holiday.class);
            String date = console.getDate();
            ArrayList<String> newHolidayParam = new ArrayList<>();
            newHolidayParam.add(Integer.toString(newId));
            newHolidayParam.add(date);
            Holiday newHoliday = new Holiday(newHolidayParam);
            DataBase.setData(newHoliday);
            console.logSuccess();
        } catch (Exception e){
            e.printStackTrace();
        }

    }


    /**
     * This is to list out the names of all TicketTypes for the admin to choose from to edit
     */
    private void listPrice(){
        while (true){
            console.logText("Please choose from the following ticket types");
            ArrayList<String> subMenu = new ArrayList<>();
            try {
                ArrayList<TicketType> ttList = DataBase.readList(TicketType.class);
                for (TicketType tt: ttList){
                    subMenu.add(tt.getName());
                }
                subMenu.add("Back");
                console.logMenu(subMenu);
                int choice = console.getInt("Enter index to proceed", 1, subMenu.size());
                if (choice == subMenu.size()){
                    return;
                } else {
                    updatePriceInfo(ttList.get(choice-1));
                }
            } catch (Exception e){
                e.printStackTrace();
                return;
            }
        }
    }

    /**
     * This is to list out regular price and 3D movie price of a specific ticket type for the admin to update values
     * @param tt the TicketType object that the admin indicated before in listPrice
     */
    private void updatePriceInfo(TicketType tt){
        while (true){
            console.logText("Please choose to set attribute");
            ArrayList<String> subMenu = new ArrayList<>();
            subMenu.add("Regular Movie Price: " + tt.getRegularPrice());
            subMenu.add("3D Movie Price: " + tt.getThreeDPrice());
            subMenu.add("Back");
            console.logMenu(subMenu);
            int choice  = console.getInt("Enter index to proceed", 1, subMenu.size());
            if (choice == subMenu.size()){
                return;
            } else {
                switch (choice) {
                    case 1:
                        tt.setRegularPrice(console.getPrice());
                        break;
                    case 2:
                        tt.setThreeDPrice(console.getPrice());
                        break;
                }
                try {
                    DataBase.setData(tt);
                    console.logSuccess();
                    return;
                } catch (Exception e){
                    e.printStackTrace();
                    return;
                }
            }
        }
    }

    /**
     * This is to create a new Cineplex object from user input and then save it to the txt file (database)
     * Inside this method, the user will asked to enter all necessary information for a Cineplex
     */
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

    /**
     * This is to add cinema(s) to a current existing cineplex
     */
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

    /**
     * This is to validate the current password of the admin who is operating
     * If the entered current password is correct, he/she will the proceed to enter new passwords
     */
    private void updateAdmin(){
        while (true){
            String oldPassword = console.getStr("Please enter your old password");
            if (admin.checkPassword(oldPassword)){
                setNewPassword();
                return;
            } else {
                console.logWarning("Incorrect Old Password! Try again?");
                if (!console.getStr("Type 'y' to continue").equals("y")){
                    return;
                }
            }
        }
    }

    /**
     * This is to set a new password for the admin who is operating
     */
    private void setNewPassword(){
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
