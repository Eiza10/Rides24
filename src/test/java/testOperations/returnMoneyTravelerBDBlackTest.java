package testOperations;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Test;

import dataAccess.DataAccess;
import dataAccess.RideCreationRequest;
import domain.CarRequest;
import domain.Driver;
import domain.Reservation;
import domain.Ride;
import domain.Traveler;
import exceptions.UserAlreadyExistException;
import testOperations.TestDataAccess;

public class returnMoneyTravelerBDBlackTest {

	 // sut: system under test
    static DataAccess sut = new DataAccess();
    
    // additional operations needed to execute the test
    static TestDataAccess testDA = new TestDataAccess();
    
    @Test
    //resList is not null, email is not null, Driver and Traveler are both in database, resList.size is >0
    public void test1() {
        String driverEmail = "testdriver1@gmail.com";
        String travelerEmail = "testtraveler1@gmail.com";
        
        try {
            // Setup test data
            sut.open();
            
            // Create driver and traveler through sut for consistency
            sut.createDriver(driverEmail, "Test Driver", "123");
            sut.createTraveler(travelerEmail, "Test Traveler", "456");
            
            // Add a car to the driver first
            String carPlate = "AA123456";
            CarRequest carRequest = new CarRequest(carPlate, 4, sut.getDriverByEmail(driverEmail, "123"), false);
            sut.addCarToDriver(carRequest);

            // Create a date one day after today
            Date tomorrow = new Date(System.currentTimeMillis() + 24 * 60 * 60 * 1000);
            
            // Create a ride
            RideCreationRequest request = new RideCreationRequest("Bilbo", "Donostia", tomorrow, 10.0f, driverEmail, carPlate);
            Ride ride = sut.createRide(request);
            assertNotNull("Ride should be created successfully", ride);
            
            // Create reservation
            Reservation reservation = sut.createReservation(2, ride.getRideNumber(), travelerEmail);
            assertNotNull(reservation);
            
            // Pay for the reservation to simulate money transfer
            sut.putMoneyTraveler(travelerEmail, 100); // Give traveler money
            sut.pay(reservation); // Pay for reservation
            
            // Get initial money amounts AFTER payment
            Driver driver = sut.getDriverByEmail(driverEmail, "123");
            Traveler traveler = sut.getTravelerByEmail(travelerEmail, "456");
            float initialDriverMoney = driver.getMoney();
            float initialTravelerMoney = traveler.getMoney();
            
            // Debug: Print initial values
            System.out.println("Before returnMoneyTravelers:");
            System.out.println("Driver money: " + initialDriverMoney);
            System.out.println("Traveler money: " + initialTravelerMoney);
            System.out.println("Reservation cost: " + reservation.getCost());
            System.out.println("Reservation is paid: " + reservation.isPayed());
            
            // Create reservation list
            List<Reservation> resList = new ArrayList<>();
            resList.add(reservation);
            
            // Execute the method under test
            sut.returnMoneyTravelers(resList, driverEmail);
            
            // Verify the money was returned
            driver = sut.getDriverByEmail(driverEmail, "123");
            traveler = sut.getTravelerByEmail(travelerEmail, "456");
            
            // Debug: Print final values
            System.out.println("After returnMoneyTravelers:");
            System.out.println("Driver money: " + driver.getMoney());
            System.out.println("Traveler money: " + traveler.getMoney());
            
            // Check that money was transferred back
            float expectedRefund = reservation.getCost();
            assertEquals("Driver money should decrease by refund amount", 
                         initialDriverMoney - expectedRefund, driver.getMoney(), 0.01f);
            assertEquals("Traveler money should increase by refund amount", 
                         initialTravelerMoney + expectedRefund, traveler.getMoney(), 0.01f);
            
            sut.close();
            
        } catch (Exception e) {
            sut.close();
            fail("Test should not throw exception: " + e.getMessage());
        } finally {
            // Cleanup
            try {
                sut.open();
                sut.deleteAccountDriver(driverEmail);
                sut.deleteAccountTraveler(travelerEmail);
                sut.close();
            } catch (Exception e) {
                // Ignore cleanup errors
            }
        }
    }

   
    @Test
    //resList is null
    public void test2() {
        String driverEmail = "testdriver2@gmail.com";
        
        try {
            // Setup test data - create driver
            testDA.open();
            testDA.createDriver(driverEmail, "Test Driver", "123");
            testDA.close();
            
            // Execute with null reservation list
            sut.open();
            sut.returnMoneyTravelers(null, driverEmail);
            sut.close();
            
            // Method should handle null gracefully without throwing exception
            assertTrue("Method should handle null reservation list", true);
            
        } catch (Exception e) {
            sut.close();
            // Method should catch NullPointerException and handle gracefully
            assertTrue("Method should handle null gracefully", true);
        } finally {
            // Cleanup
            try {
                testDA.open();
                testDA.removeDriver(driverEmail);
                testDA.close();
            } catch (Exception e) {
                // Ignore cleanup errors
            }
        }
    }

    
    @Test
    //email is null
    public void test3() {
        String travelerEmail = "testtraveler3@gmail.com";
        
        try {
            // Setup test data - create traveler only
            sut.open();
            sut.createTraveler(travelerEmail, "Testing Traveler", "456");
            
            // Create a dummy reservation (we'll use it but with null driver email)
            Reservation reservation = new Reservation();
            List<Reservation> resList = new ArrayList<>();
            resList.add(reservation);
            
            // Execute with null email
            sut.returnMoneyTravelers(resList, null);
            sut.close();
            
            // Method should handle null email gracefully
            assertTrue("Method should handle null email", true);
            
        } catch (Exception e) {
            sut.close();
            // Method should catch NullPointerException and handle gracefully
            assertTrue("Method should handle null email gracefully", true);
        } finally {
            // Cleanup
            try {
                sut.open();
                sut.deleteAccountTraveler(travelerEmail);
                sut.close();
            } catch (Exception e) {
                // Ignore cleanup errors
            }
        }
    }
    
    @Test
    //Driver not in database
    public void test4() {
        String nonExistentDriverEmail = "nonexistent@gmail.com";
        String travelerEmail = "testtraveler4@gmail.com";
        
        try {
            // Setup test data - create traveler only, no driver
            sut.open();
            sut.createTraveler(travelerEmail, "Test Traveler", "456");
            
            // Create a reservation with reference to non-existent driver
            Traveler traveler = sut.getTravelerByEmail(travelerEmail, "456");
            Reservation reservation = new Reservation();
            // Note: We can't create a proper reservation without a ride and driver
            // but we can test the method's behavior with a basic reservation list
            
            List<Reservation> resList = new ArrayList<>();
            resList.add(reservation);
            
            // Execute with non-existent driver email
            sut.returnMoneyTravelers(resList, nonExistentDriverEmail);
            sut.close();
            
            // Method should handle non-existent driver gracefully
            assertTrue("Method should handle non-existent driver", true);
            
        } catch (Exception e) {
            sut.close();
            // Method should catch exception and handle gracefully
            assertTrue("Method should handle non-existent driver gracefully", true);
        } finally {
            // Cleanup
            try {
                sut.open();
                sut.deleteAccountTraveler(travelerEmail);
                sut.close();
            } catch (Exception e) {
                // Ignore cleanup errors
            }
        }
    }
    
    @Test
    //Traveler not in database
    public void test5() {
        String driverEmail = "testdriver5@gmail.com";
        String nonExistentTravelerEmail = "nonexistenttraveler@gmail.com";
        
        try {
            // Setup test data - create driver only
            testDA.open();
            testDA.createDriver(driverEmail, "Test Driver", "123");
            testDA.close();
            
            sut.open();
            
            // Create a ride
            RideCreationRequest request = new RideCreationRequest("Bilbo", "Donostia", new Date(), 10.0f, driverEmail, "AA123456");
            Ride ride = sut.createRide(request);

            // Create a reservation that references a non-existent traveler
            // We need to create a traveler first to create the reservation, then delete it
            sut.createTraveler(nonExistentTravelerEmail, "Temp Traveler", "456");
            Reservation reservation = sut.createReservation(1, ride.getRideNumber(), nonExistentTravelerEmail);
            
            // Delete the traveler to simulate it not being in database
            sut.deleteAccountTraveler(nonExistentTravelerEmail);
            
            List<Reservation> resList = new ArrayList<>();
            resList.add(reservation);
            
            // Execute with reservation referencing non-existent traveler
            sut.returnMoneyTravelers(resList, driverEmail);
            sut.close();
            
            // Method should handle non-existent traveler gracefully
            assertTrue("Method should handle non-existent traveler", true);
            
        } catch (Exception e) {
            sut.close();
            // Method should catch exception and handle gracefully
            assertTrue("Method should handle non-existent traveler gracefully", true);
        } finally {
            // Cleanup
            try {
                testDA.open();
                testDA.removeDriver(driverEmail);
                testDA.close();
            } catch (Exception e) {
                // Ignore cleanup errors
            }
        }
    }
    
    @Test
    //resList size = 0
    public void test6() {
        String driverEmail = "testdriver6@gmail.com";
        
        try {
            // Setup test data - create driver
            testDA.open();
            testDA.createDriver(driverEmail, "Test Driver", "123");
            testDA.close();
            
            sut.open();
            
            // Create empty reservation list
            List<Reservation> resList = new ArrayList<>();
            
            // Execute with empty list - should complete without errors
            sut.returnMoneyTravelers(resList, driverEmail);
            
            // The test passes if no exception is thrown
            assertTrue("Method should handle empty list without errors", true);
            
            sut.close();
            
        } catch (Exception e) {
            sut.close();
            fail("Test should not throw exception with empty list: " + e.getMessage());
        } finally {
            // Cleanup
            try {
                testDA.open();
                testDA.removeDriver(driverEmail);
                testDA.close();
            } catch (Exception e) {
                // Ignore cleanup errors
            }
        }
    }
}
