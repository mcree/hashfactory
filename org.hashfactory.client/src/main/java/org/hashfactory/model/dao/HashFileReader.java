package org.hashfactory.model.dao;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.text.ParseException;
import java.util.Date;
import java.util.zip.GZIPInputStream;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;

import org.codehaus.staxmate.SMInputFactory;
import org.codehaus.staxmate.in.SMHierarchicCursor;
import org.codehaus.staxmate.in.SMInputCursor;

public class HashFileReader {

	Reader in;
	private String version;
	private Date timestamp;
	private SMInputCursor ccur;
	private SMHierarchicCursor rcur;

	public HashFileReader(String fileName) throws XMLStreamException,
			IOException, ParseException {
		File file = new File(fileName);
		if (file.exists()) {
			throw new IOException(fileName
					+ ": file exists - refusing overwrite");
		}
		XMLInputFactory factory = XMLInputFactory.newInstance();
		SMInputFactory inf = new SMInputFactory(factory);
		in = new InputStreamReader(new GZIPInputStream(
				new FileInputStream(file)), "UTF-8");
		rcur = inf.rootElementCursor(in);
		rcur.advance(); // first entry
		version = rcur.getAttrValue("version");
		timestamp = HashFileWriter.getDateFormat().parse(
				rcur.getAttrValue("timestamp"));
		ccur = rcur.childElementCursor("entry");
	}

	public void read() throws XMLStreamException {
		ccur.advance();
		// TODO: implement...
	}

	public void close() throws XMLStreamException {
		rcur.getStreamReader().closeCompletely();
	}

	public String getVersion() {
		return version;
	}

	public Date getTimestamp() {
		return timestamp;
	}

}
