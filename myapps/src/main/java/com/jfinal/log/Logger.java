package com.jfinal.log;

import org.apache.log4j.Level;

@SuppressWarnings("rawtypes")
public class Logger {

	private org.apache.log4j.Logger log;
	private static final String callerFQCN = Logger.class.getName();
	
	Logger(Class<?> clazz) {
		log = org.apache.log4j.Logger.getLogger(clazz);
	}
	
	Logger(String name) {
		log = org.apache.log4j.Logger.getLogger(name);
	}
	
	public static Logger getLogger(Class clazz) {
		return new Logger(clazz);
	}
	
	public void info(String message) {
		log.log(callerFQCN, Level.INFO, message, null);
	}
	
	public void info(String message, Throwable t) {
		log.log(callerFQCN, Level.INFO, message, t);
	}
	
	public void debug(String message) {
		log.log(callerFQCN, Level.DEBUG, message, null);
	}
	
	public void debug(String message, Throwable t) {
		log.log(callerFQCN, Level.DEBUG, message, t);
	}
	
	public void warn(String message) {
		log.log(callerFQCN, Level.WARN, message, null);
	}
	
	public void warn(String message, Throwable t) {
		log.log(callerFQCN, Level.WARN, message, t);
	}
	
	public void error(String message) {
		log.log(callerFQCN, Level.ERROR, message, null);
	}
	
	public void error(String message, Throwable t) {
		log.log(callerFQCN, Level.ERROR, message, t);
	}
	
	public void fatal(String message) {
		log.log(callerFQCN, Level.FATAL, message, null);
	}
	
	public void fatal(String message, Throwable t) {
		log.log(callerFQCN, Level.FATAL, message, t);
	}
	
	public boolean isDebugEnabled() {
		return log.isDebugEnabled();
	}
	
	public boolean isInfoEnabled() {
		return log.isInfoEnabled();
	}
	
	public boolean isWarnEnabled() {
		return log.isEnabledFor(Level.WARN);
	}
	
	public boolean isErrorEnabled() {
		return log.isEnabledFor(Level.ERROR);
	}
	
	public boolean isFatalEnabled() {
		return log.isEnabledFor(Level.FATAL);
	}
}
