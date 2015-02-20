package org.test.example;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.ThreadContext;
import org.apache.logging.log4j.core.Filter;
import org.apache.logging.log4j.core.LoggerContext;
import org.apache.logging.log4j.core.config.Configuration;
import org.apache.logging.log4j.core.filter.DynamicThresholdFilter;


/**
 * Hello world!
 *
 */
public class App 
{
	private static final Logger LOG = LogManager.getLogger(App.class);
	
	private static String users = System.getProperty("household");
	
	private static synchronized void configureLogger() {
		// synchronized method to ensure only one update the logger occurs at a time.
		String [] userArray = users.split(",");
    	final LoggerContext ctx = (LoggerContext) LogManager.getContext(false);
    	Configuration cfg = ctx.getConfiguration();
    	Filter filter = cfg.getFilter();
    	DynamicThresholdFilter dtf = (DynamicThresholdFilter) filter;
    	for (String user: userArray){
    		if (dtf.getLevelMap().get(user) == null) {
    			dtf.getLevelMap().put(user, Level.DEBUG);
    		}
    	}
    	// TODO: remove items in levelMap that aren't in users list.
	}
	
    public static void main( String[] args )
    {
    	// Logging only to debug for household User1.

    	System.setProperty("household", "40001,40006");
    	
    	// if the system property has changed, programmatically configure logger.
    	// this could be first in servlet method or some such.
    	if (users != System.getProperty("household")) {
    		users = System.getProperty("household");
        	configureLogger();
    	}
    	    	
    	// the context.put will only be in the servlet/mdb once.
        ThreadContext.put("household", "40001");
        LOG.error("error", new java.lang.Exception());
        LOG.debug("one");
        ThreadContext.put("household", "40001");
        LOG.debug("two");
        LOG.info("INFO");
        ThreadContext.put("household", "40005");
        LOG.error("5 error", new java.lang.Exception());
        ThreadContext.put("household", "40006");
        LOG.debug("three");
        LOG.info("INFO 2");
        ThreadContext.put("household", "40001");
        LOG.debug("four");
        
    }
}
