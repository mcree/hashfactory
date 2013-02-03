package org.hashfactory.client;

import com.beust.jcommander.Parameter;

public abstract class CmdLineOpts {
	
	@Parameter(names = "--help", help = true)
	private boolean help;

	public boolean isHelp() {
		return help;
	}

	public void setHelp(boolean help) {
		this.help = help;
	}
	
	abstract public String getSelector();
	
}