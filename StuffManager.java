package com.company;

interface Function {
    int fun( int a, int b);
}
public class StuffManager {
    public int manageStuff (int freeSpace, int stuffSize, Function function ){
        return function.fun(freeSpace, stuffSize);
    }
}
