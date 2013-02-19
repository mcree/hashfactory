package org.hashfactory.client.uploader;

import org.hashfactory.client.CmdLineOpts;

import com.beust.jcommander.Parameter;

public class UploaderCmdLineOpts extends CmdLineOpts {

	@Parameter(names = "--fileSet", description = "fileSet file (created by scan --out)", required = true)
	private String fileSet;

	
	@Override
	public String getSelector() {
		return "upload";
	}


	public String getFileSet() {
		return fileSet;
	}


	public void setFileSet(String fileSet) {
		this.fileSet = fileSet;
	}

}
