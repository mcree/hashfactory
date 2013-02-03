package org.hashfactory.client.filewalker;

import java.util.ArrayList;

public class FileWalkerImpl implements FileWalker {

	private ArrayList<FileHandler> handlers = new ArrayList<FileHandler>();
	
	@Override
	public void walk() {
		
		
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
