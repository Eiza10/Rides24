package dataAccess;

import java.util.Date;

public class RideCreationRequest {
    private final String from;
    private final String to;
    private final Date date;
    private final float price;
    private final String driverEmail;
    private final String carPlate;
    
    public RideCreationRequest(String from, String to, Date date, float price, String driverEmail, String carPlate) {
        this.from = from;
        this.to = to;
        this.date = date;
        this.price = price;
        this.driverEmail = driverEmail;
        this.carPlate = carPlate;
    }
    
    public String getFrom() {
        return from;
    }
    
    public String getTo() {
        return to;
    }
    
    public Date getDate() {
        return date;
    }
    
    public float getPrice() {
        return price;
    }
    
    public String getDriverEmail() {
        return driverEmail;
    }
    
    public String getCarPlate() {
        return carPlate;
    }
}
