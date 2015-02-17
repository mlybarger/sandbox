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
        ThreadContext.put("shouldDebug", "false");
        LOG.debug("one");
        ThreadContext.put("shouldDebug", "true");
        LOG.debug("two");
        
        LOG.error("error", new java.lang.Exception());
        
    }
}
