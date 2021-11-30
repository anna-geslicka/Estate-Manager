package com.company;

public interface Stuff extends Comparable<Stuff>{

    int getSize();
    String getName();

    @Override
    int compareTo(Stuff stuff);

    static int customCompareTo(Stuff stuff, int size, String name) {
        if (size > stuff.getSize())
            return -1;
        else if (size < stuff.getSize())
            return 1;
        else {
            char thisName = name.charAt(0);
            char comparedName = stuff.getName().charAt(0);
            if (thisName > comparedName)
                return 1;
            else
                return -1;
        }
    }

    @Override
    String toString();
}