package org.hashfactory.client.scanner;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;

import org.hashfactory.client.CmdLineOpts;
import org.hashfactory.client.ProgramModule;
import org.hashfactory.client.filewalker.FileDescr;
import org.hashfactory.client.filewalker.FileHandler;
import org.hashfactory.client.filewalker.FileWalker;
import org.hashfactory.client.filewalker.FileWalkerFactory;
import org.hashfactory.model.dao.HashEntryDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

public class ScannerImpl implements ProgramModule, Scanner {

	@Autowired(required=true)
	HashEntryDao edao;
	
	static Logger logger = LoggerFactory.getLogger(Scanner.class);

	ScannerCmdLineOpts cmdLine;

	public CmdLineOpts getCmdLineOpts() {
		return new ScannerCmdLineOpts();
	}

	DigestFactory df;

	public ScannerImpl() throws NoSuchAlgorithmException {
		df = new DigestFactory();
	}

	private HashMap<FileDescr, MessageDigest> md5Digests = new HashMap<FileDescr, MessageDigest>();
	private HashMap<FileDescr, MessageDigest> sha256Digests = new HashMap<FileDescr, MessageDigest>();

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
				sha256Digests.put(descr, df.createSha256Digest());
				md5Digests.put(descr, df.createMd5Digest());
			}

			@Override
			public void handleFileClose(FileDescr descr) {
				logger.info("handling close: " + descr);
				byte[] sha256 = sha256Digests.get(descr).digest();
				byte[] md5 = md5Digests.get(descr).digest();
				logger.info("sha256 "+DigestUtil.digest2Hex(sha256));
				logger.info("md5 "+DigestUtil.digest2Hex(md5));
			}

			@Override
			public void handleFileData(FileDescr descr, byte[] data) {
				logger.info("handling data: " + data.length + " bytes, "
						+ descr);
				sha256Digests.get(descr).update(data);
				md5Digests.get(descr).update(data);
			}
		});
		walker.walk();
	}

}
