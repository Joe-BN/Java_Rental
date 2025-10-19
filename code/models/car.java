package com.models;

/* A type of vehicle available for rent
 * Basic characteristincs as vehicle but with unique values.
*/

public class car extends vehicle {
    private int _seats;

    public car(String brand, String model, int seats){
        super(brand, model);
        _seats = seats;
    }

    public int get_seats(){ return _seats; }
    
    @Override
    public String get_type(){ return "Car"; }
}
