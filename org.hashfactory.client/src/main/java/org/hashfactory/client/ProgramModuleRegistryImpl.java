package org.hashfactory.client;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;

public class ProgramModuleRegistryImpl implements BeanPostProcessor,
		ProgramModuleRegistry {

	static Logger logger = LoggerFactory.getLogger(ProgramModuleRegistry.class);

	private Set<ProgramModule> modules = Collections
			.synchronizedSet(new HashSet<ProgramModule>());

	public Object postProcessAfterInitialization(Object pm, String name)
			throws BeansException {
		logger.info("examining " + name + " (" + pm + ")");
		if (pm == null)
			return null;
		if (pm instanceof ProgramModule) {
			modules.add((ProgramModule) pm);
		}
		return pm;
	}

	public Object postProcessBeforeInitialization(Object pm, String name)
			throws BeansException {
		logger.info("examining " + name + " (" + pm + ")");
		return pm;
	}

	public Set<ProgramModule> getModules() {
		return modules;
	}

	public void setModules(Set<ProgramModule> modules) {
		this.modules = modules;
	}

}
