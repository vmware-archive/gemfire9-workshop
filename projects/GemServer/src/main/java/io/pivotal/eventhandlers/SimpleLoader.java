package io.pivotal.eventhandlers;

import io.pivotal.domain.Person;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.util.Random;
import java.util.Set;

import org.apache.geode.cache.CacheLoader;
import org.apache.geode.cache.Declarable;
import org.apache.geode.cache.LoaderHelper;

public class SimpleLoader<K,V> implements CacheLoader<K,V>, Declarable {
	
	private String url = null;
	private List<String> firstNames = new ArrayList<String>();
	private List<String> lastNames = new ArrayList<String>();

    Random r = new Random(System.currentTimeMillis());

	/*
	 * The return value gets placed into the region and returned to the caller.
	 * Load your value from your persistent layer, RDBMS, HAWQ, etc
	 */
	 @SuppressWarnings("unchecked")
	public V load(LoaderHelper<K,V> helper) {
		 String key = (String) helper.getKey();
		 
		 if (helper.getRegion().getName().equalsIgnoreCase("people"))
			 return (V) generatePerson(Integer.parseInt(key));
		 else 
			 return (V) ("LoadedValue for " + key + " from " + this.url);
	 }
	 
	 public void close() { }
	 public void init(Properties props) {
		 Set<Object> propKeys = props.keySet();
		 Iterator<Object> propKeyIter = propKeys.iterator();
		 while (propKeyIter.hasNext()) {
			 String key = (String) propKeyIter.next();
			 if (key.equalsIgnoreCase("url")) {
				 setUrl(props.getProperty(key));
			 }
			 else if (key.equalsIgnoreCase("firstNames")) {
				 setFirstNames(props.getProperty(key));
			 }
			 else if (key.equalsIgnoreCase("lastNames")) {
				 setLastNames(props.getProperty(key));
			 }
		 }
	 }
	 
	 public void setUrl(String url) {
		 this.url = url;
	 }
		
	public void setFirstNames(String firstNames) {
		String[] parsedFirstNames = firstNames.split(",");
		for (int i=0; i<parsedFirstNames.length; i++) {
			this.firstNames.add(parsedFirstNames[i]);
		}
	}

	public void setLastNames(String lastNames) {
		String[] parsedLastNames = lastNames.split(",");
		for (int i=0; i<parsedLastNames.length; i++) {
			this.lastNames.add(parsedLastNames[i]);
		}
	}
	
    private Person generatePerson(int key) {
       	int firstNameIx = r.nextInt(firstNames.size());
    	int lastNameIx = r.nextInt(lastNames.size());
        return new Person(key, String.format("%s %s", firstNames.get(firstNameIx), lastNames.get(lastNameIx)));
    }

}