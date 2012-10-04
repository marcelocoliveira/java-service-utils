package com.utils.xml;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.StringReader;
import org.exolab.castor.mapping.Mapping;
import org.exolab.castor.mapping.MappingException;
import org.exolab.castor.xml.MarshalException;
import org.exolab.castor.xml.Unmarshaller;
import org.exolab.castor.xml.ValidationException;
import org.xml.sax.InputSource;


public class XMLParser {

	
	public static Object toObject (String content, String xmlMapping) throws XMLMapperException {
		 try {	
		    Mapping mapping = new Mapping();
            mapping.loadMapping(xmlMapping );
            Unmarshaller unmar = new Unmarshaller(mapping);
           	return unmar.unmarshal(new InputSource(content));
			} catch (MarshalException e) {
				throw new XMLMapperException(e);
			} catch (ValidationException e) {
				throw new XMLMapperException(e);
			} catch (FileNotFoundException e) {
				throw new XMLMapperException(e);
			} catch (MappingException e) {
				throw new XMLMapperException(e);
			} catch (IOException e) {
				throw new XMLMapperException(e);
			}
	}
	
	public static Object toObject (Class<?> clazz, String content) throws XMLMapperException {
		 try {	
			 return Unmarshaller.unmarshal(clazz, new StringReader(content));
          	
			} catch (MarshalException e) {
				throw new XMLMapperException(e);
			} catch (ValidationException e) {
				throw new XMLMapperException(e);
			} 
	}
}
