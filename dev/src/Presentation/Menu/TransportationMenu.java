package Presentation.Menu;

import Business.ApplicationFacade.outObjects.*;
import Presentation.Controllers;
import Presentation.TransportationController;
import Business.Type.Area;
import Business.Type.Pair;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

public class TransportationMenu extends Menu{
    private int option;
    private int subOption;
    private final int numOfOptions = 7;
    private final Area[] areas={Area.South,Area.North,Area.Center };
    private final TransportationController transportationController;
    private boolean finish;


    public TransportationMenu(Controllers r , Scanner input){
        super(r,input);
        transportationController =new TransportationController(r.getMc());
        this.option=0;
        subOption=0;
        finish=false;
    }

    @Override
    public void show(){
        while(endOfProgram()){
            chooseOption();
            nextStep();
        }
    }
    /**
     *The starting choice of the user if to keep run the system or shut it off.
     */
    public void chooseOption(){
        System.out.print("1) See all Transportations.\n2) Create a new Business.Transportation.\nOption: ");
        option = chooseOp(numOfOptions);
        System.out.println();
    }

    /**
     * prints menu and received the user's choice for which area is the new transportation.
     * @param t : the presentation's transportation object to show the user and to contact the business layer.
     */
    private void chooseArea(TransportationServiceDTO t){
        System.out.println("Please chose an Area");
        for (int i=0; i<areas.length;i++) {
            System.out.println((i+1)+") "+areas[i]);
        }
        System.out.print("Area number: ");
        int area=chooseOp(areas.length)-1;
        System.out.println();
        Area chosen=areas[area];
        t.setArea(chosen);
        transportationController.setTransportationArea(t);
    }

    /**
     *Asks and received the user's input for the wanted trans hour.
     * @param tran : the presentation's transportation object to show the user and to contact the business layer.
     */
    private void chooseTime(TransportationServiceDTO tran){
        boolean success=false;
        while (!success) {
            try {
                System.out.print("Please chose time for transportation.\nUse the format hh:mm.\nTime:");
                String tim = input.next();
                System.out.println();
                LocalTime time = LocalTime.parse(tim);
                tran.setLeavingTime(time);
                transportationController.setTransportationLeavingTime(tran);
                success = true;
            }
            catch (Exception e) { }
        }
    }

    /**
     * Asks and received the user's input for the wanted trans date.
     * @param tran: the presentation's transportation object to show the user and to contact the business layer.
     */
    private void chooseDate(TransportationServiceDTO tran){
        boolean success=false;
        while (!success) {
            try {
                System.out.print("Please chose a date for transportation.\nUse the format yyyy-mm-dd.\nDate:");
                String tim = input.next();
                System.out.println();
                LocalDate date=LocalDate.parse(tim);
                tran.setDate(date);
                transportationController.setTransportationDate(tran);
                success = true;
            }
            catch (Exception e) { }
        }
    }

    /**
     * This is the add transportation menu method. the whole user's input and dialog runs/called from here.
     */
    private void chooseAddOption(){

        System.out.println("\n*************************************************");
        System.out.println("************ New Business.Transportation Menu ************");
        System.out.println("*************************************************\n");
        finish=false;
        TransportationServiceDTO newTrans= transportationController.createNewTransportation();
        chooseArea(newTrans);
        chooseDate(newTrans);
        chooseTime(newTrans);
        while (!finish) {
            System.out.println("\nChoose an option:");
            System.out.println("0) Cancel transportation");
            System.out.println("1) Add a truck");
            System.out.println("2) Add a driver");
            System.out.println("3) Add a suppliers and items");
            System.out.println("4) Add branches and items to a branches");
            System.out.println("5) Set the truck weight");
            System.out.println("6) Submit your transportation");
            System.out.print("Option:");
            subOption = chooseOp(numOfOptions);
            switch (subOption) {
                case 1:  chooseTruck(newTrans); break;
                case 2 : chooseDriver(newTrans); break;
                case 3 : chooseSupplier(newTrans); break;
                case 4 : chooseBranch(newTrans); break;
                case 5 : chooseWeight(newTrans); break;
                case 6 : submit(newTrans); break;
                case 0: {Delete(); return;}
            }
            System.out.println(newTrans);
        }
    }

    /**
     * Delete new transportation
     */
    private void Delete() {

        try {
            transportationController.delete();
        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    /**
     *Asks and received the user's input for the wanted trans weight.
     * @param t: the presentation's transportation object to show the user and to contact the business layer.
     */
    private void chooseWeight(TransportationServiceDTO t){
        try {
            System.out.println("\n*************************************************");
            System.out.println("************ Adding Weight ************");
            System.out.println("*************************************************\n");
            System.out.println("please enter transportation total weight:");
            System.out.print("\nWeight: ");
            int chose = input.nextInt();
            System.out.println();
            t.setWeight(chose);
            if(!transportationController.setTransportationWeight(t))
                t.setWeight(-1);
        }
        catch (Exception e){
            t.setTruck(null);
        }
    }

    /**
     *Method that called by the addTransportation menu. try to close the new trans with the all new data.
     * If one field is empty the method will no allow.
     * If finished, back to first menu.
     * @param t: the presentation's transportation object to show the user and to contact the business layer
     */
    private void submit(TransportationServiceDTO t){
        try {
            System.out.println("\nTrying to submit transportation... \n");
            if(t.isComplete()) {
                transportationController.setTransportation(t);
                finish = true;
                System.out.println("Success to submit new transportation! ");
            }
            else{
                System.out.println("Please fill all the information before submit the transportation.\n");
                finish=false;
            }
        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    /**
     *Menu of suppliers and their items.
     * The method ask for a flow of suppliers until -1 number.
     * After each input, ask in a loop for item and quantity until -2 number.
     * @param t: the presentation's transportation object to show the user and to contact the business layer
     */
    private void chooseSupplier(TransportationServiceDTO t) {
        try {
            System.out.println("\n*************************************************");
            System.out.println("************ Adding Suppliers ************");
            System.out.println("*************************************************\n");
            System.out.println("Select suppliers and items from the lists below:");
            printAllSuppliers();
            int chose;
            HashMap<SupplierServiceDTO, List<Pair<ItemServiceDTO,Integer>>> suppliers=new HashMap<>();
            do {
                System.out.print("\nselect supplier, to end press -1:\nSupplier Id: ");
                chose = input.nextInt();
                System.out.println();
                if(chose==-1)
                    break;
                List<Pair<ItemServiceDTO, Integer>> lis =new LinkedList<>();
                long id;
                int num;
                do{
                    printItemsBySupplier(chose);
                    System.out.println("Enter item and quantity,to end press -2:");
                    System.out.print("Item Id: ");
                    id= input.nextLong();
                    if(id==-2)
                        break;
                    System.out.print("Quantity: ");
                    num= input.nextInt();
                    System.out.println();

                    lis.add(new Pair<>(transportationController.getItem(id),num));
                }
                while(true);
                suppliers.put(transportationController.getSupplier(chose), lis);
            }
            while(true);
            t.setSuppliers(suppliers);
            transportationController.setSuppliersToTransportation(t);
        }
        catch (Exception e){
            t.setSuppliers(null);
            System.out.println("Error: "+e.getMessage());
        }
    }

    /**
     *Menu of suppliers and their items.
     * The method ask for a flow of branches until -1 number.
     * After each input, ask in a loop for item and quantity until -2 number.
     * @param t: the presentation's transportation object to show the user and to contact the business layer
     */
    private void chooseBranch(TransportationServiceDTO t) {
        try {
            System.out.println("\n*************************************************");
            System.out.println("**************** Adding Suppliers ***************");
            System.out.println("*************************************************\n");
            System.out.println("Select branches and items from the lists below:");
            printAllBranches();
            int chose;
            HashMap<BranchServiceDTO, List<Pair<ItemServiceDTO,Integer>>> branches=new HashMap<>();
            do {
                System.out.print("\nselect branch, to end press -1:\nBranch Id:");
                chose = input.nextInt();
                System.out.println();
                if(chose==-1)
                    break;
                List<Pair<ItemServiceDTO, Integer>> lis =new LinkedList<>();
                long id;
                int num;
                do{
                    PrintAllItems();
                    System.out.println("Enter item and quantity,to end press -2:");
                    System.out.print("Item Id: ");
                    id= input.nextLong();
                    if(id==-2)
                        break;
                    System.out.print("Quantity: ");
                    num= input.nextInt();

                    lis.add(new Pair<>(transportationController.getItem(id),num));
                }
                while(true);
                branches.put(transportationController.getBranch(chose), lis);
            }
            while(true);
            t.setDeliveryItems(branches);
            if(!transportationController.setDeliveryItemsToTransportation(t))
                t.resetDeliveryItems();
        }
        catch (Exception e){
            t.setDeliveryItems(null);
            System.out.println("Error: "+e.getMessage());
        }
    }

    /**
     *Asks and received from user the driver for the transportation by id number.
     * If the addition could not complete, the transportation's driver's details will not be changed.
     * @param t: the presentation's transportation object to show the user and to contact the business layer
     */
    private void chooseDriver(TransportationServiceDTO t) {
        try {
            System.out.println("\n*************************************************");
            System.out.println("  ****************** Adding Driver ****************");
            System.out.println("*************************************************\n");
            System.out.println("please select driver id from the trucks list below:\n");
            List<DriverServiceDTO> drivers=getAllDrivers(t.getDate(),t.getLeavingTime());
            if(drivers==null||!drivers.isEmpty()) {
                for (DriverServiceDTO driver:drivers) {
                    System.out.println(driver);
                }
                System.out.print("\nId: ");
                int chose = input.nextInt();
                System.out.println();
                if(t.getTruck() != null){
                    t.setDriver(transportationController.getDriver(chose));
                    transportationController.setDriverOnTransportation(t);
                }else {
                    System.out.println("You must enter truck before driver.");
                }
            }
            else{
                System.out.println("There are no available drivers for those specific date and time.\nPlease select an option: \n ");
                System.out.println("1) Change Date \n2) Change time");
                int option= chooseOp(2);
                if(option==1){
                    chooseDate(t);
                }
                else{
                    chooseTime(t);
                }
            }
        }
        catch (Exception e){
            t.setDriver(null);
            System.out.println("There are no available drivers for those specific date and time.\nPlease select an option: \n ");
            System.out.println("1) Change Date \n2) Change time");
            int option= chooseOp(2);
            if(option==1){
                chooseDate(t);
            }
            else{
                chooseTime(t);
            }
        }
    }

    /**
     *Asks and received from user the truck for the transportation by id number.
     * If the addition could not complete, the transportation's truck's details will not be changed.
     * @param t: the presentation's transportation object to show the user and to contact the business layer
     */
    private void chooseTruck(TransportationServiceDTO t) {

        try {
            System.out.println("\n*************************************************");
            System.out.println("****************** Adding Truck *****************");
            System.out.println("*************************************************\n");
            System.out.println("Type a truck id from the list below:\n");
            printAllTucks();
            System.out.print("\nId: ");
            long chose = input.nextLong();
            System.out.println();
            t.setTruck(transportationController.getTruck(chose));
            transportationController.setTruckOnTransportation(t);
        }
        catch (Exception e){
            t.setTruck(null);
        }
    }

    /**
     *Prints to the user all the available drivers in the database
     */

    public List<DriverServiceDTO> getAllDrivers(LocalDate date, LocalTime leavingTime){
       return  transportationController.getAllDrivers(date,leavingTime);
    }
    /**
     *Prints to the user all the available trucks in the database
     */
    public void printAllTucks(){
        List<TruckServiceDTO> lis= transportationController.getAllTrucks();
        for (TruckServiceDTO tru:lis) {
            System.out.println(tru);
        }
    }

    /**
     *Prints to the user all the available branches in the database
     */
    public void printAllBranches(){
        List<BranchServiceDTO> bra= transportationController.getAllBranches();
        for (BranchServiceDTO tru:bra) { System.out.println(tru); }
    }

    /**
     *Prints to the user all the available suppliers in the database
     */
    public void printAllSuppliers(){
        List<SupplierServiceDTO> sup= transportationController.getAllSuppliers();
        for (SupplierServiceDTO tru:sup) { System.out.println(tru); }

    }
    public void printItemsBySupplier(int id){
        List<ItemServiceDTO> items = transportationController.getItemsBySupplier(id);
        for(ItemServiceDTO item: items) System.out.println(item);
    }
    /**
     *Prints to the user all the available items in the database
     */
    public void PrintAllItems(){
        List<ItemServiceDTO> sup= transportationController.getAllItems();
        for (ItemServiceDTO tru:sup) { System.out.println(tru); }

    }

    /**
     *Prints to the user all the available transportations in the database
     * Used for the transportations printing option in the menu.
     */
    public void printAllTransportations(){
        System.out.println("\n*************************************************");
        System.out.println("************ Printing Business.Transportation ************");
        System.out.println("*************************************************\n");
        List<TransportationServiceDTO> sup= transportationController.getAllTransportations();
        for (TransportationServiceDTO tru:sup) { System.out.println(tru); }
    }

    /**
     *The starting menu of the system.
     * runs by the main of the project.
     * By user's input it keep running the system or shut it off.
     * @return : if to keep run the program or terminate it
     */
    public boolean endOfProgram(){
        System.out.println("\n****************** Transportation menu *******************");
        System.out.println("Hello.\nPlease choose an option:");
        System.out.print("1)Continue\n2)Exit\nOption: ");
        int numOfEndProgramOp = 2;
        return chooseOp(numOfEndProgramOp) == 1;
    }

    /**
     *Method to receive an input from the user with boundary limit.
     * @param con : the num of options the user can type. For boundary check.
     * @return : the choice of the user.
     */
    private int chooseOp(int con){
        boolean validInput = false;
        int userOption = -1;
        while (!validInput) {
            userOption = input.nextInt();
            if((userOption <= con) && (userOption >= 0)) {
                validInput = true;
            }else {
                System.out.println("your choose without bounds");
            }
        }
        return userOption;
    }

    /**
     *Method to direct the menu by the user's choice in the starting menu.
     */
    public void nextStep() {

        if(option==2)
            chooseAddOption();
        else
            printAllTransportations();
    }
}
