package org.hashfactory.client.filewalker;

public class FileWalkerFactory {

	public static FileWalker createFileWalker() {
		return new FileWalkerImpl();
	}
	
}
