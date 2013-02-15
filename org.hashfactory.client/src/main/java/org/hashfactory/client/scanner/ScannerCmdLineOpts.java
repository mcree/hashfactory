package org.hashfactory.client.scanner;

import org.hashfactory.client.CmdLineOpts;

import com.beust.jcommander.Parameter;

public class ScannerCmdLineOpts extends CmdLineOpts {

	@Parameter(names = "--dir", description = "base directory", required = true)
	private String dir;

	@Parameter(names = "--out", description = "output file", required = true)
	private String out;

	@Parameter(names = "--overwrite", description = "overwrite output file")
	private Boolean overwrite = false;

	public String getDir() {
		return dir;
	}

	public void setDir(String dir) {
		this.dir = dir;
	}

	@Override
	public String getSelector() {
		return "scan";
	}

	public String getOut() {
		return out;
	}

	public void setOut(String out) {
		this.out = out;
	}

	public Boolean getOverwrite() {
		return overwrite;
	}

	public void setOverwrite(Boolean overwrite) {
		this.overwrite = overwrite;
	}

}