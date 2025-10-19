package com.models;

public class van extends vehicle {
    private double _capacity;

    public van(String brand, String model, double capacity){
        super(brand, model);
        _capacity = capacity;
    }

    public double get_capacity(){ return _capacity; }
    
    @Override
    public String get_type(){ return "Van"; }
}
