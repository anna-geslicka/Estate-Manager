# Estate-Manager
Holiday Camp Database is the result of a student project on Java Foundations (1st year).
This project is an example of a system supproting residential aeras management.

**Assignment:**
Write a program that will be used to manage a network of residential areas by a developer.
The developer may have many residential areas, and within a residential area there may be many apartment buildings, in which there is an finite number of apartments for rent.

Each room may be inhabited by any number of people. Information about them should be stored (objects of the Person class).
First person, who starts a tenancy is responsible for paying the rent.
As part of the lease of an apartment (only within a residential area) it is possible to additionally lease a closed parking space.
Tenants can store vehicles and other belongings in their parking space.

Each apartment and parking space has information about its usable area, and a unique ID, thanks to which we can simultaneously define the object symbolizing the apartment or parking space.
The tenant can freely check residents in and out of the apartment as well as, if he owns a parking space, he can put in and take out objects and vehicles.
Each person may rent more than one unit as long as the total number of apartments and parking spaces rented by this person (tenant) is not more than 5.
Each unit may have only one tenant at a time.
It is necessary to ensure, that room IDs are unique and created automatically when creating an apartment and a parking space.

The size of the usable area for both types of rooms should be provided when creating an object.
There are two ways to indicate the size of a room:
- by volume in cubic metres
- by providing the room size, in the form of length, width and height (we assume that these are cuboids). With this approach, the usable area is calculated when creating the object.

A room lease for a particular tenant also has a start and end date.
If the lease expiration date has passed, a letter is issued (a File object), which is saved in the object of the Person class that defines this particular tenant.

You should also implement a time simulating mechanism, baseed on threads. 
The thread should move the date forward by 1 day every 5 seconds, simulating the passage of time.
In parallel, every 10 seconds it should check if all rooms are still being rented,or if the rental of the room has stopped.
