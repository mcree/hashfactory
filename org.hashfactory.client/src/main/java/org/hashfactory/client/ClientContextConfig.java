package org.hashfactory.client;

import java.security.NoSuchAlgorithmException;

import me.prettyprint.hector.api.Cluster;

import org.hashfactory.client.scanner.Scanner;
import org.hashfactory.client.scanner.ScannerImpl;
import org.hashfactory.client.uploader.Uploader;
import org.hashfactory.client.uploader.UploaderImpl;
import org.hashfactory.model.HashEntry;
import org.hashfactory.model.dao.HashEntryDao;
import org.hashfactory.model.dao.HashEntryDaoImpl;
import org.hashfactory.model.schema.ConnectionFactory;
import org.hashfactory.model.schema.ConnectionFactoryImpl;
import org.hashfactory.model.schema.SchemaTool;
import org.hashfactory.model.schema.SchemaToolImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ClientContextConfig {

	@Bean
	Scanner scanner() throws NoSuchAlgorithmException {
		return new ScannerImpl();
	}
	
	@Bean
	Uploader uploader() {
		return new UploaderImpl();
	}

	@Bean
	ProgramModuleRegistry programModuleRegistryImpl() {
		return new ProgramModuleRegistryImpl();
	}

	@Bean
	ConnectionFactory connectionFactory() {
		return new ConnectionFactoryImpl();
	}

	@Bean
	SchemaTool schemaTool() {
		return new SchemaToolImpl();
	}

	@Bean HashEntryDao hashEntryDao() {
		return new HashEntryDaoImpl();
	}
	
	
}
