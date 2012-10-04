package com.utils.ssh;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Properties;

import com.utils.logging.MyLogger;

// THIS CLASS NEEDS WORK.
public class FileTransport {

	private ServerInfo server;
	private String baseLocation;
	private String frameworkName;
	private String IOUTILPROP = "our.propfile";
	private static final String STORAGEUSR="cq";
	private static final String STORAGEPWD="webos123";
	private static String STORAGEPATH="/home/cq/cats/reportlogs";
	
	
	private MyLogger logger = new MyLogger(FileTransport.class);
	
	public FileTransport (ServerInfo server, String baseLocation){
		this.server = server;
		STORAGEPATH = baseLocation;
	}
	
	
	public FileTransport(){
		Properties properties = new Properties();
		try {
		    properties.load(FileTransport.class.getResourceAsStream(IOUTILPROP));
		} catch (IOException e) {
			logger.logError("IOUtils property file not found."+IOUTILPROP, e);
		}
		server = new ServerInfo(properties.getProperty("WEB_TARGET_SERVER"), STORAGEUSR, STORAGEPWD);
		baseLocation = STORAGEPATH;
	}
	
	
	
	
	public void copy (Long jobId, List<String> files) throws IOException {
		String email = "some location";
		logger.logInfo("FileTransport >> Copy +"+files.size() +" files for job: "+jobId);
		String dest = baseLocation + File.separator + email + File.separator + frameworkName +File.separator+ jobId;
		logger.logInfo("BaseFileLocation:"+dest);
		mkdir(baseLocation + File.separator + email);
		mkdir(baseLocation + File.separator + email + File.separator + frameworkName);
		mkdir(dest);
		
		ScpTo scp = new ScpTo(server);
		for (String file : files){
			if (!scp.copy(file, dest)) {
				logger.logError("Unable to copy file "+ file +" to storage server.");
			}
		}
	}
	
	
	
	public void mkdir (String dir){
		SSH ssh = new SSH(server);
		if (!ssh.run("ls "+ dir)) ssh.run("mkdir "+dir);
	}
	
	
}
