package gui;

import configuration.ConfigXML;

public class BLFactory {
    public BLProduct createBLProduct() {
        BLProduct product = null;
        ConfigXML config=ConfigXML.getInstance();
        if (config.isBusinessLogicLocal()) {
            product = new BLLocal(config);
        } else {
            try {
                product = new BLRemote(config);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return product;
    }
}
