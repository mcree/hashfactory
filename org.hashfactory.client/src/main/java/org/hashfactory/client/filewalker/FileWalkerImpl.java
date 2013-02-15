package org.hashfactory.client.filewalker;

import java.util.ArrayList;
import java.util.Arrays;

import net.java.truevfs.access.TFile;
import net.java.truevfs.access.TFileInputStream;
import net.java.truevfs.access.TVFS;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import eu.medsea.mimeutil.MimeType;
import eu.medsea.mimeutil.MimeUtil;
import eu.medsea.mimeutil.MimeUtil2;
import eu.medsea.mimeutil.detector.MagicMimeMimeDetector;

// TODO: upgrade to JAVA SE 7 NIO Files class usage
public class FileWalkerImpl implements FileWalker {

	private Logger logger = LoggerFactory.getLogger(getClass());

	private ArrayList<FileHandler> handlers = new ArrayList<FileHandler>();

	private MimeType unknownMime;

	private MimeUtil2 mimeUtil;

	public FileWalkerImpl() {
		unknownMime = new MimeType("application/octet-stream");
		mimeUtil = new MimeUtil2();
		mimeUtil.registerMimeDetector(MagicMimeMimeDetector.class.getName());
	}

	@Override
	public void walk() throws Throwable {
		walk(base);
		try {
			TVFS.umount();
		} catch (Throwable ignored) {
		}
	}

	private void walk(String path) throws Throwable {
		if (logger.isDebugEnabled()) {
			logger.debug("walking " + path);
		}
		TFile f = new TFile(path);
		if (f.canRead()) {
			if (f.isFile()) {
				int bufsize = 640 * 1024; // ought to be enough ;-)
				FileDescr descr = new FileDescr();
				try {
					// ByteBuffer buf = ByteBuffer.allocate(bufsize);
					descr.setBaseName(f.getName());
					descr.setFullPath(f.getCanonicalPath());
					descr.setSize(f.length());
					descr.setMimeType("application/octet-stream");
				} catch (Throwable e) {
					logger.error("error getting file descriptor for " + path, e);
					throw e;
				}
				for (FileHandler h : handlers) {
					h.handleFileOpen(descr);
				}
				TFile rf = new TFile(f);
				TFileInputStream is = new TFileInputStream(rf);
				// FileChannel channel = rf.getChannel();
				// channel.map(MapMode.READ_ONLY, 0, f.length());
				int block = 0;
				int read = 0;
				byte[] buf = new byte[bufsize];
				byte[] data;
				while ((read = is.read(buf)) > 0) {
					block++;

					if (read < bufsize) {
						data = Arrays.copyOf(buf, read);
					} else {
						data = buf;
					}

					if (block == 1) {
						descr.setMimeType(MimeUtil.getMostSpecificMimeType(
								mimeUtil.getMimeTypes(data, unknownMime))
								.toString());
					}

					for (FileHandler h : handlers) {
						h.handleFileData(descr, data);
					}
				}
				for (FileHandler h : handlers) {
					h.handleFileClose(descr);
				}
				is.close();

			}
			if (f.isDirectory() || f.isArchive()) {
				try {
					for (String e : f.list()) {
						if (logger.isTraceEnabled()) {
							logger.trace("entering " + path
									+ TFile.separatorChar + e);
						}
						walk(path + TFile.separatorChar + e);
					}
				} catch (Throwable e) {
					logger.error("error listing contents for " + path, e);
					throw e;
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
