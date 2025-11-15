package gui;

import javax.swing.table.AbstractTableModel;

import domain.Driver;
import domain.Ride;

/**
 * Adapter class that adapts a Driver object to a TableModel for JTable display.
 * This class follows the Adapter design pattern, converting the Driver's rides
 * into a format that can be displayed in a JTable.
 */
public class DriverAdapter extends AbstractTableModel {
    
    private Driver driver;
    private String[] columnNames = new String[] {"Origin", "Destination", "Date", "Price", "nPlaces"};
    
    /**
     * Constructor that creates an adapter for the given driver
     * @param driver The driver whose rides will be displayed
     */
    public DriverAdapter(Driver driver) {
        this.driver = driver;
        System.out.println("DriverAdapter created for: " + driver.getEmail());
        System.out.println("Number of rides: " + (driver.getRides() != null ? driver.getRides().size() : "NULL"));
    }
    
    @Override
    public int getRowCount() {
        if (driver == null || driver.getRides() == null) {
            return 0;
        }
        return driver.getRides().size();
    }
    
    @Override
    public int getColumnCount() {
        return columnNames.length;
    }
    
    @Override
    public String getColumnName(int column) {
        return columnNames[column];
    }
    
    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Ride ride = driver.getRides().get(rowIndex);
        
        switch (columnIndex) {
            case 0:
                return ride.getFrom();
            case 1:
                return ride.getTo();
            case 2:
                return ride.getDate();
            case 3:
                return ride.getPrice();
            case 4:
                return ride.getnPlaces();
            default:
                return null;
        }
    }
    
    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return false;
    }
}
