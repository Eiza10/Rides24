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
		
	    Driver driver=new Driver("driver3@gmail.com","Test Driver", "123");

		
		MainGUI mainWindow=new MainGUI();
		mainWindow.setVisible(true);


		try {
			
			BLFacade appFacadeInterface;
			UIManager.setLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel");
			
			if (config.isBusinessLogicLocal()) {
			
				DataAccess dataAccess= new DataAccess();
				appFacadeInterface=new BLFacadeImplementation(dataAccess);

				
			}
			
			else { //If remote
				
				 String serviceName= "http://"+config.getBusinessLogicNode() +":"+ config.getBusinessLogicPort()+"/ws/"+config.getBusinessLogicName()+"?wsdl";
				 
				URL url = new URL(serviceName);

		 
		        //1st argument refers to wsdl document above
				//2nd argument is service name, refer to wsdl document above
		        QName qname = new QName("http://businessLogic/", "BLFacadeImplementationService");
		 
		        Service service = Service.create(url, qname);

		         appFacadeInterface = service.getPort(BLFacade.class);
			} 
			
			MainGUI.setBussinessLogic(appFacadeInterface);

		

			
		}catch (Exception e) {
			mainWindow.jLabelWelcome.setText("Error: "+e.toString());
			mainWindow.jLabelWelcome.setForeground(Color.RED);	
			
			System.out.println("Error in ApplicationLauncher: "+e.toString());
		}
		//a.pack();


	}

	
}
