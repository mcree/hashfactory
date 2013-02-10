package org.hashfactory.client.filewalker;

import java.io.File;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.FileChannel.MapMode;
import java.util.ArrayList;
import java.util.Arrays;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

// TODO: upgrade to JAVA SE 7 NIO Files class usage
public class FileWalkerImpl implements FileWalker {

	private Logger logger = LoggerFactory.getLogger(getClass());

	private ArrayList<FileHandler> handlers = new ArrayList<FileHandler>();

	@Override
	public void walk() {
		walk(base);
	}

	private void walk(String path) {
		if (logger.isDebugEnabled()) {
			logger.debug("walking " + path);
		}
		File f = new File(path);
		if (f.canRead()) {
			if (f.isFile()) {
				try {
					int bufsize = 64*1024;
					ByteBuffer buf = ByteBuffer.allocate(bufsize);
					FileDescr descr = new FileDescr();
					descr.setBaseName(f.getName());
					descr.setFullPath(f.getCanonicalPath());
					descr.setSize(f.length());
					descr.setMimeType("application/octet-stream");
					for (FileHandler h : handlers) {
						h.handleFileOpen(descr);
					}
					FileChannel channel = new RandomAccessFile(f, "r")
							.getChannel();
					channel.map(MapMode.READ_ONLY, 0, f.length());
					while (channel.read(buf) > 0) {
						for (FileHandler h : handlers) {
							if (buf.position() < bufsize) {
								h.handleFileData(
										descr,
										Arrays.copyOf(buf.array(),
												buf.position()));
							} else {
								h.handleFileData(descr, buf.array());
							}
							buf.rewind();
						}
					}
					for (FileHandler h : handlers) {
						h.handleFileClose(descr);
					}

				} catch (Throwable e) {
					logger.error("error getting file descriptor for " + path, e);
				}
			}
			if (f.isDirectory()) {
				try {
					for (String e : f.list()) {
						if (logger.isTraceEnabled()) {
							logger.trace("entering " + path
									+ File.separatorChar + e);
						}
						walk(path + File.separatorChar + e);
					}
				} catch (Throwable e) {
					logger.error("error listing contents for " + path, e);
				}
			}
		} else {
			logger.error("cannot read " + path);
		}
	}

	@Override
	public void addFileHandler(FileHandler handler) {
		this.handlers.add(handler);
	}

	// base for file walking (eg start directory or url)
	String base;

	@Override
	public void setBase(String base) {
		this.base = base;
	}

}
