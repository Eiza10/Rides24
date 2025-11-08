package domain;

public class CarRequest {
    private final String carPlate;
    private final int nPlaces;
    private final Driver driver;
    private final boolean hasDiscount;
    
    public CarRequest(String carPlate, int nPlaces, Driver driver, boolean hasDiscount) {
        this.carPlate = carPlate;
        this.nPlaces = nPlaces;
        this.driver = driver;
        this.hasDiscount = hasDiscount;
    }
    
    public String getCarPlate() { return carPlate; }
    public int getNPlaces() { return nPlaces; }
    public Driver getDriver() { return driver; }
    public boolean hasDiscount() { return hasDiscount; }
}