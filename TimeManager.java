package com.company;

import java.time.LocalDate;
import java.time.Month;

public class TimeManager extends Thread{
    LocalDate date = LocalDate.of(2020, Month.JANUARY, 1);
    Developer dev;

    public TimeManager(Developer dev){
        this.dev = dev;
    }

    @Override
    public void run() {
        while (true) {
            for (int i = 0; i < 2; i++) {
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                this.date = date.plusDays(1);
            }
            checkLeaseAgreements();
        }
    }

    public LocalDate getDate(){
        return this.date;
    }

    public LocalDate datePlus30Days(){
        return this.date.plusDays(30);
    }

    public void checkLeaseAgreements() {
        for (int i = 0; i < this.dev.residentialAreaList.size(); i++) {
            ResidentialArea residentialArea = this.dev.residentialAreaList.get(i);
            for (int j = 0; j < residentialArea.apartmentBuildingList.size(); j++) {
                ApartmentBuilding apartmentBuilding = residentialArea.apartmentBuildingList.get(j);
                for (int k = 0; k < apartmentBuilding.apartmentList.size(); k++) {
                    Apartment apartment = apartmentBuilding.apartmentList.get(k);
                    if (apartment.getOwner() != null) {
                        if (!apartment.getIsSetToRenew() && getDate().isAfter(apartment.getLeaseFinish()) && !apartment.delayFileSent) {
                            apartment.getOwner().addToPaymentDelayList();
                            apartment.delayFileSent = true;
                        } else if (!apartment.getIsSetToRenew() && getDate().isAfter(apartment.getLeaseFinish().plusDays(30))) {
                            apartment.stuffList.clear();
                            apartment.residentList.clear();
                            apartment.removeOwner();
                        }
                    }
                }
            }
        }
        for (int i = 0; i < this.dev.residentialAreaList.size(); i++) {
            ResidentialArea residentialArea = this.dev.residentialAreaList.get(i);
            for (int j = 0; j < residentialArea.parkingSpaceList.size(); j++) {
                ParkingSpace parkingSpace = residentialArea.parkingSpaceList.get(j);
                if (parkingSpace.getOwner() != null) {
                    if (!parkingSpace.getIsSetToRenew() && getDate().isAfter(parkingSpace.getLeaseFinish()) && !parkingSpace.delayFileSent) {
                        parkingSpace.getOwner().addToPaymentDelayList();
                        parkingSpace.delayFileSent = true;
                    } else if (!parkingSpace.getIsSetToRenew() && getDate().isAfter(parkingSpace.getLeaseFinish().plusDays(30))) {
                        for (int k = 0; k < parkingSpace.stuffList.size(); k++) {
                            if (parkingSpace.stuffList.get(k) instanceof Vehicle) {
                                parkingSpace.stuffList.remove(k);
                                parkingSpace.leaseFinish = parkingSpace.leaseFinish.plusDays(60);
                                break;
                            } else
                                parkingSpace.stuffList.clear();
                            parkingSpace.removeOwner();
                        }
                    }
                }
            }
        }
    }
}
