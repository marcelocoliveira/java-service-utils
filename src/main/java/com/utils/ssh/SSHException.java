package com.utils.ssh;

public class SSHException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public SSHException (String cmd, String server) {
		super("Failure executing command "+cmd +" on server "+server +".");
	}
	
}
