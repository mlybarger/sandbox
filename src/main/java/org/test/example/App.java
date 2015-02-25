package org.test.example;

import java.util.ArrayList;
import java.util.Arrays;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.ThreadContext;
import org.apache.logging.log4j.core.Filter;
import org.apache.logging.log4j.core.Filter.Result;
import org.apache.logging.log4j.core.LoggerContext;
import org.apache.logging.log4j.core.config.Configuration;
import org.apache.logging.log4j.core.filter.DynamicThresholdFilter;
import org.apache.logging.log4j.core.filter.ThresholdFilter;
import org.apache.logging.log4j.core.util.KeyValuePair;


/**
 * Hello world!
 *
 */
public class App 
{
	private static final Logger LOG = LogManager.getLogger(App.class);

	private static final ArrayList<String> TEST_HOUSEHOLDS = new ArrayList<String>(Arrays.asList("40001", "40002", "40003","40004", "40005","40006"));
	
	private static String users = System.getProperty("households");
	
	private static synchronized void configureLogger() {
		// synchronized method to ensure only one update the logger occurs at a time.
		String[] userArray = users.split(",");
		final LoggerContext ctx = (LoggerContext) LogManager.getContext(false);
		Configuration cfg = ctx.getConfiguration();
		if ((userArray != null) && (userArray.length == 1)) {
			if ("ALL".equals(userArray[0])) {
				ctx.getConfiguration().removeFilter(cfg.getFilter());
				cfg.addFilter(ThresholdFilter.createFilter(Level.DEBUG,Result.ACCEPT,Result.DENY));
			}
		} else {

			ctx.getConfiguration().removeFilter(cfg.getFilter());
			DynamicThresholdFilter dtf = DynamicThresholdFilter.createFilter("household", new KeyValuePair[0], Level.INFO,Result.ACCEPT,Result.DENY);
			cfg.addFilter(dtf);
			dtf.getLevelMap().clear();

			for (String user : userArray) {
				if (dtf.getLevelMap().get(user) == null) {
					dtf.getLevelMap().put(user, Level.DEBUG);
				}
			}
		}
	}
	
    public static void main( String[] args )
    {
    	// Logging only to debug for household User1.

    	System.setProperty("households", "40001,40006");
    	
    	// if the system property has changed, programmatically configure logger.
    	// this could be first in servlet method or some such.
    	if (users != System.getProperty("households")) {
    		users = System.getProperty("households");
        	configureLogger();
    	}
    	    	
    	logMessages();
        
    	LOG.error("now test all");
    	System.setProperty("households", "ALL");
    	
    	// if the system property has changed, programmatically configure logger.
    	// this could be first in servlet method or some such.
    	if (users != System.getProperty("households")) {
    		users = System.getProperty("households");
        	configureLogger();
    	}
    	        	
    	logMessages();
    	
    	System.setProperty("households", "40001,40006");
    	
    	// if the system property has changed, programmatically configure logger.
    	// this could be first in servlet method or some such.
    	if (users != System.getProperty("households")) {
    		users = System.getProperty("households");
        	configureLogger();
    	}
    	    	
    	logMessages();
    	
    }
    
    private static void logMessages() {
    	for (String household: TEST_HOUSEHOLDS) {
    		ThreadContext.put("household", household);
            LOG.trace("household:" + household);
            LOG.debug("household:" + household);
            LOG.info("household:" + household);
    		ThreadContext.remove(household);
    	}
    	
    }
}
