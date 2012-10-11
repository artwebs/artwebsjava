package com.bin.log;



import org.apache.log4j.Logger;

import com.bin.utils.Utils;

public class Log {
	 private static Logger logger = null;

	    private static void init() 
	    {
	        logger = Logger.getLogger(com.bin.log.Log.class);
	    }

	    public static Logger getLogger() 
	    {
	    	System.setProperty ("WORKDIR", Utils.getRootPath());
	        if (null == logger) 
	        {
	            init();
	        }
	        return logger;
	    }
	    public static void setFilePath(String path){
	    	System.setProperty ("WORKDIR", path);
	        if (null == logger) 
	        {
	            init();
	        }
	    }
	    public static void setInfo(Object message){
	    	logger.info(message);
	    }
	    public static void setDebug(Object message){
	    	logger.debug(message);
	    }
	    public static void setWarn(Object message){
	    	logger.warn(message);
	    }
	    public static void setError(Object message){
	    	logger.error(message);
	    }
	    public static void setFatal(Object message){
	    	logger.fatal(message);
	    }
}
