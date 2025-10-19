package com.models;

public class bike extends vehicle {
    private String _spec;

    public bike(String brand, String model, String spec){
        super(brand, model);
        _spec = spec;
    }

    public String get_seats(){ return _spec; }
    
    @Override
    public String get_type(){ return "Bike"; }
}
