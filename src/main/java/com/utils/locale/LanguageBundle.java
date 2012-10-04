package com.utils.locale;

import java.util.ResourceBundle;

public class LanguageBundle {

	public static ResourceBundle load (String resource) {
		return  ResourceBundle.getBundle(resource);
	}
	
}
