package org.hashfactory.client.filewalker;

public interface FileHandler {

	void handleFileOpen(FileDescr descr);

	void handleFileData(FileDescr descr, byte[] data);

	void handleFileClose(FileDescr descr);

}
