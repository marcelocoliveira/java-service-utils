package com.utils.ssh;

/************************************************************************************
# CLASS NAME:  StreamGobbler.java
# PURPOSE:  Serves as trap of cmd output
# AUTHOR: Marcelo Oliveira
# DATE WRITTEN: May 08 2009
*******************************************************************************************/

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

class StreamGobbler extends Thread
{
    InputStream is;
    String type;
    StreamGobbler(InputStream is)
    {
        this.is = is;
    }
    
 
	public void run()
    {
        try
        {
            InputStreamReader isr = new InputStreamReader(is);
            BufferedReader br = new BufferedReader(isr);
            String line=null;
            while ( (line = br.readLine()) != null){
             }
            } catch (IOException ioe)
              {
            	
              }
    }
}
