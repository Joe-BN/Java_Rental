package com;
import java.util.Scanner;
import com.models.*;

public class Main {
    public static void main(String[] args) {
             
        Scanner sc = new Scanner(System.in);
        System.out.println("=== VEHICLE RENTAL SYSTEM ===");

        while (true) {
            System.out.println("\n1. View Available Vehicles");
            System.out.println("2. Rent a Vehicle");
            System.out.println("3. Return a Vehicle");
            System.out.println("4. View Rental Records");
            System.out.println("5. Exit");
            
            System.out.print("\nChoose an option: ");
            int choice = sc.nextInt();
            sc.nextLine(); // consume newline

            Database db = new Database();

            switch (choice) {
                case 1 -> {
                    System.out.println("\n------------------------ Available Vehicles ------------------------ \n");
                    System.out.println("Category \t Brand \t\t Model \t\t DailyRate \t Stock");
                    // get all the cars in the db -> array as a function \t
                    String[] vehicles = db.getavailableVehicles();
                    for (int i = 0; i < vehicles.length; i++) {
                        System.out.println(vehicles[i]);
                    }
                }

                case 2 -> {
                    // enter renter information
                    String email = "";
                    boolean state = true;

                    System.out.print("Enter customer email: ");
                    email = sc.nextLine();
                    while (state) {
                        if (!db.isValidEmail(email)) {
                            System.out.print("Enter a VALID customer email (<user>@<gmail.com>): ");
                            email = sc.nextLine();
                        } else {
                            state=false;
                        }
                    }

                    // inputs for vehicle particulars
                    String model = "";
                    String brand = "";

                    System.out.print("Enter EXACT vehicle Category (e.g. Car, Bike or Van): ");
                    String category = sc.nextLine();

                    if (category.equalsIgnoreCase("Car")) {
                        System.out.print("Enter EXACT vehicle Brand (Toyota, Nissan, Honda, Nissan): ");
                        brand = sc.nextLine();
                        System.out.print("Enter EXACT vehicle model (Corolla, Civic, Axela, Sunny): ");
                        model = sc.nextLine();
                        model = db.formatString(model);
                        System.out.print("Enter number of seats needed; (1, 2, 3, 4, 5): ");
                        int seats = sc.nextInt();
                        sc.nextLine(); // clear leftover newline

                        if (0>seats || seats>5){
                            System.out.println("\nSorry that number does not compute");
                            System.out.println("We will set you to default 5\n");
                            seats = 5;
                        }
                        car car = new car(brand, model, seats);
                        System.out.println ("\nYou are ordering a "+car.get_type()+" ...\n"); // later usable for more comples and object specific porcesses

                    } else if (category.equalsIgnoreCase("Bike")){
                        System.out.print("Enter EXACT vehicle Brand (Yamaha, Honda, Suzuki, Bajaj): ");
                        brand = sc.nextLine();
                        System.out.print("Enter EXACT bike model ;(YBR125,CB125F,GSX150,Boxer150): ");
                        model = sc.nextLine();
                        System.out.print("Enter EXACT bike spec; (Standard, Commuter, Sport, Utility)");
                        String spec = sc.nextLine();

                        bike bike = new bike(brand, model, spec);
                        System.out.println ("\nYou are ordering a "+bike.get_type()+" ...\n");

                    } else if (category.equalsIgnoreCase("Van")){
                        System.out.print("Enter EXACT vehicle Brand (Nissan, Toyota, Mazda, Hyundai): ");
                        brand = sc.nextLine();
                        System.out.print("Enter EXACT bike model ;(Caravan,Hiace,Bongo,H1): ");
                        model = sc.nextLine();
                        model = db.formatString(model);
                        System.out.print("Enter expected carrying capacity: (1 < X < 200) quibic metres: ");
                        int cap = sc.nextInt();
                        sc.nextLine();

                        if (cap<1 || cap>200) {
                            System.out.println("\nFor that, our services do not match");
                            System.out.println("We will set you to default 100 quibic metres\n");
                            cap = 5;
                        }

                        van van = new van(brand, model, cap);
                        System.out.println ("\nYou are ordering a "+van.get_type()+" ...\n");
                    } else {
                        System.out.println("Invalid input");
                    }


                    System.out.print("Enter number of days for rent: ");
                    int days = sc.nextInt();
                    
                    
                    String msg = db.placeOrder(category, brand, model,email, days);
                    System.out.println("\n"+msg);
                    
                }

                case 3 -> {
                    boolean state = true;
                    System.out.print("Enter customer email: ");
                    String returnEmail = sc.nextLine();

                    while (state) {
                        if (!db.isValidEmail(returnEmail)) {
                            System.out.print("Enter a Valid customer email: ");
                            returnEmail = sc.nextLine();
                        } else {
                            state=false;
                        }
                    }


                    System.out.print("Enter type rented; (Corolla,GSX150,...) ");
                    String returnCar = sc.nextLine();
                    db.formatString(returnCar);
                    String msg = db.returnVehicle(returnEmail, returnCar);
                    System.out.println(msg);
                }

                case 4 -> {
                    System.out.println("\n---Customer Rental Records---");
                    String[] rented = db.getRented();
                    if (rented.length==0){
                        System.out.println("There are no records at the moment\n");
                    } else {
                        for (int i = 0; i < rented.length; i++) {
                            System.out.println(rented[i]);
                        }
                    }
                }


                case 5 -> {
                    System.out.println("\nExiting system... Goodbye!");
                    sc.close();
                    return;
                }
                 
                default -> System.out.println("\nInvalid entry! Try again.");
            }
        }
    }
    
}
