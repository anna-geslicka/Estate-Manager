package com.company;

import java.util.InputMismatchException;
import java.util.Scanner;

public class User{
    private Developer developer;
    private Tenant tenant;
    private int personIdGenerator = 5;

    public User (Developer developer, Tenant tenant){
        this.developer = developer;
        this.tenant = tenant;
    }

    public void initialSet(){
        ResidentialArea sunnyVale = new ResidentialArea("Sunnyvale");
        this.developer.residentialAreaList.add(sunnyVale);
        sunnyVale.createApartment(100);
        sunnyVale.createApartment(150);
        sunnyVale.createApartment(200);
        sunnyVale.createApartment(4, 10, 5);
        sunnyVale.createApartment(4, 6, 8);
        sunnyVale.createApartment(120);
        sunnyVale.createApartment(210);
        sunnyVale.createApartment(3, 5, 8);
        sunnyVale.createApartment(250);
        sunnyVale.createApartment(180);

        this.tenant.personMap.put(1, new Person("Jake", "Smith", 97111118235L));
        this.tenant.personMap.put(2, new Person("Alicia", "Black", 83072110203L));
        this.tenant.personMap.put(3, new Person("Felix", "Black", 92100344256L));
        this.tenant.personMap.put(4, new Person("Rhonda", "Erickson", 95030411457L));
        this.tenant.personMap.put(5, new Person("Blair", "Johnson", 85100112377L));
    }

    public void chooseRole(){
        System.out.println("Choose role:\n[D]eveloper\n[T]enant\nor\n[E]xit");
        Scanner scan = new Scanner(System.in);
        String answer = scan.nextLine();

        switch (answer.toLowerCase()){
            case "d": {
                System.out.println("You have successfully logged in.");
                this.developer.chooseAction();
                chooseRole();
            } break;
            case "t": {
                tenantAction();
                chooseRole();
            } break;
            case "e": {
                System.out.println("Now exiting The Estate Manager.");
                System.exit(0);
            }
            default: {
                System.err.println("Invalid answer. Please, choose again.");
                chooseRole();
            }
        }
    }

    public void tenantAction(){
        System.out.println("What would You like to do?\n[L]og in\n[C]reate new account\n[G]o back to menu\n[E]xit");
        Scanner scan = new Scanner(System.in);
        String answer = scan.nextLine();
        switch (answer.toLowerCase()){
            case "l": {
                this.tenant.logIn();
                chooseRole();
            } break;
            case "c": {
                createNewAccount();
                tenantAction();
            } break;
            case "g": {
                chooseRole();
            } break;
            case "e": {
                System.out.println("Now exiting The Estate Manager.");
                System.exit(0);
            }
            default: {
                System.err.println("Invalid answer. Please, choose again.");
                tenantAction();
            }
        }
    }

    public void createNewAccount(){
        Scanner sc = new Scanner(System.in);
        System.out.println("\nEnter name: ");
        String name = sc.nextLine();
        System.out.println("Enter surname:");
        String surname = sc.nextLine();
        System.out.println("Enter PESEL: ");
        try {
            long pesel = sc.nextLong();
            this.tenant.personMap.put(this.personIdGenerator, new Person(name, surname, pesel));
            System.out.println("Account created successfully. Your ID: " + this.personIdGenerator);
            this.personIdGenerator++;
        } catch (InputMismatchException e){
            System.err.println("Invalid input. Please, try again.");
            createNewAccount();
        }
    }
}
