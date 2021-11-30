package com.company;

import java.time.LocalDate;
import java.util.*;

public abstract class UsableFloorArea implements Comparable<UsableFloorArea>{
    protected int id;
    private Person owner;
    private LocalDate leaseStart;
    public LocalDate leaseFinish;
    private int size;
    private int freeSpace;
    private boolean isSetToRenew;
    private StuffManager manager = new StuffManager();
    public List<Stuff> stuffList = new ArrayList<>();
    public boolean delayFileSent = false;


    public UsableFloorArea(int size, int id){
        this.id = id;
        this.size = size;
        this.freeSpace = this.size;
    }

    public UsableFloorArea(int width, int height, int length, int id){
        this.id = id;
        this.owner = null;
        this.size = (width * height * length);
        this.freeSpace = this.size;
    }

    public int getId(){
        return this.id;
    }

    public Person getOwner(){
        return this.owner;
    }

    public LocalDate getLeaseStart() {
        return this.leaseStart;
    }

    public LocalDate getLeaseFinish(){
        return this.leaseFinish;
    }

    public int getSize(){
        return this.size;
    }

    public int getFreeSpace(){
        return this.freeSpace;
    }

    public boolean getIsSetToRenew(){
        return this.isSetToRenew;
    }

    public boolean assignOwner (Person owner, TimeManager timeManager){
        this.owner = owner;
        this.leaseStart = timeManager.getDate();
        this.leaseFinish = timeManager.datePlus30Days();
        this.isSetToRenew = true;
        return true;
    }

    public boolean removeOwner (){
        this.owner = null;
        this.leaseStart = null;
        this.leaseFinish = null;
        return true;
    }

    public void setToRenew(boolean setToRenew){
        this.isSetToRenew = setToRenew;
    }

    public boolean addStuff(Stuff stuff){
        int stuffSize = stuff.getSize();
        int temp = manager.manageStuff
                (this.freeSpace, stuffSize, (a, b) -> a-b);
        try{
            if (temp >= 0) {
                this.freeSpace = temp;
                this.stuffList.add(stuff);
                return true;
            }
            else
                throw new TooManyThingsException(getFreeSpace());
        } catch (TooManyThingsException e) {
            e.message();
        } return false;
    }

    public boolean removeStuff (Stuff stuff){
        int stuffSize = stuff.getSize();
        this.freeSpace = manager.manageStuff
                (this.freeSpace, stuffSize, (a, b) -> a+b);
        this.stuffList.remove(stuff);
        return true;
    }

    public void showStuffList(){
        this.stuffList.sort(Stuff::compareTo);
        Iterator<Stuff> iterator = this.stuffList.iterator();
        int index = 1;
        String areaClass;
        if (this instanceof Apartment)
            areaClass = "Apartment";
        else
            areaClass = "Parking space";

        System.out.print(areaClass + ": [Area ID: "+ getId() + " content: " );
        while (iterator.hasNext())
            System.out.print(index++ + ". " + iterator.next() + "\n");
        System.out.print("]\n");
    }

    @Override
    public int compareTo(UsableFloorArea usableFloorArea){
        if (this.size > usableFloorArea.size)
            return 1;
        else if (this.size < usableFloorArea.size)
            return -1;
        else
            return 0;
    }

    @Override
    public String toString(){
        return "[ID:" + getId() + ", Owner: " + getOwner() + ", Start of the lease: "
                + getLeaseStart() + ", Finish of the Lease: " + getLeaseFinish() + ", Size: " + getSize() + " m3]";
    }
}

class TooManyThingsException extends Exception{
    int freeSpaceLeft;

    public TooManyThingsException(int freeSpaceLeft){
        this.freeSpaceLeft = freeSpaceLeft;
    }

    public void message(){
        System.err.println("Remove some old items to insert a new item. Free space left: " + freeSpaceLeft + " m3.");
    }
}
