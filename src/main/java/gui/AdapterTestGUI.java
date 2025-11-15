package gui;

import businessLogic.BLFacade;
import domain.Driver;
import exceptions.PasswordDoesNotMatchException;
import exceptions.UserDoesNotExistException;

public class AdapterTestGUI {
    
    public static void main(String[] args) {
        try {
            // the BL is local
            BLFacade blFacade = new BLFactory().createBLProduct().getBLFacade();
            System.out.println("Facade created");
            
            Driver d = blFacade.getDriverByEmail("driver1@gmail.com", "123");
            System.out.println("Driver retrieved: " + d.getEmail());
            System.out.println("Driver name: " + d.getName());
            System.out.println("Number of rides: " + (d.getRides() != null ? d.getRides().size() : "NULL"));
            
            if (d.getRides() != null && !d.getRides().isEmpty()) {
                System.out.println("Rides list:");
                for (int i = 0; i < d.getRides().size(); i++) {
                    System.out.println("  Ride " + (i+1) + ": " + 
                        d.getRides().get(i).getFrom() + " -> " + 
                        d.getRides().get(i).getTo());
                }
            }
            
            DriverTable dt = new DriverTable(d);
            dt.setVisible(true);
        } catch (UserDoesNotExistException | PasswordDoesNotMatchException e) {
            System.err.println("Error: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
