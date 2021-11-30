package com.company;

public abstract class Vehicle implements Stuff{
    private String name;
    private int size;
    protected int horsePower;

    private enum Color{
        BLACK, RED, GREEN, WHITE
    }

    private Color color;

    public Vehicle(String name, int size, String color){
        this.name = name;
        this.size = size;
        setColor(color);
    }

    private void setColor(String color){
        switch (color.toLowerCase()) {
            case ("black"): {
                this.color = Color.BLACK;
                this.horsePower = 200;
            } break;
            case ("red"): {
                this.color = Color.RED;
                this.horsePower = 280;
            } break;
            case ("green"): {
                this.color = Color.GREEN;
                this.horsePower = 180;
            } break;
            case ("white"): {
                this.color = Color.WHITE;
                this.horsePower = 150;
            } break;
            default:
                System.err.println("Choose from following colors: Black, Red, Green, White.");
        }
    }

    @Override
    public int getSize() {
        return this.size;
    }

    @Override
    public String getName() {
        return this.name;
    }

    public Color getColor() {
        return this.color;
    }

    public int getHorsePower() {
        return this.horsePower;
    }

    @Override
    public int compareTo(Stuff stuff) {
        return Stuff.customCompareTo(stuff, this.size, this.name);
    }

    @Override
    public String toString(){
        return "[Name: " + getName() + " Size: " + getSize() + " m3, Color: " + getColor() + ", Horse power: " + getHorsePower();
    }
}

class Car extends Vehicle {

    private enum Type {
        CITY_CAR, OFF_ROAD_CAR
    }
    private com.company.Car.Type type;

    public Car(String name, int size, String color, String type) {
        super(name, size, color);
        setType(type);
    }

    public void setType(String type) {
        switch (type.toLowerCase()) {
            case ("city car"): {
                this.type = com.company.Car.Type.CITY_CAR;
            }
            break;
            case ("off-road car"): {
                this.type = com.company.Car.Type.OFF_ROAD_CAR;
                this.horsePower += 20;
            }
            break;
            default:
                System.out.println("Choose from following types: City car, Off-road car.");
        }
    }

    public Type getType(){
        return this.type;
    }

    @Override
    public String toString(){
        return "Car: " + super.toString() + ", Type: " + getType() + "]";
    }
}

class Motorcycle extends Vehicle {
    boolean hasBackseat;

    public Motorcycle(String name, int size, String color, boolean hasBackseat) {
        super(name, size, color);
        this.hasBackseat = hasBackseat;
    }

    public boolean getHasBackseat(){
        return this.hasBackseat;
    }

    @Override
    public String toString(){
        return "Motorcycle: " + super.toString() + ", Has a backseat: " + getHasBackseat() + "]";
    }
}

class Boat extends Vehicle {
    private boolean isMotorboat;

    public Boat(String name, int size, String color,  boolean isMotorboat) {
        super(name, size, color);
        this.isMotorboat = isMotorboat;
    }

    public boolean getIsMotorboat(){
        return this.isMotorboat;
    }

    @Override
    public String toString(){
        return "Boat: " + super.toString() + ", Is a motorboat: " + getIsMotorboat() + "]";
    }
}

class Amphibian extends Vehicle {
    private boolean isSuitableForSeaTravels;

    public Amphibian(String name, int size, String color,  boolean isSuitableForSeaTravels) {
        super(name, size, color);
        this.isSuitableForSeaTravels = isSuitableForSeaTravels;
    }

    public boolean getIsSuitableForSeaTravels(){
        return this.isSuitableForSeaTravels;
    }

    @Override
    public String toString(){
        return "Amphibian: " + super.toString() + ", Is suitable for sea travels: " + getIsSuitableForSeaTravels() + "]";
    }
}
