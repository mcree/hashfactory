package org.hashfactory.client.scanner;

import org.hashfactory.client.CmdLineOpts;

import com.beust.jcommander.Parameter;

public class ScannerCmdLineOpts extends CmdLineOpts {

	@Parameter(names = "--dir", description = "base directory", required = true)
	private String dir;

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

}