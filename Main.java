package com.company;

public class Main {

    public static void main(String[] args) {
        Developer dev = new Developer();
        TimeManager time = new TimeManager(dev);
        time.start();
        User user = new User(dev, new Tenant(dev, time));
        user.initialSet();
        System.out.println("\nWelcome to The Estate Manager.");
        user.chooseRole();
    }
}
