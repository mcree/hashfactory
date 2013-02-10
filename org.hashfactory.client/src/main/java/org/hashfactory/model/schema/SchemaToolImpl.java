package org.hashfactory.model.schema;

import me.prettyprint.cassandra.serializers.StringSerializer;
import me.prettyprint.cassandra.service.ThriftKsDef;
import me.prettyprint.cassandra.service.template.ColumnFamilyResult;
import me.prettyprint.cassandra.service.template.ColumnFamilyTemplate;
import me.prettyprint.cassandra.service.template.ColumnFamilyUpdater;
import me.prettyprint.cassandra.service.template.ThriftColumnFamilyTemplate;
import me.prettyprint.hector.api.Keyspace;
import me.prettyprint.hector.api.ddl.ColumnFamilyDefinition;
import me.prettyprint.hector.api.ddl.ComparatorType;
import me.prettyprint.hector.api.ddl.KeyspaceDefinition;
import me.prettyprint.hector.api.factory.HFactory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

public class SchemaToolImpl implements SchemaTool {

	@Autowired(required = true)
	ConnectionFactory cf;

	@Override
	public void createOrUpgradeSchema() {

		KeyspaceDefinition keyspaceDef = cf.getCluster().describeKeyspace(
				SchemaDefinition.KEYSPACE_NAME);

		if (keyspaceDef == null) {
			createSchema();
		}

	}

	public Integer getVersion() {
		ColumnFamilyResult<String, String> res = versionTemplate()
				.queryColumns("version");
		return res.getInteger("version");
	}

	public void setVersion(Integer version) {
		logger.warn("setting schema version to " + version);
		ColumnFamilyUpdater<String, String> updater = versionTemplate()
				.createUpdater("version");
		updater.setInteger("version", version);
		versionTemplate().update(updater);
	}

	private ColumnFamilyTemplate<String, String> versionTemplate() {
		ColumnFamilyTemplate<String, String> template = new ThriftColumnFamilyTemplate<String, String>(
				cf.getKeyspace(), SchemaDefinition.VERSION_CF,
				StringSerializer.get(), StringSerializer.get());
		return template;
	}

	private void createKeyspace() {
		logger.warn("creating keyspace " + SchemaDefinition.KEYSPACE_NAME);

		KeyspaceDefinition newKeyspace = HFactory.createKeyspaceDefinition(
				SchemaDefinition.KEYSPACE_NAME, ThriftKsDef.DEF_STRATEGY_CLASS,
				2, null);

		cf.getCluster().addKeyspace(newKeyspace, true);
	}

	private void createHashEntryCf() {
		logger.warn("creating column familiy " + SchemaDefinition.HASHENTRY_CF);

		ColumnFamilyDefinition cfDef = HFactory.createColumnFamilyDefinition(
				SchemaDefinition.KEYSPACE_NAME, SchemaDefinition.HASHENTRY_CF,
				ComparatorType.BYTESTYPE);

		cf.getCluster().addColumnFamily(cfDef, true);
	}

	private void createVersionCf() {
		logger.warn("creating column familiy " + SchemaDefinition.VERSION_CF);

		ColumnFamilyDefinition cfDef = HFactory.createColumnFamilyDefinition(
				SchemaDefinition.KEYSPACE_NAME, SchemaDefinition.VERSION_CF,
				ComparatorType.BYTESTYPE);

		cf.getCluster().addColumnFamily(cfDef, true);
	}

	private Logger logger = LoggerFactory.getLogger(getClass());

	private void createSchema() {
		logger.warn("creating schema");
		createKeyspace();
		createHashEntryCf();
		createVersionCf();
		setVersion(1);
	}

}
