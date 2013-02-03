package org.hashfactory.client.scanner;


import org.hashfactory.client.CmdLineOpts;
import org.hashfactory.client.ProgramModule;
import org.hashfactory.client.filewalker.FileDescr;
import org.hashfactory.client.filewalker.FileHandler;
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

		FileWalker walker = FileWalkerFactory.getInstance().createFileWalker();
		walker.setBase(dir);
		walker.addFileHandler(new FileHandler() {

			@Override
			public void handleFileOpen(FileDescr descr) {
				logger.info("handling open: " + descr);
			}

			@Override
			public void handleFileClose(FileDescr descr) {
				logger.info("handling close: " + descr);
			}

			@Override
			public void handleFileData(FileDescr descr, byte[] data) {
				logger.info("handling data: " + descr);
			}
		});
		walker.walk();
	}

}
