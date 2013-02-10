package org.hashfactory.model.dao;

import org.hashfactory.model.schema.ConnectionFactory;
import org.springframework.beans.factory.annotation.Autowired;

public class HashEntryDaoImpl implements HashEntryDao {

	@Autowired(required=true)
	ConnectionFactory cf;
	
	@Override
	public void storeEntry(String hash, String hashSet) {
		
		cf.getCluster();
		
		
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("not implemented");
		//
	}

}
