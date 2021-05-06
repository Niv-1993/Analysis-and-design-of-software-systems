package Presentation;

import org.apache.log4j.Logger;

import java.util.Scanner;

public class mainCLI {
    PresentationCL presentationCL;
    StockCLI stockCLI;
    Scanner scan=new Scanner(System.in);
    final static Logger log= Logger.getLogger(StockCLI.class);

    String read(){
        return scan.nextLine().toLowerCase().replaceAll("\\s", "");
    }

    public mainCLI(){
        presentationCL = new PresentationCL();
        stockCLI = new StockCLI();
    }

    private int menuCheck(Scanner scanner) {
        String choice;
        int toReturn;
        while (true) {
            try {
                choice = read();
                toReturn = Integer.parseInt(choice);
                break;
            }
            catch (NumberFormatException e) {
                System.out.println("illegal!\n please enter a number");
                scanner.nextLine();
            }
        }
        return toReturn;
    }

    public void start(boolean firstTime){
        String in;
        if(firstTime) {
            String[] load = {"load last data", "new data"};
            while (true) {
                System.out.println("please select an option: ");
                for (int i = 1; i <= load.length; i++) {
                    System.out.println(i + ") " + load[i - 1]);
                }
                int n = -1;
                n = menuCheck(scan);
                switch (n) {
                    case 1 -> {
                        presentationCL.loadData();
                        /// need to add here Stock.loadData();
                    }
                    case 2 -> { }
                    default -> System.out.println("illegal option!!!");
                }
                if(n == 1 || n == 2) break;
            }
        }
        while (true){
            System.out.println("please enter 1 for Suppliers and 2 for Stock or 3 to exit");
            in = read();
            try {
                if(Integer.parseInt(in) == 1) presentationCL.mainRun(true);
                else if(Integer.parseInt(in) == 2) stockCLI.start();
                else if(Integer.parseInt(in) == 3) System.exit(0);
                else System.out.println("illegal input!!!");
            }catch (Exception e){
                System.out.println(e.getMessage());
            }
        }
    }

}
