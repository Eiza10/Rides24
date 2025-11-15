package gui;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import domain.Driver;

public class DriverTable extends JFrame {
    private static final long serialVersionUID = 1L;
    private JTable tabla;

    public DriverTable(Driver driver) {
        super(driver.getEmail() + "'s rides");
        this.setBounds(100, 100, 700, 300);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        DriverAdapter adapt = new DriverAdapter(driver);
        tabla = new JTable(adapt);
        tabla.setPreferredScrollableViewportSize(new Dimension(650, 200));
        
        // Creamos un JscrollPane y le agregamos la JTable
        JScrollPane scrollPane = new JScrollPane(tabla);
        
        // Agregamos el JScrollPane al contenedor
        getContentPane().add(scrollPane, BorderLayout.CENTER);
        
        // Pack to fit content
        pack();
    }
}
