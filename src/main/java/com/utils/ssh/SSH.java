package com.utils.ssh;

/************************************************************************************
# CLASS NAME:  SSH.java
# PURPOSE:  Class to send commands remotely to ssh servers
# AUTHOR: Marcelo Oliveira
# DATE WRITTEN: May 08 2009
*******************************************************************************************/

import java.io.IOException;
import java.io.InputStream;
import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.UserInfo;


	public class SSH {

		private String host;
		private String user;
		private String password;
		private String sshOutput="";
		private int exitStatus ;


		/**
		 * @return the exitStatus
		 */
		public int getExitStatus() {
			return this.exitStatus;
		}


		/**
		 * @param exitStatus the exitStatus to set
		 */
		public void setExitStatus(int exitStatus) {
			this.exitStatus = exitStatus;
		}


		public SSH (ServerInfo server) {
			host = server.getHostname();
			user = server.getUsername();
			password =server.getPassword();
		}


	   


	public  boolean runCmd (String cmd){
		  try{
	      JSch jsch=new JSch();
	      Session session=jsch.getSession(user, host, 22);
	      session.setPassword(password);
	      UserInfo ui=new MyUserInfo(password);
	      session.setUserInfo(ui);
	      session.connect();
	      Channel channel=session.openChannel("exec");
	      ((ChannelExec)channel).setCommand(cmd);
   	      channel.setInputStream(null);
   	     // ((ChannelExec)channel).setErrStream(System.err);
	      InputStream in=channel.getInputStream();
	      channel.connect();
	      byte[] tmp=new byte[1024];
	      while(true){
	        while(in.available()>0){
	          int i=in.read(tmp, 0, 1024);
	          if(i<0)break;
	            String out = new String(tmp, 0, i);
	          	setSshOutput(out);

	        }
	        if(channel.isClosed()){
	        	if (channel.getExitStatus() == 0){
	        		setExitStatus(0);
	        	}
	        	else {
	        		setExitStatus(channel.getExitStatus());
	        		return false;
	        	}
	          break;
	        }
	        try{Thread.sleep(1000);}catch(Exception ee){}
	      }
	      channel.disconnect();
	      session.disconnect();

	      return true;
	    }
	    catch(JSchException e){
	    	return false;
	    } catch (IOException e) {
	    	return false;
		}

	  }


	  /**
	 * @return the sshOutput
	 */
	public String getSshOutput() {
		return this.sshOutput;
	}


	/**
	 * @param sshOutput the sshOutput to set
	 */
	public void setSshOutput(String sshOutput) {
		this.sshOutput = sshOutput;
	}


	public boolean run (String cmd) {
		//String format = setEnvVar (cmd);
		if (runCmd(cmd)) {
			return true;
		} else {
			return false;
		}

	}


	public  String execute (String cmd){
		   if (run(cmd)) {
			   return getSshOutput();
		   } else {
			   return "";
		   }
	}

}