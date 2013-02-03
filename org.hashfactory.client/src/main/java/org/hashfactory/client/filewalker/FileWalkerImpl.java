package org.hashfactory.client.filewalker;

import java.io.File;
import java.util.ArrayList;

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
				for (FileHandler h : handlers) {
					try {
						FileDescr descr = new FileDescr();
						descr.setBaseName(f.getName());
						descr.setFullPath(f.getCanonicalPath());
						descr.setSize(f.length());
						descr.setMimeType("application/octet-stream");
						h.handleFileOpen(descr);
					} catch (Throwable e) {
						logger.error("error getting file descriptor for "
								+ path, e);
					}
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
