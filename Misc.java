package com.company;

public class Misc implements Stuff{
    private String name;
    private int size;
    private String material;

    public Misc(String name, int size, String material){
        this.name = name;
        this.size = size;
        this.material = material;
    }

    @Override
    public int getSize() {
        return this.size;
    }

    @Override
    public String getName() {
        return this.name;
    }

    public String getMaterial(){
        return  this.material;
    }

    @Override
    public int compareTo(Stuff stuff) {
        return Stuff.customCompareTo(stuff, this.size, this.name);
    }

    @Override
    public String toString(){
        return "Misc: [Name: " + getName() + ", Material: " +getMaterial() + ", Size: " + getSize() + " m3]";
    }
}