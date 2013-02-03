package org.hashfactory.client.scanner;

import org.hashfactory.client.CmdLineOpts;
import org.hashfactory.client.ProgramModule;

public interface Scanner extends ProgramModule {

	CmdLineOpts getCmdLineOpts();

	void run(CmdLineOpts cmdLine);

}