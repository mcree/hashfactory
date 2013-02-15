package org.hashfactory.client.filewalker;

public interface FileWalker {
	
	void addFileHandler(FileHandler handler);
	void setBase(String base);
	void walk() throws Throwable;
	
}
