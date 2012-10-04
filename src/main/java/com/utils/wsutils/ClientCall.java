package com.utils.wsutils;

import org.apache.wink.client.ClientAuthenticationException;
import org.apache.wink.client.ClientRuntimeException;
import org.apache.wink.client.ClientWebException;
import org.apache.wink.client.EntityType;
import org.apache.wink.client.Resource;
import org.apache.wink.client.RestClient;
import org.apache.wink.client.ClientConfig;
import org.apache.wink.client.handlers.BasicAuthSecurityHandler;

public class ClientCall {

	
	public static String get (String URL) throws ServiceException {
		RestClient libraryClient = new RestClient(); 
        Resource editionResource = libraryClient.resource(URL); 
        editionResource.header("Accept", "application/json"); 
        try {
        String response =  editionResource.get(new EntityType<String>(){});
        return response;
        } catch (ClientWebException e){
			throw new ServiceException(e);
		} catch (ClientRuntimeException e){
			throw new ServiceException (e);
		}
	}
	
	public static String getWithHeader (String URL, String headerkey, String headervalue) throws ServiceException {
		RestClient libraryClient = new RestClient(); 
		Resource editionResource = libraryClient.resource(URL); 
        editionResource.header(headerkey, headervalue); 
        try {
        String response =  editionResource.get(new EntityType<String>(){});
        return response;
        } catch (ClientWebException e){
			throw new ServiceException(e);
		} catch (ClientRuntimeException e){
			throw new ServiceException (e);
		}
	}
	
	public static String post (String URL, String payload ) throws ServiceException {
		RestClient client = new RestClient(); 
		Resource resource = client.resource(URL);
		String response;
		try {
		  response = resource.contentType("application/json").accept("application/json").post(String.class, payload);
		} catch (ClientWebException e){
			throw new ServiceException(e);
		} catch (ClientRuntimeException e){
			throw new ServiceException (e);
		}
		return response;
	}
	
	public static String get (String URL, String username, String password) throws ServiceException {
		String response;
		 try {
		 ClientConfig config = new ClientConfig();
		 BasicAuthSecurityHandler basicAuthHandler = new BasicAuthSecurityHandler();
		 basicAuthHandler.setUserName(username);
		 basicAuthHandler.setPassword(password);
		 config.handlers(basicAuthHandler);
		 RestClient client = new RestClient(config);
		 Resource resource = client.resource(URL);
		 resource.header("Accept", "application/json"); 
	     response = resource.get(new EntityType<String>(){});
		 } catch (ClientAuthenticationException e){
			 throw new ServiceException(e);
		}  catch (ClientWebException e){
			throw new ServiceException(e);
		}  catch (ClientRuntimeException e){
			throw new ServiceException (e);
		}
		return response;
	}

		
}
