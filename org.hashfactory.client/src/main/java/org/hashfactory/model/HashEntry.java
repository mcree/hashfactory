package org.hashfactory.model;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class HashEntry {

	public HashEntry(String hashType, String hashValue, String mime, Long size,
			String name, String path) {
		super();
		this.setHash(hashType, hashValue);
		this.mime = mime;
		this.size = size;
		this.names.add(name);
		this.paths.add(path);
		this.lastModify = new Date();
	}

	private String hash;
	private Set<String> fileSets = new HashSet<String>();
	private Date lastModify;
	private String mime;
	private Long size;
	private Set<String> names = new HashSet<String>();;
	private Set<String> paths = new HashSet<String>();;

	public String getHash() {
		return hash;
	}

	public void setHash(String hash) {
		this.hash = hash;
	}

	public void setHash(String algo, String value) {
		this.hash = "{" + algo + "}" + value;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((hash == null) ? 0 : hash.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		HashEntry other = (HashEntry) obj;
		if (hash == null) {
			if (other.hash != null)
				return false;
		} else if (!hash.equals(other.hash))
			return false;
		return true;
	}

	public Set<String> getFileSets() {
		return fileSets;
	}

	public void setFileSets(Set<String> hashSets) {
		this.fileSets = hashSets;
	}

	public Date getLastModify() {
		return lastModify;
	}

	public void setLastModify(Date lastModify) {
		this.lastModify = lastModify;
	}

	public String getMime() {
		return mime;
	}

	public void setMime(String mime) {
		this.mime = mime;
	}

	public Long getSize() {
		return size;
	}

	public void setSize(Long size) {
		this.size = size;
	}

	public Set<String> getNames() {
		return names;
	}

	public void setNames(Set<String> names) {
		this.names = names;
	}

	public Set<String> getPaths() {
		return paths;
	}

	public void setPaths(Set<String> paths) {
		this.paths = paths;
	}

}
