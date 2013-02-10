package org.hashfactory.model.schema;

import me.prettyprint.hector.api.Cluster;
import me.prettyprint.hector.api.Keyspace;
import me.prettyprint.hector.api.factory.HFactory;

public class ConnectionFactoryImpl implements ConnectionFactory {

	@Override
	public Cluster getCluster() {
		return HFactory.getOrCreateCluster(SchemaDefinition.CLUSTER_NAME,
				"localhost:9160");
	}

	private Keyspace ksp;

	@Override
	public Keyspace getKeyspace() {
		if (ksp == null) {
			ksp = HFactory.createKeyspace(SchemaDefinition.KEYSPACE_NAME,
					getCluster());
		}
		return ksp;
	}

}
