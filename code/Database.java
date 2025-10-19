package com;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Database {
    private final String url = "jdbc:sqlite:rental.db";

    // connect to the database
    private Connection connect() throws SQLException {
        try {
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException e) {
            throw new SQLException("SQLite JDBC driver not found", e);
        }
        return DriverManager.getConnection(url);
    }


    // validate email
    public boolean isValidEmail(String email) {
        return email.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$");
    }
    // format string
    public String formatString(String word){
        word.trim();
        if (!word.isEmpty()) {
            word = word.substring(0, 1).toUpperCase() + word.substring(1).toLowerCase();
            return word;
        }
        return "";
    }


    //Ordering system
    public String placeOrder(String category, String brand,String model, String email, int days){
        if (emailExistsinOrders(email)){
            return "You have already rented a vehicle with email: "+email;
        } else {
            boolean avail = updateStock(model);
            if (!avail){
                return model+"s are not there \t Sorry";
            }
            float cost = getRates(model)*days;
            System.out.println(cost);
            if (cost == 0) {
                return "Something went wrong when geting the  daily rate";            
            }
            // add record to the database + update number
                // record: email, carmodel, cost
            String sql = "INSERT INTO orders(customer, vehicle, cost) VALUES(?,?,?)";
            try (Connection conn = connect();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setString(1, email);
                pstmt.setString(2, model);
                pstmt.setFloat(3, cost);
                pstmt.executeUpdate();
            } catch (SQLException e) {
                return "Some thing went wron when uploading Data";
            }
            // give confirmation message
            return "Ordered: "+model+" by: "+email+" for: "+days+ " at: "+cost+" $";
        }
    }



    // reteruning car
    public String returnVehicle(String email, String model){
        if (emailExistsinOrders(email)){
           String sql = "DELETE FROM orders WHERE customer = ?";
            try (Connection conn = connect();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setString(1, email);
                pstmt.executeUpdate();
                boolean done = increaseAvailability(model);
                if (done) {
                    return "\nDeleted "+ email +"'s : "+model+" Order record cleared";
                } else {
                    return "\nUnable to update "+ email +"'s Rental, Try again";
                }
                
            } catch (SQLException e) {
                //e.printStackTrace();
                return " \nMissing record \n";
            } 
        } else {
            return " \n Record does not exist \n";
        }
    }

    // get the rental records
    public String[] getRented() {
        List<String> rented = new ArrayList<>();
        String sql = "SELECT customer, vehicle, cost FROM orders";

        try (Connection conn = connect();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                String line = rs.getString("customer")
                            + "\t" + rs.getString("vehicle")
                            + "\t" + rs.getInt("cost");
                rented.add(line);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        // convert list to array
        return rented.toArray(new String[0]);
    }

    // get available vehicles
    public String[] getavailableVehicles() {
        List<String> available = new ArrayList<>();
        String sql = "SELECT type, brand, model, rate, available FROM vehicles";

        try (Connection conn = connect();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                String line = rs.getString("type")
                            + "\t\t" + rs.getString("brand")
                            + "\t\t" + rs.getString("model")
                            + "\t\t" + rs.getDouble("rate")
                            + "\t\t" + rs.getInt("available");
                available.add(line);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        // convert list to array
        return available.toArray(new String[0]);
    }






    // OTHER UTILITES ... HELPER functions
    // Update availability
    private boolean updateStock(String model) {
        int available = 0;
        String sql = "SELECT available FROM vehicles WHERE model = ?";

        try (Connection conn = connect();
            PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, model);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                available = rs.getInt("available");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        if (available<1){
            return false;
        } else {
            String sql2 = "UPDATE vehicles SET available = available - 1 WHERE model = ?";
            try (Connection conn = connect();
                PreparedStatement pstmt = conn.prepareStatement(sql2)) {
                pstmt.setString(1, model);
                pstmt.executeUpdate();
                return true;

            } catch (SQLException e) {
                //e.printStackTrace();
                return false;
            }
        }
    }
    // update available after return
    private boolean increaseAvailability(String model) {
        String sql = "UPDATE vehicles SET available = available + 1 WHERE model = ?";

        try (Connection conn = connect();
            PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, model);
            int affected = pstmt.executeUpdate();
            return affected == 1;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // get rate
    private int getRates(String model) {
        int rate = 0;
        String sql = "SELECT rate FROM vehicles WHERE model = ?";

        try (Connection conn = connect();
            PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, model);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                rate = rs.getInt("rate");
            }
            return rate;
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }
    public boolean emailExistsinOrders(String email) {
        String sql = "SELECT 1 FROM orders WHERE customer = ? LIMIT 1";

        try (Connection conn = connect();
            PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, email);
            ResultSet rs = pstmt.executeQuery();

            // If any row is returned, the email exists
            return rs.next();

        } catch (SQLException e) {
            return false; // Treat errors as "not found"
        }
    }


}
