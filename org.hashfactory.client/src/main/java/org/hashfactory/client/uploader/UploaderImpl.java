package org.hashfactory.client.uploader;

import org.hashfactory.client.CmdLineOpts;
import org.hashfactory.client.scanner.Scanner;
import org.hashfactory.model.dao.HashEntryDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

public class UploaderImpl implements Uploader {

	@Autowired(required = true)
	HashEntryDao hed;

	static Logger logger = LoggerFactory.getLogger(Scanner.class);

	UploaderCmdLineOpts cmdLine;

	@Override
	public CmdLineOpts getCmdLineOpts() {
		return new UploaderCmdLineOpts();
	}

	@Override
	public void run(CmdLineOpts cmdLine) {
		this.cmdLine = (UploaderCmdLineOpts) cmdLine;		
		try {
			hed.connect();
			
			this.cmdLine.getFileSet();
			
		} catch (Throwable e) {
			e.printStackTrace();
		}
	}

}
