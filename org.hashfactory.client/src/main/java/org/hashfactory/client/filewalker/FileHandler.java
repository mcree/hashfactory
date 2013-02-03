package org.hashfactory.client.filewalker;

import java.nio.channels.ReadableByteChannel;

public interface FileHandler {

	void handleFile(FileDescr descr, ReadableByteChannel data);

}
