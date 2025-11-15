package gui;

import java.net.URL;

import javax.xml.namespace.QName;
import javax.xml.ws.Service;

import businessLogic.BLFacade;
import configuration.ConfigXML;

public class BLRemote implements BLProduct {
    BLFacade appFacadeInterface;
    

    // Implementation for remote business logic
    public BLRemote(ConfigXML config) throws Exception {
        // Constructor implementation
        String serviceName= "http://"+config.getBusinessLogicNode() +":"+ config.getBusinessLogicPort()+"/ws/"+config.getBusinessLogicName()+"?wsdl";
				 
	    URL url = new URL(serviceName);

		 
        //1st argument refers to wsdl document above
        //2nd argument is service name, refer to wsdl document above
        QName qname = new QName("http://businessLogic/", "BLFacadeImplementationService");
            
        Service service = Service.create(url, qname);

        appFacadeInterface = service.getPort(BLFacade.class);
    }


    @Override
    public BLFacade getBLFacade() {
        return appFacadeInterface;
    }
    
}
