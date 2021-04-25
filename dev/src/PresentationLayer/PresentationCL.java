package PresentationLayer;

import java.io.InputStreamReader;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Scanner;

import  BussniesLayer.facade.SupplierService;
import BussniesLayer.facade.Tresponse;
import BussniesLayer.facade.outObjects.*;
import BussniesLayer.facade.response;

public class PresentationCL{

    private final SupplierService service;

    public PresentationCL() {
        service = new SupplierService();
    }

    public void mainRun(boolean firstTime){
        Scanner scanner = new Scanner(System.in);
        if(firstTime) {
            String[] load = {"load last data", "new data"};
            boolean legal = false;
            while (!legal) {
                System.out.println("please select an option: ");
                for (int i = 1; i <= load.length; i++) {
                    System.out.println(i + ") " + load[i - 1]);
                }
                int n = -1;
                n= menuCheck(n, scanner);
                switch (n) {
                    case 1 -> {
                        service.LoadData();
                        legal = true;
                    }
                    case 2 -> legal = true;
                    default -> System.out.println("illegal option!!!");
                }
            }
        }
        String[] mainMenuArray = {"showing methods" , "adding methods" , "removing methods" , "updating methods" ,"end program" };
        int option = -1;
        String check = "";
        while(true){
            System.out.println("please select an option: ");
            for(int i = 1 ; i <= mainMenuArray.length ; i++){
                System.out.println(i + ") " + mainMenuArray[i-1]);
            }
            option = menuCheck(option,scanner);
            switch (option) {
                case 1 -> showingMethods();
                case 2 -> addingMethods();
                case 3 -> removingMethods();
                case 4 -> updatingMethods();
                case 5 -> {
                    System.out.println("PROGRAM DONE.");
                    service.deleteData();
                    System.exit(0);
                }
                default -> System.out.println("illegal option!!!");
            }
        }
    }

    private void showingMethods(){
        Scanner scanner = new Scanner(new InputStreamReader(System.in));
        int option = -1;
        String[] showingMethodArray = {"show Supplier","show SupplierBN","show All Suppliers", "show Item Of Supplier","show All Items Of Supplier",
                                       "show All Items", "show Order Of Supplier","show All Orders Of Supplier","show Total Amount",
                                        "show Deliver Time", "show Quantity Document","show Supplier Agreement","back to the main menu"};
        System.out.println("please select the showing method: ");
        while (true) {
            for (int i = 1; i <= showingMethodArray.length ; i++) {
                System.out.println(i + ") " + showingMethodArray[i - 1]);
            }
            option = menuCheck(option, scanner);
            int BN;
            switch (option) {
                case 1 -> {
                    BN = BNScan(scanner);
                    Tresponse<SupplierCard> response = service.showSupplier(BN);
                    if (response.isError()) System.out.println(response.getError());
                    else {
                        System.out.println(response.getOutObject().toString());
                        Tresponse<List<Item>> items = service.showAllItemsOfSupplier(BN);
                        if (items.isError()) System.out.println(items.getError() + "\n");
                        else {
                            List<Item> responsesItem = items.getOutObject();
                            System.out.println("\tItemsID:");
                            for (Item item : responsesItem) {
                                System.out.println("\t\t" +item.toStringId());
                            }
                            Tresponse<List<Order>> orders = service.showAllOrdersOfSupplier(BN);
                            if (orders.isError()) System.out.println(items.getError() + "\n");
                            else {
                                List<Order> responsesOrders = orders.getOutObject();
                                System.out.println("\tOrdersId:");
                                for (Order order : responsesOrders) {
                                    System.out.println("\t\t" + order.toStringId());
                                }
                            }
                        }
                    }
                    toContinue(scanner , false);
                }
                case 2 -> {
                    String name = supplierNameScan(scanner);
                    Tresponse<SupplierCard> response = service.showSupplierBN(name);
                    if (response.isError()) System.out.println(response.getError() + "\n");
                    else System.out.println("supplierBN is: " + response.getOutObject().toStringId());
                    toContinue(scanner , true);
                }
                case 3 -> {
                    Tresponse<List<SupplierCard>> responsesList = service.showAllSuppliers();
                    if (responsesList.isError()) System.out.println(responsesList.getError() + "\n");
                    else {
                        List<SupplierCard> responses = responsesList.getOutObject();
                        for (SupplierCard supplierCard : responses) {
                            System.out.println(supplierCard.toString());
                        }
                    }
                    toContinue(scanner , true);
                }
                case 4 -> {
                    BN = BNScan(scanner);
                    int itemId = itemScan(scanner);
                    Tresponse<Item> response = service.showItemOfSupplier(BN , itemId);
                    if (response.isError()) System.out.println(response.getError() + "\n");
                    else System.out.println(response.getOutObject().toString(false));
                    toContinue(scanner , true);
                }
                case 5 -> {
                    BN = BNScan(scanner);
                    Tresponse<List<Item>> responsesList = service.showAllItemsOfSupplier(BN);
                    if (responsesList.isError()) System.out.println(responsesList.getError() + "\n");
                    else {
                        List<Item> responses = responsesList.getOutObject();
                        for (Item item : responses) {
                            System.out.println(item.toString(false));
                        }
                    }
                    toContinue(scanner , false);
                }
                case 6 ->{
                    Tresponse<List<Item>> responsesList = service.showAllItems();
                    if (responsesList.isError()) System.out.println(responsesList.getError() + "\n");
                    else {
                        List<Item> responses = responsesList.getOutObject();
                        for (Item item : responses) {
                            System.out.println(item.toString(false));
                        }
                    }
                    toContinue(scanner , true);
                }
                case 7 -> {
                    BN = BNScan(scanner);
                    int orderId = orderScan(scanner);
                    service.showTotalAmount(BN , orderId);
                    Tresponse<Order> response = service.showOrderOfSupplier(BN, orderId);
                    if (response.isError()) System.out.println(response.getError() + "\n");
                    else {
                        System.out.println(response.getOutObject().toString());
                        Tresponse<SupplierAgreement> supplierAgreement = service.showSupplierAgreement(BN);
                        if(supplierAgreement.isError()) System.out.println(supplierAgreement.getError() + "\n");
                        else System.out.println("\tship to us: " + supplierAgreement.getOutObject().toStringShipToUs());
                        Tresponse<List<Item>> items = service.showAllItemsOfOrder(BN, orderId);
                        if (items.isError()) System.out.println(items.getError() + "\n");
                        else {
                            List<Item> responseItem = items.getOutObject();
                            for (Item item : responseItem) {
                                System.out.println(item.toString(true));
                            }
                        }
                    }
                    toContinue(scanner , false);
                }
                case 8 -> {
                    BN = BNScan(scanner);
                    Tresponse<List<Order>> responsesList = service.showAllOrdersOfSupplier(BN);
                    if (responsesList.isError()) System.out.println(responsesList.getError() + "\n");
                    else {
                        List<Order> responses = responsesList.getOutObject();
                        for (Order order : responses) {
                            System.out.println(order.toString());
                            Tresponse<List<Item>> items = service.showAllItemsOfOrder(BN, Integer.parseInt(order.toStringId()));
                            if (items.isError()) System.out.println(items.getError() + "\n");
                            else {
                                Tresponse<SupplierAgreement> supplierAgreement = service.showSupplierAgreement(BN);
                                if(supplierAgreement.isError()) System.out.println(supplierAgreement.getError() + "\n");
                                else System.out.println("\tship to us: " + supplierAgreement.getOutObject().toStringShipToUs());
                                List<Item> responseItem = items.getOutObject();
                                for (Item item : responseItem) {
                                    System.out.println(item.toString(true));
                                }
                            }
                        }
                    }
                    toContinue(scanner , false);
                }
                case 9 -> {
                    BN = BNScan(scanner);
                    int orderId = orderScan(scanner);
                    Tresponse<Order> response = service.showTotalAmount(BN, orderId);
                    if (response.isError()) System.out.println(response.getError() + "\n");
                    else System.out.println("total amount is: " + response.getOutObject().toStringTotalAmount());
                    toContinue(scanner , false);
                }
                case 10 -> {
                    BN = BNScan(scanner);
                    int orderId = orderScan(scanner);
                    Tresponse<Order> response = service.showDeliverTime(BN, orderId);
                    if (response.isError()) System.out.println(response.getError() + "\n");
                    else System.out.println("deliver time is : " + response.getOutObject().toStringDeliverTime());
                    toContinue(scanner , false);
                }
                case 11 -> {
                    BN = BNScan(scanner);
                    int itemId = itemScan(scanner);
                    Tresponse<QuantityDocument> response = service.showQuantityDocument(BN, itemId);
                    if (response.isError()) System.out.println(response.getError() + "\n");
                    else System.out.println(response.getOutObject().toString());
                    toContinue(scanner  ,false);
                }
                case 12 -> {
                    BN = BNScan(scanner);
                    Tresponse<SupplierAgreement> response = service.showSupplierAgreement(BN);
                    if (response.isError()) System.out.println(response.getError() + "\n");
                    else System.out.println(response.getOutObject().toString());
                    toContinue(scanner , false);
                }
                case 13 -> mainRun(false);
                default -> {
                    System.out.println("illegal option!!!");
                    toContinue(scanner , false);
                }
            }
        }
    }

    private void addingMethods(){
        Scanner scanner = new Scanner(new InputStreamReader(System.in));
        int option = -1;
        String[] showingMethodArray = {"add supplier","add Contact Phone","add Contact Email","add Item","add Regular Order", "add Needed Order",
                                       "add Item To Order","add Quantity Document","add Supplier Agreement","back to the main menu"};
        System.out.println("please select the showing method: ");
        while (true) {
            for (int i = 1; i <= showingMethodArray.length; i++) {
                System.out.println(i + ") " + showingMethodArray[i - 1]);
            }
            option = menuCheck(option,scanner);
            int BN;
            switch (option) {
                case 1 -> {
                    String name = supplierNameScan(scanner);
                    int bankNumber = bankNumberScan(scanner);
                    int branchNumber = branchNumberScan(scanner);
                    int bankAccount = bankAccountScan(scanner);
                    String payWay = payWayScan(scanner);
                    response response = service.addSupplier(name, bankNumber , branchNumber , bankAccount, payWay);
                    if (response.isError()) System.out.println(response.getError()+ "\n");
                    else System.out.println("The operation was completed successfully\n");
                }
                case 2 -> {
                    BN = BNScan(scanner);
                    String phone = contactPhoneScan(scanner , "");
                    String name = contactNameScan(scanner);
                    response response = service.addContactPhone(BN, phone, name);
                    if (response.isError()) System.out.println(response.getError()+ "\n");
                    else System.out.println("The operation was completed successfully\n");
                }
                case 3 -> {
                    BN = BNScan(scanner);
                    String email = contactEmailScan(scanner , "");
                    String name = contactNameScan(scanner);
                    response response = service.addContactEmail(BN, email, name);
                    if (response.isError()) System.out.println(response.getError()+ "\n");
                    else System.out.println("The operation was completed successfully\n");
                }
                case 4 -> {
                    BN = BNScan(scanner);
                    String name = itemNameScanner(scanner);
                    double price = priceScan(scanner);
                    int typeID = typeScan(scanner);
                    LocalDate expirationDate = dateScan(scanner);
                    Tresponse<Item> response = service.addItem(BN,name, price, typeID, expirationDate );
                    if (response.isError()) System.out.println(response.getError()+ "\n");
                    else System.out.println("ItemId is: " + response.getOutObject().toStringId() + "\n");
                    toContinue(scanner , true);
                }
                case 5 -> {
                    BN = BNScan(scanner);
                    int branchID = branchIDScan(scanner);
                    Hashtable<Integer , Integer> itmes = constantOrderScan(scanner);
                    int deliverDays = deliverDaysScan(scanner);
                    Tresponse<Order> response = service.addRegularOrder(BN, deliverDays,  branchID , itmes);
                    if (response.isError()) System.out.println(response.getError() + "\n");
                    else System.out.println("orderId is: " + response.getOutObject().toStringId() + "\n");
                    toContinue(scanner , false);
                }
                case 6 -> {
                    int typeID = typeScan(scanner);
                    int neededAmount = amountScan(scanner);
                    int branchID = branchIDScan(scanner);
                    Tresponse<Order> response = service.addNeededOrder(typeID, neededAmount, branchID);
                    if (response.isError()) System.out.println(response.getError() + "\n");
                    else System.out.println("orderId is: " + response.getOutObject().toStringId() + "\n");
                    toContinue(scanner , false);
                }
                case 7 -> {
                    BN = BNScan(scanner);
                    int orderId = orderScan(scanner);
                    int itemId = itemScan(scanner);
                    int amount = amountScan(scanner);
                    response response = service.addItemToOrder(BN, orderId, itemId, amount);
                    if (response.isError()) System.out.println(response.getError() + "\n");
                    else System.out.println("The operation was completed successfully\n");
                }
                case 8 -> {
                    BN = BNScan(scanner);
                    int itemId = itemScan(scanner);
                    int minimalAmount = minimalAmountScan(scanner);
                    int discount = discountScan(scanner);
                    response response = service.addQuantityDocument(BN, itemId, minimalAmount, discount);
                    if (response.isError()) System.out.println(response.getError() + "\n");
                    else System.out.println("The operation was completed successfully\n");
                }
                case 9 -> {
                    BN = BNScan(scanner);
                    int minimalAmount = minimalAmountScan(scanner);
                    int discount = discountScan(scanner);
                    boolean constantTime = constantTimeScan(scanner);
                    boolean shipToUs = shipToUsScan(scanner);
                    response response = service.addSupplierAgreement(BN, minimalAmount, discount, constantTime, shipToUs);
                    if (response.isError()) System.out.println(response.getError() + "\n");
                    else System.out.println("The operation was completed successfully\n");
                }
                case 10 -> mainRun(false);
                default ->{
                    System.out.println("illegal option!!!\n");
                    toContinue(scanner , false);
                }
            }
        }
    }

    private void removingMethods(){
        Scanner scanner = new Scanner(new InputStreamReader(System.in));
        int option = -1;
        String check = "";
        String[] removeMethodArray = {"remove Supplier","remove Contact Phone","remove Contact Email","remove Item",
                                      "remove Quantity Document","back to the main menu"};
        System.out.println("please select the showing method: ");
        while (true) {
            for (int i = 1; i <= removeMethodArray.length; i++) {
                System.out.println(i + ") " + removeMethodArray[i - 1]);
            }
            option = menuCheck(option,scanner);
            int BN;
            switch (option) {
                case 1 -> {
                    BN = BNScan(scanner);
                    response response = service.removeSupplier(BN);
                    if (response.isError()) System.out.println(response.getError()+ "\n");
                    else System.out.println("The operation was completed successfully\n");
                }
                case 2 -> {
                    BN = BNScan(scanner);
                    String phone = contactPhoneScan(scanner , "");
                    response response = service.removeContactPhone(BN, phone);
                    if (response.isError()) System.out.println(response.getError()+ "\n");
                    else System.out.println("The operation was completed successfully\n");
                }
                case 3 -> {
                    BN = BNScan(scanner);
                    String email = contactEmailScan(scanner , "");
                    response response = service.removeContactEmail(BN, email);
                    if (response.isError()) System.out.println(response.getError()+ "\n");
                    else System.out.println("The operation was completed successfully\n");
                }
                case 4 -> {
                    int itemId = itemScan(scanner);
                    response response = service.removeItem(itemId);
                    if (response.isError()) System.out.println(response.getError()+ "\n");
                    else System.out.println("The operation was completed successfully\n");
                }
                case 5 -> {
                    BN = BNScan(scanner);
                    int itemId = itemScan(scanner);
                    response response = service.removeQuantityDocument(BN, itemId);
                    if (response.isError()) System.out.println(response.getError()+ "\n");
                    else System.out.println("The operation was completed successfully\n");
                }
                case 6 -> mainRun(false);
                default ->{
                    System.out.println("illegal option!!!");
                    toContinue(scanner , false);
                }
            }
        }
    }

    private void updatingMethods() {
        Scanner scanner = new Scanner(new InputStreamReader(System.in));
        int option  = -1;
        int BN;
        String[] updateMethodArray = {"update Supplier PayWay", "update Supplier BankAccount", "update Contact Phone", "update Contact Email",
                "update Deliver Time", "update Minimal Amount Of Quantity Document", "update Discount Of Quantity Document",
                "update Minimal Amount Of Supplier Agreement", "update Discount Of Supplier Agreement",
                "update Constant Time", "update Ship To Us", "update Price", "back to the main menu"};
        System.out.println("please select the showing method: ");
        while (true) {
            for (int i = 1; i <= updateMethodArray.length; i++) {
                System.out.println(i + ") " + updateMethodArray[i - 1]);
            }
            option = menuCheck(option, scanner);
            switch (option) {
                case 1 -> {
                    BN = BNScan(scanner);
                    String payWay = payWayScan(scanner);
                    response response = service.updateSupplierPayWay(BN, payWay);
                    if (response.isError()) System.out.println(response.getError()+ "\n");
                    else System.out.println("The operation was completed successfully\n");
                }
                case 2 -> {
                    BN = BNScan(scanner);
                    int bankNumber = bankNumberScan(scanner);
                    int branchNumber = branchNumberScan(scanner);
                    int bankAccount = bankAccountScan(scanner);
                    response response = service.updateSupplierBankAccount(BN, bankNumber , branchNumber, bankAccount);
                    if (response.isError()) System.out.println(response.getError()+ "\n");
                    else System.out.println("The operation was completed successfully\n");
                }
                case 3 -> {
                    BN = BNScan(scanner);
                    String phone = contactPhoneScan(scanner , "new");
                    String name = contactNameScan(scanner);
                    response response = service.updateContactPhone(BN, phone , name);
                    if (response.isError()) System.out.println(response.getError()+ "\n");
                    else System.out.println("The operation was completed successfully\n");
                }
                case 4 -> {
                    BN = BNScan(scanner);
                    String email = contactEmailScan(scanner , "new");
                    String name = contactNameScan(scanner);
                    response response = service.updateContactEmail(BN, email , name);
                    if (response.isError()) System.out.println(response.getError()+ "\n");
                    else System.out.println("The operation was completed successfully\n");
                }
                case 5 -> {
                    BN = BNScan(scanner);
                    int orderId = orderScan(scanner);
                    LocalDate date = dateScan(scanner);
                    response response = service.updateDeliverTime(BN, orderId, date);
                    if (response.isError()) System.out.println(response.getError()+ "\n");
                    else System.out.println("The operation was completed successfully\n");
                }
                case 6 -> {
                    BN = BNScan(scanner);
                    int itemId = itemScan(scanner);
                    int minimalAmount = minimalAmountScan(scanner);
                    response response = service.updateMinimalAmountOfQD(BN, itemId, minimalAmount);
                    if (response.isError()) System.out.println(response.getError()+ "\n");
                    else System.out.println("The operation was completed successfully\n");
                }
                case 7 -> {
                    BN = BNScan(scanner);
                    int itemId = itemScan(scanner);
                    int discount = discountScan(scanner);
                    response response = service.updateDiscountOfQD(BN, itemId, discount);
                    if (response.isError()) System.out.println(response.getError()+ "\n");
                    else System.out.println("The operation was completed successfully\n");
                }
                case 8 -> {
                    BN = BNScan(scanner);
                    int minimalAmount = minimalAmountScan(scanner);
                    response response = service.updateMinimalAmountOfSA(BN, minimalAmount);
                    if (response.isError()) System.out.println(response.getError()+ "\n");
                    else System.out.println("The operation was completed successfully\n");
                }
                case 9 -> {
                    BN = BNScan(scanner);
                    int discount = discountScan(scanner);
                    response response = service.updateDiscountOfSA(BN, discount);
                    if (response.isError()) System.out.println(response.getError()+ "\n");
                    else System.out.println("The operation was completed successfully\n");
                }
                case 10 -> {
                    BN = BNScan(scanner);
                    boolean constantTime = constantTimeScan(scanner);
                    response response = service.updateConstantTime(BN, constantTime);
                    if (response.isError()) System.out.println(response.getError()+ "\n");
                    else System.out.println("The operation was completed successfully\n");
                }
                case 11 -> {
                    BN = BNScan(scanner);
                    boolean shipToUs = shipToUsScan(scanner);
                    response response = service.updateShipToUs(BN, shipToUs);
                    if (response.isError()) System.out.println(response.getError()+ "\n");
                    else System.out.println("The operation was completed successfully\n");
                }
                case 12 -> {
                    BN = BNScan(scanner);
                    int itemId = itemScan(scanner);
                    double price = priceScan(scanner);
                    response response = service.updatePrice(BN, itemId, price);
                    if (response.isError()) System.out.println(response.getError()+ "\n");
                    else System.out.println("The operation was completed successfully\n");
                }
                case 13 -> mainRun(false);
                default ->{
                    System.out.println("illegal option!!!");
                    toContinue(scanner , false);
                }
            }
        }
    }

    private int BNScan(Scanner scanner){
        int BN = -1;
        while (BN == -1) {
            System.out.println("please enter the SupplierBN");
            try {
                BN = scanner.nextInt();
            } catch (Exception e) {
                System.out.println("supplier BN must be a number\n");
                scanner.nextLine();
            }
        }
        return BN;
    }

    private int typeScan(Scanner scanner){
        int type = -1;
        while (type == -1) {
            System.out.println("please enter the typeID");
            try {
                type = scanner.nextInt();
            } catch (Exception e) {
                System.out.println("typeID must be a number\n");
                scanner.nextLine();
            }
        }
        return type;
    }

    private int branchIDScan(Scanner scanner){
        int branchID = -1;
        while (branchID == -1) {
            System.out.println("please enter the branchID");
            try {
                branchID = scanner.nextInt();
            } catch (Exception e) {
                System.out.println("branchID must be a number\n");
                scanner.nextLine();
            }
        }
        return branchID;
    }

    private int itemScan(Scanner scanner){
        int itemId = -1;
        while (itemId == -1) {
            System.out.println("please enter the itemId.");
            try {
                itemId = scanner.nextInt();
                scanner.nextLine();
            } catch (Exception e) {
                System.out.println("itemId must be a number\n");
                scanner.nextLine();
            }
        }
        return itemId;
    }

    private int orderScan(Scanner scanner){
        int orderId = -1;
        while (orderId == -1) {
            System.out.println("please enter the orderId.");
            try {
                orderId = scanner.nextInt();
            } catch (Exception e) {
                System.out.println("orderId must be a number\n");
                scanner.nextLine();
            }
        }
        return orderId;
    }

    private String supplierNameScan(Scanner scanner){
        boolean toContinue = true;
        String name = "";
        while (toContinue) {
            try {
                name = scanner.nextLine();
                if(!name.equals("")) toContinue = false;
                if(toContinue)System.out.println("please enter supplier name");
            }catch (Exception e) {
                System.out.println("you must enter a name\n");
            }
        }
        return name;
    }

    private int bankNumberScan(Scanner scanner){
        int bankNumber = -1;
        while(bankNumber== -1){
            System.out.println("please enter supplier bank number");
            try {
                bankNumber = scanner.nextInt();
            }catch (Exception e) {
                System.out.println("bank number must be a number\n");
                scanner.nextLine();
            }
        }
        return bankNumber;
    }

    private int branchNumberScan(Scanner scanner){
        int branchNumber = -1;
        while(branchNumber == -1){
            System.out.println("please enter supplier bank branch number");
            try {
                scanner.nextLine();
                branchNumber = scanner.nextInt();
            }catch (Exception e) {
                System.out.println("branch number must be a number\n");
                scanner.nextLine();
            }
        }
        return branchNumber;
    }

    private int bankAccountScan(Scanner scanner){
        int bankAccount = -1;
        while(bankAccount== -1){
            System.out.println("please enter supplier bank account");
            try {
                scanner.nextLine();
                bankAccount = scanner.nextInt();
            }catch (Exception e) {
                System.out.println("bank account must be a number\n");
                scanner.nextLine();
            }
        }
        return bankAccount;
    }

    private String payWayScan(Scanner scanner){
        boolean toContinue = true;
        String payWay = "";
        while (toContinue) {
            try {
                if(payWay.equals("")) payWay = scanner.nextLine();
                if(!payWay.equals("")) toContinue = false;
                if(toContinue) System.out.println("please enter supplier payWay");
            }catch (Exception e) {
                System.out.println("you must enter payWay.\n");
                toContinue = true;
            }
        }
        return payWay;
    }

    private int minimalAmountScan(Scanner scanner){
        int minimalAmount = -1;
        while(minimalAmount == -1){
            System.out.println("please enter the minimal amount");
            try {
                minimalAmount = scanner.nextInt();
            }catch (Exception e){
                System.out.println("minimal amount must be a number\n");
                scanner.nextLine();
            }
        }
        return minimalAmount;
    }

    private int discountScan(Scanner scanner){
        int discount = -1;
        while(discount == -1){
            scanner.nextLine();
            System.out.println("please enter the discount");
            try {
                discount = scanner.nextInt();
            }catch (Exception e){
                System.out.println("discount must be a number.\n");
                scanner.nextLine();
            }
        }
        return discount;
    }

    private String contactPhoneScan(Scanner scanner , String isNew) {
        boolean toContinue = true;
        String phone = "";
        while (toContinue) {
            try {
                phone = scanner.nextLine();
                if(!phone.equals("")) toContinue = false;
                if(toContinue) System.out.println("please enter the" + isNew + " supplier contact phone");
            }catch (Exception e) {
                System.out.println("you must enter a phone\n");
            }
        }
        return phone;
    }

    private String contactEmailScan(Scanner scanner , String isNew) {
        boolean toContinue = true;
        String email = "";
        while (toContinue) {
            try {
                email = scanner.nextLine();
                if(!email.equals("")) toContinue = false;
                if(toContinue) System.out.println("please enter the" + isNew +" supplier contact email");
            }catch (Exception e) {
                System.out.println("you must enter an email\n");
            }
        }
        return email;
    }

    private String contactNameScan(Scanner scanner) {
        boolean toContinue = true;
        String name = "";
        while (toContinue) {
            try {
                if(!name.equals("")) toContinue = false;
                if(toContinue) System.out.println("please enter supplier contact name");
                name = scanner.nextLine();
            } catch (Exception e) {
                System.out.println("you must enter a name\n");
            }
        }
        return name;
    }

    private boolean constantTimeScan(Scanner scanner) {
        boolean constantTime = true;
        boolean hasChanged = false;
        while(!hasChanged){
            System.out.println("please enter true for constant time or false otherwise");
            try {
                constantTime = scanner.nextBoolean();
                hasChanged = true;
            } catch (Exception e){
                System.out.println("you must enter true or false.\n");
                hasChanged = false;
                scanner.nextLine();
            }
        }
        return constantTime;
    }

    private boolean shipToUsScan(Scanner scanner) {
        boolean shipToUs = true;
        boolean hasChanged = false;
        while(!hasChanged){
            System.out.println("please enter true for ship to us or false otherwise");
            try {
                scanner.nextLine();
                shipToUs = scanner.nextBoolean();
                hasChanged = true;
            } catch (Exception e){
                System.out.println("shipToUs time must be true or false.\n");
                hasChanged = false;
                scanner.nextLine();
            }
        }
        return shipToUs;
    }

    private double priceScan(Scanner scanner){
        double price = -1;
        while(price == -1){
            System.out.println("please enter the price");
            try {
                price = scanner.nextDouble();
                scanner.nextLine();
            }catch (Exception e){
                System.out.println("price must be a number\n");
                scanner.nextLine();
            }
        }
        return price;
    }

    private LocalDate dateScan(Scanner scanner){
        int month = -1;
        while(month == -1){
            System.out.println("please enter  the month of the deliver time of the order");
            try {
                scanner.nextLine();
                month = scanner.nextInt();
            }catch (Exception e){
                System.out.println("month must be a number.\n");
            }
        }
        int day = -1;
        while(day == -1){
            System.out.println("please enter the day in the month of the deliver time of the order");
            try {
                scanner.nextLine();
                day = scanner.nextInt();
            }catch (Exception e){
                System.out.println("day must be a number.\n");
            }
        }
        return LocalDate.of(LocalDate.now().getYear(), month , day);
    }

    private int amountScan(Scanner scanner){
        int amount = -1;
        while(amount == -1){
            System.out.println("please enter the amount of item");
            try {
                amount = scanner.nextInt();
                scanner.nextLine();
            }catch (Exception e){
                System.out.println("amount must be a number.\n");
                scanner.nextLine();
            }
        }
        return amount;
    }

    private int deliverDaysScan(Scanner scanner){
        int deliverDays = -1;
        while(deliverDays == -1){
            System.out.println("please enter the deliver days of the regular Order.");
            try {
                deliverDays = scanner.nextInt();
                scanner.nextLine();
            }catch (Exception e){
                System.out.println("deliver days must be a number.\n");
                scanner.nextLine();
            }
        }
        return deliverDays;
    }

    private Hashtable<Integer , Integer> constantOrderScan(Scanner scanner){
        Hashtable<Integer , Integer> items = new Hashtable<>();
        int toStop = -2;
        int ItemId;
        int amount = -1;
        while(toStop != -1){
            ItemId = itemScan(scanner);
            System.out.println("please enter the amount of item");
            try {
                amount = scanner.nextInt();
                scanner.nextLine();
            }catch (Exception e){
                System.out.println("amount must be a number.\n");
                scanner.nextLine();
            }
            items.put(ItemId , amount);
            System.out.println("to put more item , pls press any number beside -1");
            try {
                toStop = scanner.nextInt();
                scanner.nextLine();
            }catch (Exception e){
                System.out.println("amount must be a number.\n");
                scanner.nextLine();
            }
        }
        return items;
    }

    private String itemNameScanner(Scanner scanner){
        boolean toContinue = true;
        String name = "";
        while (toContinue) {
            try {
                if(name.equals("")) name = scanner.nextLine();
                if(!name.equals("")) toContinue = false;
                if(toContinue)  System.out.println("please enter item name");
            }catch (Exception e) {
                System.out.println("you must enter a name\n");
            }
        }
        return name;
    }

    private void toContinue(Scanner scanner , boolean first){
        boolean toContinue = true;
        while (toContinue) {
            if(!first) scanner.nextLine();
            first = true;
            System.out.println("\nto continue please use enter");
            if(scanner.nextLine().equals("")) toContinue = false;
        }
    }

    private int menuCheck(int n, Scanner scanner) {
        boolean flag = false;
        while (!flag) {
            try {
                n = scanner.nextInt();
                flag = true;
            }
            catch (NumberFormatException e) {
                System.out.println("illegal!\n please enter a number");
                scanner.nextLine();
            }
        }
        return n;
    }
}


