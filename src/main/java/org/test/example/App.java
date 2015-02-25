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
	private static String CCF_FILTER_LEVEL = System.getProperty("ccf.filter.level");
	
	private static synchronized void configureLogger() {
		// synchronized method to ensure only one update the logger occurs at a time.
		
		if (CCF_FILTER_LEVEL != System.getProperty("ccf.filter.level")) {
			CCF_FILTER_LEVEL = System.getProperty("ccf.filter.level");
		}
		String[] userArray = users.split(",");
		final LoggerContext ctx = (LoggerContext) LogManager.getContext(false);
		Configuration cfg = ctx.getConfiguration();
		if ((userArray != null) && (userArray.length == 1)) {
			if ("ALL".equals(userArray[0])) {
				ctx.getConfiguration().removeFilter(cfg.getFilter());
				cfg.addFilter(ThresholdFilter.createFilter(Level.getLevel(CCF_FILTER_LEVEL),Result.ACCEPT,Result.DENY));
			}
		} else {
			ctx.getConfiguration().removeFilter(cfg.getFilter());
			DynamicThresholdFilter dtf = DynamicThresholdFilter.createFilter("household", new KeyValuePair[0], Level.INFO,Result.ACCEPT,Result.DENY);
			cfg.addFilter(dtf);
			dtf.getLevelMap().clear();

			for (String user : userArray) {
				if (dtf.getLevelMap().get(user) == null) {
					dtf.getLevelMap().put(user, Level.getLevel(CCF_FILTER_LEVEL));
				}
			}
		}
	}
	
    public static void main( String[] args )
    {
    	System.setProperty("ccf.filter.level", "DEBUG");
    	
    	System.setProperty("households", "40001,40006");
    	LOG.error("debug :" + System.getProperty("households"));
    	
    	if (users != System.getProperty("households")) {
    		users = System.getProperty("households");
        	configureLogger();
    	}
    	    	
    	logMessages();
        
    	System.setProperty("households", "ALL");
    	LOG.error("debug :" + System.getProperty("households"));
    	
    	if (users != System.getProperty("households")) {
    		users = System.getProperty("households");
        	configureLogger();
    	}
    	        	
    	logMessages();
    	
    	System.setProperty("households", "40001,40006");
    	LOG.error("debug :" + System.getProperty("households"));
    	
    	if (users != System.getProperty("households")) {
    		users = System.getProperty("households");
        	configureLogger();
    	}
    	    	
    	logMessages();

    	System.setProperty("ccf.filter.level", "TRACE");
    	
    	System.setProperty("households", "40001,FOO");
    	LOG.error("trace :" + System.getProperty("households"));
    	
    	if (users != System.getProperty("households")) {
    		users = System.getProperty("households");
        	configureLogger();
    	}
    	    	
    	logMessages();    	
    	
    	System.setProperty("ccf.filter.level", "TRACE");
    	
    	System.setProperty("households", "ALL");
    	LOG.error("trace :" + System.getProperty("households"));
    	
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
