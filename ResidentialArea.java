package com.company;

import java.util.ArrayList;

public class ResidentialArea {
    String name;
    ArrayList<ApartmentBuilding> apartmentBuildingList = new ArrayList<>();
    ArrayList<ParkingSpace> parkingSpaceList = new ArrayList<>();
    private int apartmentBuildingIdGenerator = 1;
    private int parkingSpaceIdGenerator = 1;
    private int apartmentIdGenerator = 1;


    public ResidentialArea(String name){
        this.name = name;
        setSpecifications();
    }

    private void setSpecifications() {
        for (int i = 0; i <= 25; i++) {
            this.apartmentBuildingList.add((new ApartmentBuilding(100, this.apartmentBuildingIdGenerator)));
            this.apartmentBuildingIdGenerator++;
        }
        for (int i = 0; i <= 10; i++) {
            this.parkingSpaceList.add(new ParkingSpace(2, 2, 3, this.parkingSpaceIdGenerator));
            this.parkingSpaceIdGenerator++;
        }
    }

    public int getApartmentBuildingQuantity(){
        return  100;
    }

    public void createApartment(int size) {
        int apartmentBuildingQuantity = getApartmentBuildingQuantity();
        int i = 0;
        while (i < apartmentBuildingQuantity) {
            ApartmentBuilding checkedApartmentBuilding = this.apartmentBuildingList.get(i);
            int maxQuantity = checkedApartmentBuilding.getApartmentQuantity();
            int currentQuantity = checkedApartmentBuilding.getCurrentQuantity();
            Apartment apartment = new Apartment(size, this.apartmentIdGenerator);

            if (currentQuantity < maxQuantity) {
                checkedApartmentBuilding.addToApartmentList(apartment);
                this.apartmentIdGenerator++;
                currentQuantity++;
                this.apartmentBuildingList.get(i).setCurrentQuantity(currentQuantity);
                break;
            } else
                System.err.println("You can't add any more apartments to " + this.name + ". This residential area is full.");
            i++;
        }
    }

    public void createApartment(int width, int height, int length) {
        int apartmentBuildingQuantity = getApartmentBuildingQuantity();
        int i = 0;
        while (i < apartmentBuildingQuantity) {
            ApartmentBuilding checkedApartmentBuilding = this.apartmentBuildingList.get(i);
            int maxQuantity = checkedApartmentBuilding.getApartmentQuantity();
            int currentQuantity = checkedApartmentBuilding.getCurrentQuantity();

            if (currentQuantity < maxQuantity) {
                checkedApartmentBuilding.addToApartmentList(new Apartment(width, height, length, this.apartmentIdGenerator));
                this.apartmentIdGenerator++;
                currentQuantity++;
                this.apartmentBuildingList.get(i).setCurrentQuantity(currentQuantity);
                break;
            } else
                System.err.println("You can't add any more apartments to " + this.name + ". This residential area is full.");
            i++;
        }
    }

    @Override
    public String toString(){
        return "\n\nResidential area: [Name: " + this.name + "\n\nApartment building list:\n"
                + this.apartmentBuildingList.toString() + "\n\nParking space list:" + this.parkingSpaceList.toString()+"]";
    }
}
