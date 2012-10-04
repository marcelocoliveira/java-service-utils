package com.utils.xml;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import org.apache.commons.io.FileUtils;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.converters.SingleValueConverter;
import com.thoughtworks.xstream.io.xml.DomDriver;
import com.utils.xml.AliasField.AliasInner;

public class XMLMapper {
	
	 private static XStream xstream = new XStream(new DomDriver());
	 private static Map<String, Class<?>> alias = new HashMap<String, Class<?>>();
	 private static AliasField aliasField = new AliasField();
	 
	 public static void setImplicitCollection (Class<?> clazz, String tag) {
		 xstream.addImplicitCollection(clazz, tag);
	 }
	 
	 public static void addAlias (String node, Class<?> clazz) {
		 alias.put(node, clazz);
	 }
	 
	 public static void addAliasField (String oldNode, Class<?> clazz, String newNode) {
		 aliasField.add(oldNode, clazz, newNode);
	 }
	 
	 public static void setAttributeFor (String node, Class<?> clazz){
		 xstream.useAttributeFor(clazz, node);
	 }
	 
	 public static void registerConverter (SingleValueConverter converter) {
		 xstream.registerConverter(converter);
	 }
	 
	 public static void toFile (Object object, File xml) throws XMLMapperException {
		 try {
			 	for (Map.Entry<String,Class<?>>  map : alias.entrySet())
					xstream.alias(map.getKey(), map.getValue());
			 	
			 	for (Map.Entry<String,AliasField.AliasInner>  map : aliasField.getMap().entrySet()) {
			 		AliasInner inner = map.getValue();
			 		xstream.aliasField(map.getKey(), inner.getClazz(), inner.getNewValue());
			 		}
			 	
			 	String xmlContent = xstream.toXML(object);
				 FileUtils.writeStringToFile(xml, xmlContent);
				} catch (Exception e){
					throw new XMLMapperException(e);
				}
	 }
	 
	 public static String toString (Object object) throws XMLMapperException {
		 try {
			 	for (Map.Entry<String,Class<?>>  map : alias.entrySet())
					xstream.alias(map.getKey(), map.getValue());
			 	
			 	 	for (Map.Entry<String,AliasField.AliasInner>  map : aliasField.getMap().entrySet()) {
				 		AliasInner inner = map.getValue();
				 		xstream.aliasField(map.getKey(), inner.getClazz(), inner.getNewValue());
					}
			 	 	
				 return xstream.toXML(object);
				} catch (Exception e){
					throw new XMLMapperException(e);
				}
	 }
	 
	public static Object toObject (String xmlContent) throws XMLMapperException {
		try {
		for (Map.Entry<String,Class<?>>  map : alias.entrySet())
			xstream.alias(map.getKey(), map.getValue());
		return  xstream.fromXML(xmlContent);
		} catch (Exception e){
			throw new XMLMapperException(e);
		}
		 
	 }
	
}
