package org.hashfactory.client.filewalker;

public class FileDescr {

	private String baseName;
	
	private String fullPath;
	
	private String mimeType;
	
	private Long size;

	public String getBaseName() {
		return baseName;
	}

	public void setBaseName(String baseName) {
		this.baseName = baseName;
	}

	public String getFullPath() {
		return fullPath;
	}

	public void setFullPath(String fullPath) {
		this.fullPath = fullPath;
	}

	public String getMimeType() {
		return mimeType;
	}

	public void setMimeType(String mimeType) {
		this.mimeType = mimeType;
	}

	public Long getSize() {
		return size;
	}

	public void setSize(Long size) {
		this.size = size;
	}

	@Override
	public String toString() {
		return "FileDescr [baseName=" + baseName + ", fullPath=" + fullPath
				+ ", mimeType=" + mimeType + ", size=" + size + "]";
	}
	
}
