package org.test.example;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.ThreadContext;


/**
 * Hello world!
 *
 */
public class App 
{
	private static final Logger LOG = LogManager.getLogger(App.class);
	
    public static void main( String[] args )
    {
        ThreadContext.put("household", "User12");
        LOG.error("error", new java.lang.Exception());
        LOG.debug("one");
        ThreadContext.put("household", "User1");
        LOG.debug("two");
        ThreadContext.put("household", "User12");
        LOG.error("error", new java.lang.Exception());
        ThreadContext.put("household", "User12");
        LOG.debug("three");
        ThreadContext.put("household", "User1");
        LOG.debug("four");
        
    }
}
