package com.utils.ssh;

import java.awt.GridBagConstraints;
import java.awt.Insets;

import javax.swing.JOptionPane;

import com.jcraft.jsch.UIKeyboardInteractive;
import com.jcraft.jsch.UserInfo;

public class MyUserInfo implements UserInfo, UIKeyboardInteractive{
    String pwd;
    String passwd;
	public MyUserInfo(String pwd){
		this.pwd = pwd;
	}
	
 public String getPassword(){ return pwd; }
  
  public boolean promptYesNo(String str){
     return true;
  }

  
 
  public String getPassphrase(){ return null; }
  public boolean promptPassphrase(String message){ return true; }
  public boolean promptPassword(String message){
      passwd=pwd;
      return true;
    }
  public void showMessage(String message){
    JOptionPane.showMessageDialog(null, message);
  }
  final GridBagConstraints gbc = 
    new GridBagConstraints(0,0,1,1,1,1,
                           GridBagConstraints.NORTHWEST,
                           GridBagConstraints.NONE,
                           new Insets(0,0,0,0),0,0);
  public String[] promptKeyboardInteractive(String destination,
                                            String name,
                                            String instruction,
                                            String[] prompt,
                                            boolean[] echo){
      String[] response=new String[1];
      
        response[0]=pwd;
        return response;
    
  }



}


