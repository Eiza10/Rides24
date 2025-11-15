package gui;

import businessLogic.BLFacade;
import businessLogic.BLFacadeImplementation;
import configuration.ConfigXML;
import dataAccess.DataAccess;

public class BLLocal implements BLProduct {
    DataAccess dataAccess;
    BLFacade appFacadeInterface;

    public BLLocal(ConfigXML c) {
        dataAccess = new DataAccess();
		appFacadeInterface =new BLFacadeImplementation(dataAccess);
    }
    @Override
    public BLFacade getBLFacade() {
        return appFacadeInterface;
    }
    
}
