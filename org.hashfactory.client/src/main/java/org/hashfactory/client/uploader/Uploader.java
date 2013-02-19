package org.hashfactory.client.uploader;

import org.hashfactory.client.CmdLineOpts;
import org.hashfactory.client.ProgramModule;

public interface Uploader extends ProgramModule {

	CmdLineOpts getCmdLineOpts();

	void run(CmdLineOpts cmdLine);

}
