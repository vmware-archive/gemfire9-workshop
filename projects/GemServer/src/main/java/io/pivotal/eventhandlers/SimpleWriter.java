package io.pivotal.eventhandlers;

import io.pivotal.domain.Employee;

import java.util.Iterator;
import java.util.Properties;
import java.util.Set;

import org.apache.geode.cache.Declarable;
import org.apache.geode.cache.EntryEvent;
import org.apache.geode.cache.util.CacheWriterAdapter;
import org.apache.geode.internal.cache.EntryEventImpl;
import org.apache.geode.pdx.PdxInstance;
import org.apache.geode.pdx.WritablePdxInstance;

public class SimpleWriter<K,V> extends CacheWriterAdapter<K,V> implements Declarable {

	private String fromName = null;
	private String toName = null;
	
	/*
	 * (non-Javadoc)
	 * @see org.apache.geode.cache.util.CacheWriterAdapter#beforeCreate(org.apache.geode.cache.EntryEvent)
	 */
	@Override
	 public void beforeCreate(EntryEvent<K, V> event) {
		 /*
		  * update the name in fromName to toName when inserting a new record
		  */
		 EntryEventImpl eventImpl = (EntryEventImpl) event;
		 Object cachedEmployee = event.getNewValue();
		 
		 if (cachedEmployee instanceof PdxInstance) {
			 PdxInstance pdxPerson = (PdxInstance) cachedEmployee;			 
			 String name = (String) pdxPerson.getField("name");
			 
			 if (name != null && name.endsWith(fromName)) {
				 // Update a field and put it back into the cache
				 // without deserializing the entire object
				 // pretty amazing stuff that no other in-memory product does
				 WritablePdxInstance writablePdxPerson = pdxPerson.createWriter();
				 writablePdxPerson.setField("name", name.substring(0, name.indexOf(" ")+1) + toName);
				 eventImpl.setNewValue((PdxInstance) writablePdxPerson);
			 }
		 }
		 else if (cachedEmployee instanceof Employee) {
			 Employee employee = (Employee) event.getNewValue();
			 if (employee.getName().endsWith(fromName)) {
				 employee.replaceLastName(toName);
			 }
		 }
		 eventImpl.makeSerializedNewValue();
	 }
	
	@Override
	 public void beforeUpdate(EntryEvent<K, V> event) {
	}

	public void init(Properties props) {
		 Set<Object> propKeys = props.keySet();
		 Iterator<Object> propKeyIter = propKeys.iterator();
		 while (propKeyIter.hasNext()) {
			 String key = (String) propKeyIter.next();
			 if (key.equalsIgnoreCase("fromName")) {
				 setfromName(props.getProperty(key));
			 }
			 else if (key.equalsIgnoreCase("toName")) {
				 settoName(props.getProperty(key));
			 }
		 }
	}
	
	private void settoName(String toName) {
		this.toName = toName;
	}
	
	private void setfromName(String fromName) {
		this.fromName = fromName;
	}
}
