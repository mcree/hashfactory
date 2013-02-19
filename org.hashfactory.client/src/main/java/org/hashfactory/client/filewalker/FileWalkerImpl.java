package org.hashfactory.client.filewalker;

import java.io.IOException;
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
			if (f.isFile() || f.isArchive()) {
				FileDescr descr = getDescr(path);
				handleData(descr);
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

	private void handleData(FileDescr descr) throws Throwable, IOException {
		int bufsize = 640 * 1024; // ought to be enough ;-)
		for (FileHandler h : handlers) {
			h.handleFileOpen(descr);
		}
		TFile rf = new TFile(descr.getFullPath()).toNonArchiveFile();
		TFileInputStream is = new TFileInputStream(rf);
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
						mimeUtil.getMimeTypes(data, unknownMime)).toString());
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

	private FileDescr getDescr(String path) throws Throwable {

		TFile f = new TFile(path).toNonArchiveFile();
		FileDescr descr = new FileDescr();
		try {
			// ByteBuffer buf = ByteBuffer.allocate(bufsize);
			descr.setBaseName(f.getName());
			descr.setFullPath(f.getNormalizedAbsolutePath());
			descr.setRelPath(f.getNormalizedAbsolutePath().substring(base.length()+1));
			descr.setSize(f.length());
			descr.setMimeType("application/octet-stream");
		} catch (Throwable e) {
			logger.error("error getting file descriptor for " + path, e);
			throw e;
		}
		return descr;
	}

	@Override
	public void addFileHandler(FileHandler handler) {
		this.handlers.add(handler);
	}

	// base for file walking (eg start directory or url)
	String base;

	@Override
	public void setBase(String base) {
		this.base = new TFile(base).getNormalizedAbsolutePath();
	}

}
