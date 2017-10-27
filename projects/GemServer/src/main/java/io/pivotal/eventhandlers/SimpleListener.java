package io.pivotal.eventhandlers;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Properties;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import org.apache.geode.cache.Declarable;
import org.apache.geode.cache.EntryEvent;
import org.apache.geode.cache.RegionEvent;
import org.apache.geode.cache.util.CacheListenerAdapter;

public class SimpleListener<K,V> extends CacheListenerAdapter<K,V> implements Declarable {

	private PrintWriter out = null;
	public SimpleListener() {
		try {
			out = new PrintWriter(new BufferedWriter(new FileWriter("listener.log", true)));
		} catch (IOException e) {
		}

	}
	private static Logger log = LogManager.getLogger(SimpleListener.class);

	/*
	 * This method is called asynchronously after an insert.
	 * If the node fails while here, delivery is not guaranteed. 
	 * Good for dashboarding. Use this as a Spring XD source.
	 * For guaranteed delivery use the AsyncCacheListener 
	 *
	 */
	  @Override
	  public void afterCreate(EntryEvent<K,V> e) {
		  String line = " Received afterCreate event for entry: " +
			      e.getKey() + ", " + e.getNewValue();
	    writeToFile(line);
	  }
	  
	  @Override
	  public void afterUpdate(EntryEvent<K,V> e) {
		  String line = "    Received afterUpdate event for entry: " +
	      e.getKey() + ", " + e.getNewValue();
	    writeToFile(line);
	  }
	  
	  @Override
	  public void afterDestroy(EntryEvent<K,V> e) {
	    System.out.println("    Received afterDestroy event for entry: " +
	      e.getKey());
	  }

	  @Override
	  public void afterInvalidate(EntryEvent<K,V> e) {
	    System.out.println("    Received afterInvalidate event for entry: " +
	      e.getKey());
	  }

	  @Override
	  public void afterRegionLive(RegionEvent<K, V> e) {
		  log.info("    Received afterRegionLive event, sent to durable clients after \nthe server has finished replaying stored events.  ");
	  }

	public void init(Properties props) {
	}
	
	private void writeToFile(String line) {
		out.write(line);
	}
	
	public void close() {
		out.close();
	}
}