package com.company;

import java.io.*;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Iterator;
import java.util.Scanner;

public class Developer{
    ArrayList<ResidentialArea> residentialAreaList = new ArrayList<>();

    public void chooseAction(){
        System.out.println("\nWhat would You like to do?\n[V]iew list of owned residential areas" +
                "\n[A]dd new apartments\n[S]ave current data\n[B]uy new residential area\n[L]og off\n[E]xit");
        Scanner scan = new Scanner(System.in);
        String answer = scan.nextLine();
        switch (answer.toLowerCase()) {
            case "v": {
                showResidentialAreaList();
                chooseAction();
            } break;
            case "a": {
                createApartmentMenu();
            } break;
            case "s": {
                saveData();
                chooseAction();
            } break;
            case "b": {
                buyNewResidentialArea();
                chooseAction();
            } break;
            case "l": {
                System.out.println("You have successfully logged off.");
            } break;
            case "e": {
                System.out.println("Now exiting The Estate Manager.");
                System.exit(0);
            }
            default: {
                System.err.println("Invalid answer. Please, choose again.");
                chooseAction();
            }
        }
    }


    public void buyNewResidentialArea(){
        System.out.println("\nDo You want to buy new residential area?\n[Y]es\n[N]o");
        Scanner scan = new Scanner(System.in);
        String answer = scan.nextLine();

        switch (answer.toLowerCase()) {
            case "y": {
                System.out.println("Please, enter residential area name:");
                String name = scan.nextLine();
                ResidentialArea newResidentialArea = new ResidentialArea(name);
                this.residentialAreaList.add(newResidentialArea);
            } break;
            case "n":
                chooseAction();
                break;
            default: {
                System.err.println("Invalid answer. Please, choose again.");
                buyNewResidentialArea();
            }
        }
    }

    public void showResidentialAreaList(){
        Iterator<ResidentialArea> iterator = this.residentialAreaList.iterator();

        System.out.print("Residential area list:");
        while (iterator.hasNext())
            System.out.print(iterator.next() + " ");
        System.out.println();
    }

    public void showResientialAreas(){
        System.out.println("Your residential areas:");
        for (ResidentialArea residentialArea : this.residentialAreaList) {
            System.out.println(residentialArea.name);
        }
    }

    public void showAvailableApartmentList(){
        System.out.println("Apartments available for rent:");
        for (ResidentialArea residentialArea : this.residentialAreaList) {
            for (int j = 0; j < residentialArea.apartmentBuildingList.size(); j++) {
                ApartmentBuilding apartmentBuilding = residentialArea.apartmentBuildingList.get(j);
                for (int k = 0; k < apartmentBuilding.apartmentList.size(); k++) {
                    Apartment ap = apartmentBuilding.apartmentList.get(k);
                    if (ap.getOwner() == null)
                        System.out.println(ap.toString());
                }
            }
        }
    }

    public void showAvailableParkingSpaceList() {
        System.out.println("Parking spaces available for rent:");
        for (ResidentialArea res : this.residentialAreaList) {
            for (int j = 0; j < res.parkingSpaceList.size(); j++) {
                ParkingSpace parkingSpace = res.parkingSpaceList.get(j);
                if (parkingSpace.getOwner() == null)
                    System.out.println(res.parkingSpaceList.get(j).toString());
            }
        }
    }

    public void createApartmentMenu() {
        Scanner scan = new Scanner(System.in);
        System.out.println("Would you like to create a new apartment?\n[Y]es\n[N]o");
        String answer = scan.nextLine();
        if (answer.toLowerCase().equals("y")) {
            createApartment();
        }
        else if (answer.toLowerCase().equals("n"))
            chooseAction();
        else{
            System.err.println("Invalid answer. Please, try again.");
            createApartmentMenu();
        }
    }

    public void createApartment(){
        Scanner scan = new Scanner(System.in);
        showResientialAreas();
        System.out.println("\nPlease, enter name of the residential area, to add new apartment to:");
        String name = scan.nextLine();
        for (ResidentialArea residentialArea : this.residentialAreaList) {
            if (residentialArea.name.equals(name)) {
                System.out.println("Would You like to give \n[S]ize\nor\n[D]imensions\nof the new apartment?");
                String sizeType = scan.nextLine();
                residentialArea.createApartment(getSize(sizeType));
                System.out.println("Apartment created successfully.");
                chooseAction();
            }
        } System.err.println("Invalid residential area name. Please, try again.");
        createApartmentMenu();
    }

    public int getSize (String sizeType){
        Scanner scan = new Scanner(System.in);
        int size;
        try {
            switch (sizeType.toLowerCase()) {
                case "s": {
                    System.out.println("Please, enter size:");
                    size = scan.nextInt();
                } return size;
                case "d": {
                    System.out.println("Please, enter width:");
                    int width = scan.nextInt();
                    System.out.println("Please, enter height:");
                    int height = scan.nextInt();
                    System.out.println("Please, enter length:");
                    int length = scan.nextInt();
                    size = (width * height * length);
                } return size;
                default: {
                    System.err.println("Invalid answer. Please, try again.");
                    createApartmentMenu();
                }
            } } catch (InputMismatchException e){
            System.err.println("Invalid input. Please, try again.");
            createApartmentMenu();
        } return 0;
    }

    public void saveData() {
        try {
            FileWriter developerLog = new FileWriter("developerLog.txt");
            PrintWriter pw = new PrintWriter(developerLog);
            pw.println(this.residentialAreaList.toString());
            pw.close();
            System.out.println("Data saved in The Estate Manager directory.");
        } catch (FileNotFoundException e){
            System.err.println("Wrong directory. Please contact customer service.");
        } catch (IOException e){
            e.printStackTrace();
        }
    }
}
