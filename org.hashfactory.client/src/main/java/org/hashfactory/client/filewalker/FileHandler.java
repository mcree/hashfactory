package org.hashfactory.client.filewalker;

public interface FileHandler {

	void handleFileOpen(FileDescr descr) throws Throwable;

	void handleFileData(FileDescr descr, byte[] data) throws Throwable;

	void handleFileClose(FileDescr descr) throws Throwable;

}
