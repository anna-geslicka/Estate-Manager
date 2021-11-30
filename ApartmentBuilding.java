package com.company;

import java.util.ArrayList;

public class ApartmentBuilding {
    private int apartmentQuantity;
    private int currentQuantity = 0;
    private int id;
    public ArrayList<Apartment> apartmentList = new ArrayList<>();

    public ApartmentBuilding (int apartmentQuantity, int id){
        this.id = id;
        this.apartmentQuantity = apartmentQuantity;
    }

    public void setCurrentQuantity(int quantity){
        this.currentQuantity = quantity;
    }

    public int getId(){
        return this.id;
    }

    public int getApartmentQuantity(){
        return this.apartmentQuantity;
    }

    public int getCurrentQuantity(){
        return this.currentQuantity;
    }

    public boolean addToApartmentList(Apartment apartment){
        this.apartmentList.add(apartment);
        return true;
    }

    @Override
    public String toString(){
        this.apartmentList.sort(UsableFloorArea::compareTo);
        return "Apartment Building: [ID: " + getId() + ", Apartment quantity: "
                + getApartmentQuantity() + "\nApartment list:\n" + this.apartmentList.toString() + "]";
    }
}