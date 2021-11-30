package com.company;

import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Tenant{
    public HashMap<Integer, Person> personMap = new HashMap<>();
    Person loggedUser;
    Developer developer;
    TimeManager timeManager;

    public Tenant(Developer developer, TimeManager timeManager){
        this.developer = developer;
        this.timeManager = timeManager;
    }

    public void logIn(){
        System.out.println("Please, enter your ID:");
        Scanner scan = new Scanner(System.in);
        int id = scan.nextInt();
        if (id <= this.personMap.size()){
            this.loggedUser = this.personMap.get(id);
            chooseAction();
        } else{
            System.err.println("Invalid ID. Please, try again.");
            logIn();
        }
    }

    public void chooseAction(){
        System.out.println("\nWhat would You like to do?\n[C]heck your data" +
                "\n[R]ent\n[S]et your payments\n[Re]gister or [U]nregister someone in one of Your apartments\n[M]anage your items\n[L]og off\n[E]xit");
        Scanner scan = new Scanner(System.in);
        String answer = scan.nextLine();
        switch (answer.toLowerCase()){
            case "c":{
                manageData();
            } break;
            case "r":{
                rentMenu();
            } break;
            case "s":{
                setPaymentsMenu();
            } break;
            case "re":{
                registerMenu();
            } break;
            case "u":{
                unregisterMenu();
            } break;
            case "m":{
                manageItemsMenu();
            } break;
            case "l":{
                System.out.println("You have successfully logged off.");
            } break;
            case "e":{
                System.out.println("Now exiting The Estate Manager.");
                System.exit(0);
            } break;
            default:{
                System.err.println("Invalid answer. Please, choose again.");
                chooseAction();
            }
        }
    }

    public void manageData(){
        System.out.println("\nWhat would You like to do?\n[V]iew your details" +
                "\n[S]ave current details\n[G]o back to previous menu\n[L]og off\n[E]xit");
        Scanner scan = new Scanner(System.in);
        String answer = scan.nextLine();
        switch (answer.toLowerCase()) {
            case "v":{
                System.out.println(this.loggedUser.toString());
                this.loggedUser.showOwnedAreaList();
                manageData();
            } break;
            case "s":{
                saveData();
                manageData();
            } break;
            case "g":{
                chooseAction();
            } break;
            case "l":{
                System.out.println("You have successfully logged off.");
            } break;
            case "e":{
                System.out.println("Now exiting The Estate Manager.");
                System.exit(0);
            } break;
            default:{
                System.err.println("Invalid answer. Please, choose again.");
                manageData();
            }
        }
    }

    public void saveData(){
        try {
            FileWriter tenantLog = new FileWriter("tenantLog.txt");
            PrintWriter pw = new PrintWriter(tenantLog);
            pw.println(this.loggedUser.toString());
            pw.close();
            System.out.println("Data saved in The Estate Manager directory.");
        } catch (FileNotFoundException e){
            System.err.println("Wrong directory. Please contact customer service.");
        } catch (IOException e){
            e.printStackTrace();
        }
    }


    public void rentMenu(){
        try {
            if (this.loggedUser.getOwnedAreasCounter() >= 5) {
                System.out.println("You can't rent anything more.");
                chooseAction();
            } else if (this.loggedUser.paymentDelayList.size() >= 3) {
                throw new ProblematicTenantException(this.loggedUser);
            } else {
                System.out.println("\nWould You like to rent \n[A]partment\n[P]arking space\n[N]othing");
                Scanner scan = new Scanner(System.in);
                String answer = scan.nextLine();
                switch (answer.toLowerCase()) {
                    case "a": {
                        rentApartment();
                        chooseAction();
                    }
                    break;
                    case "p": {
                        rentParkingSpace();
                        chooseAction();
                    }
                    break;
                    case "n": {
                        chooseAction();
                    }
                    break;
                    default: {
                        System.err.println("Invalid answer. Please, choose again.");
                        rentMenu();
                    }
                }
            }
        }catch (ProblematicTenantException e){e.message();}
    }

    public void rentApartment(){
        this.developer.showAvailableApartmentList();
        System.out.println("\nPlease, enter ID of the apartment you're interested in:");
        Scanner scan = new Scanner(System.in);
        try {
            int Id = scan.nextInt();
            for (int i = 0; i < this.developer.residentialAreaList.size(); i++) {
                ResidentialArea residentialArea = this.developer.residentialAreaList.get(i);
                for (int j = 0; j < residentialArea.apartmentBuildingList.size(); j++) {
                    ApartmentBuilding apartmentBuilding = residentialArea.apartmentBuildingList.get(j);
                    for (int k = 0; k < apartmentBuilding.apartmentList.size(); k++) {
                        Apartment apartment = apartmentBuilding.apartmentList.get(k);
                        if (apartment.getId() == Id && apartment.getOwner() == null) {
                            apartment.assignOwner(this.loggedUser, this.timeManager);
                            System.out.println("Apartment rented successfully");
                            this.loggedUser.addToOwnedAreaList(apartment);
                            if (this.loggedUser.getOwnedAreasCounter() == 1) {
                                this.loggedUser.setAddress(apartment);
                                apartment.addToResidentList(this.loggedUser);
                                return;
                            } return;
                        }
                    }
                }
            } System.err.println("This apartment isn't available. Please, try again.");
        } catch (InputMismatchException e){
            System.err.println("Invalid input. Please, try again.");
            rentApartment();
        }
    }

    public void rentParkingSpace() {
        this.developer.showAvailableParkingSpaceList();
        int apartmentCounter = 0;
        for (int i = 0; i < this.loggedUser.ownedAreaList.size(); i++) {
            if (this.loggedUser.ownedAreaList.get(i) instanceof Apartment)
                apartmentCounter++;
        }
        if (apartmentCounter > 0){
            System.out.println("\nPlease, enter ID of the parking space you're interested in:");
            try{
                Scanner scan = new Scanner(System.in);
                int Id = scan.nextInt();
                for (int i = 0; i < this.developer.residentialAreaList.size(); i++) {
                    ResidentialArea res = this.developer.residentialAreaList.get(i);
                    for (int j = 0; j < res.parkingSpaceList.size(); j++) {
                        ParkingSpace parkingSpace = res.parkingSpaceList.get(j);
                        if (parkingSpace.getId() == Id && parkingSpace.getOwner() == null) {
                            parkingSpace.assignOwner(this.loggedUser, this.timeManager);
                            System.out.println("Parking space rented successfully");
                            this.loggedUser.addToOwnedAreaList(parkingSpace);
                            return;
                        }
                    }
                } System.err.println("This parking space isn't available. Please, try again.");
            }catch (InputMismatchException e){
                System.err.println("Invalid input. Please, try again.");
                rentParkingSpace();
            }
        } else {
            try {
                Thread.sleep(100);
                System.err.println("You have to rent apartment first.");
                rentMenu();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void setPaymentsMenu(){
        System.out.println("Your current settings: ");
        for (int i = 0; i < this.loggedUser.ownedAreaList.size(); i++) {
            UsableFloorArea ownedArea = this.loggedUser.ownedAreaList.get(i);
            String areaClass;
            if (ownedArea instanceof Apartment)
                areaClass = "Apartment";
            else
                areaClass = "Parking space";
            System.out.println(areaClass + ": [ID: " + ownedArea.getId() + ", Lease agreement set to renewal: " + ownedArea.getIsSetToRenew() + "]");
        } System.out.println("Do You want to change the settings?\n[Y]es\n[N]o");
        Scanner scan = new Scanner(System.in);
        String answer = scan.nextLine();
        switch (answer.toLowerCase()) {
            case "y": {
                setPayments();
            } break;
            case "n": {
                chooseAction();
            } break;
            default: {
                System.err.println("Invalid answer. Please, choose again.");
                setPaymentsMenu();
            }
        }
    }

    public void setPayments(){
        System.out.println("Please, enter ID of the area, which payment setting you want to change:");
        Scanner scan = new Scanner(System.in);
        int id = scan.nextInt();

        for (int i = 0; i < this.loggedUser.ownedAreaList.size(); i++) {
            UsableFloorArea ownedArea = this.loggedUser.ownedAreaList.get(i);
            if (ownedArea.getId() == id){
                boolean settingCheck = ownedArea.getIsSetToRenew();
                if (settingCheck)
                    ownedArea.setToRenew(false);
                else {
                    ownedArea.setToRenew(true);
                    if (timeManager.getDate().isBefore(ownedArea.getLeaseFinish().plusDays(30))) {
                        this.loggedUser.paymentDelayCounter--;
                        this.loggedUser.paymentDelayList.remove(0);
                    }
                }
            } else {
                System.err.println("Invalid ID. Please, try again.");
                setPayments();
            }
        } setPaymentsMenu();
    }

    public void registerMenu(){
        int apartmentCounter = 0;
        for (int i = 0; i < this.loggedUser.ownedAreaList.size(); i++) {
            if (this.loggedUser.ownedAreaList.get(i) instanceof Apartment)
                apartmentCounter++;
        }
        if (apartmentCounter > 0) {
            System.out.println("Would You like to register someone?\n[Y]es\n[N]o");
            Scanner scan = new Scanner(System.in);
            String answer = scan.nextLine();

            switch (answer.toLowerCase()) {
                case "y": {
                    register();
                    registerMenu();
                } break;
                case "n": {
                    chooseAction();
                } break;
                default: {
                    System.err.println("Invalid answer. Please, choose again.");
                    registerMenu();
                }
            }
        }
        else {
            System.err.println("You don't have any apartments.");
            chooseAction();
        }
    }

    public void register(){
        System.out.println("Your apartments:");
        for (int i = 0; i < this.loggedUser.ownedAreaList.size(); i++) {
            UsableFloorArea ownedArea = this.loggedUser.ownedAreaList.get(i);
            if (ownedArea instanceof Apartment)
                System.out.println(ownedArea.toString());
        }
        System.out.println("Please, enter the ID of the apartment, in which you want to register someone:");
        try {
            Scanner scan = new Scanner(System.in);
            int idApartment = scan.nextInt();
            registerApartmentIdCheck(idApartment);
            Person registeredPerson = registerPersonSet();
            registerPersonCheck(registeredPerson, idApartment);
            for (int i = 0; i < this.loggedUser.ownedAreaList.size(); i++) {
                UsableFloorArea ownedArea = this.loggedUser.ownedAreaList.get(i);
                if (ownedArea instanceof Apartment && ownedArea.getId() == idApartment) {
                    ((Apartment) ownedArea).addToResidentList(registeredPerson);
                    registeredPerson.setAddress((Apartment)ownedArea);
                    System.out.println("Person successfully registered.");
                    return;
                }
            }
        } catch (InputMismatchException e){
            System.err.println("Invalid input. Please, try again.");
            register();
        }
    }
    public void unregisterMenu(){
        int apartmentCounter = 0;
        for (int i = 0; i < this.loggedUser.ownedAreaList.size(); i++) {
            if (this.loggedUser.ownedAreaList.get(i) instanceof Apartment)
                apartmentCounter++;
        }
        if (apartmentCounter > 0) {
            System.out.println("Would You like to unregister someone?\n[Y]es\n[N]o");
            Scanner scan = new Scanner(System.in);
            String answer = scan.nextLine();

            switch (answer.toLowerCase()) {
                case "y": {
                    unregister();
                    unregisterMenu();
                } break;
                case "n": {
                    chooseAction();
                } break;
                default: {
                    System.err.println("Invalid answer. Please, choose again.");
                    unregisterMenu();
                }
            }
        }
        else {
            System.err.println("You don't have any apartments.");
            chooseAction();
        }
    }


    public void unregister(){
        System.out.println("Your apartments:");
        for (int i = 0; i < this.loggedUser.ownedAreaList.size(); i++) {
            UsableFloorArea ownedArea = this.loggedUser.ownedAreaList.get(i);
            if (ownedArea instanceof Apartment)
                System.out.println(ownedArea.toString());
        }
        System.out.println("Please, enter the ID of the apartment, in which you want to unregister someone:");
        try {
            Scanner scan = new Scanner(System.in);
            int idApartment = scan.nextInt();
            registerApartmentIdCheck(idApartment);
            Person unregisteredPerson = registerPersonSet();
            for (int i = 0; i < this.loggedUser.ownedAreaList.size(); i++) {
                UsableFloorArea ownedArea = this.loggedUser.ownedAreaList.get(i);
                if (ownedArea instanceof Apartment && ownedArea.getId() == idApartment) {
                    ((Apartment) ownedArea).residentList.remove(unregisteredPerson);
                    unregisteredPerson.setAddress(null);
                    System.out.println("Person successfully unregistered.");
                    return;
                }
            }
        } catch (InputMismatchException e){
            System.err.println("Invalid input. Please, try again.");
            register();
        }
    }

    public void registerApartmentIdCheck(int id){
        for (int i = 0; i < this.loggedUser.ownedAreaList.size(); i++) {
            if (this.loggedUser.ownedAreaList.get(i).getId() == id) {
                return;
            }
        } System.err.println("You don't own an apartment with this ID. Please, try again.\n");
        register();
    }

    public Person registerPersonSet() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Please, enter the ID of the person:");
        try {
            int id = sc.nextInt();
            Person registeredPerson = this.personMap.get(id);
            if (registeredPerson == null) {
                System.err.println("There is no account with this ID. Please, try again.\n");
                registerPersonSet();
            }
            else
                return registeredPerson;
        } catch (InputMismatchException e){
            System.err.println("Invalid input. Please, try again.");
            registerPersonSet();
        } return null;
    }

    public void registerPersonCheck(Person registeredPerson, int idApartment){
        for (int i = 0; i < this.loggedUser.ownedAreaList.size(); i++) {
            UsableFloorArea ownedArea = this.loggedUser.ownedAreaList.get(i);
            if (ownedArea.getId() == idApartment && ownedArea instanceof Apartment) {
                for (int j = 0; j < ((Apartment) ownedArea).residentList.size(); j++) {
                    Person personCheck = ((Apartment) ownedArea).residentList.get(j);
                    if (registeredPerson.getPesel() == personCheck.getPesel()) {
                        System.err.println("This person is already registered.");
                        registerMenu();
                    }
                }
            }
        }
    }

    public void manageItemsMenu() {
        System.out.println("\nWhat would you like to do? \n[S]how content of your owned areas\n[A]dd item do owned area" +
                "\n[R]emove item from owned area\n[G]o back to previous menu\n[L]og off\n[E]xit");
        Scanner scan = new Scanner(System.in);
        String answer = scan.nextLine();
        switch (answer.toLowerCase()) {
            case "s":{
                manageItemsShow();
                manageItemsMenu();
            } break;
            case "a":{
                manageItemsAdd();
                manageItemsMenu();
            } break;
            case "r":{
                manageItemsRemove();
                manageItemsMenu();
            } break;
            case "g":{
                chooseAction();
            } break;
            case "l":{
                System.out.println("You have successfully logged off.");
            } break;
            case "e":{
                System.out.println("Now exiting The Estate Manager.");
                System.exit(0);
            }
            default:{
                System.err.println("Invalid answer. Please, choose again.");
                manageItemsMenu();
            }
        }
    }

    public void manageItemsAdd(){
        System.out.println("What would you like to do? \nAdd item to [A]partment or [P]arking space\n[G]o back to previous menu");
        Scanner scan = new Scanner(System.in);
        String answer = scan.nextLine();
        System.out.println("Please, enter ID of the area: ");
        int id = scan.nextInt();
        for (int i = 0; i < this.loggedUser.ownedAreaList.size(); i++) {
            switch (answer.toLowerCase()) {
                case "a": {
                    UsableFloorArea ownedArea = this.loggedUser.ownedAreaList.get(i);
                    if (ownedArea instanceof Apartment && ownedArea.getId() == id) {
                        if (ownedArea.addStuff(createStuffMenu()))
                            System.out.println("Item added successfully\n");
                    }
                    else {
                        System.err.println("You don't own apartment with this ID. Please, try again.");
                        manageItemsAdd();
                    } break;
                }
                case "p": {
                    UsableFloorArea ownedArea = this.loggedUser.ownedAreaList.get(i);
                    if (ownedArea instanceof ParkingSpace && ownedArea.getId() == id) {
                        ownedArea.addStuff(createStuffMenu());
                        System.out.println("Item added successfully\n");
                    }
                    else {
                        System.err.println("You don't own parking space with this ID. Please, try again.");
                        manageItemsAdd();
                    } break;
                }
                case "g":
                    manageItemsMenu();
                    break;
                default:
                    System.err.println("Invalid answer. Please, try again.");
                    manageItemsAdd();
                    break;
            }
        }
    }

    public Stuff createStuffMenu(){
        System.out.println("What would you like to add?\n[V]echicle\n[S]omething else\n[N]othing");
        Scanner scan = new Scanner(System.in);
        String answer = scan.nextLine();
        switch (answer.toLowerCase()) {
            case "v": {
                return createVehicle();
            }
            case "s": {
                return createMisc();
            }
            case "n": {
                manageItemsAdd();
            } break;
            default: {
                System.err.println("Invalid answer. Please, try again.");
                createStuffMenu();
            }
        } return null;
    }

    public Stuff createVehicle(){
        String name;
        String color;
        System.out.println("What type of vehicle would you like to add?\n[C]ar\n[B]oat\n[M]otorcycle\n[A]mphibian\n[N]one");
        Scanner scan = new Scanner(System.in);
        String answer = scan.nextLine();
        System.out.println("Would You like to give \n[S]ize\nor\n[D]imensions\nof your vehicle?");
        String sizeType = scan.nextLine();
        int size = getSize(sizeType);
        System.out.println("Please, enter name of your vehicle:");
        name = scan.nextLine();
        System.out.println("Please, enter color of your vehicle (black, red, green, white):");
        color = scan.nextLine();
        switch (answer.toLowerCase()) {
            case "c": {
                return createCar(name, size, color);
            }
            case "b": {
                return createBoat(name, size, color);
            }
            case "m": {
                return createMotorcycle(name, size, color);
            }
            case "a": {
                return createAmphibian(name, size, color);
            }
            case "n": {
                createStuffMenu();
            }
            break;
            default: {
                System.err.println("Invalid answer. Please, try again.");
                createVehicle();
            }
        }
        return null;
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
                    createVehicle();
                }
            } } catch (InputMismatchException e){
            System.err.println("Invalid input. Please, try again.");
            createStuffMenu();
        } return 0;
    }

    public Stuff createCar(String name, int size, String color){
        Scanner scan = new Scanner(System.in);
        System.out.println("Is your car a\n[C]ity car\nor\n[O]ff-road car?");
        String type = scan.nextLine();
        switch (type.toLowerCase()) {
            case "c": {
                return new Car(name, size, color, "city car");
            }
            case "o": {
                return new Car(name, size, color, "off-road car");
            }
            default: {
                System.err.println("Invalid answer. Please, try again.");
                createCar(name, size, color);
            }
        }
        return null;
    }

    public Stuff createBoat(String name, int size, String color){
        Scanner scan = new Scanner(System.in);
        System.out.println("Is your boat a motorboat?\n[Y]es\n[N]o");
        String isMotorboat = scan.nextLine();
        switch (isMotorboat.toLowerCase()) {
            case "y": {
                return new Boat(name, size, color, true);
            }
            case "n": {
                return new Boat(name, size, color, false);
            }
            default: {
                System.err.println("Invalid answer. Please, try again.");
                createBoat(name, size, color);
            }
        }
        return null;
    }

    public Stuff createMotorcycle(String name, int size, String color){
        Scanner scan = new Scanner(System.in);
        System.out.println("Does your motorcycle has a backseat?\n[Y]es\n[N]o");
        String hasBackseat = scan.nextLine();
        switch (hasBackseat.toLowerCase()) {
            case "y": {
                return new Motorcycle(name, size, color, true);
            }
            case "n": {
                return new Motorcycle(name, size, color, false);
            }
            default: {
                System.err.println("Invalid answer. Please, try again.");
                createMotorcycle(name, size, color);
            }
        }
        return null;
    }

    public Stuff createAmphibian(String name, int size, String color){
        Scanner scan = new Scanner(System.in);
        System.out.println("Is your amphibian suitable for sea travels?\n[Y]es\n[N]o");
        String suitableForSea = scan.nextLine();
        switch (suitableForSea.toLowerCase()) {
            case "y": {
                return new Amphibian(name, size, color, true);
            }
            case "n": {
                return new Amphibian(name, size, color, false);
            }
            default: {
                System.err.println("Invalid answer. Please, try again.");
                createAmphibian(name, size, color);
            }
        }
        return null;
    }

    public Stuff createMisc(){
        String name;
        String material;
        System.out.println("What would you like to add?");
        Scanner scan = new Scanner(System.in);
        name = scan.nextLine();
        System.out.println("What material is your object made from?");
        material = scan.nextLine();
        System.out.println("Would You like to give \n[S]ize\nor\n[D]imensions\nof your object?");
        String sizeType = scan.nextLine();
        int size = getSize(sizeType);
        return new Misc(name, size, material);
    }

    public void manageItemsRemove() {
        System.out.println("What would you like to do?\n Remove item from\n[A]partment\nor\n[P]arking space\n[G]o back to previous menu");
        Scanner scan = new Scanner(System.in);
        String answer = scan.nextLine();
        System.out.println("Please, enter ID of the area: ");
        int id = scan.nextInt();

        for (int i = 0; i < this.loggedUser.ownedAreaList.size(); i++) {
            switch (answer.toLowerCase()) {
                case "a": {
                    UsableFloorArea ownedArea = this.loggedUser.ownedAreaList.get(i);
                    if (ownedArea instanceof Apartment && ownedArea.getId() == id) {
                        ownedArea.showStuffList();
                        System.out.println("Please, enter index of the object you would like to remove:");
                        int index = scan.nextInt();
                        Stuff stuff = ownedArea.stuffList.get(index);
                        ownedArea.removeStuff(stuff);
                        System.out.println("Item removed successfully\n");
                    }
                    break;
                }
                case "p": {
                    UsableFloorArea ownedArea = this.loggedUser.ownedAreaList.get(i);
                    if (ownedArea instanceof ParkingSpace && ownedArea.getId() == id) {
                        ownedArea.showStuffList();
                        System.out.println("Please, enter index of the object you would like to remove:");
                        int index = scan.nextInt();
                        Stuff stuff = ownedArea.stuffList.get(index);
                        ownedArea.removeStuff(stuff);
                        System.out.println("Item removed successfully\n");
                    }
                    break;
                }
                case "g":
                    manageItemsMenu();
                    break;
                default:
                    System.err.println("Invalid answer. Please, try again.");
                    manageItemsRemove();
                    break;
            }
        }
    }

    public void manageItemsShow() {
        for (int i = 0; i < this.loggedUser.ownedAreaList.size(); i++) {
            this.loggedUser.ownedAreaList.get(i).showStuffList();
            System.out.println();
        }
    }
}

class ProblematicTenantException extends Exception{
    private Person tenant;

    public ProblematicTenantException(Person tenant){
        this.tenant = tenant;
    }
    public void message(){
        System.out.println(this.tenant.getName() + " " + this.tenant.getSurname() + " already owned following areas:\n" + this.tenant.ownedAreaListHistory.toString());
    }
}