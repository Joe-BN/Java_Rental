package com;

import java.util.Objects;

public class Customer {
    private final String name;
    

    public Customer(String name) {
        this.name = name;
        
    }

    public String getName() {
        return name;
    }

    // Ensure proper comparison in rentedVehicles map
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Customer)) return false;
        Customer c = (Customer) o;
        return Objects.equals(name.toLowerCase(), c.name.toLowerCase());
    }

    @Override
    public int hashCode() {
        return Objects.hash(name.toLowerCase());
    }
}
