package com.company;

import java.util.ArrayList;

public class Apartment extends UsableFloorArea {
    public ArrayList<Person> residentList = new ArrayList<>();

    public Apartment(int size, int id) {
        super(size, id);
    }

    public Apartment(int width, int height, int length, int id) {
        super(width, height, length, id);
    }

    @Override
    public boolean assignOwner (Person owner, TimeManager date){
        this.residentList.clear();
        super.assignOwner(owner, date);
        return true;
    }

    @Override
    public boolean removeOwner (){
        this.residentList.clear();
        super.removeOwner();
        return true;
    }

    public boolean addToResidentList(Person resident){
        residentList.add(resident);
        return true;
    }

    @Override
    public String toString(){
        return "Apartment: " + super.toString() + "\nResident list:\n" + this.residentList.toString() + "\n";
    }
}