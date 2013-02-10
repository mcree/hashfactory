package org.hashfactory.model;

public class HashEntry {

	/**
	 * @param hash
	 * @param hashSet
	 */
	private HashEntry(String hash, String hashSet) {
		super();
		this.hash = hash;
		this.hashSet = hashSet;
	}

	private String hash;
	private String hashSet;

	public String getHash() {
		return hash;
	}

	public void setHash(String hash) {
		this.hash = hash;
	}

	public String getHashSet() {
		return hashSet;
	}

	public void setHashSet(String hashSet) {
		this.hashSet = hashSet;
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

	@Override
	public String toString() {
		return "HashEntry [hash=" + hash + ", hashSet=" + hashSet + "]";
	}

}
