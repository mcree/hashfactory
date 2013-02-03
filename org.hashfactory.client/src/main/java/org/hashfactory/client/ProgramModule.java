package org.hashfactory.client;

public interface ProgramModule {

	CmdLineOpts getCmdLineOpts();
	void run(CmdLineOpts cmdLine);
	
}
