package gui;

import java.awt.Color;
import java.net.URL;
import java.util.Locale;

import javax.swing.UIManager;
import javax.xml.namespace.QName;
import javax.xml.ws.Service;

import configuration.ConfigXML;
import dataAccess.DataAccess;
import domain.Driver;
import businessLogic.BLFacade;
import businessLogic.BLFacadeImplementation;

public class ApplicationLauncher { 
	
	
	
	public static void main(String[] args) {

		ConfigXML config=ConfigXML.getInstance();
	
		System.out.println(config.getLocale());
		
		Locale.setDefault(new Locale(config.getLocale()));
		
		System.out.println("Locale: "+Locale.getDefault());
		
		MainGUI mainWindow=new MainGUI();
		mainWindow.setVisible(true);


		try {
			
			UIManager.setLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel");
			
			BLFactory factory = new BLFactory();
			BLProduct BLproduct = factory.createBLProduct();
			MainGUI.setBussinessLogic(BLproduct.getBLFacade());

		}catch (Exception e) {
			mainWindow.jLabelWelcome.setText("Error: "+e.toString());
			mainWindow.jLabelWelcome.setForeground(Color.RED);	
			
			System.out.println("Error in ApplicationLauncher: "+e.toString());
		}
		//a.pack();


	}

	
}
