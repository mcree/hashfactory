package org.hashfactory.client;

import java.security.NoSuchAlgorithmException;

import org.hashfactory.client.scanner.Scanner;
import org.hashfactory.client.scanner.ScannerImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ClientContextConfig {

	@Bean
	Scanner scanner() throws NoSuchAlgorithmException {
		return new ScannerImpl();
	}
	
	@Bean
	ProgramModuleRegistry programModuleRegistryImpl() {
		return new ProgramModuleRegistryImpl();
	}
	
}
