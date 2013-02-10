package org.hashfactory.model.schema;

import me.prettyprint.hector.api.Cluster;
import me.prettyprint.hector.api.Keyspace;

public interface ConnectionFactory {
	
	Cluster getCluster();

	Keyspace getKeyspace();

}
