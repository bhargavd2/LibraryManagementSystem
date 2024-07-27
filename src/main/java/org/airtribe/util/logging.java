package org.airtribe.util;

import org.airtribe.Main;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

public class logging
{
    private static Logger logger;

    public logging(){
        if (logger == null) {
            logger = LogManager.getLogger();
        }
    }

    public void logInfo(String message) {
        logger.info(message);
    }

    public void logDebug(String message) {
        logger.debug(message);
    }

    public void logError(String message) {
        logger.error(message);
    }
}
