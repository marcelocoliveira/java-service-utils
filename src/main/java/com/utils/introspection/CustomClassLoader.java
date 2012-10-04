package com.utils.introspection;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarInputStream;
import org.codehaus.plexus.classworlds.ClassWorld;
import org.codehaus.plexus.classworlds.realm.ClassRealm;
import org.codehaus.plexus.classworlds.realm.DuplicateRealmException;

import com.utils.logging.MyLogger;


public class CustomClassLoader  {

	private MyLogger logger = new MyLogger(CustomClassLoader.class);
	
	
	@SuppressWarnings("deprecation")
	public List<Class<?>> getClasses(String[] clazzPath, List<String> directories) throws MalformedURLException, ClassNotFoundException{
		
		URL[] urls = new URL[directories.size()];
		for (int i=0; i < directories.size(); i++) {
		File file = new File(directories.get(i));
			URL url = file.toURL();  
	        urls[i] = url;
		}
	        ClassLoader loader = new URLClassLoader(urls);
	        List<Class<?>> clazzList = new ArrayList<Class<?>>();
	        for (String clazz :clazzPath) {
	        if (clazz.toLowerCase().contains("jar")) continue;
	        	clazzList.add(loader.loadClass(clazz));
	        }
	        
	        return clazzList; 
	}
	
	public ClassRealm loadJar (String jarPath) throws DuplicateRealmException, MalformedURLException {
		logger.logInfo("Loading jar ." +jarPath);
		ClassWorld world = new ClassWorld();
		ClassRealm containerRealm    = world.newRealm( "container" );
		containerRealm.addURL(new URL(jarPath));
		return containerRealm;
	}
	
	public List<String> getClasseNamesInPackage (URL jarURL, String[] packages) throws IOException{
	  logger.logInfo("Listing classes from  jar: " +jarURL.getPath());
	  List<String> classes = new ArrayList<String> ();
	  
	   JarInputStream jarFile = new JarInputStream(jarURL.openStream());
	   JarEntry jarEntry;
	    while(true) {
	      jarEntry=jarFile.getNextJarEntry ();
	      if(jarEntry == null) break;
	      String clazzName = jarEntry.getName ();
	      logger.logInfo("jar entry: "+clazzName);
	      for (String packageName : packages){
	   	   logger.logInfo("Searching classes in package: "+packageName);  
	   	   if((clazzName.startsWith (packageName)) &&  (clazzName.endsWith (".class")) ) {
	    	  clazzName = clazzName.replaceAll("/", "\\.");
	    	  clazzName = clazzName.replaceAll(".class", "");
	    	  logger.logInfo("## Found class: "+clazzName);
	    	  classes.add (clazzName);
	       }
	      }
	    }
	   return classes;
	}
}