package org.elibrary;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Demo {

    private static final Logger LOG = LogManager.getLogger(Demo.class);

    public static void main(String[] args) {
        LOG.error("Error!");
        LOG.info("Info!");
        LOG.warn("Warn!");
    }

}
