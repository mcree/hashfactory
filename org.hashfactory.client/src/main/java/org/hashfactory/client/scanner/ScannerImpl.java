package org.hashfactory.client.scanner;

import org.hashfactory.client.CmdLineOpts;
import org.hashfactory.client.ProgramModule;
import org.hashfactory.client.filewalker.FileWalker;
import org.hashfactory.client.filewalker.FileWalkerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ScannerImpl implements ProgramModule, Scanner {

	static Logger logger = LoggerFactory.getLogger(Scanner.class);

	ScannerCmdLineOpts cmdLine;

	public CmdLineOpts getCmdLineOpts() {
		return new ScannerCmdLineOpts();
	}

	public void run(CmdLineOpts cmdLine) {

		this.cmdLine = (ScannerCmdLineOpts) cmdLine;

		String dir = this.cmdLine.getDir();

		logger.info("scanning: " + dir);

		FileWalker walker = FileWalkerFactory.createFileWalker();
		
		walker.walk();
	}

}
