package com.utils.ssh;
	
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.UserInfo;

public class ScpTo{
	
	 private String host;
	 private String user;
	 private String password;

	 
	public ScpTo (ServerInfo server) {
		host = server.getHostname();
		user = server.getUsername();
		password = server.getPassword();
}
	
		
	public  boolean copy (String lfile, String rfile) throws IOException{
	FileInputStream fis=null;
    try{
    	
      JSch jsch=new JSch();
      Session session=jsch.getSession(user, host, 22);
      session.setPassword(password);
      
      UserInfo ui= new MyUserInfo(password);
      session.setUserInfo(ui);
      session.connect();

      String command="scp -p -t "+rfile;
      Channel channel=session.openChannel("exec");
      ((ChannelExec)channel).setCommand(command);

      OutputStream out=channel.getOutputStream();
      InputStream in=channel.getInputStream();

      channel.connect();

      if(checkAck(in)!=0){
		return true;
      }
     
      long filesize=(new File(lfile)).length();
      command="C0644 "+filesize+" ";
      if(lfile.lastIndexOf('/')>0){
        command+=lfile.substring(lfile.lastIndexOf('/')+1);
      }
      else{
        command+=lfile;
      }
      command+="\n";
      out.write(command.getBytes()); out.flush();
      if(checkAck(in)!=0){
    	  return false;
      }

      
      fis=new FileInputStream(lfile);
      byte[] buf=new byte[1024];
      while(true){
        int len=fis.read(buf, 0, buf.length);
	if(len<=0) break;
        out.write(buf, 0, len); //out.flush();
      }
      fis.close();
      fis=null;
      // send '\0'
      buf[0]=0; out.write(buf, 0, 1); out.flush();
      if(checkAck(in)!=0){
    	  return false;
      }
      out.close();

      channel.disconnect();
      session.disconnect();

      return true;
    }
    catch(Exception e){
      return false;
    }
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

