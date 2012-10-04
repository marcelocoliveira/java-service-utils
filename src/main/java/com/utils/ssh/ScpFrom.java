package com.utils.ssh;

/************************************************************************************
# CLASS NAME:  ScpFrom.java
# PURPOSE: remote copy from server to local
# AUTHOR: Marcelo Oliveira
# DATE WRITTEN: May 08 2009
*******************************************************************************************/

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.UserInfo;


public class ScpFrom{
	
	 private String host;
	 private String user;
	 private String password;
	 
		
	public ScpFrom (ServerInfo server) {	
		host = server.getHostname();
		user = server.getUsername();
		password = server.getPassword();
	
	}

	public  boolean copy (String lfile, String rfile){
	
	FileOutputStream fos=null;
   
    try{
      String prefix=null;
      if(new File(lfile).isDirectory()){
        prefix=lfile+File.separator;
      }

      JSch jsch=new JSch();
      Session session=jsch.getSession(user, host, 22);
      session.setPassword(password);
      
      // username and password will be given via UserInfo interface.
      UserInfo ui=new MyUserInfo(password);
      session.setUserInfo(ui);
      session.connect();

      // exec 'scp -f rfile' remotely
      String command="scp -f "+rfile;
      Channel channel=session.openChannel("exec");
      ((ChannelExec)channel).setCommand(command);

      // get I/O streams for remote scp
      OutputStream out=channel.getOutputStream();
      InputStream in=channel.getInputStream();

      channel.connect();

      byte[] buf=new byte[1024];

      // send '\0'
      buf[0]=0; out.write(buf, 0, 1); out.flush();

      while(true){
	int c=checkAck(in);
        if(c!='C'){
	  break;
	}

        // read '0644 '
        in.read(buf, 0, 5);

        long filesize=0L;
        while(true){
          if(in.read(buf, 0, 1)<0){
            // error
            return false; 
          }
          if(buf[0]==' ')break;
          filesize=filesize*10L+(buf[0]-'0');
        }

        String file=null;
        for(int i=0;;i++){
          in.read(buf, i, 1);
          if(buf[i]==(byte)0x0a){
            file=new String(buf, 0, i);
            break;
  	  }
        }

        // send '\0'
        buf[0]=0; out.write(buf, 0, 1); out.flush();

        // read a content of lfile
        fos=new FileOutputStream(prefix==null ? lfile : prefix+file);
        int foo;
        while(true){
          if(buf.length<filesize) foo=buf.length;
	  else foo=(int)filesize;
          foo=in.read(buf, 0, foo);
          if(foo<0){
            // error 
            return false;
          }
          fos.write(buf, 0, foo);
          filesize-=foo;
          if(filesize==0L) break;
        }
        fos.close();
        fos=null;

	if(checkAck(in)!=0){
	  return true;
	}

        // send '\0'
        buf[0]=0; out.write(buf, 0, 1); out.flush();
      }

      session.disconnect();

      return true;
    }
    catch(Exception e){
      try{if(fos!=null)fos.close();}catch(Exception ee){}
    }
	return true;
  }

  static int checkAck(InputStream in) throws IOException{
    int b=in.read();
    // b may be 0 for success,
    //          1 for error,
    //          2 for fatal error,
    //          -1
    if(b==0) return b;
    if(b==-1) return b;

    if(b==1 || b==2){
      StringBuffer sb=new StringBuffer();
      int c;
      do {
	c=in.read();
	sb.append((char)c);
      }
      while(c!='\n');
      if(b==1){ // error
	System.out.print(sb.toString());
      }
      if(b==2){ // fatal error
	System.out.print(sb.toString());
      }
    }
    return b;
  }

  }

