package com.company;

public class ParkingSpace extends UsableFloorArea {

    public ParkingSpace(int size, int id) {
        super(size, id);
    }

    public ParkingSpace(int width, int height, int length, int id) {
        super(width, height, length, id);
    }

    @Override
    public String toString(){
        return "\nParking Space: " + super.toString();
    }
}
