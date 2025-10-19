package com.models;

public abstract class vehicle {
    protected String _brand;
    protected String _model;
    protected double _dailyrate;
    protected boolean _availability;
    
    public vehicle(String brand,String model){
        this._brand = brand;
        this._model = model;
    }

    public void set_data( double rate, boolean avail){
        this._dailyrate = rate;
        this._availability = avail;
    }

    public String get_brand(){return _brand;}
    public String get_model(){return _model;}
    public double get_dailyrate(){return _dailyrate;}
    public boolean get_availability(){return _availability;}

    public abstract String get_type();
}
