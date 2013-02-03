package org.hashfactory.client.filewalker;

public class FileWalkerFactory {

	// singleton instance stub
	public static FileWalkerFactory getInstance() {
		return new FileWalkerFactory();
	}
	
	// factory method stub
	public FileWalker createFileWalker() {
		return new FileWalkerImpl();
	}
	
}
