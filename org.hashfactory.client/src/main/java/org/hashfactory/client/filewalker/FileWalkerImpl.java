package org.hashfactory.client.filewalker;

import java.io.File;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.FileChannel.MapMode;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

import net.java.truevfs.access.TFile;
import net.java.truevfs.access.TFileInputStream;

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
	public void walk() {
		walk(base);
	}

	private void walk(String path) {
		if (logger.isDebugEnabled()) {
			logger.debug("walking " + path);
		}
		TFile f = new TFile(path);
		if (f.canRead()) {
			if (f.isFile()) {
				try {
					int bufsize = 64 * 1024;
					//ByteBuffer buf = ByteBuffer.allocate(bufsize);
					FileDescr descr = new FileDescr();
					descr.setBaseName(f.getName());
					descr.setFullPath(f.getCanonicalPath());
					descr.setSize(f.length());
					descr.setMimeType("application/octet-stream");
					for (FileHandler h : handlers) {
						h.handleFileOpen(descr);
					}
					TFile rf = new TFile(f);
					TFileInputStream is = new TFileInputStream(rf);
					//FileChannel channel = rf.getChannel();
					//channel.map(MapMode.READ_ONLY, 0, f.length());
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

				} catch (Throwable e) {
					logger.error("error getting file descriptor for " + path, e);
				}
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
