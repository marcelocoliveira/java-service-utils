package com.utils.ioutils;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.Charset;

import org.springframework.util.FileCopyUtils;

public class FileCopyUtil extends FileCopyUtils {

	public static void appendHexArrayToFile(String messageType, short[] packet, String fileName) throws IOException {
		
		BufferedWriter out = null;
		try {
		    out = new BufferedWriter(new FileWriter(fileName, true));
		    out.write(messageType + " : ");
		    for (int i=0; i<packet.length; i++) {
		    	out.write(Integer.toHexString(packet[i]) + " ");
		    }
		    out.write("\n\n");
		} finally {
			if (out != null) {
				out.close();
			}
		}
		
	}

	public static void saveToFile(String content, String fileName) throws IOException {
		OutputStreamWriter osw = null;
		try {
			// using utf-16 to support international characters
			osw = new OutputStreamWriter(new FileOutputStream(fileName), 
					Charset.forName("UTF-16"));
			
		    osw.write(content);
		    osw.flush();
		    
		} finally {
			if (osw != null) {
				osw.close();
			}
		}
		
	}
}
