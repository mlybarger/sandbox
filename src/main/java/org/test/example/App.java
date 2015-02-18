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
	
    public static void main( String[] args )
    {
    	// Logging only to debug for household User1.
    	
    	// programmatically configure.
    	
    	final LoggerContext ctx = (LoggerContext) LogManager.getContext(false);
    	Configuration cfg = ctx.getConfiguration();
    	Filter filter = cfg.getFilter();
    	DynamicThresholdFilter dtf = (DynamicThresholdFilter) filter;
    	dtf.getLevelMap().put("User12", Level.DEBUG);
    	
    	//System.setProperty("DebugHousehold", "User1");
    	//ThreadContext.put("DebugHousehold", "User1");
    	
        ThreadContext.put("household", "User12");
        LOG.error("error", new java.lang.Exception());
        LOG.debug("one");
        ThreadContext.put("household", "User1");
        LOG.debug("two");
        LOG.info("INFO");
        ThreadContext.put("household", "User12");
        LOG.error("error", new java.lang.Exception());
        ThreadContext.put("household", "User12");
        LOG.debug("three");
        LOG.info("INFO 2");
        ThreadContext.put("household", "User1");
        LOG.debug("four");
        
    }
}
