package org.hashfactory.client;

import java.util.Set;

import org.springframework.beans.factory.config.BeanPostProcessor;

public interface ProgramModuleRegistry extends BeanPostProcessor {

	Set<ProgramModule> getModules();

}