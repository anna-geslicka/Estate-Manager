package com.company;

import java.util.ArrayList;
import java.util.Iterator;

public class Person {
    private String name;
    private String surname;
    private long pesel;
    private StringBuilder birthDate;
    private String address = null;
    ArrayList<UsableFloorArea> ownedAreaList = new ArrayList<>();
    ArrayList<UsableFloorArea> ownedAreaListHistory = new ArrayList<>();
    private int ownedAreasCounter = 0;
    public int paymentDelayCounter  = 0;
    ArrayList<File> paymentDelayList = new ArrayList<>();

    public Person(String name, String surname, long pesel){
        this.name = name;
        this.surname = surname;
        setPesel(pesel);
        setBirthDate(this.pesel);
    }

    public void setPesel(long pesel){
        if (pesel >= 20010100000L && pesel <= 99999999999L)
            this.pesel = pesel;
        else
            this.pesel = 10000000000L;
    }

    public void setBirthDate(long pesel){
        StringBuilder birthDate = new StringBuilder("19");
        String temp = String.valueOf(pesel);
        int counter = 0;
        for(int i = 0; i <=2; i++) {
            for (int j = 0; j <= 1; j++) {
                char ch = temp.charAt(counter);
                birthDate.append(ch);
                counter++;
            } birthDate.append(".");
        }
        this.birthDate = birthDate;
    }

    public void setAddress(Apartment apartment){
        this.address = "Apartment ID: " + apartment.getId();
    }

    public String getName(){
        return this.name;
    }

    public String getSurname(){
        return this.surname;
    }

    public long getPesel(){
        return this.pesel;
    }

    public StringBuilder getBirthDate(){
        return this.birthDate;
    }

    public int getOwnedAreasCounter(){
        return this.ownedAreasCounter;
    }

    public boolean addToOwnedAreaList(UsableFloorArea usableFloorArea){
        this.ownedAreaList.add(usableFloorArea);
        this.ownedAreaListHistory.add(usableFloorArea);
        this.ownedAreasCounter++;
        return true;
    }

    public String getAddress(){
        return this.address;
    }

    public void showOwnedAreaList(){
        Iterator<UsableFloorArea> iterator = this.ownedAreaList.iterator();

        System.out.println("Owned area list:");
        while (iterator.hasNext())
            System.out.println(iterator.next() + " ");
    }

    public void addToPaymentDelayList(){
        this.paymentDelayList.add(new File());
        paymentDelayCounter++;
    }


    @Override
    public String toString(){
        return "Person: [Name: " + getName() + ", Surname: " + getSurname() + ", PESEL: " + getPesel() + ", Birth date: " + getBirthDate() + ", Address: " + getAddress() + "]\n";
    }
}
