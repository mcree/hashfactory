package org.hashfactory.client.scanner;

import java.io.File;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;

import javax.xml.stream.XMLStreamException;

import org.hashfactory.client.CmdLineOpts;
import org.hashfactory.client.ProgramModule;
import org.hashfactory.client.filewalker.FileDescr;
import org.hashfactory.client.filewalker.FileHandler;
import org.hashfactory.client.filewalker.FileWalker;
import org.hashfactory.client.filewalker.FileWalkerFactory;
import org.hashfactory.model.HashEntry;
import org.hashfactory.model.dao.HashEntryDao;
import org.hashfactory.model.dao.HashFileWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

public class ScannerImpl implements ProgramModule, Scanner {

	@Autowired(required = true)
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

		logger.trace("scanning: " + dir);

		try {
			File outFile = new File(this.cmdLine.getOut());
			if (outFile.exists() && this.cmdLine.getOverwrite()) {
				outFile.delete();
			}

			final HashFileWriter writer = new HashFileWriter(
					this.cmdLine.getOut());

			FileWalker walker = FileWalkerFactory.getInstance()
					.createFileWalker();
			walker.setBase(dir);
			walker.addFileHandler(new FileHandler() {

				@Override
				public void handleFileOpen(FileDescr descr) {
					logger.trace("handling open: " + descr);
					sha256Digests.put(descr, df.createSha256Digest());
					md5Digests.put(descr, df.createMd5Digest());
				}

				@Override
				public void handleFileClose(FileDescr descr) throws Throwable {
					logger.trace("handling close: " + descr);

					{
						String sha256 = DigestUtil.digest2Hex(sha256Digests
								.get(descr).digest());
						sha256Digests.remove(descr);
						writer.write(new HashEntry("SHA256", sha256, descr
								.getMimeType(), descr.getSize(), descr
								.getBaseName(), descr.getFullPath()));
					}

					{
						String md5 = DigestUtil.digest2Hex(md5Digests
								.get(descr).digest());
						md5Digests.remove(descr);
						writer.write(new HashEntry("MD5", md5, descr
								.getMimeType(), descr.getSize(), descr
								.getBaseName(), descr.getFullPath()));
					}
				}

				@Override
				public void handleFileData(FileDescr descr, byte[] data) {
					logger.trace("handling data: " + data.length + " bytes, "
							+ descr);
					sha256Digests.get(descr).update(data);
					md5Digests.get(descr).update(data);
				}
			});
			walker.walk();
			writer.close();
		} catch (Throwable e) {
			logger.error("failure while scanning files", e);
		}
	}

}
