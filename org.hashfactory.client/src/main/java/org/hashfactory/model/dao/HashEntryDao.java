package org.hashfactory.model.dao;

public interface HashEntryDao {

	void connect() throws Throwable;
	
	void storeEntry(String hash, String hashSet);

}
